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

package com.fortmocks.core.model;

/**
 * The TypeIdentifiable interface is marking a class that it can be identified with a {@link TypeIdentifier}
 * @author Karl Dahlgren
 * @since 1.0
 * @see TypeIdentifier
 */
public interface TypeIdentifiable {

    /**
     * The TypeIdentifier that is used to identify a specific class
     * @return Returns the type identifier
     */
    TypeIdentifier getTypeIdentifier();

    /**
     * Set a new value to the type identifier value
     * @param typeIdentifier The new type identifier value
     */
    void setTypeIdentifier(TypeIdentifier typeIdentifier);

}
