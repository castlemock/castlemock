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

package com.castlemock.model.mock.rest.domain;

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
public class RestResource {

    private String id;
    private String name;
    private String uri;
    private String applicationId;
    private String invokeAddress;
    private List<RestMethod> methods = new CopyOnWriteArrayList<RestMethod>();
    private Map<RestMethodStatus, Integer> statusCount = new HashMap<RestMethodStatus, Integer>();

    public RestResource(){

    }

    private RestResource(final Builder builder){
        this.id = Objects.requireNonNull(builder.id);
        this.name = Objects.requireNonNull(builder.name);
        this.uri = Objects.requireNonNull(builder.uri);
        this.applicationId = Objects.requireNonNull(builder.applicationId);
        this.invokeAddress = Objects.requireNonNull(builder.invokeAddress);
        this.methods = Optional.ofNullable(builder.methods).orElseGet(CopyOnWriteArrayList::new);
        this.statusCount = Optional.ofNullable(builder.statusCount).orElseGet(HashMap::new);
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @XmlElement
    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    @XmlElement
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final String applicationId) {
        this.applicationId = applicationId;
    }

    @XmlElementWrapper(name = "methods")
    @XmlElement(name = "method")
    public List<RestMethod> getMethods() {
        return methods;
    }

    public void setMethods(final List<RestMethod> methods) {
        this.methods = methods;
    }

    @XmlElement
    public String getInvokeAddress() {
        return invokeAddress;
    }

    public void setInvokeAddress(final String invokeAddress) {
        this.invokeAddress = invokeAddress;
    }

    @XmlTransient
    public Map<RestMethodStatus, Integer> getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(final Map<RestMethodStatus, Integer> statusCount) {
        this.statusCount = statusCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestResource that = (RestResource) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(uri, that.uri) &&
                Objects.equals(applicationId, that.applicationId) &&
                Objects.equals(invokeAddress, that.invokeAddress) &&
                Objects.equals(methods, that.methods) &&
                Objects.equals(statusCount, that.statusCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, uri, applicationId, invokeAddress, methods, statusCount);
    }

    @Override
    public String toString() {
        return "RestResource{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", invokeAddress='" + invokeAddress + '\'' +
                ", methods=" + methods +
                ", statusCount=" + statusCount +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private String uri;
        private String applicationId;
        private String invokeAddress;
        private List<RestMethod> methods = new CopyOnWriteArrayList<RestMethod>();
        private Map<RestMethodStatus, Integer> statusCount = new HashMap<RestMethodStatus, Integer>();

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

        public Builder applicationId(final String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder invokeAddress(final String invokeAddress) {
            this.invokeAddress = invokeAddress;
            return this;
        }

        public Builder methods(final List<RestMethod> methods) {
            this.methods = methods;
            return this;
        }

        public Builder statusCount(final Map<RestMethodStatus, Integer> statusCount) {
            this.statusCount = statusCount;
            return this;
        }

        public RestResource build() {
            return new RestResource(this);
        }
    }
}
