package com.castlemock.repository.graphql.mongodb.project;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.repository.core.mongodb.MongoRepository;
import org.dozer.Mapping;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Mohammad Hewedy
 * @since 1.35
 */
public abstract class AbstractGraphQLTypeMongoRepository<T extends AbstractGraphQLTypeMongoRepository.GraphQLTypeDocument, D> extends MongoRepository<T, D, String> {

    @Document(collection = "graphQLType")
    protected static abstract class GraphQLTypeDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("description")
        private String description;
        @Mapping("applicationId")
        private String applicationId;

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

        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }
    }

}
