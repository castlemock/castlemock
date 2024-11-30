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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import jakarta.xml.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = SoapPort.Builder.class)
public class SoapPort {

    @XmlElement
    private final String id;

    @XmlElement
    private final String name;

    @XmlElement
    private final String uri;

    @XmlElement
    private final String projectId;

    @XmlTransient
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String invokeAddress;

    @XmlElementWrapper(name = "operations")
    @XmlElement(name = "operation")
    private final List<SoapOperation> operations;

    @XmlTransient
    private final Map<SoapOperationStatus, Integer> statusCount;

    private SoapPort(final Builder builder){
        this.id = Objects.requireNonNull(builder.id);
        this.name = Objects.requireNonNull(builder.name);
        this.uri = Objects.requireNonNull(builder.uri);
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.invokeAddress = builder.invokeAddress;
        this.operations = Optional.ofNullable(builder.operations).orElseGet(List::of);
        this.statusCount = Optional.ofNullable(builder.statusCount).orElseGet(Map::of);
    }


    public String getId() {
        return id;
    }


    public List<SoapOperation> getOperations() {
        return Optional.ofNullable(operations)
                .map(List::copyOf)
                .orElseGet(List::of);
    }


    public Map<SoapOperationStatus, Integer> getStatusCount() {
        return Optional.ofNullable(statusCount)
                .map(Map::copyOf)
                .orElseGet(Map::of);
    }


    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public String getProjectId() {
        return projectId;
    }

    public Optional<String> getInvokeAddress() {
        return Optional.ofNullable(invokeAddress);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .id(this.id)
                .projectId(this.projectId)
                .invokeAddress(this.invokeAddress)
                .name(this.name)
                .uri(this.uri)
                .operations(Optional.ofNullable(this.operations)
                        .map(operations -> operations.stream()
                                .map(SoapOperation::toBuilder)
                                .map(SoapOperation.Builder::build)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .statusCount(Optional.ofNullable(this.statusCount)
                        .map(HashMap::new)
                        .orElse(null));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoapPort soapPort = (SoapPort) o;
        return Objects.equals(id, soapPort.id) && Objects.equals(name, soapPort.name) && Objects.equals(uri, soapPort.uri) && Objects.equals(projectId, soapPort.projectId) && Objects.equals(invokeAddress, soapPort.invokeAddress) && Objects.equals(operations, soapPort.operations) && Objects.equals(statusCount, soapPort.statusCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, uri, projectId, invokeAddress, operations, statusCount);
    }

    @Override
    public String toString() {
        return "SoapPort{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", projectId='" + projectId + '\'' +
                ", invokeAddress='" + invokeAddress + '\'' +
                ", operations=" + operations +
                ", statusCount=" + statusCount +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String id;
        private String name;
        private String uri;
        private String projectId;
        private String invokeAddress;
        private List<SoapOperation> operations;
        private Map<SoapOperationStatus, Integer> statusCount;

        private Builder() {
        }

        public Builder id(final String id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder uri(final String uri) {
            this.uri = uri;
            return this;
        }

        public Builder projectId(final String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder operations(final List<SoapOperation> operations) {
            this.operations = operations;
            return this;
        }

        public Builder invokeAddress(final String invokeAddress) {
            this.invokeAddress = invokeAddress;
            return this;
        }

        public Builder statusCount(final Map<SoapOperationStatus, Integer> statusCount) {
            this.statusCount = statusCount;
            return this;
        }

        public SoapPort build() {
            return new SoapPort(this);
        }
    }
}
