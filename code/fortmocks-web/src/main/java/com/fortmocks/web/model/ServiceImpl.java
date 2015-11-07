/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fortmocks.web.model;

import com.fortmocks.core.model.Repository;
import com.fortmocks.core.model.Saveable;
import com.fortmocks.core.model.Service;
import com.google.common.base.Preconditions;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The Abstract service provides the basic functionality for a specific type and its corresponding dto class
 * The functionality specific in the service class has to be implemented and support by all the service classes
 * inherited from the class. The functionality mainly involves interacting towards the repository layer and
 * handling the conversation between the type class and the DTO class.
 * <p>
 * The class, which inherits from {@link Service}, implements all the method
 * presented in the GenericService. The class also provides extract functionality, such as methods to convert a single TYPE or
 * a list of TYPEs to their corresponding DTO classes
 * @author Karl Dahlgren
 * @since 1.0
 * @param <T> The type that the operation is managing
 * @param <D> The DTO (Data transfer object) version of the type (TYPE)
 * @param <I> The ID type that is used to identify the type (TYPE)
 * @see Saveable
 * @see ServiceImpl
 */
@org.springframework.stereotype.Service
public abstract class ServiceImpl<T extends Saveable<I>, D, I extends Serializable> implements Service<T, D, I> {

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
    public ServiceImpl() {
        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.dtoClass = (Class<D>) genericSuperclass.getActualTypeArguments()[1];
    }

    protected T findOneType(I id){
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
    @Override
    public D findOne(I id) {
        Preconditions.checkNotNull(id, "The provided id cannot be null");
        final T type = findOneType(id);
        return type == null ? null : mapper.map(type, dtoClass);
    }

    /**
     * Retrieves a list of all the instances of the specific type
     * @return A list that contains all the instances of the type that is managed by the operation
     */
    @Override
    public List<D> findAll() {
        final Collection<T> types = findAllTypes();
        return toDtoList(types);
    }

    /**
     * The method takes an instance id and save the instance
     * @param id The id of the type instance that will be saved
     * @return Returns the saved instance
     */
    @Override
    public T save(final I id){
        final T type = repository.findOne(id);
        return save(type);
    }

    /**
     * The save method provides functionality to save the provided instance to the database
     * @param dto The instance that will be saved
     * @return Return the same instance that has been saved in the database
     */
    @Override
    public D save(D dto) {
        Preconditions.checkNotNull(dto, "Unable to save due to invalid " + entityClass.getName() + " instance. Instance cannot be null");
        final T type = mapper.map(dto, entityClass);
        final T returnedType = save(type);
        return toDto(returnedType);
    }

    /**
     * The method takes a type instance and save it
     * @param type The type that will be saved
     * @return The saved instance
     */
    @Override
    public T save(T type) {
        return repository.save(type);
    }

    /**
     * Delete an instance that match the provided id
     * @param id The instance that matches the provided id will be deleted in the database
     */
    @Override
    public void delete(final I id) {
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
    @Override
    public D update(final I id, final D dto) {
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
}
