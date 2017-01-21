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

package com.castlemock.web.basis.model;

import com.castlemock.core.basis.model.Output;
import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.ServiceResult;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractService<T extends Saveable<I>, D, I extends Serializable, R extends Repository<T, D, I>> {

    @Autowired
    protected R repository;

    /**
     * The default constructor for the GenericServiceImpl class.
     * It will create an instance of the entity class and the dto class
     */
    protected AbstractService() {
    }



    /**
     * The method provides the functionality to find a specific instance that matches the provided id
     * @param id The id that an instance has to match in order to be retrieved
     * @return Returns an instance that matches the provided id
     */
    protected D find(I id) {
        Preconditions.checkNotNull(id, "The provided id cannot be null");
        return repository.findOne(id);
    }

    /**
     * Retrieves a list of all the instances of the specific type
     * @return A list that contains all the instances of the type that is managed by the operation
     */
    public List<D> findAll() {
        return repository.findAll();
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
        Preconditions.checkNotNull(dto, "Unable to save due to invalid " + dto.getClass().getName() + " instance. Instance cannot be null");
        return repository.save(dto);
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
        Preconditions.checkNotNull(dto, "Unable to update due to invalid " + dto.getClass().getName() + " instance. Instance cannot be null");
        return null;

    }


    protected <O extends Output> ServiceResult<O> createServiceResult(O output){
        return new ServiceResult(output);
    }
}
