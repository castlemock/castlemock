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

import com.castlemock.model.core.project.Project;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = RestProject.Builder.class)
public class RestProject extends Project {

    @XmlElementWrapper(name = "applications")
    @XmlElement(name = "application")
    private final List<RestApplication> applications;


    private RestProject(final Builder builder){
        super(builder);
        this.applications = Optional.ofNullable(builder.applications).orElseGet(List::of);
    }

    public List<RestApplication> getApplications() {
        return Optional.ofNullable(applications)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof RestProject that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(applications, that.applications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), applications);
    }

    @Override
    public String toString() {
        return "RestProject{" +
                "applications=" + applications +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .id(id)
                .name(name)
                .created(created)
                .updated(updated)
                .description(description)
                .applications(Optional.ofNullable(applications)
                        .map(applications -> applications.stream()
                                .map(RestApplication::toBuilder)
                                .map(RestApplication.Builder::build)
                                .collect(Collectors.toList()))
                        .orElse(null));
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder extends Project.Builder<Builder> {
        private List<RestApplication> applications;

        private Builder() {
        }

        public Builder applications(final List<RestApplication> applications) {
            this.applications = applications;
            return this;
        }

        public RestProject build() {
            return new RestProject(this);
        }
    }
}
