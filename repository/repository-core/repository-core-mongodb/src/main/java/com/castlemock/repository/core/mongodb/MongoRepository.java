package com.castlemock.repository.core.mongodb;

import com.castlemock.model.core.model.Saveable;
import com.castlemock.model.core.model.SearchQuery;
import com.castlemock.repository.Repository;
import com.google.common.base.Preconditions;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * The abstract repository provides functionality to interact with the mongodb in order to manage a specific type.
 * The abstract repository is responsible for retrieving and managing instances for a specific type. All the communication
 * that involves interacting with mongodb, such as saving and loading instances, are done through the {@link MongoOperations}.
 *
 * @param <T> The type that is being managed by mongodb.
 * @param <I> The id is used as an identifier for the type.
 * @author Mohammad hewedy
 * @see Saveable
 * @since 1.35
 */
@org.springframework.stereotype.Repository
public abstract class MongoRepository<T extends Saveable<I>, D, I extends Serializable> implements Repository<D, I> {

    @Autowired
    protected DozerBeanMapper mapper;
    @Autowired
    protected MongoOperations mongoOperations;

    private final Class<T> entityClass;

    private final Class<D> dtoClass;

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoRepository.class);

    /**
     * The default constructor for the AbstractRepositoryImpl class. The constructor will extract class instances of the
     * generic types (TYPE and ID). These instances could later be used to identify the types for when interacting
     * with mongodb.
     */
    @SuppressWarnings("unchecked")
    public MongoRepository() {
        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.dtoClass = (Class<D>) genericSuperclass.getActualTypeArguments()[1];
    }

    /**
     * The initialize method is responsible for initiating the repository.
     * has empty implementation by default.
     *
     * @see #postInitiate()
     */
    @Override
    public void initialize() {
        postInitiate();
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
        T type = mongoOperations.findById(id, entityClass);
        return type != null ? mapper.map(type, dtoClass) : null;
    }

    /**
     * Retrieves a list of all the instances of the specific type
     *
     * @return A list that contains all the instances of the type that is managed by the operation
     */
    @Override
    public List<D> findAll() {
        LOGGER.debug("Retrieving all instances for " + entityClass.getSimpleName());
        return toDtoList(mongoOperations.find(new Query(), entityClass), dtoClass);
    }

    /**
     * The save method provides the functionality to save an instance to mongodb.
     *
     * @param dto The type that will be saved to mongodb.
     * @return The type that was saved to mongodb. The main reason for it is being returned is because
     * there could be modifications of the object during the save process. For example, if the type does not
     * have an identifier, then the method will generate a new identifier for the type.
     */
    @Override
    public D save(final D dto) {
        T type = mapper.map(dto, entityClass);
        checkType(type);
        mongoOperations.save(type);
        return mapper.map(type, dtoClass);
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
        return (int) mongoOperations.getCollection(mongoOperations.getCollectionName(entityClass)).countDocuments();
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
        T type = mongoOperations.findAndRemove(getIdQuery(id), entityClass);
        LOGGER.debug("Deletion of " + entityClass.getSimpleName() + " with id " + id + " was successfully completed");
        return mapper.map(type, dtoClass);
    }


    /**
     * @throws UnsupportedOperationException not supported.
     */
    @Override
    public String exportOne(final I id) {
        throw new UnsupportedOperationException("exportOne method is not supported in the Mongo repository");
    }

    /**
     * @throws UnsupportedOperationException not supported.
     */
    @Override
    public D importOne(final String raw) {
        throw new UnsupportedOperationException("importOne method is not supported in the Mongo repository");
    }

    /**
     * Checks if the provided <code>id</code> already exists.
     *
     * @param id The id that will be checked if it is already being used.
     * @return <code>true</code> if the id exists. <code>false</code> otherwise.
     */
    public boolean exists(I id) {
        return mongoOperations.exists(getIdQuery(id), entityClass);
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
     * The method is responsible for controller that the type that is about the be saved to mongodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to mongodb, but also loaded from mongodb
     * upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             mongodb.
     * @see #save
     */
    protected abstract void checkType(T type);

    /**
     * The method provides the functionality to convert a Collection of TYPE instances into a list of DTO instances
     *
     * @param types The collection that will be converted into a list of DTO
     * @param clazz CLass of the DTO type (D)
     * @param <T>   The type that the operation is managing
     * @param <D>   The DTO (Data transfer object) version of the type (TYPE)
     * @return The provided collection but converted into the DTO class
     */
    protected <T, D> List<D> toDtoList(final Collection<T> types, Class<D> clazz) {
        final List<D> dtos = new ArrayList<D>();
        for (T type : types) {
            dtos.add(mapper.map(type, clazz));
        }
        return dtos;
    }

    /**
     * Case-insensitive contains search.
     *
     * @see com.castlemock.model.core.model.SearchValidator#validate(String, String)
     */
    protected Query getSearchQuery(String property, SearchQuery searchQuery) {
        return query(where(property).regex(".*" + searchQuery.getQuery() + ".*", "i"));
    }

    @Document(collection = "httpHeader")
    protected static class HttpHeaderDocument {

        @Mapping("name")
        private String name;
        @Mapping("value")
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @Document(collection = "httpParameter")
    protected static class HttpParameterDocument {

        @Mapping("name")
        private String name;
        @Mapping("value")
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    private Query getIdQuery(Object id) {
        return new Query(getIdCriteria(id));
    }

    private Criteria getIdCriteria(Object id) {
        return where("id").is(id);
    }
}