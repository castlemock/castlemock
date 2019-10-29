package com.castlemock.repository.graphql.mongodb.project;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLArgument;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLAttributeType;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLOperationStatus;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLResponseStrategy;
import com.castlemock.repository.core.mongodb.MongoRepository;
import org.dozer.Mapping;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Mohammad Hewedy
 * @since 1.35
 */
public abstract class AbstractGraphQLOperationMongoRepository<T extends AbstractGraphQLOperationMongoRepository.GraphQLOperationDocument, D> extends MongoRepository<T, D, String> {

    @Document(collection = "graphQLOperation")
    protected static abstract class GraphQLOperationDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("description")
        private String description;
        @Mapping("applicationId")
        private String applicationId;
        @Mapping("networkDelay")
        private long networkDelay;
        @Mapping("httpMethod")
        private HttpMethod httpMethod;
        @Mapping("forwardedEndpoint")
        private String forwardedEndpoint;
        @Mapping("originalEndpoint")
        private String originalEndpoint;
        @Mapping("simulateNetworkDelay")
        private boolean simulateNetworkDelay;
        @Mapping("status")
        private GraphQLOperationStatus status;
        @Mapping("responseStrategy")
        private GraphQLResponseStrategy responseStrategy;
        @Mapping("result")
        private GraphQLResultDocument result;
        @Mapping("arguments")
        private List<GraphQLArgument> arguments = new CopyOnWriteArrayList<GraphQLArgument>();


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

        public GraphQLResponseStrategy getResponseStrategy() {
            return responseStrategy;
        }

        public void setResponseStrategy(GraphQLResponseStrategy responseStrategy) {
            this.responseStrategy = responseStrategy;
        }

        public GraphQLOperationStatus getStatus() {
            return status;
        }

        public void setStatus(GraphQLOperationStatus status) {
            this.status = status;
        }

        public long getNetworkDelay() {
            return networkDelay;
        }

        public void setNetworkDelay(long networkDelay) {
            this.networkDelay = networkDelay;
        }

        public HttpMethod getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
        }

        public String getForwardedEndpoint() {
            return forwardedEndpoint;
        }

        public void setForwardedEndpoint(String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
        }

        public String getOriginalEndpoint() {
            return originalEndpoint;
        }

        public void setOriginalEndpoint(String originalEndpoint) {
            this.originalEndpoint = originalEndpoint;
        }

        public boolean isSimulateNetworkDelay() {
            return simulateNetworkDelay;
        }

        public void setSimulateNetworkDelay(boolean simulateNetworkDelay) {
            this.simulateNetworkDelay = simulateNetworkDelay;
        }

        public GraphQLResultDocument getResult() {
            return result;
        }

        public void setResult(GraphQLResultDocument result) {
            this.result = result;
        }

        public List<GraphQLArgument> getArguments() {
            return arguments;
        }

        public void setArguments(List<GraphQLArgument> arguments) {
            this.arguments = arguments;
        }

        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }
    }

    @Document(collection = "graphQLResult")
    protected static class GraphQLResultDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("typeName")
        private String typeName;
        @Mapping("typeId")
        private String typeId;
        @Mapping("nullable")
        private Boolean nullable;
        @Mapping("listable")
        private Boolean listable;
        @Mapping("attributeType")
        private GraphQLAttributeType attributeType;

        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public Boolean getNullable() {
            return nullable;
        }

        public void setNullable(Boolean nullable) {
            this.nullable = nullable;
        }

        public Boolean getListable() {
            return listable;
        }

        public void setListable(Boolean listable) {
            this.listable = listable;
        }

        public GraphQLAttributeType getAttributeType() {
            return attributeType;
        }

        public void setAttributeType(GraphQLAttributeType attributeType) {
            this.attributeType = attributeType;
        }
    }

}
