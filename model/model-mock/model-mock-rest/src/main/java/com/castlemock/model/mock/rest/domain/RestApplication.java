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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = RestApplication.Builder.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RestApplication {

    @XmlElement
    private final String id;

    @XmlElement
    private final String name;

    @XmlElement
    private final String projectId;

    @XmlElementWrapper(name = "resources")
    @XmlElement(name = "resource")
    private final List<RestResource> resources;

    @XmlTransient
    private final Map<RestMethodStatus, Integer> statusCount;

    private RestApplication(final Builder builder){
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.resources = Optional.ofNullable(builder.resources)
                .orElseGet(List::of);
        this.statusCount = Optional.ofNullable(builder.statusCount)
                .orElseGet(Map::of);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProjectId() {
        return projectId;
    }

    public List<RestResource> getResources() {
        return List.copyOf(resources);
    }

    public Map<RestMethodStatus, Integer> getStatusCount() {
        return Map.copyOf(statusCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestApplication that = (RestApplication) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(resources, that.resources) &&
                Objects.equals(statusCount, that.statusCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, projectId, resources, statusCount);
    }

    @Override
    public String toString() {
        return "RestApplication{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", projectId='" + projectId + '\'' +
                ", resources=" + resources +
                ", statusCount=" + statusCount +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .id(this.id)
                .name(this.name)
                .projectId(this.projectId)
                .resources(this.resources)
                .statusCount(this.statusCount);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String id;
        private String name;
        private String projectId;
        private List<RestResource> resources = new CopyOnWriteArrayList<>();
        private Map<RestMethodStatus, Integer> statusCount = new HashMap<>();

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

        public Builder projectId(final String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder resources(final List<RestResource> resources) {
            this.resources = resources;
            return this;
        }

        public Builder statusCount(final Map<RestMethodStatus, Integer> statusCount) {
            this.statusCount = statusCount;
            return this;
        }

        public RestApplication build() {
            return new RestApplication(this);
        }
    }
}
