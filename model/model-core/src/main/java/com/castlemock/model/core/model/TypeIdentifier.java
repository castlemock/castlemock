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

package com.castlemock.model.core.model;

/**
 * The TypeIdentifier is used by certain classes that needs to be identified as either a specific type
 * or a type URL.
 * @author Karl Dahlgren
 * @since 1.0
 * @see TypeIdentifiable
 */
public interface TypeIdentifier {

    /**
     * The type variable is used to identify a specific class by a type name.
     * @return The identifier type
     */
    String getType();

    /**
     * The type URL variable is used to identify a specific class by a type URL
     * @return The identifier type URL
     */
    String getTypeUrl();

}
