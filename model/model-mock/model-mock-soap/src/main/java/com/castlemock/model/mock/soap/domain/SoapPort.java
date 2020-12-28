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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class SoapPort {

    private String id;
    private String name;
    private String uri;
    private String projectId;
    private String invokeAddress;
    private List<SoapOperation> operations = new CopyOnWriteArrayList<SoapOperation>();
    private Map<SoapOperationStatus, Integer> statusCount = new HashMap<SoapOperationStatus, Integer>();

    public SoapPort(){

    }

    public SoapPort(final Builder builder){
        this.id = Objects.requireNonNull(builder.id);
        this.name = Objects.requireNonNull(builder.name);
        this.uri = Objects.requireNonNull(builder.uri);
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.invokeAddress = Objects.requireNonNull(builder.invokeAddress);
        this.operations = Optional.ofNullable(builder.operations).orElseGet(CopyOnWriteArrayList::new);
        this.statusCount = Optional.ofNullable(builder.statusCount).orElseGet(HashMap::new);
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElementWrapper(name = "operations")
    @XmlElement(name = "operation")
    public List<SoapOperation> getOperations() {
        return operations;
    }

    public void setOperations(List<SoapOperation> operations) {
        this.operations = operations;
    }

    @XmlTransient
    public Map<SoapOperationStatus, Integer> getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(Map<SoapOperationStatus, Integer> statusCount) {
        this.statusCount = statusCount;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @XmlElement
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @XmlTransient
    public String getInvokeAddress() {
        return invokeAddress;
    }

    public void setInvokeAddress(String invokeAddress) {
        this.invokeAddress = invokeAddress;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private String uri;
        private String projectId;
        private String invokeAddress;
        private List<SoapOperation> operations = new CopyOnWriteArrayList<SoapOperation>();
        private Map<SoapOperationStatus, Integer> statusCount = new HashMap<SoapOperationStatus, Integer>();

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder projectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder operations(List<SoapOperation> operations) {
            this.operations = operations;
            return this;
        }

        public Builder invokeAddress(String invokeAddress) {
            this.invokeAddress = invokeAddress;
            return this;
        }

        public Builder statusCount(Map<SoapOperationStatus, Integer> statusCount) {
            this.statusCount = statusCount;
            return this;
        }

        public SoapPort build() {
            return new SoapPort(this);
        }
    }
}
