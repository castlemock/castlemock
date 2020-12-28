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

package com.castlemock.model.core.model.project.domain;

import com.castlemock.model.core.model.TypeIdentifiable;
import com.castlemock.model.core.model.TypeIdentifier;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.Objects;

/**
 * The Project DTO is a DTO (Data transfer object) class for the project class.
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class Project implements TypeIdentifiable {

    protected String id;
    protected String name;
    protected Date updated;
    protected Date created;
    protected String description;
    protected TypeIdentifier typeIdentifier;

    /**
     * The default constructor for the project DTO
     */
    public Project() {
        // Empty constructor
    }

    /**
     * The constructor provides the functionality to initialize a new project DTO based on another project DTO
     * @param projectDto The project DTO that the new project DTO is going to based on
     */
    protected Project(Project projectDto){
        this.id = projectDto.getId();
        this.name = projectDto.getName();
        this.updated = projectDto.getUpdated();
        this.created = projectDto.getCreated();
        this.description = projectDto.getDescription();
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

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @XmlElement
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    @XmlTransient
    public TypeIdentifier getTypeIdentifier() {
        return typeIdentifier;
    }

    @Override
    public void setTypeIdentifier(final TypeIdentifier typeIdentifier) {
        this.typeIdentifier = typeIdentifier;
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
                Objects.equals(description, project.description) &&
                Objects.equals(typeIdentifier, project.typeIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, updated, created, description, typeIdentifier);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", updated=" + updated +
                ", created=" + created +
                ", description='" + description + '\'' +
                ", typeIdentifier=" + typeIdentifier +
                '}';
    }


}
