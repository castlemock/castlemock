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

import com.castlemock.model.core.TypeIdentifier;
import com.castlemock.model.core.project.Project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
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

    private List<RestApplication> applications = new CopyOnWriteArrayList<RestApplication>();

    /**
     * The default REST project constructor
     */
    public RestProject() {
    }

    private RestProject(final Builder builder){
        this.id = Objects.requireNonNull(builder.id);
        this.name = Objects.requireNonNull(builder.name);
        this.created = Objects.requireNonNull(builder.created);
        this.updated = Objects.requireNonNull(builder.updated);
        this.description = Objects.requireNonNull(builder.description);
        this.typeIdentifier = Objects.requireNonNull(builder.typeIdentifier);
        this.applications = Optional.ofNullable(builder.applications).orElseGet(CopyOnWriteArrayList::new);
    }


    /**
     * The constructor will create a new REST project DTO based on the provided projectDto
     * @param projectDto The new REST project DTO will be based on the provided project DTO and contain
     *                   the same information as the provided project DTO
     */
    public RestProject(final Project projectDto){
        super(projectDto);
    }

    @XmlElementWrapper(name = "applications")
    @XmlElement(name = "application")
    public List<RestApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<RestApplication> applications) {
        this.applications = applications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestProject)) return false;
        if (!super.equals(o)) return false;
        RestProject that = (RestProject) o;
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

    public static final class Builder {
        private List<RestApplication> applications = new CopyOnWriteArrayList<RestApplication>();
        private String id;
        private String name;
        private Date updated;
        private Date created;
        private String description;
        private TypeIdentifier typeIdentifier;

        private Builder() {
        }

        public Builder applications(final List<RestApplication> applications) {
            this.applications = applications;
            return this;
        }

        public Builder id(final String id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder updated(final Date updated) {
            this.updated = updated;
            return this;
        }

        public Builder created(final Date created) {
            this.created = created;
            return this;
        }

        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        public Builder typeIdentifier(final TypeIdentifier typeIdentifier) {
            this.typeIdentifier = typeIdentifier;
            return this;
        }

        public RestProject build() {
            return new RestProject(this);
        }
    }
}
