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

package com.castlemock.core.mock.soap.legacy.model.project.v1.converter;

import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.model.http.dto.HttpHeaderDto;
import com.castlemock.core.mock.soap.legacy.model.project.v1.domain.*;
import com.castlemock.core.mock.soap.model.project.dto.*;

import java.util.ArrayList;
import java.util.List;

public class SoapProjectConverterV1 {


    public static SoapProjectDto convert(SoapProjectV1 projectV1){
        SoapProjectDto project = new SoapProjectDto();
        project.setCreated(projectV1.getCreated());
        project.setUpdated(projectV1.getUpdated());
        project.setDescription(projectV1.getDescription());
        project.setId(projectV1.getId());
        project.setName(projectV1.getName());

        for(SoapResourceV1 soapResourceV1 : projectV1.getResources()){
            SoapResourceDto resource = new SoapResourceDto();
            resource.setName(soapResourceV1.getName());
            resource.setId(soapResourceV1.getId());
            resource.setType(soapResourceV1.getType());
            resource.setProjectId(project.getId());
            project.getResources().add(resource);
        }

        for(SoapPortV1 portV1 : projectV1.getPorts()){
            SoapPortDto port = new SoapPortDto();
            port.setName(portV1.getName());
            port.setUri(portV1.getUri());
            port.setId(portV1.getId());
            port.setProjectId(project.getId());
            project.getPorts().add(port);

            for(SoapOperationV1 operationV1 : portV1.getOperations()){
                SoapOperationDto operation = new SoapOperationDto();
                operation.setId(operationV1.getId());
                operation.setName(operationV1.getName());
                operation.setIdentifier(operationV1.getIdentifier());
                operation.setResponseStrategy(operationV1.getResponseStrategy());
                operation.setStatus(operationV1.getStatus());
                operation.setHttpMethod(operationV1.getHttpMethod());
                operation.setSoapVersion(operationV1.getSoapVersion());
                operation.setDefaultBody(operationV1.getDefaultBody());
                operation.setCurrentResponseSequenceIndex(operationV1.getCurrentResponseSequenceIndex());
                operation.setForwardedEndpoint(operationV1.getForwardedEndpoint());
                operation.setOriginalEndpoint(operationV1.getOriginalEndpoint());
                operation.setDefaultXPathMockResponseId(operationV1.getDefaultXPathMockResponseId());
                operation.setSimulateNetworkDelay(operationV1.getSimulateNetworkDelay());
                operation.setNetworkDelay(operationV1.getNetworkDelay());
                operation.setPortId(port.getId());
                port.getOperations().add(operation);

                for(SoapMockResponseV1 mockResponseV1 : operationV1.getMockResponses()){
                    SoapMockResponseDto mockResponse = new SoapMockResponseDto();
                    mockResponse.setId(mockResponseV1.getId());
                    mockResponse.setName(mockResponseV1.getName());
                    mockResponse.setBody(mockResponseV1.getBody());
                    mockResponse.setOperationId(operation.getId());
                    mockResponse.setStatus(mockResponseV1.getStatus());
                    mockResponse.setHttpStatusCode(mockResponseV1.getHttpStatusCode());
                    mockResponse.setUsingExpressions(mockResponseV1.isUsingExpressions());
                    mockResponse.setXpathExpression(mockResponseV1.getXpathExpression());

                    List<HttpHeaderDto> httpHeaders = new ArrayList<>();
                    if(mockResponseV1.getHttpHeaders() != null){
                        for(HttpHeader httpHeader : mockResponseV1.getHttpHeaders()){
                            HttpHeaderDto httpHeaderDto = new HttpHeaderDto();
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
                    operation.getMockResponses().add(mockResponse);
                }
            }
        }
        return project;
    }
}
