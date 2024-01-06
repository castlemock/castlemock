package com.castlemock.repository.core.file.project.model;

import com.castlemock.model.core.Saveable;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Objects;

@XmlRootElement(name = "project")
public abstract class ProjectFile implements Saveable<String> {

    @Mapping("id")
    private String id;

    @Mapping("name")
    private String name;

    @Mapping("description")
    private String description;

    @Mapping("updated")
    private Date updated;

    @Mapping("created")
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

    @XmlElement
    @Override
    public String getId() {
        return id;
    }

    @Override
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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @SuppressWarnings("unchecked")
    public static abstract class Builder<B extends Builder<B>> {
        private String id;
        private String name;
        private String description;
        private Date updated;
        private Date created;

        protected Builder() {
        }

        public B id(String id) {
            this.id = id;
            return (B) this;
        }

        public B name(String name) {
            this.name = name;
            return (B) this;
        }

        public B description(String description) {
            this.description = description;
            return (B) this;
        }

        public B updated(Date updated) {
            this.updated = updated;
            return (B) this;
        }

        public B created(Date created) {
            this.created = created;
            return (B) this;
        }

    }
}