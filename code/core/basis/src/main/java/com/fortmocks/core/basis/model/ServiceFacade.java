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

package com.fortmocks.core.basis.model;

import java.io.Serializable;
import java.util.List;

/**
 * The facade class is responsible for gather all services for a specific type and collaborate with all services
 * in order to provide unified functionality. This enables the using classes to operate all service of a specific type
 * from one single point.
 * @author Karl Dahlgren
 * @since 1.0
 * @param <D> The DTO (Data transfer object) version of the type (TYPE)
 * @param <I> The ID type that is used to identify the type (TYPE)
 * @see Service
 */
public interface ServiceFacade<D extends TypeIdentifiable, I extends Serializable> {


    /**
     * The initiate method is responsible for for locating all the service instances for a specific module
     * and organizing them depending on the type.
     * @see Service
     * @see TypeIdentifier
     * @see TypeIdentifiable
     */
    void initiate();

    /**
     * The method provides the functionality to create and store a DTO instance to a specific service.
     * The service is identified with the provided type value.
     * @param type The type of the instance that will be created
     * @param parent The instance that will be created
     * @return The saved instance
     */
    D save(String type, D parent);

    /**
     * The method provides the functionality to delete a specific instance. The type is
     * identified with the provided typeUrl value. When the type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching type
     * and id will be deleted.
     * @param typeUrl The url for the specific type that the instance belongs to
     * @param id The id of the instance that will be deleted
     */
    void delete(String typeUrl, I id);

    /**
     * The method is used to update an already existing instance. The instance type is
     * identified with the provided typeUrl value. When the instance type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching id will be
     * replaced with the provided dto instance. Please note that not all values will be updated. It depends on the instance
     * type.
     * @param typeUrl The url for the specific type that the instance belongs to
     * @param id The id of the instance that will be updated
     * @param parent The instance with the new updated values
     * @return The updated instance
     */
    D update(String typeUrl, I id, D parent);

    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    List<D> findAll();

    /**
     * The method is used to retrieve a instance with a specific type. The type is
     * identified with the provided typeUrl value. When the instance type has been identified, the instance
     * itself has to be identified.
     * @param typeUrl The url for the specific type that the instance belongs to
     * @param id The id of the instance that will be retrieved
     * @return A instance that matches the instance type and the provided id. If no instance matches the provided
     *         values, null will be returned.
     */
    D findOne(String typeUrl, I id);

    /**
     * The method retrieves all service types
     * @return A list with all the service types
     */
    List<String> getTypes();

    /**
     * Returns the type URL for a specific type
     * @param type The type that will be used to retrieve the type URL
     * @return The matching type URL
     * @throws java.lang.IllegalArgumentException A IllegalArgumentException will be thrown
     * If no service matches the provided type
     */
    String getTypeUrl(String type);

}