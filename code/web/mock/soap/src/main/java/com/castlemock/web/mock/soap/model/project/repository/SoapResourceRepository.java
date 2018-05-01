/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.mock.soap.model.project.repository;

import com.castlemock.core.basis.model.Repository;
import com.castlemock.core.mock.soap.model.project.domain.SoapResourceType;
import com.castlemock.core.mock.soap.model.project.domain.SoapResource;

import java.util.Collection;
import java.util.List;


public interface SoapResourceRepository extends Repository<SoapResource, String> {

    void deleteWithProjectId(String projectId);

    List<SoapResource> findWithProjectId(String projectId);

    /**
     * The method loads a resource that matching the search criteria and returns the result
     * @param soapResourceId The id of the resource that will be loaded
     * @return Returns the loaded resource and returns it as a String.
     * @throws IllegalArgumentException IllegalArgumentException will be thrown jf no matching SOAP operation was found
     * @see SoapResource
     * @since 1.16
     */
    String loadSoapResource(String soapResourceId);


    /**
     * The method adds a new {@link SoapResource}.
     * @param soapResource The  instance of {@link SoapResource} that will be saved.
     * @param resource The raw resource
     * @return The saved {@link SoapResource}
     * @see SoapResource
     */
    SoapResource saveSoapResource(SoapResource soapResource, String resource);

    /**
     * The method returns a list of {@link SoapResource} that matches the
     * search criteria.
     * @param soapProjectId The id of the project.
     * @param type The type of {@link SoapResource} that should be returned.
     * @return A list of {@link SoapResource} of the specific provided type.
     * All resources will be returned if the type is null.
     * @since 1.16
     */
    Collection<SoapResource> findSoapResources(String soapProjectId, SoapResourceType type);

}
