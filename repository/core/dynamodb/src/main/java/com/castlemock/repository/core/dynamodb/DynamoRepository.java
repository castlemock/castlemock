package com.castlemock.repository.core.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.repository.Repository;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.dozer.DozerBeanMapper;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * The abstract repository provides functionality to interact with the dynamodb in order to manage a specific type.
 * The abstract repository is responsible for retrieving and managing instances for a specific type. All the communication
 * that involves interacting with dynamodb, such as saving and loading instances, are done through the {@link DynamoDBMapper}.
 *
 * @param <T> The type that is being managed by dynamodb.
 * @param <I> The id is used as an identifier for the type.
 * @author Tiago Santos
 * @see Saveable
 * @since 1.51
 */
@org.springframework.stereotype.Repository
public abstract class DynamoRepository<T extends Saveable<I>, D, I extends Serializable> implements Repository<D, I> {

    protected DynamoDBMapper dynamoDBMapper;

    protected DynamoDBMapperConfig dynamoDBMapperConfig;

    protected AmazonDynamoDB amazonDynamoDB;

    protected DozerBeanMapper mapper;

    protected final Class<T> entityClass;

    protected final Class<D> dtoClass;

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamoRepository.class);

     /**
     * The default constructor for the AbstractRepositoryImpl class. The constructor will extract class instances of the
     * generic types (TYPE and ID). These instances could later be used to identify the types for when interacting
     * with dynamodb.
     */
    @SuppressWarnings("unchecked")
    public DynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB,
                            DynamoDBMapperConfig dynamoDBMapperConfig) {
        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.dtoClass = (Class<D>) genericSuperclass.getActualTypeArguments()[1];
        this.mapper = mapper;
        this.amazonDynamoDB = amazonDynamoDB;
        this.dynamoDBMapperConfig = dynamoDBMapperConfig;
        this.dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB, dynamoDBMapperConfig);
    }

    public DynamoRepository(DozerBeanMapper mapper, AmazonDynamoDB amazonDynamoDB) {
        this(mapper, amazonDynamoDB, null);
    }

    /**
     * The initialize method is responsible for initiating the repository.
     * has empty implementation by default.
     *
     * @see #postInitiate()
     */
    @Override
    public void initialize() {
        createTableIfNotExists();
        postInitiate();
    }

    protected void createTableIfNotExists() {
        CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(entityClass)
                .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);
    }

    /**
     * The method provides the functionality to find a specific instance that matches the provided id
     *
     * @param id The id that an instance has to match in order to be retrieved
     * @return Returns an instance that matches the provided id
     */
    @Override
    public D findOne(final I id) {
        Preconditions.checkNotNull(id, "The provided id cannot be null");
        LOGGER.debug("Retrieving " + entityClass.getSimpleName() + " with id " + id);
        Optional<T> type = Optional.ofNullable(dynamoDBMapper.load(entityClass, id));
        return type.map(this::mapToDTO).orElse(null);
    }

    /**
     * Retrieves a list of all the instances of the specific type
     *
     * @return A list that contains all the instances of the type that is managed by the operation
     */
    @Override
    public List<D> findAll() {
        LOGGER.debug("Retrieving all instances for " + entityClass.getSimpleName());
        return toDtoList(dynamoDBMapper.scan(entityClass, new DynamoDBScanExpression()));
    }

    /**
     * The save method provides the functionality to save an instance to dynamodb.
     *
     * @param dto The type that will be saved to dynamodb.
     * @return The type that was saved to dynamodb. The main reason for it is being returned is because
     * there could be modifications of the object during the save process. For example, if the type does not
     * have an identifier, then the method will generate a new identifier for the type.
     */
    @Override
    public D save(final D dto) {
        T type = mapToEntity(dto);
        checkType(type);
        dynamoDBMapper.save(type);
        return mapToDTO(type);
    }

    protected T mapToEntity(final D dto) {
        return mapper.map(dto, entityClass);
    }

    protected D mapToDTO(final T entity) {
        return mapper.map(entity, dtoClass);
    }

    /**
     * Updates an instance that matches the provided id.
     *
     * @param id   The id of the instance that will be updated.
     * @param type The updated version that will replace the old one.
     * @return A copy of the replaced value.
     */
    @Override
    public D update(I id, D type) {
        return save(type);
    }

    /**
     * Count all the stored entities for the repository
     *
     * @return The count of entities
     */
    @Override
    public Integer count() {
        return dynamoDBMapper.count(entityClass, new DynamoDBScanExpression());
    }

    /**
     * Delete an instance that match the provided id
     *
     * @param id The instance that matches the provided id will be deleted in the database
     */
    @Override
    public D delete(final I id) {
        Preconditions.checkNotNull(id, "The provided id cannot be null");
        LOGGER.debug("Start the deletion of " + entityClass.getSimpleName() + " with id " + id);
        Optional<T> optionalType = Optional.ofNullable(dynamoDBMapper.load(entityClass, id));
        optionalType.ifPresent(x -> dynamoDBMapper.delete(x));
        LOGGER.debug("Deletion of " + entityClass.getSimpleName() + " with id " + id + " was successfully completed");
        return mapToDTO(optionalType.orElse(null));
    }


    /**
     * @throws UnsupportedOperationException not supported.
     */
    @Override
    public String exportOne(final I id) {
        throw new UnsupportedOperationException("exportOne method is not supported in the Dynamo repository");
    }

    /**
     * @throws UnsupportedOperationException not supported.
     */
    @Override
    public D importOne(final String raw) {
        throw new UnsupportedOperationException("importOne method is not supported in the Dynamo repository");
    }

    /**
     * Checks if the provided <code>id</code> already exists.
     *
     * @param id The id that will be checked if it is already being used.
     * @return <code>true</code> if the id exists. <code>false</code> otherwise.
     */
    public boolean exists(I id) {
        return Optional.ofNullable(dynamoDBMapper.load(entityClass, id)).isPresent();
    }

    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful. The method does not contain any functionality and the
     * whole idea is the it should be overridden by subclasses, but only if certain functionality is required to
     * run after the {@link #initialize} method has completed.
     *
     * @see #initialize
     */
    protected void postInitiate() {
        LOGGER.debug("Post initialize method not implemented for " + entityClass.getSimpleName());
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to dynamodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to dynamodb, but also loaded from dynamodb
     * upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             dynamodb.
     * @see #save
     */
    protected abstract void checkType(T type);

    /**
     * The method provides the functionality to convert a Collection of TYPE instances into a list of DTO instances
     *
     * @param types The collection that will be converted into a list of DTO
     * @return The provided collection but converted into the DTO class
     */
    protected List<D> toDtoList(final Iterable<T> types) {
        return StreamSupport.stream(types.spliterator(), false)
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    protected DynamoDBScanExpression getAttributeScan(
            String attributeName, String value, ComparisonOperator comparisonOperator
    ) {
        return new DynamoDBScanExpression().withFilterConditionEntry(attributeName,
                new Condition().withComparisonOperator(comparisonOperator)
                        .withAttributeValueList(new AttributeValue(value)));
    }

    //@Document(collection = "httpHeader")
    @DynamoDBDocument
    @Getter
    @Setter
    public static class HttpHeaderDocument {

        @DynamoDBAttribute(attributeName = "name")
        @Mapping("name")
        private String name;
        @DynamoDBAttribute(attributeName = "value")
        @Mapping("value")
        private String value;
    }

    //@Document(collection = "httpParameter")
    @DynamoDBDocument
    @Getter
    @Setter
    public static class HttpParameterDocument {

        @DynamoDBAttribute(attributeName = "name")
        @Mapping("name")
        private String name;
        @DynamoDBAttribute(attributeName = "value")
        @Mapping("value")
        private String value;
    }
}