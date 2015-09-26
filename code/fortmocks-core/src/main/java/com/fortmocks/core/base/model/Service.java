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

package com.fortmocks.core.base.model;

import java.io.Serializable;
import java.util.List;

/**
 * The service provides the basic functionality for a specific type and its corresponding dto class
 * The functionality specific in the service class has to be implemented and support by all the service classes
 * inherited from the class. The functionality mainly involves interacting towards the repository layer and
 * handling the conversation between the type class and the DTO class.
 * @author Karl Dahlgren
 * @since 1.0
 * @param <T> The type that the operation is managing
 * @param <D> The DTO (Data transfer object) version of the type (TYPE)
 * @param <I> The ID type that is used to identify the type (TYPE)
 * @see Saveable
 */
public interface Service<T extends Saveable<I>, D, I extends Serializable>{

    /**
     * The method provides the functionality to find a specific instance that matches the provided id
     * @param id The id that an instance has to match in order to be retrieved
     * @return Returns an instance that matches the provided id
     */
    D findOne(I id);

    /**
     * Retrieves a list of all the instances of the specific type
     * @return A list that contains all the instances of the type that is managed by the operation
     */
    List<D> findAll();

    /**
     * The save method provides functionality to save the provided instance to the database
     * @param dto The instance that will be saved
     * @return Return the same instance that has been saved in the database
     */
    D save(D dto);

    /**
     * The method takes a type instance and save it
     * @param type The type that will be saved
     * @return The saved instance
     */
    T save(T type);

    /**
     * The method takes an instance id and save the instance
     * @param id The id of the type instance that will be saved
     * @return Returns the saved instance
     */
    T save(I id);

    /**
     * Delete an instance that match the provided id
     * @param id The instance that matches the provided id will be deleted in the database
     */
    void delete(I id);

    /**
     * Updates an instance that matches the provided id. The provided dto contains the new information that will be
     * stored
     * @param id The id of the instance that will be updated
     * @param dto The dto contains the new information that will be stored
     * @return The updated version of the instance that matches the id
     */
    D update(I id, D dto);
}
