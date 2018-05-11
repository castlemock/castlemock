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

package com.castlemock.core.mock.graphql.model.event.domain;

import com.castlemock.core.basis.model.event.domain.Event;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLOperation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
@XmlRootElement
public class GraphQLEvent extends Event {

    private GraphQLRequest request;
    private GraphQLResponse response;
    private String projectId;
    private String applicationId;

    /**
     * Constructor for the SOAP event DTO
     * @param request The SOAP request that the event is representing
     * @param projectId The id of the SOAP project that is affected by the provided SOAP request
     * @param applicationId The id of the SOAP port that is affected by the provided SOAP request
     * @see GraphQLOperation
     */
    public GraphQLEvent(final GraphQLRequest request,
                        final String projectId,
                        final String applicationId) {
        super(applicationId);
        this.request = request;
        this.projectId = projectId;
        this.applicationId = applicationId;
    }


    /**
     * The finish method is used to sent the response that was sent back, but was also
     * to set the date/time for when the event ended.
     * @param response
     */
    public void finish(final GraphQLResponse response) {
        this.response = response;
        setEndDate(new Date());
    }

    @XmlElement
    public GraphQLRequest getRequest() {
        return request;
    }

    public void setRequest(GraphQLRequest request) {
        this.request = request;
    }

    @XmlElement
    public GraphQLResponse getResponse() {
        return response;
    }

    public void setResponse(GraphQLResponse response) {
        this.response = response;
    }

    @XmlElement
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @XmlElement
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

}
