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

package com.castlemock.core.mock.rest.legacy.model.project.v1.convert;

import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.mock.rest.legacy.model.project.v1.domain.*;
import com.castlemock.core.mock.rest.model.project.domain.*;

import java.util.ArrayList;
import java.util.List;

public class RestProjectConverterV1 {


    public static RestProject convert(RestProjectV1 projectV1){
        RestProject project = new RestProject();
        project.setCreated(projectV1.getCreated());
        project.setUpdated(projectV1.getUpdated());
        project.setDescription(projectV1.getDescription());
        project.setId(projectV1.getId());
        project.setName(projectV1.getName());

        for(RestApplicationV1 applicationV1 : projectV1.getApplications()){
            RestApplication application = new RestApplication();
            application.setId(applicationV1.getId());
            application.setName(applicationV1.getName());
            application.setProjectId(project.getId());
            project.getApplications().add(application);

            for(RestResourceV1 restResourceV1 : applicationV1.getResources()){
                RestResource resource = new RestResource();
                resource.setUri(restResourceV1.getUri());
                resource.setName(restResourceV1.getName());
                resource.setId(restResourceV1.getId());
                resource.setApplicationId(application.getId());
                application.getResources().add(resource);

                for(RestMethodV1 methodV1 : restResourceV1.getMethods()){
                    RestMethod restMethod = new RestMethod();
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
                    resource.getMethods().add(restMethod);

                    for(RestMockResponseV1 mockResponseV1 : methodV1.getMockResponses()){
                        RestMockResponse mockResponse = new RestMockResponse();
                        mockResponse.setId(mockResponseV1.getId());
                        mockResponse.setName(mockResponseV1.getName());
                        mockResponse.setBody(mockResponseV1.getBody());
                        mockResponse.setStatus(mockResponseV1.getStatus());
                        mockResponse.setHttpStatusCode(mockResponseV1.getHttpStatusCode());
                        mockResponse.setUsingExpressions(mockResponseV1.isUsingExpressions());

                        List<HttpHeader> httpHeaders = new ArrayList<>();
                        if(mockResponseV1.getHttpHeaders() != null){
                            for(HttpHeader httpHeader : mockResponseV1.getHttpHeaders()){
                                HttpHeader httpHeaderDto = new HttpHeader();
                                httpHeaderDto.setName(httpHeader.getName());
                                httpHeaderDto.setValue(httpHeader.getValue());
                                httpHeaders.add(httpHeaderDto);
                            }
                        }
                        List<ContentEncoding> contentEncodings = new ArrayList<>();
                        if(mockResponseV1.getContentEncodings() != null){
                            contentEncodings = mockResponseV1.getContentEncodings();
                        }

                        mockResponse.setHttpHeaders(httpHeaders);
                        mockResponse.setContentEncodings(contentEncodings);
                        mockResponse.setMethodId(restMethod.getId());
                        restMethod.getMockResponses().add(mockResponse);
                    }
                }
            }
        }
        return project;
    }


}
