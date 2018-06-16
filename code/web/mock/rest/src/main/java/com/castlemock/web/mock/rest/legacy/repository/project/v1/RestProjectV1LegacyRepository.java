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

package com.castlemock.web.mock.rest.legacy.repository.project.v1;

import com.castlemock.core.basis.legacy.model.project.v1.domain.ProjectV1;
import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.rest.model.project.domain.*;
import com.castlemock.web.basis.legacy.repository.AbstractLegacyRepositoryImpl;
import com.castlemock.web.mock.rest.repository.project.*;
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
public class RestProjectV1LegacyRepository extends AbstractLegacyRepositoryImpl<RestProjectV1LegacyRepository.RestProjectV1, RestProject, String> {

    @Value(value = "${legacy.rest.project.v1.directory}")
    private String fileDirectory;
    @Value(value = "${rest.project.file.extension}")
    private String fileExtension;

    @Autowired
    private RestProjectRepository projectRepository;
    @Autowired
    private RestApplicationRepository applicationRepository;
    @Autowired
    private RestResourceRepository resourceRepository;
    @Autowired
    private RestMethodRepository methodRepository;
    @Autowired
    private RestMockResponseRepository mockResponseRepository;



    /**
     * The initialize method is responsible for initiating the file repository. This procedure involves loading
     * the types (TYPE) from the file system and store them in the collection.
     */
    @Override
    public void initialize() {
        Collection<RestProjectV1> projects =
                fileRepositorySupport.load(RestProjectV1.class, fileDirectory, fileExtension);

        for(RestProjectV1 projectV1 : projects){
            save(projectV1);
            fileRepositorySupport.delete(this.fileDirectory, projectV1.getId() + this.fileExtension);
        }
    }

    public static RestProject convert(RestProjectV1 projectV1){
        RestProject project = new RestProject();
        project.setCreated(projectV1.getCreated());
        project.setUpdated(projectV1.getUpdated());
        project.setDescription(projectV1.getDescription());
        project.setId(projectV1.getId());
        project.setName(projectV1.getName());

        for(RestApplicationV1 applicationV1 : projectV1.getApplications()){
            RestApplication application = new RestApplication();
            application.setId(applicationV1.getId());
            application.setName(applicationV1.getName());
            application.setProjectId(project.getId());
            project.getApplications().add(application);

            for(RestResourceV1 restResourceV1 : applicationV1.getResources()){
                RestResource resource = new RestResource();
                resource.setUri(restResourceV1.getUri());
                resource.setName(restResourceV1.getName());
                resource.setId(restResourceV1.getId());
                resource.setApplicationId(application.getId());
                application.getResources().add(resource);

                for(RestMethodV1 methodV1 : restResourceV1.getMethods()){
                    RestMethod restMethod = new RestMethod();
                    restMethod.setStatus(methodV1.getStatus());
                    restMethod.setSimulateNetworkDelay(methodV1.getSimulateNetworkDelay());
                    restMethod.setNetworkDelay(methodV1.getNetworkDelay());
                    restMethod.setResponseStrategy(methodV1.getResponseStrategy());
                    restMethod.setName(methodV1.getName());
                    restMethod.setId(methodV1.getId());
                    restMethod.setHttpMethod(methodV1.getHttpMethod());
                    restMethod.setForwardedEndpoint(methodV1.getForwardedEndpoint());
                    restMethod.setDefaultBody(methodV1.getDefaultBody());
                    restMethod.setCurrentResponseSequenceIndex(methodV1.getCurrentResponseSequenceIndex());
                    restMethod.setResourceId(resource.getId());
                    resource.getMethods().add(restMethod);

                    for(RestMockResponseV1 mockResponseV1 : methodV1.getMockResponses()){
                        RestMockResponse mockResponse = new RestMockResponse();
                        mockResponse.setId(mockResponseV1.getId());
                        mockResponse.setName(mockResponseV1.getName());
                        mockResponse.setBody(mockResponseV1.getBody());
                        mockResponse.setStatus(mockResponseV1.getStatus());
                        mockResponse.setHttpStatusCode(mockResponseV1.getHttpStatusCode());
                        mockResponse.setUsingExpressions(mockResponseV1.isUsingExpressions());

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
                        mockResponse.setMethodId(restMethod.getId());
                        restMethod.getMockResponses().add(mockResponse);
                    }
                }
            }
        }
        return project;
    }



    /**
     * Save an instance.
     * @param type The instance that will be saved.
     */
    @Override
    protected RestProject save(RestProjectV1 type) {
        RestProject project = convert(type);

        if(this.projectRepository.exists(project.getId())){
            throw new IllegalArgumentException("A project with the following key already exists: " + project.getId());
        }

        this.projectRepository.save(project);
        for(RestApplication application : project.getApplications()) {
            if(this.applicationRepository.exists(application.getId())){
                throw new IllegalArgumentException("An application with the following key already exists: " + application.getId());
            }

            this.applicationRepository.save(application);
            for (RestResource resource : application.getResources()) {
                if(this.resourceRepository.exists(resource.getId())){
                    throw new IllegalArgumentException("A resource with the following key already exists: " + resource.getId());
                }

                this.resourceRepository.save(resource);
                for (RestMethod method : resource.getMethods()) {
                    if(this.methodRepository.exists(method.getId())){
                        throw new IllegalArgumentException("A method with the following key already exists: " + method.getId());
                    }

                    this.methodRepository.save(method);
                    for (RestMockResponse mockResponse : method.getMockResponses()) {
                        if(this.mockResponseRepository.exists(mockResponse.getId())){
                            throw new IllegalArgumentException("A mock response with the following key already exists: " + mockResponse.getId());
                        }

                        this.mockResponseRepository.save(mockResponse);
                    }
                }
            }
        }
        return project;
    }


    @XmlRootElement(name = "restProject")
    protected static class RestProjectV1 extends ProjectV1 {

        private List<RestApplicationV1> applications = new CopyOnWriteArrayList<RestApplicationV1>();

        @XmlElementWrapper(name = "applications")
        @XmlElement(name = "application")
        public List<RestApplicationV1> getApplications() {
            return applications;
        }

        public void setApplications(List<RestApplicationV1> applications) {
            this.applications = applications;
        }


    }

    @XmlRootElement(name = "restApplication")
    protected static class RestApplicationV1 implements Saveable<String> {

        private String id;
        private String name;
        private List<RestResourceV1> resources = new CopyOnWriteArrayList<RestResourceV1>();

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

        @XmlElementWrapper(name = "resources")
        @XmlElement(name = "resource")
        public List<RestResourceV1> getResources() {
            return resources;
        }

        public void setResources(List<RestResourceV1> resources) {
            this.resources = resources;
        }
    }

    @XmlRootElement(name = "restMockResponse")
    protected static class RestResourceV1 implements Saveable<String> {

        private String id;
        private String name;
        private String uri;
        private List<RestMethodV1> methods = new CopyOnWriteArrayList<RestMethodV1>();


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
        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        @XmlElementWrapper(name = "methods")
        @XmlElement(name = "method")
        public List<RestMethodV1> getMethods() {
            return methods;
        }

        public void setMethods(List<RestMethodV1> methods) {
            this.methods = methods;
        }
    }

    @XmlRootElement(name = "restMethod")
    protected static class RestMethodV1 implements Saveable<String> {

        private String id;
        private String name;
        private String defaultBody;
        private HttpMethod httpMethod;
        private String forwardedEndpoint;
        private RestMethodStatus status;
        private RestResponseStrategy responseStrategy;
        private Integer currentResponseSequenceIndex;
        private boolean simulateNetworkDelay;
        private long networkDelay;
        private List<RestMockResponseV1> mockResponses = new CopyOnWriteArrayList<RestMockResponseV1>();

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
        public String getDefaultBody() {
            return defaultBody;
        }

        public void setDefaultBody(String defaultBody) {
            this.defaultBody = defaultBody;
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
        public RestMethodStatus getStatus() {
            return status;
        }

        public void setStatus(RestMethodStatus status) {
            this.status = status;
        }

        @XmlElement
        public RestResponseStrategy getResponseStrategy() {
            return responseStrategy;
        }

        public void setResponseStrategy(RestResponseStrategy responseStrategy) {
            this.responseStrategy = responseStrategy;
        }

        @XmlElement
        public Integer getCurrentResponseSequenceIndex() {
            return currentResponseSequenceIndex;
        }

        public void setCurrentResponseSequenceIndex(Integer currentResponseSequenceIndex) {
            this.currentResponseSequenceIndex = currentResponseSequenceIndex;
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

        @XmlElementWrapper(name = "mockResponses")
        @XmlElement(name = "mockResponse")
        public List<RestMockResponseV1> getMockResponses() {
            return mockResponses;
        }

        public void setMockResponses(List<RestMockResponseV1> mockResponses) {
            this.mockResponses = mockResponses;
        }
    }

    @XmlRootElement(name = "restMockResponse")
    protected static class RestMockResponseV1 implements Saveable<String> {

        private String id;
        private String name;
        private String body;
        private RestMockResponseStatus status;
        private Integer httpStatusCode;
        private boolean usingExpressions;
        private List<HttpHeader> httpHeaders = new CopyOnWriteArrayList<HttpHeader>();
        private List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<ContentEncoding>();

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
        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        @XmlElement
        public RestMockResponseStatus getStatus() {
            return status;
        }

        public void setStatus(RestMockResponseStatus status) {
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
            if (!(o instanceof RestMockResponseV1))
                return false;

            RestMockResponseV1 that = (RestMockResponseV1) o;

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
