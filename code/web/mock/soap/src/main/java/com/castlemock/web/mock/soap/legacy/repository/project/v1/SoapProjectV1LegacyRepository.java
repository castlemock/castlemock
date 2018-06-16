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

package com.castlemock.web.mock.soap.legacy.repository.project.v1;

import com.castlemock.core.basis.legacy.model.project.v1.domain.ProjectV1;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.project.domain.*;
import com.castlemock.web.basis.legacy.repository.AbstractLegacyRepositoryImpl;
import com.castlemock.web.mock.soap.repository.project.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class SoapProjectV1LegacyRepository extends AbstractLegacyRepositoryImpl<SoapProjectV1LegacyRepository.SoapProjectV1, SoapProject, String> {

    @Value(value = "${legacy.soap.project.v1.directory}")
    private String projectLegacyFileDirectory;
    @Value(value = "${legacy.soap.resource.v1.directory}")
    private String resourceLegacyFileDirectory;

    @Value(value = "${soap.project.file.extension}")
    private String projectFileExtension;
    @Value(value = "${soap.resource.file.extension}")
    private String resourceFileExtension;
    @Value(value = "${soap.resource.file.directory}")
    private String resourceFileDirectory;



    @Autowired
    private SoapProjectRepository projectRepository;
    @Autowired
    private SoapPortRepository portRepository;
    @Autowired
    private SoapResourceRepository resourceRepository;
    @Autowired
    private SoapOperationRepository operationRepository;
    @Autowired
    private SoapMockResponseRepository mockResponseRepository;



    /**
     * The initialize method is responsible for initiating the file repository. This procedure involves loading
     * the types (TYPE) from the file system and store them in the collection.
     */
    @Override
    public void initialize() {

        final String oldWSDLPath = resourceLegacyFileDirectory + "/wsdl";
        final String newWSDLPath = resourceFileDirectory + "/wsdl";

        // Move the old event files to the new directory
        fileRepositorySupport.moveAllFiles(oldWSDLPath,
                newWSDLPath, resourceFileExtension);


        Collection<SoapProjectV1> projects =
                fileRepositorySupport.load(SoapProjectV1.class, projectLegacyFileDirectory, projectFileExtension);

        for(SoapProjectV1 projectV1 : projects){
            save(projectV1);
            fileRepositorySupport.delete(this.projectLegacyFileDirectory, projectV1.getId() + this.projectFileExtension);
        }
    }

    /**
     * Save an instance.
     * @param type The instance that will be saved.
     */
    @Override
    protected SoapProject save(final SoapProjectV1 type) {
        SoapProject project = convert(type);
        if(this.projectRepository.exists(project.getId())){
            throw new IllegalArgumentException("A project with the following key already exists: " + project.getId());
        }

        this.projectRepository.save(project);
        for(SoapResource resource : project.getResources()){
            if(this.resourceRepository.exists(resource.getId())){
                throw new IllegalArgumentException("A resource with the following key already exists: " + resource.getId());
            }

            this.resourceRepository.save(resource);
        }
        for(SoapPort port : project.getPorts()){
            if(this.portRepository.exists(port.getId())){
                throw new IllegalArgumentException("A port with the following key already exists: " + port.getId());
            }

            this.portRepository.save(port);
            for(SoapOperation operation : port.getOperations()){
                if(this.operationRepository.exists(operation.getId())){
                    throw new IllegalArgumentException("An operation with the following key already exists: " + operation.getId());
                }

                this.operationRepository.save(operation);
                for(SoapMockResponse mockResponse : operation.getMockResponses()){
                    if(this.mockResponseRepository.exists(mockResponse.getId())){
                        throw new IllegalArgumentException("A mocked response with the following key already exists: " + mockResponse.getId());
                    }

                    this.mockResponseRepository.save(mockResponse);
                }
            }
        }
        return project;
    }


    private SoapProject convert(SoapProjectV1 projectV1){
        SoapProject project = new SoapProject();
        project.setCreated(projectV1.getCreated());
        project.setUpdated(projectV1.getUpdated());
        project.setDescription(projectV1.getDescription());
        project.setId(projectV1.getId());
        project.setName(projectV1.getName());

        for(SoapResourceV1 soapResourceV1 : projectV1.getResources()){
            SoapResource resource = new SoapResource();
            resource.setName(soapResourceV1.getName());
            resource.setId(soapResourceV1.getId());
            resource.setType(soapResourceV1.getType());
            resource.setProjectId(project.getId());
            project.getResources().add(resource);
        }

        for(SoapPortV1 portV1 : projectV1.getPorts()){
            SoapPort port = new SoapPort();
            port.setName(portV1.getName());
            port.setUri(portV1.getUri());
            port.setId(portV1.getId());
            port.setProjectId(project.getId());
            project.getPorts().add(port);

            for(SoapOperationV1 operationV1 : portV1.getOperations()){
                SoapOperation operation = new SoapOperation();
                operation.setId(operationV1.getId());
                operation.setName(operationV1.getName());
                operation.setIdentifier(operationV1.getIdentifier());
                operation.setResponseStrategy(operationV1.getResponseStrategy());
                operation.setStatus(operationV1.getStatus());
                operation.setHttpMethod(operationV1.getHttpMethod());
                operation.setSoapVersion(operationV1.getSoapVersion());
                operation.setDefaultBody(operationV1.getDefaultBody());
                operation.setCurrentResponseSequenceIndex(operationV1.getCurrentResponseSequenceIndex());
                operation.setForwardedEndpoint(operationV1.getForwardedEndpoint());
                operation.setOriginalEndpoint(operationV1.getOriginalEndpoint());
                operation.setDefaultXPathMockResponseId(operationV1.getDefaultXPathMockResponseId());
                operation.setSimulateNetworkDelay(operationV1.getSimulateNetworkDelay());
                operation.setNetworkDelay(operationV1.getNetworkDelay());
                operation.setPortId(port.getId());
                port.getOperations().add(operation);

                for(SoapMockResponseV1 mockResponseV1 : operationV1.getMockResponses()){
                    SoapMockResponse mockResponse = new SoapMockResponse();
                    mockResponse.setId(mockResponseV1.getId());
                    mockResponse.setName(mockResponseV1.getName());
                    mockResponse.setBody(mockResponseV1.getBody());
                    mockResponse.setOperationId(operation.getId());
                    mockResponse.setStatus(mockResponseV1.getStatus());
                    mockResponse.setHttpStatusCode(mockResponseV1.getHttpStatusCode());
                    mockResponse.setUsingExpressions(mockResponseV1.isUsingExpressions());
                    mockResponse.setXpathExpression(mockResponseV1.getXpathExpression());

                    List<HttpHeader> httpHeaders = new ArrayList<>();
                    if(mockResponseV1.getHttpHeaders() != null){
                        for(HttpHeader httpHeader : mockResponseV1.getHttpHeaders()){
                            HttpHeader httpHeaderDto = new HttpHeader();
                            httpHeaderDto.setName(httpHeader.getName());
                            httpHeaderDto.setValue(httpHeader.getValue());
                            httpHeaders.add(httpHeaderDto);
                        }
                    }
                    List<ContentEncoding> contentEncodings = new ArrayList<>();
                    if(mockResponseV1.getContentEncodings() != null){
                        contentEncodings = mockResponseV1.getContentEncodings();
                    }

                    mockResponse.setHttpHeaders(httpHeaders);
                    mockResponse.setContentEncodings(contentEncodings);
                    operation.getMockResponses().add(mockResponse);
                }
            }
        }
        return project;
    }

    @XmlRootElement(name = "soapProject")
    protected static class SoapProjectV1 extends ProjectV1 {

        private List<SoapPortV1> ports = new CopyOnWriteArrayList<SoapPortV1>();
        private List<SoapResourceV1> resources = new CopyOnWriteArrayList<SoapResourceV1>();

        @XmlElementWrapper(name = "ports")
        @XmlElement(name = "port")
        public List<SoapPortV1> getPorts() {
            return ports;
        }

        public void setPorts(List<SoapPortV1> ports) {
            this.ports = ports;
        }

        @XmlElementWrapper(name = "resources")
        @XmlElement(name = "resource")
        public List<SoapResourceV1> getResources() {
            return resources;
        }

        public void setResources(List<SoapResourceV1> resources) {
            this.resources = resources;
        }


    }

    @XmlRootElement(name = "soapPort")
    protected static class SoapPortV1 implements Saveable<String> {

        private String id;
        private String name;
        private String uri;
        private List<SoapOperationV1> operations = new CopyOnWriteArrayList<SoapOperationV1>();

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
        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        @XmlElementWrapper(name = "operations")
        @XmlElement(name = "operation")
        public List<SoapOperationV1> getOperations() {
            return operations;
        }

        public void setOperations(List<SoapOperationV1> operations) {
            this.operations = operations;
        }

    }

    @XmlRootElement
    protected static class SoapOperationV1 implements Saveable<String> {

        private String id;
        private String name;
        private String identifier;
        private List<SoapMockResponseV1> mockResponses = new CopyOnWriteArrayList<SoapMockResponseV1>();
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

        @XmlElementWrapper(name = "mockResponses")
        @XmlElement(name = "mockResponse")
        public List<SoapMockResponseV1> getMockResponses() {
            return mockResponses;
        }

        public void setMockResponses(List<SoapMockResponseV1> mockResponses) {
            this.mockResponses = mockResponses;
        }

    }

    @XmlRootElement(name = "soapResource")
    protected static class SoapResourceV1 implements Saveable<String> {

        private String id;
        private String name;
        private String projectId;
        private SoapResourceType type;

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
        public SoapResourceType getType() {
            return type;
        }

        public void setType(SoapResourceType type) {
            this.type = type;
        }

        @XmlElement
        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }
    }

    @XmlRootElement(name = "soapMockResponse")
    protected static class SoapMockResponseV1 implements Saveable<String> {

        private String id;
        private String name;
        private String body;
        private String operationId;
        private SoapMockResponseStatus status;
        private Integer httpStatusCode;
        private boolean usingExpressions;
        private String xpathExpression;
        private List<HttpHeader> httpHeaders = new CopyOnWriteArrayList<HttpHeader>();
        private List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<ContentEncoding>();

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
        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        @XmlElement
        public String getOperationId() {
            return operationId;
        }

        public void setOperationId(String operationId) {
            this.operationId = operationId;
        }

        @XmlElement
        public SoapMockResponseStatus getStatus() {
            return status;
        }

        public void setStatus(SoapMockResponseStatus status) {
            this.status = status;
        }

        @XmlElement
        public Integer getHttpStatusCode() {
            return httpStatusCode;
        }

        public void setHttpStatusCode(Integer httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
        }

        @XmlElement
        public boolean isUsingExpressions() {
            return usingExpressions;
        }

        public void setUsingExpressions(boolean usingExpressions) {
            this.usingExpressions = usingExpressions;
        }

        @XmlElementWrapper(name = "httpHeaders")
        @XmlElement(name = "httpHeader")
        public List<HttpHeader> getHttpHeaders() {
            return httpHeaders;
        }

        public void setHttpHeaders(List<HttpHeader> httpHeaders) {
            this.httpHeaders = httpHeaders;
        }


        @XmlElement
        public String getXpathExpression() {
            return xpathExpression;
        }

        public void setXpathExpression(String xpathExpression) {
            this.xpathExpression = xpathExpression;
        }

        @XmlElementWrapper(name = "contentEncodings")
        @XmlElement(name = "contentEncoding")
        public List<ContentEncoding> getContentEncodings() {
            return contentEncodings;
        }

        public void setContentEncodings(List<ContentEncoding> contentEncodings) {
            this.contentEncodings = contentEncodings;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof SoapMockResponseV1))
                return false;

            SoapMockResponseV1 that = (SoapMockResponseV1) o;

            if (id != null ? !id.equals(that.id) : that.id != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }
    }

}
