package com.castlemock.repository.core.mongodb.project;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.repository.core.mongodb.MongoRepository;
import org.dozer.Mapping;

import java.util.Date;

public abstract class AbstractProjectMongoRepository<T extends AbstractProjectMongoRepository.ProjectDocument, D> extends MongoRepository<T, D, String> {

    public static abstract class ProjectDocument implements Saveable<String> {

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

        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Date getUpdated() {
            return updated;
        }

        public void setUpdated(Date updated) {
            this.updated = updated;
        }

        public Date getCreated() {
            return created;
        }

        public void setCreated(Date created) {
            this.created = created;
        }

    }
}
