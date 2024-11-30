/*
 * Copyright 2024 Karl Dahlgren
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

package com.castlemock.repository.core.file.project.model;

import com.castlemock.model.core.Saveable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Objects;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.NONE)
public abstract class ProjectFile implements Saveable<String> {

    @XmlElement
    private String id;

    @XmlElement
    private String name;

    @XmlElement
    private String description;

    @XmlElement
    private Date updated;

    @XmlElement
    private Date created;

    protected ProjectFile() {

    }

    protected ProjectFile(final Builder<?> builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.updated = Objects.requireNonNull(builder.updated, "updated");
        this.created = Objects.requireNonNull(builder.created, "created");
        this.description = builder.description;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getUpdated() {
        return updated;
    }

    public Date getCreated() {
        return created;
    }

    @SuppressWarnings("unchecked")
    public static abstract class Builder<B extends Builder<B>> {

        private String id;
        private String name;
        private String description;
        private Date updated;
        private Date created;

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

        public B description(final String description) {
            this.description = description;
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

    }
}