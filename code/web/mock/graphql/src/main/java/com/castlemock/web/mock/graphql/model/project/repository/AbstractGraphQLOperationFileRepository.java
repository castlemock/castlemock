package com.castlemock.web.mock.graphql.model.project.repository;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.graphql.model.project.domain.*;
import com.castlemock.web.basis.model.RepositoryImpl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractGraphQLOperationFileRepository<T extends AbstractGraphQLOperationFileRepository.GraphQLOperationFile, D> extends RepositoryImpl<T, D, String> {

    /**
     * @author Karl Dahlgren
     * @since 1.19
     */
    @XmlRootElement(name = "graphQLOperation")
    @XmlSeeAlso({GraphQLQueryRepositoryImpl.GraphQLQueryFile.class, GraphQLMutation.class, GraphQLSubscription.class})
    protected static abstract class GraphQLOperationFile implements Saveable<String> {

        private String id;
        private String name;
        private String description;
        private String applicationId;
        private long networkDelay;
        private HttpMethod httpMethod;
        private String forwardedEndpoint;
        private String originalEndpoint;
        private boolean simulateNetworkDelay;
        private GraphQLOperationStatus status;
        private GraphQLResponseStrategy responseStrategy;
        private GraphQLResultFile result;
        private List<GraphQLArgument> arguments = new CopyOnWriteArrayList<GraphQLArgument>();


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
        public GraphQLResponseStrategy getResponseStrategy() {
            return responseStrategy;
        }

        public void setResponseStrategy(GraphQLResponseStrategy responseStrategy) {
            this.responseStrategy = responseStrategy;
        }

        @XmlElement
        public GraphQLOperationStatus getStatus() {
            return status;
        }

        public void setStatus(GraphQLOperationStatus status) {
            this.status = status;
        }

        @XmlElement
        public long getNetworkDelay() {
            return networkDelay;
        }

        public void setNetworkDelay(long networkDelay) {
            this.networkDelay = networkDelay;
        }

        @XmlElement
        public HttpMethod getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
        }

        @XmlElement
        public String getForwardedEndpoint() {
            return forwardedEndpoint;
        }

        public void setForwardedEndpoint(String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
        }

        @XmlElement
        public String getOriginalEndpoint() {
            return originalEndpoint;
        }

        public void setOriginalEndpoint(String originalEndpoint) {
            this.originalEndpoint = originalEndpoint;
        }

        @XmlElement
        public boolean isSimulateNetworkDelay() {
            return simulateNetworkDelay;
        }

        public void setSimulateNetworkDelay(boolean simulateNetworkDelay) {
            this.simulateNetworkDelay = simulateNetworkDelay;
        }


        @XmlElement
        public GraphQLResultFile getResult() {
            return result;
        }

        public void setResult(GraphQLResultFile result) {
            this.result = result;
        }

        @XmlElementWrapper(name = "arguments")
        @XmlElement(name = "argument")
        public List<GraphQLArgument> getArguments() {
            return arguments;
        }

        public void setArguments(List<GraphQLArgument> arguments) {
            this.arguments = arguments;
        }

        @XmlElement
        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }
    }

    @XmlRootElement
    protected static class GraphQLResultFile implements Saveable<String> {

        private String id;
        private String typeName;
        private String typeId;
        private Boolean nullable;
        private Boolean listable;
        private GraphQLAttributeType attributeType;

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
        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        @XmlElement
        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        @XmlElement
        public Boolean getNullable() {
            return nullable;
        }

        public void setNullable(Boolean nullable) {
            this.nullable = nullable;
        }

        @XmlElement
        public Boolean getListable() {
            return listable;
        }

        public void setListable(Boolean listable) {
            this.listable = listable;
        }

        @XmlElement
        public GraphQLAttributeType getAttributeType() {
            return attributeType;
        }

        public void setAttributeType(GraphQLAttributeType attributeType) {
            this.attributeType = attributeType;
        }
    }



}
