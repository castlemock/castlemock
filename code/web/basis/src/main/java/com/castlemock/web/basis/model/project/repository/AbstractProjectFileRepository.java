package com.castlemock.web.basis.model.project.repository;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.web.basis.model.RepositoryImpl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

public abstract class AbstractProjectFileRepository<T extends AbstractProjectFileRepository.ProjectFile, D> extends RepositoryImpl<T, D, String> {

    @XmlRootElement(name = "project")
    public static abstract class ProjectFile implements Saveable<String> {

        private String id;
        private String name;
        private String description;
        private Date updated;
        private Date created;

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

    }


}
