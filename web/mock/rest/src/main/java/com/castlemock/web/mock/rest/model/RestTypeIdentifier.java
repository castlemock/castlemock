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

package com.castlemock.web.mock.rest.model;


import com.castlemock.core.basis.model.TypeIdentifier;

/**
 * The RestTypeIdentifier is used to identify a REST project on the certain shared project pages:
 * <ul>
 *  <li>Project overview page</li>
 *  <li>Update project</li>
 *  <li>Delete projects</li>
 * </ul>
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestTypeIdentifier implements TypeIdentifier {

    private static final String REST_TYPE = "REST";
    private static final String REST_TYPE_URL = "rest";

    /**
     * The type variable is used to identify a specific class by a type name.
     * @return The identifier type
     */
    @Override
    public String getType(){
        return REST_TYPE;
    }

    /**
     * The type URL variable is used to identify a specific class by a type URL
     * @return The identifier type URL
     */
    @Override
    public String getTypeUrl(){
        return REST_TYPE_URL;
    }

}
