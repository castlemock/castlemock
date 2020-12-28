/*
 * Copyright 2018 Karl Dahlgren
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


package com.castlemock.repository.soap.mongodb.project;

import com.castlemock.model.core.Saveable;
import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.soap.domain.*;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.mongodb.MongoRepository;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import org.dozer.Mapping;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Mohammad Hewedy
 * @since 1.35
 */
@Repository
@Profile(Profiles.MONGODB)
public class SoapOperationMongoRepository extends MongoRepository<SoapOperationMongoRepository.SoapOperationDocument, SoapOperation, String> implements SoapOperationRepository {

    /**
     * The method is responsible for controller that the type that is about the be saved to mongodb is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to mongodb, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             mongodb.
     * @see #save
     */
    @Override
    protected void checkType(SoapOperationDocument type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SoapOperation> search(SearchQuery query) {
        Query nameQuery = getSearchQuery("name", query);
        List<SoapOperationDocument> operations =
                mongoOperations.find(nameQuery, SoapOperationDocument.class);
        return toDtoList(operations, SoapOperation.class);
    }

    @Override
    public void deleteWithPortId(String portId) {
        mongoOperations.remove(getPortIdQuery(portId), SoapOperationDocument.class);
    }


    @Override
    public List<SoapOperation> findWithPortId(String portId) {
        List<SoapOperationDocument> responses =
                mongoOperations.find(getPortIdQuery(portId), SoapOperationDocument.class);
        return toDtoList(responses, SoapOperation.class);
    }

    /**
     * The method provides the functionality to find a SOAP operation with a specific name
     *
     * @param soapOperationName The name of the SOAP operation that should be retrieved
     * @return A SOAP operation that matches the search criteria. If no SOAP operation matches the provided
     * name then null will be returned.
     */
    @Override
    public SoapOperation findWithName(final String soapPortId,
                                      final String soapOperationName) {
        Query portIdAndNameQuery = query(where("portId").is(soapPortId).and("name").is(soapOperationName));
        SoapOperationDocument operation =
                mongoOperations.findOne(portIdAndNameQuery, SoapOperationDocument.class);
        return operation == null ? null : mapper.map(operation, SoapOperation.class);
    }

    /**
     * Find a {@link SoapOperation} with a provided {@link HttpMethod}, {@link SoapVersion}
     * and an identifier.
     *
     * @param method              The HTTP method
     * @param version             The SOAP version
     * @param operationIdentifier The identifier
     * @return A {@link SoapOperation} that matches the provided search criteria.
     */
    @Override
    public SoapOperation findWithMethodAndVersionAndIdentifier(final String portId, final HttpMethod method,
                                                               final SoapVersion version,
                                                               final SoapOperationIdentifier operationIdentifier) {
        Query portIdMethodAndVersionQuery = query(where("portId").is(portId)
                .and("httpMethod").is(method)
                .and("soapVersion").is(version));
        List<SoapOperationDocument> soapOperations =
                mongoOperations.find(portIdMethodAndVersionQuery, SoapOperationDocument.class);

        for (SoapOperationDocument soapOperation : soapOperations) {
            final SoapOperationIdentifierDocument operationIdentifierFile =
                    soapOperation.getOperationIdentifier();

            if (operationIdentifier.getName().equalsIgnoreCase(operationIdentifierFile.getName())) {

                // Three ways to identify SOAP operation:
                // 1. Namespace is missing from the stored files (Legacy)
                // 2. The identify strategy is ELEMENT (Ignore namespace)
                // 3. Both the name and namespace is matching
                if (operationIdentifierFile.getNamespace() == null ||
                        soapOperation.getIdentifyStrategy() == SoapOperationIdentifyStrategy.ELEMENT ||
                        operationIdentifierFile.getNamespace().equalsIgnoreCase(operationIdentifier.getNamespace())) {
                    return this.mapper.map(soapOperation, SoapOperation.class);
                }
            }
        }
        return null;
    }

    /**
     * Updates the current response sequence index.
     *
     * @param soapOperationId The operation id.
     * @param index           The new response sequence index.
     */
    @Override
    public void setCurrentResponseSequenceIndex(final String soapOperationId, final Integer index) {
        SoapOperationDocument soapOperation = mongoOperations.findById(soapOperationId, SoapOperationDocument.class);

        if (soapOperation == null) {
            throw new IllegalArgumentException("Unable to find an operation with the following id: " + soapOperationId);
        }
        soapOperation.setCurrentResponseSequenceIndex(index);
    }

    /**
     * Retrieve the {@link SoapPort} id
     * for the {@link SoapOperation} with the provided id.
     *
     * @param operationId The id of the {@link SoapOperation}.
     * @return The id of the port.
     */
    @Override
    public String getPortId(String operationId) {
        SoapOperationDocument soapOperation = mongoOperations.findById(operationId, SoapOperationDocument.class);

        if (soapOperation == null) {
            throw new IllegalArgumentException("Unable to find an operation with the following id: " + operationId);
        }
        return soapOperation.getPortId();
    }

    @Document(collection = "soapOperation")
    protected static class SoapOperationDocument implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("portId")
        private String portId;
        @Mapping("responseStrategy")
        private SoapResponseStrategy responseStrategy;
        @Mapping("identifier")
        private String identifier;
        @Mapping("operationIdentifier")
        private SoapOperationIdentifierDocument operationIdentifier;
        @Mapping("status")
        private SoapOperationStatus status;
        @Mapping("httpMethod")
        private HttpMethod httpMethod;
        @Mapping("soapVersion")
        private SoapVersion soapVersion;
        @Mapping("defaultBody")
        private String defaultBody;
        @Mapping("currentResponseSequenceIndex")
        private Integer currentResponseSequenceIndex;
        @Mapping("forwardedEndpoint")
        private String forwardedEndpoint;
        @Mapping("originalEndpoint")
        private String originalEndpoint;
        @Mapping("defaultMockResponseId")
        private String defaultMockResponseId;
        @Mapping("simulateNetworkDelay")
        private boolean simulateNetworkDelay;
        @Mapping("networkDelay")
        private long networkDelay;
        @Mapping("mockOnFailure")
        private boolean mockOnFailure;
        @Mapping("identifyStrategy")
        private SoapOperationIdentifyStrategy identifyStrategy;

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

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public SoapOperationIdentifierDocument getOperationIdentifier() {
            return operationIdentifier;
        }

        public void setOperationIdentifier(SoapOperationIdentifierDocument operationIdentifier) {
            this.operationIdentifier = operationIdentifier;
        }

        public String getPortId() {
            return portId;
        }

        public void setPortId(String portId) {
            this.portId = portId;
        }

        public SoapResponseStrategy getResponseStrategy() {
            return responseStrategy;
        }

        public void setResponseStrategy(SoapResponseStrategy responseStrategy) {
            this.responseStrategy = responseStrategy;
        }

        public SoapOperationStatus getStatus() {
            return status;
        }

        public void setStatus(SoapOperationStatus status) {
            this.status = status;
        }

        public HttpMethod getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
        }

        public SoapVersion getSoapVersion() {
            return soapVersion;
        }

        public void setSoapVersion(SoapVersion soapVersion) {
            this.soapVersion = soapVersion;
        }

        public String getDefaultBody() {
            return defaultBody;
        }

        public void setDefaultBody(String defaultBody) {
            this.defaultBody = defaultBody;
        }

        public Integer getCurrentResponseSequenceIndex() {
            return currentResponseSequenceIndex;
        }

        public void setCurrentResponseSequenceIndex(Integer currentResponseSequenceIndex) {
            this.currentResponseSequenceIndex = currentResponseSequenceIndex;
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

        public boolean getSimulateNetworkDelay() {
            return simulateNetworkDelay;
        }

        public void setSimulateNetworkDelay(boolean simulateNetworkDelay) {
            this.simulateNetworkDelay = simulateNetworkDelay;
        }

        public long getNetworkDelay() {
            return networkDelay;
        }

        public void setNetworkDelay(long networkDelay) {
            this.networkDelay = networkDelay;
        }

        public String getDefaultMockResponseId() {
            return defaultMockResponseId;
        }

        public void setDefaultMockResponseId(String defaultMockResponseId) {
            this.defaultMockResponseId = defaultMockResponseId;
        }

        public boolean getMockOnFailure() {
            return mockOnFailure;
        }

        public void setMockOnFailure(boolean mockOnFailure) {
            this.mockOnFailure = mockOnFailure;
        }

        public SoapOperationIdentifyStrategy getIdentifyStrategy() {
            return identifyStrategy;
        }

        public void setIdentifyStrategy(SoapOperationIdentifyStrategy identifyStrategy) {
            this.identifyStrategy = identifyStrategy;
        }
    }

    @Document(collection = "soapOperationIdentifier")
    protected static class SoapOperationIdentifierDocument {

        @Mapping("name")
        private String name;
        @Mapping("namespace")
        private String namespace;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }
    }

    private Query getPortIdQuery(String portId) {
        return query(getPortIdCriteria(portId));
    }

    private Criteria getPortIdCriteria(String portId) {
        return where("portId").is(portId);
    }
}


