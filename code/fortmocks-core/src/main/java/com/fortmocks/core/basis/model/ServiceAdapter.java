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
 * @author Karl Dahlgren
 * @since 1.0
 */
public interface ServiceAdapter<D extends TypeIdentifiable, I extends Serializable>  {

    /**
     * The method provides the functionality to create and store a DTO instance to a specific service.
     * The service is identified with the provided type value.
     * @param dto The instance that will be created
     * @return The saved instance
     */
    D create(D dto);

    /**
     * The method provides the functionality to delete a specific instance. The type is
     * identified with the provided typeUrl value. When the type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching type
     * and id will be deleted.
     * @param id The id of the instance that will be deleted
     */
    void delete(I id);

    /**
     * The method is used to update an already existing instance. The instance type is
     * identified with the provided typeUrl value. When the instance type has been identified, the instance
     * itself has to be identified. This is done with the provided id. The instance with the matching id will be
     * replaced with the provided dto instance. Please note that not all values will be updated. It depends on the instance
     * type.
     * @param id The id of the instance that will be updated
     * @param dto The instance with the new updated values
     * @return The updated instance
     */
    D update(I id, D dto);

    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    List<D> readAll();

    /**
     * The method is used to retrieve a instance with a specific type. The type is
     * identified with the provided typeUrl value. When the instance type has been identified, the instance
     * itself has to be identified.
     * @param id The id of the instance that will be retrieved
     * @return A instance that matches the instance type and the provided id. If no instance matches the provided
     *         values, null will be returned.
     */
    D read(I id);

    /**
     * The TypeIdentifier that is used to identify a specific class
     * @return Returns the type identifier
     */
    TypeIdentifier getTypeIdentifier();

    /**
     * The method provides the functionality to convert an instance (parent) to an instance of a subclass to the
     * provided parent.
     * @param parent The parent that will be converted into a subtype of the provided parent
     * @return A new instance of the parent, but as a subtype of the parent
     */
    D convertType(D parent);
}
