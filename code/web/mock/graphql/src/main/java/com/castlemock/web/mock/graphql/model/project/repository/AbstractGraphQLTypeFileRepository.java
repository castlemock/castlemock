package com.castlemock.web.mock.graphql.model.project.repository;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.web.basis.model.RepositoryImpl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public abstract class AbstractGraphQLTypeFileRepository<T extends AbstractGraphQLTypeFileRepository.GraphQLTypeFile, D> extends RepositoryImpl<T, D, String> {

    @XmlRootElement(name = "graphQLType")
    protected static abstract class GraphQLTypeFile implements Saveable<String> {

        private String id;
        private String name;
        private String description;
        private String applicationId;

        @Override
        @XmlElement
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
        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }
    }



}
