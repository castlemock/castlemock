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

package com.castlemock.model.core.project;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * The Project DTO is a DTO (Data transfer object) class for the project class.
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = Project.Builder.class)
public class Project {

    @XmlElement
    protected final String id;

    @XmlElement
    protected final String name;

    @XmlElement
    protected final Date updated;

    @XmlElement
    protected final Date created;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    protected final String description;

    protected Project(final Builder<?> builder){
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.updated = Objects.requireNonNull(builder.updated, "updated");
        this.created = Objects.requireNonNull(builder.created, "created");
        this.description = builder.description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getUpdated() {
        return updated;
    }

    public Date getCreated() {
        return created;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) &&
                Objects.equals(name, project.name) &&
                Objects.equals(updated, project.updated) &&
                Objects.equals(created, project.created) &&
                Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, updated, created, description);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", updated=" + updated +
                ", created=" + created +
                ", description='" + description + '\'' +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "")
    @SuppressWarnings("unchecked")
    public static class Builder<B extends Builder<B>> {

        private String id;
        private String name;
        private Date updated;
        private Date created;
        private String description;

        protected Builder() {
        }

        public B id(final String id) {
            this.id = id;
            return (B) this;
        }

        public B name(final String name) {
            this.name = name;
            return (B) this;
        }

        public B updated(final Date updated) {
            this.updated = updated;
            return (B) this;
        }

        public B created(final Date created) {
            this.created = created;
            return (B) this;
        }

        public B description(final String description) {
            this.description = description;
            return (B) this;
        }

    }

}
