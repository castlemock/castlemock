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

package com.castlemock.model.core;

import java.io.Serializable;

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
public interface ServiceFacade<D, I extends Serializable> {


    /**
     * The initialize method is responsible for for locating all the service instances for a specific module
     * and organizing them depending on the type.
     * @see Service
     */
    void initiate();

}