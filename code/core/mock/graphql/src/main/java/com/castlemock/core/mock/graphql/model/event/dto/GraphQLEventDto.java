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

package com.castlemock.core.mock.graphql.model.event.dto;

import com.castlemock.core.basis.model.event.dto.EventDto;
import org.dozer.Mapping;

import java.util.Date;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public class GraphQLEventDto extends EventDto {

    @Mapping("request")
    private GraphQLRequestDto request;

    @Mapping("response")
    private GraphQLResponseDto response;

    @Mapping("projectId")
    private String projectId;

    @Mapping("applicationId")
    private String applicationId;

    @Mapping("operationId")
    private String operationId;


    /**
     * Constructor for the SOAP event DTO
     * @param request The SOAP request that the event is representing
     * @param projectId The id of the SOAP project that is affected by the provided SOAP request
     * @param applicationId The id of the SOAP port that is affected by the provided SOAP request
     * @param operationId The id of the SOAP operation that is affected by the provided SOAP request
     * @see com.castlemock.core.mock.graphql.model.project.dto.GraphQLOperationDto
     */
    public GraphQLEventDto(final String resourceName, final GraphQLRequestDto request,
                           final String projectId, final String applicationId, final String operationId) {
        super(resourceName);
        this.request = request;
        this.projectId = projectId;
        this.applicationId = applicationId;
        this.operationId = operationId;
    }


    /**
     * The finish method is used to sent the response that was sent back, but was also
     * to set the date/time for when the event ended.
     * @param response
     */
    public void finish(final GraphQLResponseDto response) {
        this.response = response;
        setEndDate(new Date());
    }

    public GraphQLRequestDto getRequest() {
        return request;
    }

    public void setRequest(GraphQLRequestDto request) {
        this.request = request;
    }

    public GraphQLResponseDto getResponse() {
        return response;
    }

    public void setResponse(GraphQLResponseDto response) {
        this.response = response;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
}
