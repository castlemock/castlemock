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


package com.castlemock.repository.soap.file.project;

import com.castlemock.model.core.Saveable;
import com.castlemock.model.core.SearchQuery;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.core.SearchValidator;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationIdentifier;
import com.castlemock.model.mock.soap.domain.SoapOperationIdentifyStrategy;
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapResponseStrategy;
import com.castlemock.model.mock.soap.domain.SoapVersion;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.soap.project.SoapOperationRepository;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Optional;

@Repository
@Profile(Profiles.FILE)
public class SoapOperationFileRepository extends FileRepository<SoapOperationFileRepository.SoapOperationFile, SoapOperation, String> implements SoapOperationRepository {

    @Value(value = "${soap.operation.file.directory}")
    private String fileDirectory;
    @Value(value = "${soap.operation.file.extension}")
    private String fileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from. The method is abstract and every subclass is responsible for
     * overriding the method and provided the directory for their corresponding file type.
     *
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return fileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * The method is abstract and every subclass is responsible for overriding the method and provided the postfix
     * for their corresponding file type.
     *
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return fileExtension;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     */
    @Override
    protected void checkType(final SoapOperationFile type) {

    }

    /**
     * The post initialize method can be used to run functionality for a specific service. The method is called when
     * the method {@link #initialize} has finished successful. The method does not contain any functionality and the
     * whole idea is the it should be overridden by subclasses, but only if certain functionality is required to
     * run after the {@link #initialize} method has completed.
     * @see #initialize
     */
    @Override
    public void postInitiate(){
        for(SoapOperationFile soapOperation : this.collection.values()){
            if(soapOperation.getOperationIdentifier() == null){
                SoapOperationIdentifierFile operationIdentifier =
                        new SoapOperationIdentifierFile();
                operationIdentifier.setName(soapOperation.getIdentifier());

                soapOperation.setOperationIdentifier(operationIdentifier);
                soapOperation.setIdentifier(null);
                save(soapOperation);
            }

            if(soapOperation.getIdentifyStrategy() == null){
                soapOperation.setIdentifyStrategy(SoapOperationIdentifyStrategy.ELEMENT_NAMESPACE);
                save(soapOperation);
            }

            if(soapOperation.getCurrentResponseSequenceIndex() == null){
                soapOperation.setCurrentResponseSequenceIndex(0);
                save(soapOperation);
            }

        }
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SoapOperation> search(final SearchQuery query) {
        return this.collection.values()
                .stream()
                .filter(operation -> SearchValidator.validate(operation.getName(), query.getQuery()))
                .map(operation -> mapper.map(operation, SoapOperation.class))
                .toList();
    }

    @Override
    public void deleteWithPortId(final String portId) {
        this.collection.values()
                .stream()
                .filter(operation -> operation.getPortId().equals(portId))
                .map(SoapOperationFile::getId)
                .toList()
                .forEach(this::delete);
    }


    @Override
    public List<SoapOperation> findWithPortId(final String portId) {
        return this.collection.values()
                .stream()
                .filter(operation -> operation.getPortId().equals(portId))
                .map(operation -> this.mapper.map(operation, SoapOperation.class))
                .toList();
    }

    /**
     * The method provides the functionality to find a SOAP operation with a specific name
     * @param soapOperationName The name of the SOAP operation that should be retrieved
     * @return A SOAP operation that matches the search criteria. If no SOAP operation matches the provided
     * name then null will be returned.
     */
    @Override
    public Optional<SoapOperation> findWithName(final String soapPortId,
                                               final String soapOperationName){
        for(SoapOperationFile soapOperation : this.collection.values()){
            if(soapOperation.getPortId().equals(soapPortId) &&
                    soapOperation.getName().equals(soapOperationName)){
                return Optional.ofNullable(mapper.map(soapOperation, SoapOperation.class));
            }
        }
        return Optional.empty();
    }

    /**
     * Find a {@link SoapOperation} with a provided {@link HttpMethod}, {@link SoapVersion}
     * and an identifier.
     *
     * @param method     The HTTP method
     * @param version    The SOAP version
     * @param operationIdentifier The identifier
     * @return A {@link SoapOperation} that matches the provided search criteria.
     */
    @Override
    public Optional<SoapOperation> findWithMethodAndVersionAndIdentifier(final String portId, final HttpMethod method,
                                                               final SoapVersion version,
                                                               final SoapOperationIdentifier operationIdentifier) {
        for(SoapOperationFile soapOperation : this.collection.values()){
            if(soapOperation.getPortId().equals(portId) &&
                    soapOperation.getHttpMethod().equals(method) &&
                    soapOperation.getSoapVersion().equals(version)){

                final SoapOperationIdentifierFile operationIdentifierFile =
                        soapOperation.getOperationIdentifier();

                if(operationIdentifier.getName().equalsIgnoreCase(operationIdentifierFile.getName())){

                    // Three ways to identify SOAP operation:
                    // 1. Namespace is missing from the stored files (Legacy)
                    // 2. The identify strategy is ELEMENT (Ignore namespace)
                    // 3. Both the name and namespace is matching
                    if(operationIdentifierFile.getNamespace() == null ||
                            soapOperation.getIdentifyStrategy() == SoapOperationIdentifyStrategy.ELEMENT ||
                            operationIdentifierFile.getNamespace().equalsIgnoreCase(operationIdentifier.getNamespace().orElse(null))) {
                        return Optional.ofNullable(this.mapper.map(soapOperation, SoapOperation.class));
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Updates the current response sequence index.
     *
     * @param soapOperationId The operation id.
     * @param index           The new response sequence index.
     * @since 1.17
     */
    @Override
    public void setCurrentResponseSequenceIndex(final String soapOperationId, final Integer index) {
        SoapOperationFile soapOperation = collection.get(soapOperationId);
        soapOperation.setCurrentResponseSequenceIndex(index);
    }

    /**
     * Retrieve the {@link SoapPort} id
     * for the {@link SoapOperation} with the provided id.
     *
     * @param operationId The id of the {@link SoapOperation}.
     * @return The id of the port.
     * @since 1.20
     */
    @Override
    public String getPortId(String operationId) {
        final SoapOperationFile operationFile = this.collection.get(operationId);

        if(operationFile == null){
            throw new IllegalArgumentException("Unable to find an operation with the following id: " + operationId);
        }
        return operationFile.getPortId();
    }

    @XmlRootElement(name = "soapOperation")
    protected static class SoapOperationFile implements Saveable<String> {

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
        private SoapOperationIdentifierFile operationIdentifier;
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
        @Mapping("automaticForward")
        private boolean automaticForward;

        @XmlElement
        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(final String id) {
            this.id = id;
        }

        @XmlElement
        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        @XmlElement
        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(final String identifier) {
            this.identifier = identifier;
        }

        @XmlElement
        public SoapOperationIdentifierFile getOperationIdentifier() {
            return operationIdentifier;
        }

        public void setOperationIdentifier(final SoapOperationIdentifierFile operationIdentifier) {
            this.operationIdentifier = operationIdentifier;
        }

        @XmlElement
        public String getPortId() {
            return portId;
        }

        public void setPortId(final String portId) {
            this.portId = portId;
        }

        @XmlElement
        public SoapResponseStrategy getResponseStrategy() {
            return responseStrategy;
        }

        public void setResponseStrategy(final SoapResponseStrategy responseStrategy) {
            this.responseStrategy = responseStrategy;
        }

        @XmlElement
        public SoapOperationStatus getStatus() {
            return status;
        }

        public void setStatus(final SoapOperationStatus status) {
            this.status = status;
        }

        @XmlElement
        public HttpMethod getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(final HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
        }

        @XmlElement
        public SoapVersion getSoapVersion() {
            return soapVersion;
        }

        public void setSoapVersion(final SoapVersion soapVersion) {
            this.soapVersion = soapVersion;
        }

        @XmlElement
        public String getDefaultBody() {
            return defaultBody;
        }

        public void setDefaultBody(final String defaultBody) {
            this.defaultBody = defaultBody;
        }

        @XmlElement
        public Integer getCurrentResponseSequenceIndex() {
            return currentResponseSequenceIndex;
        }

        public void setCurrentResponseSequenceIndex(final Integer currentResponseSequenceIndex) {
            this.currentResponseSequenceIndex = currentResponseSequenceIndex;
        }

        @XmlElement
        public String getForwardedEndpoint() {
            return forwardedEndpoint;
        }

        public void setForwardedEndpoint(final String forwardedEndpoint) {
            this.forwardedEndpoint = forwardedEndpoint;
        }

        @XmlElement
        public String getOriginalEndpoint() {
            return originalEndpoint;
        }

        public void setOriginalEndpoint(final String originalEndpoint) {
            this.originalEndpoint = originalEndpoint;
        }

        @XmlElement
        public boolean getSimulateNetworkDelay() {
            return simulateNetworkDelay;
        }

        public void setSimulateNetworkDelay(final boolean simulateNetworkDelay) {
            this.simulateNetworkDelay = simulateNetworkDelay;
        }

        @XmlElement
        public long getNetworkDelay() {
            return networkDelay;
        }

        public void setNetworkDelay(final long networkDelay) {
            this.networkDelay = networkDelay;
        }

        @XmlElement
        public boolean getMockOnFailure() {
            return mockOnFailure;
        }

        public void setMockOnFailure(final boolean mockOnFailure) {
            this.mockOnFailure = mockOnFailure;
        }

        @XmlElement
        public SoapOperationIdentifyStrategy getIdentifyStrategy() {
            return identifyStrategy;
        }

        public void setIdentifyStrategy(final SoapOperationIdentifyStrategy identifyStrategy) {
            this.identifyStrategy = identifyStrategy;
        }

        @XmlElement
        public String getDefaultMockResponseId() {
            return defaultMockResponseId;
        }

        public void setDefaultMockResponseId(final String defaultMockResponseId) {
            this.defaultMockResponseId = defaultMockResponseId;
        }

        @XmlElement
        protected boolean getAutomaticForward() {
            return automaticForward;
        }

        protected void setAutomaticForward(boolean automaticForward) {
            this.automaticForward = automaticForward;
        }
    }


    @XmlRootElement(name = "soapOperationIdentifier")
    protected static class SoapOperationIdentifierFile {

        @Mapping("name")
        private String name;
        @Mapping("namespace")
        private String namespace;

        @XmlElement
        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        @XmlElement
        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

    }

}


