package com.fortmocks.web.basis.model;

import com.fortmocks.core.basis.model.Output;
import com.fortmocks.core.basis.model.Repository;
import com.fortmocks.core.basis.model.Saveable;
import com.fortmocks.core.basis.model.ServiceResult;
import com.google.common.base.Preconditions;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractService<T extends Saveable<I>, D, I extends Serializable> {

    @Autowired
    private Repository<T, I> repository;
    @Autowired
    protected DozerBeanMapper mapper;

    protected Class<T> entityClass;
    protected Class<D> dtoClass;

    /**
     * The default constructor for the GenericServiceImpl class.
     * It will create an instance of the entity class and the dto class
     */
    public AbstractService() {
        Class<?>[] genericClasses = GenericTypeResolver.resolveTypeArguments(getClass(), AbstractService.class);
        this.entityClass = (Class<T>) genericClasses[0];
        this.dtoClass = (Class<D>) genericClasses[1];
    }


    protected T findType(I id){
        return repository.findOne(id);
    }

    protected Collection<T> findAllTypes() {
        return repository.findAll();
    }

    /**
     * The method provides the functionality to find a specific instance that matches the provided id
     * @param id The id that an instance has to match in order to be retrieved
     * @return Returns an instance that matches the provided id
     */
    protected D find(I id) {
        Preconditions.checkNotNull(id, "The provided id cannot be null");
        final T type = findType(id);
        return type == null ? null : mapper.map(type, dtoClass);
    }

    /**
     * Retrieves a list of all the instances of the specific type
     * @return A list that contains all the instances of the type that is managed by the operation
     */
    public List<D> findAll() {
        final Collection<T> types = findAllTypes();
        return toDtoList(types);
    }


    /**
     * The method takes a type instance and save it
     * @param type The type that will be saved
     * @return The saved instance
     */
    protected T save(T type) {
        return repository.save(type);
    }

    /**
     * The method takes an instance id and save the instance
     * @param id The id of the type instance that will be saved
     * @return Returns the saved instance
     */
    protected T save(final I id){
        final T type = repository.findOne(id);
        return save(type);
    }

    /**
     * Count all the stored entities for the repository
     * @return The count of entities
     */
    protected Integer count(){
        return repository.count();
    }

    /**
     * The save method provides functionality to save the provided instance to the database
     * @param dto The instance that will be saved
     * @return Return the same instance that has been saved in the database
     */
    protected D save(D dto) {
        Preconditions.checkNotNull(dto, "Unable to save due to invalid " + entityClass.getName() + " instance. Instance cannot be null");
        final T type = mapper.map(dto, entityClass);
        final T returnedType = save(type);
        return toDto(returnedType);
    }


    /**
     * Delete an instance that match the provided id
     * @param id The instance that matches the provided id will be deleted in the database
     */
    protected void delete(final I id) {
        Preconditions.checkNotNull(id, "The provided id cannot be null");
        repository.delete(id);
    }

    /**
     * Updates an instance that matches the provided id. The provided dto contains the new information that will be
     * stored
     * @param id The id of the instance that will be updated
     * @param dto The dto contains the new information that will be stored
     * @return The updated version of the instance that matches the id
     */
    protected D update(final I id, final D dto) {
        Preconditions.checkNotNull(id, "The provided id cannot be null");
        Preconditions.checkNotNull(dto, "Unable to update due to invalid " + entityClass.getName() + " instance. Instance cannot be null");
        return null;

    }

    /**
     * The method takes a list of ID type and then transform the list into DTO list
     * @param types The list that will be transformed to a dto list
     * @return A transformed DTO list
     */
    protected List toDtoList(final Collection<T> types) {
        return toDtoList(types, dtoClass);
    }

    /**
     * The method provides the functionality to convert a Collection of TYPE instances into a list of DTO instances
     * @param types The collection that will be converted into a list of DTO
     * @param clazz CLass of the DTO type (D)
     * @param <T> The type that the operation is managing
     * @param <D> The DTO (Data transfer object) version of the type (TYPE)
     * @return
     */
    protected <T, D> List toDtoList(final Collection<T> types, Class<D> clazz) {
        final List<D> dtos = new ArrayList<D>();
        for (T type : types) {
            dtos.add(mapper.map(type, clazz));
        }
        return dtos;
    }

    /**
     * The method provides the functionality to convert a TYPE instance into a DTO instance
     * @param type The TYPE instance that will be converted into a DTO
     * @return A new DTO instance based of the provided TYPE instance
     */
    protected D toDto(final T type){
        return mapper.map(type, dtoClass);
    }

    protected <O extends Output> ServiceResult<O> createServiceResult(O output){
        return new ServiceResult(output);
    }

    /**
     * The method provides the functionality to export a project and convert it to a String
     * @param id The id of the project that will be converted and exported
     * @return The project with the provided id as a String
     */
    protected String exportType(final I id){
        try {
            final T type = findType(id);
            final JAXBContext context = JAXBContext.newInstance(entityClass);
            final Marshaller marshaller = context.createMarshaller();
            final StringWriter writer = new StringWriter();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(type, writer);
            return writer.toString();
        }
        catch (JAXBException e) {
            throw new IllegalStateException("Unable to export type");
        }
    }

    /**
     * The method provides the functionality to import a project as a String
     * @param projectRaw The project as a String
     */
    protected void importType(final String projectRaw){

        try {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream (projectRaw.getBytes());
            final JAXBContext jaxbContext = JAXBContext.newInstance(entityClass);
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            final T type = (T) jaxbUnmarshaller.unmarshal(byteArrayInputStream);
            type.setId(null);
            save(type);
        } catch (JAXBException e) {
            throw new IllegalStateException("Unable to import type");
        }
    }

}
