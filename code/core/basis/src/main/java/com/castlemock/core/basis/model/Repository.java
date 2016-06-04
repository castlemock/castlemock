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

package com.castlemock.core.basis.model;

import java.io.Serializable;
import java.util.List;

/**
 * The abstract repository provides functionality to interact with the file system in order to manage a specific type.
 * The abstract repository is responsible for retrieving and managing instances for a specific type. All the communication
 * that involves interacting with the file system, such as saving and loading instances, are done through the file repository.
 * @author Karl Dahlgren
 * @since 1.0
 * @param <T> The type that is being managed by the file repository. This file will both be saved and loaded from the
 *              file system.
 * @param <D> The date transfer class of the type class
 * @param <I> The id is used as an identifier for the type.
 * @see Saveable
 */
public interface Repository<T extends Saveable<I>, D, I extends Serializable> {


    /**
     * The initialize method is responsible for initiating the file repository. This procedure involves loading
     * the types (TYPE) from the file system and store them in the collection.
     */
    void initialize();

    /**
     * The method provides the functionality to find a specific instance that matches the provided id
     * @param id The id that an instance has to match in order to be retrieved
     * @return Returns an instance that matches the provided id
     * @see T
     * @see I
     */
    D findOne(I id);

    /**
     * Retrieves a list of all the instances of the specific type
     * @return A list that contains all the instances of the type that is managed by the operation
     * @see T
     * @see I
     */
    List<D> findAll();

    /**
     * The save method provides the functionality to save an instance to the file system.
     * @param type The type that will be saved to the file system.
     * @return The type that was saved to the file system. The main reason for it is being returned is because
     *         there could be modifications of the object during the save process. For example, if the type does not
     *         have an identifier, then the method will generate a new identifier for the type.
     */
    D save(D type);

    /**
     * Delete an instance that match the provided id
     * @param id The instance that matches the provided id will be deleted in the database
     */
    void delete(I id);

    /**
     * Count all the stored entities for the repository
     * @return The count of entities
     */
    Integer count();

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    List<SearchResult> search(SearchQuery query);

    /**
     * The method provides the functionality to export an entity and convert it to a String
     * @param id The id of the entityy that will be converted and exported
     * @return The entity with the provided id as a String
     */
    String exportOne(I id);

    /**
     * The method provides the functionality to import a enity as a String
     * @param raw The entity as a String
     */
    void importOne(String raw);

}
