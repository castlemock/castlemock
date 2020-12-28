package com.castlemock.repository.core.file.project;

import com.castlemock.model.core.Saveable;
import com.castlemock.repository.core.file.FileRepository;
import org.dozer.Mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

public abstract class AbstractProjectFileRepository<T extends AbstractProjectFileRepository.ProjectFile, D> extends FileRepository<T, D, String> {

    @XmlRootElement(name = "project")
    public static abstract class ProjectFile implements Saveable<String> {

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
