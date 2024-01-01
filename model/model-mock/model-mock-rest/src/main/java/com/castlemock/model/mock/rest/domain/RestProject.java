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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class RestProject extends Project {

    private List<RestApplication> applications;

    private RestProject() {

    }

    private RestProject(final Builder builder){
        super(builder);
        this.applications = Optional.ofNullable(builder.applications).orElseGet(CopyOnWriteArrayList::new);
    }

    @XmlElementWrapper(name = "applications")
    @XmlElement(name = "application")
    public List<RestApplication> getApplications() {
        return applications;
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
                .applications(applications);
    }

    public static final class Builder extends Project.Builder<Builder> {
        private List<RestApplication> applications = new CopyOnWriteArrayList<>();

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
