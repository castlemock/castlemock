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

package com.castlemock.web.mock.rest.legacy.repository.project.v1;

import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.dto.HttpHeaderDto;
import com.castlemock.core.mock.rest.legacy.model.project.v1.domain.*;
import com.castlemock.core.mock.rest.model.project.dto.*;
import com.castlemock.web.basis.model.AbstractLegacyRepositoryImpl;
import com.castlemock.web.mock.rest.model.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class RestProjectLegacyRepository extends AbstractLegacyRepositoryImpl<RestProjectV1, String> {

    @Value(value = "${legacy.rest.project.v1.directory}")
    private String fileDirectory;
    @Value(value = "${rest.project.file.extension}")
    private String fileExtension;

    @Autowired
    private RestProjectRepository projectRepository;
    @Autowired
    private RestApplicationRepository applicationRepository;
    @Autowired
    private RestResourceRepository resourceRepository;
    @Autowired
    private RestMethodRepository methodRepository;
    @Autowired
    private RestMockResponseRepository mockResponseRepository;



    /**
     * The initialize method is responsible for initiating the file repository. This procedure involves loading
     * the types (TYPE) from the file system and store them in the collection.
     */
    @Override
    public void initialize() {
        Collection<RestProjectV1> projects =
                fileRepositorySupport.load(RestProjectV1.class, fileDirectory, fileExtension);

        for(RestProjectV1 projectV1 : projects){
            RestProjectDto project = new RestProjectDto();
            project.setCreated(projectV1.getCreated());
            project.setUpdated(projectV1.getUpdated());
            project.setDescription(projectV1.getDescription());
            project.setId(projectV1.getId());
            project.setName(projectV1.getName());

            project = this.projectRepository.save(project);

            for(RestApplicationV1 applicationV1 : projectV1.getApplications()){
                RestApplicationDto application = new RestApplicationDto();
                application.setId(applicationV1.getId());
                application.setName(applicationV1.getName());
                application.setProjectId(project.getId());
                application = this.applicationRepository.save(application);

                for(RestResourceV1 restResourceV1 : applicationV1.getResources()){
                    RestResourceDto resource = new RestResourceDto();

                    resource.setUri(restResourceV1.getUri());
                    resource.setName(restResourceV1.getName());
                    resource.setId(restResourceV1.getId());
                    resource.setApplicationId(application.getId());
                    resource = this.resourceRepository.save(resource);

                    for(RestMethodV1 methodV1 : restResourceV1.getMethods()){
                        RestMethodDto restMethod = new RestMethodDto();
                        restMethod.setStatus(methodV1.getStatus());
                        restMethod.setSimulateNetworkDelay(methodV1.getSimulateNetworkDelay());
                        restMethod.setNetworkDelay(methodV1.getNetworkDelay());
                        restMethod.setResponseStrategy(methodV1.getResponseStrategy());
                        restMethod.setName(methodV1.getName());
                        restMethod.setId(methodV1.getId());
                        restMethod.setHttpMethod(methodV1.getHttpMethod());
                        restMethod.setForwardedEndpoint(methodV1.getForwardedEndpoint());
                        restMethod.setDefaultBody(methodV1.getDefaultBody());
                        restMethod.setCurrentResponseSequenceIndex(methodV1.getCurrentResponseSequenceIndex());

                        restMethod.setResourceId(resource.getId());
                        restMethod = this.methodRepository.save(restMethod);

                        for(RestMockResponseV1 mockResponseV1 : methodV1.getMockResponses()){
                            RestMockResponseDto mockResponse = new RestMockResponseDto();
                            mockResponse.setId(mockResponseV1.getId());
                            mockResponse.setName(mockResponseV1.getName());
                            mockResponse.setBody(mockResponseV1.getBody());
                            mockResponse.setStatus(mockResponseV1.getStatus());
                            mockResponse.setHttpStatusCode(mockResponseV1.getHttpStatusCode());
                            mockResponse.setUsingExpressions(mockResponseV1.isUsingExpressions());

                            List<HttpHeaderDto> httpHeaders = new ArrayList<>();
                            if(mockResponseV1.getHttpHeaders() != null){
                                httpHeaders = this.toDtoList(mockResponseV1.getHttpHeaders(), HttpHeaderDto.class);
                            }
                            List<ContentEncoding> contentEncodings = new ArrayList<>();
                            if(mockResponseV1.getContentEncodings() != null){
                                contentEncodings = mockResponseV1.getContentEncodings();
                            }

                            mockResponse.setHttpHeaders(httpHeaders);
                            mockResponse.setContentEncodings(contentEncodings);

                            mockResponse.setMethodId(restMethod.getId());
                            this.mockResponseRepository.save(mockResponse);
                        }
                    }
                }
            }

            fileRepositorySupport.delete(this.fileDirectory, projectV1.getId() + this.fileExtension);
        }
    }
}
