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

package com.castlemock.model.mock.soap.domain;

import com.castlemock.model.core.event.Event;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = SoapEvent.Builder.class)
public class SoapEvent extends Event {

    @XmlElement
    private final SoapRequest request;

    @XmlElement
    private final SoapResponse response;

    @XmlElement
    private final String projectId;

    @XmlElement
    private final String portId;

    @XmlElement
    private final String operationId;


    private SoapEvent(final Builder builder){
        super(builder);
        this.request = Objects.requireNonNull(builder.request, "request");
        this.response = builder.response;
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.portId = Objects.requireNonNull(builder.portId, "portId");
        this.operationId = Objects.requireNonNull(builder.operationId, "operationId");
    }

    /**
     * Returns the SOAP request
     * @return The SOAP request
     */

    public SoapRequest getRequest() {
        return request;
    }

    /**
     * Returns the SOAP response variable
     * @return The SOAP response variable. The SOAP response will be null if the event has not yet finished
     */
    public Optional<SoapResponse> getResponse() {
        return Optional.ofNullable(response);
    }

    /**
     * Returns the SOAP operation id
     * @return The SOAP operation id
     */
    public String getOperationId() {
        return operationId;
    }

    /**
     * The SOAP project id is used to identify the project for which the
     * event spans from
     * @return The id of the project which the event affected
     */
    public String getProjectId() {
        return projectId;
    }


    /**
     * The SOAP port id is used to identify the port for which the
     * event spans from
     * @return The id of the port which the event affected
     */
    public String getPortId() {
        return portId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .id(this.id)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .resourceName(this.resourceName)
                .portId(this.portId)
                .operationId(this.operationId)
                .request(this.request)
                .response(this.response)
                .projectId(this.projectId);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder extends Event.Builder<Builder> {

        private SoapRequest request;
        private SoapResponse response;
        private String projectId;
        private String portId;
        private String operationId;

        private Builder() {
        }

        public Builder request(final SoapRequest request) {
            this.request = request;
            return this;
        }

        public Builder response(final SoapResponse response) {
            this.response = response;
            return this;
        }

        public Builder projectId(final String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder portId(final String portId) {
            this.portId = portId;
            return this;
        }

        public Builder operationId(final String operationId) {
            this.operationId = operationId;
            return this;
        }

        public SoapEvent build() {
            return new SoapEvent(this);
        }
    }
}
