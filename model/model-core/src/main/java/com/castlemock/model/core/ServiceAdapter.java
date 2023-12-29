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
import java.util.List;

/**
 * The service adapter is an adapter class that provides the basic functionality to retrieve,
 * delete, update, create instances for a specific model class.
 *
 * @author Karl Dahlgren
 * @since 1.0
 *
 */
public interface ServiceAdapter<P, D extends P, I extends Serializable>  {


    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    List<D> readAll();

    String getType();

}
