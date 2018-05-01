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


package com.castlemock.web.mock.soap.model.project.repository;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationStatus;
import com.castlemock.core.mock.soap.model.project.domain.SoapResponseStrategy;
import com.castlemock.core.mock.soap.model.project.domain.SoapVersion;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.web.basis.model.RepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class SoapOperationRepositoryImpl extends RepositoryImpl<SoapOperationRepositoryImpl.SoapOperationFile, SoapOperation, String> implements SoapOperationRepository {

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
    protected void checkType(SoapOperationFile type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SearchResult> search(SearchQuery query) {
        return null;
    }

    @Override
    public void deleteWithPortId(String portId) {
        Iterator<SoapOperationFile> iterator = this.collection.values().iterator();
        while (iterator.hasNext()){
            SoapOperationFile operation = iterator.next();
            if(operation.getPortId().equals(portId)){
                delete(operation.getId());
            }
        }
    }

    @Override
    public List<SoapOperation> findWithPortId(String portId) {
        final List<SoapOperation> operations = new ArrayList<>();
        for(SoapOperationFile operationFile : this.collection.values()){
            if(operationFile.getPortId().equals(portId)){
                SoapOperation operation = this.mapper.map(operationFile, SoapOperation.class);
                operations.add(operation);
            }
        }
        return operations;
    }

    /**
     * The method provides the functionality to find a SOAP operation with a specific name
     * @param soapOperationName The name of the SOAP operation that should be retrieved
     * @return A SOAP operation that matches the search criteria. If no SOAP operation matches the provided
     * name then null will be returned.
     */
    @Override
    public SoapOperation findWithName(final String soapPortId,
                                      final String soapOperationName){
        for(SoapOperationFile soapOperation : this.collection.values()){
            if(soapOperation.getPortId().equals(soapPortId) &&
                    soapOperation.getName().equals(soapOperationName)){
                return mapper.map(soapOperation, SoapOperation.class);
            }
        }
        return null;
    }

    /**
     * Find a {@link SoapOperation} with a provided {@link HttpMethod}, {@link SoapVersion}
     * and an identifier.
     *
     * @param method     The HTTP method
     * @param version    The SOAP version
     * @param identifier The identifier
     * @return A {@link SoapOperation} that matches the provided search criteria.
     */
    @Override
    public SoapOperation findWithMethodAndVersionAndIdentifier(final String portId, final HttpMethod method,
                                                               final SoapVersion version, final String identifier) {
        for(SoapOperationFile soapOperation : this.collection.values()){
            if(soapOperation.getPortId().equals(portId) &&
                    soapOperation.getHttpMethod().equals(method) &&
                    soapOperation.getSoapVersion().equals(version) &&
                    soapOperation.getIdentifier().equalsIgnoreCase(identifier)){
                return this.mapper.map(soapOperation, SoapOperation.class);
            }
        }
        return null;
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

    @XmlRootElement(name = "soapOperation")
    protected static class SoapOperationFile implements Saveable<String> {

        private String id;
        private String name;
        private String identifier;
        private String portId;
        private SoapResponseStrategy responseStrategy;
        private SoapOperationStatus status;
        private HttpMethod httpMethod;
        private SoapVersion soapVersion;
        private String defaultBody;
        private Integer currentResponseSequenceIndex;
        private String forwardedEndpoint;
        private String originalEndpoint;
        private String defaultXPathMockResponseId;
        private boolean simulateNetworkDelay;
        private long networkDelay;

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
        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        @XmlElement
        public String getPortId() {
            return portId;
        }

        public void setPortId(String portId) {
            this.portId = portId;
        }

        @XmlElement
        public SoapResponseStrategy getResponseStrategy() {
            return responseStrategy;
        }

        public void setResponseStrategy(SoapResponseStrategy responseStrategy) {
            this.responseStrategy = responseStrategy;
        }

        @XmlElement
        public SoapOperationStatus getStatus() {
            return status;
        }

        public void setStatus(SoapOperationStatus status) {
            this.status = status;
        }

        @XmlElement
        public HttpMethod getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
        }

        @XmlElement
        public SoapVersion getSoapVersion() {
            return soapVersion;
        }

        public void setSoapVersion(SoapVersion soapVersion) {
            this.soapVersion = soapVersion;
        }

        @XmlElement
        public String getDefaultBody() {
            return defaultBody;
        }

        public void setDefaultBody(String defaultBody) {
            this.defaultBody = defaultBody;
        }

        @XmlElement
        public Integer getCurrentResponseSequenceIndex() {
            return currentResponseSequenceIndex;
        }

        public void setCurrentResponseSequenceIndex(Integer currentResponseSequenceIndex) {
            this.currentResponseSequenceIndex = currentResponseSequenceIndex;
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
        public boolean getSimulateNetworkDelay() {
            return simulateNetworkDelay;
        }

        public void setSimulateNetworkDelay(boolean simulateNetworkDelay) {
            this.simulateNetworkDelay = simulateNetworkDelay;
        }

        @XmlElement
        public long getNetworkDelay() {
            return networkDelay;
        }

        public void setNetworkDelay(long networkDelay) {
            this.networkDelay = networkDelay;
        }

        @XmlElement
        public String getDefaultXPathMockResponseId() {
            return defaultXPathMockResponseId;
        }

        public void setDefaultXPathMockResponseId(String defaultXPathMockResponseId) {
            this.defaultXPathMockResponseId = defaultXPathMockResponseId;
        }
    }



}


