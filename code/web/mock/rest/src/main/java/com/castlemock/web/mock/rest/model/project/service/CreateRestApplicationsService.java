/*
 * Copyright 2015 Karl Dahlgren
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

package com.castlemock.web.mock.rest.model.project.service;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestResponseStrategy;
import com.castlemock.core.mock.rest.model.project.dto.*;
import com.castlemock.core.mock.rest.model.project.service.message.input.CreateRestApplicationsInput;
import com.castlemock.core.mock.rest.model.project.service.message.output.CreateRestApplicationsOutput;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateRestApplicationsService extends AbstractRestProjectService implements Service<CreateRestApplicationsInput, CreateRestApplicationsOutput> {

    private static final String AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME = "Auto-generated mocked response";

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<CreateRestApplicationsOutput> process(final ServiceTask<CreateRestApplicationsInput> serviceTask) {
        final CreateRestApplicationsInput input = serviceTask.getInput();
        final RestProjectDto restProject = repository.findOne(input.getRestProjectId());
        List<RestApplicationDto> parsedRestApplicationDtos = parseWADLFile(input.getFiles(), input.isGenerateResponse());

        final List<RestApplicationDto> restApplications = new ArrayList<>();
        for(RestApplicationDto newRestApplication : parsedRestApplicationDtos){
            RestApplicationDto existingRestApplication = findRestApplication(restProject, newRestApplication.getName());

            if(existingRestApplication == null){
                restApplications.add(newRestApplication);
                continue;
            }

            List<RestResourceDto> restResources = new ArrayList<RestResourceDto>();
            for(RestResourceDto newRestResource : newRestApplication.getResources()){
                RestResourceDto existingRestResource = findRestResource(existingRestApplication, newRestResource.getName());

                if (existingRestResource == null) {
                    restResources.add(newRestResource);
                    continue;
                }

                existingRestResource.setUri(newRestResource.getUri());

                List<RestMethodDto> restMethods = new ArrayList<RestMethodDto>();
                for(RestMethodDto newRestMethod : newRestResource.getMethods()){
                    RestMethodDto existingRestMethod = findRestMethod(existingRestResource, newRestMethod.getName());

                    if (existingRestMethod == null) {
                        restMethods.add(newRestMethod);
                        continue;
                    }

                    existingRestMethod.setHttpMethod(newRestMethod.getHttpMethod());
                    restMethods.add(newRestMethod);
                }
                existingRestResource.setMethods(restMethods);
                restResources.add(existingRestResource);
            }
            existingRestApplication.setResources(restResources);
            restApplications.add(newRestApplication);
        }
        restProject.setApplications(restApplications);

        save(restProject);
        return createServiceResult(new CreateRestApplicationsOutput());
    }

    /**
     * Find a REST application with a specific name for a rest project
     * @param restProject The REST project that the application belongs to
     * @param name The name of the REST application
     * @return A REST application that matches the search criteria. Null otherwise.
     */
    public RestApplicationDto findRestApplication(RestProjectDto restProject, String name){
        for(RestApplicationDto restApplication : restProject.getApplications()){
            if(restApplication.getName().equals(name)){
                return restApplication;
            }
        }
        return null;
    }

    /**
     * Find a REST resource with a specific name for a REST application
     * @param restApplication The REST application that the resource belongs to
     * @param name The name of the REST resource
     * @return A REST resource that matches the search criteria. Null otherwise.
     */
    public RestResourceDto findRestResource(RestApplicationDto restApplication, String name){
        for(RestResourceDto restResource : restApplication.getResources()){
            if(restResource.getName().equals(name)){
                return restResource;
            }
        }
        return null;
    }

    /**
     * Find a REST method with a specific name for a REST resource
     * @param restResource The REST resource that the method belongs to
     * @param name The name of the REST method
     * @return A REST method that matches the search criteria. Null otherwise.
     */
    public RestMethodDto findRestMethod(RestResourceDto restResource, String name){
        for(RestMethodDto restMethod : restResource.getMethods()){
            if(restMethod.getName().equals(name)){
                return restMethod;
            }
        }
        return null;
    }


    private List<RestApplicationDto> parseWADLFile(final List<File> files, final boolean generateResponse){

        try {
            List<RestApplicationDto> applications = new LinkedList<RestApplicationDto>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            for(File file : files){
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(file);
                document.getDocumentElement().normalize();

                List<Element> applicationElements = getApplications(document);

                for(Element applicationElement : applicationElements){
                    final String applicationName = file.getName().replace(".wadl", "");
                    final String baseUri = resourceBase(applicationElement);
                    final RestApplicationDto restApplicationDto = new RestApplicationDto();
                    restApplicationDto.setName(applicationName);
                    applications.add(restApplicationDto);

                    final List<Element> resourceElements = getResources(applicationElement);
                    for(Element resourceElement : resourceElements){
                        final String resourceName = resourceElement.getAttribute("path");
                        final RestResourceDto restResourceDto = new RestResourceDto();
                        restResourceDto.setName(resourceName);
                        restResourceDto.setUri(baseUri + resourceName);
                        restApplicationDto.getResources().add(restResourceDto);

                        final List<Element> methodElements = getMethods(resourceElement);
                        for(Element methodElement : methodElements){
                            final String methodName = methodElement.getAttribute("id");
                            final String methodType = methodElement.getAttribute("name");

                            final RestMethodDto restMethodDto = new RestMethodDto();
                            restMethodDto.setName(methodName);
                            restMethodDto.setHttpMethod(HttpMethod.valueOf(methodType));
                            restMethodDto.setStatus(RestMethodStatus.MOCKED);
                            restMethodDto.setResponseStrategy(RestResponseStrategy.RANDOM);
                            restMethodDto.setMockResponses(new ArrayList<RestMockResponseDto>());


                            if(generateResponse){
                                RestMockResponseDto restMockResponse = new RestMockResponseDto();
                                restMockResponse.setName(AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME);
                                restMockResponse.setHttpStatusCode(200);
                                restMockResponse.setStatus(RestMockResponseStatus.ENABLED);
                                restMethodDto.getMockResponses().add(restMockResponse);
                            }

                            restResourceDto.getMethods().add(restMethodDto);
                        }
                    }
                }
            }
            return applications;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable parse WADL file", e);
        }
    }

    /**
     * The method extracts all the application elements from the provided document
     * @param document The document which contains all the application that will be extracted
     * @return A list of application elements
     */
    private List<Element> getApplications(Document document){
        List<Element> applicationElements = new LinkedList<Element>();
        final NodeList applicationNodeList = document.getElementsByTagName("application");

        for (int applicationIndex = 0; applicationIndex < applicationNodeList.getLength(); applicationIndex++) {
            Node applicationNode = applicationNodeList.item(applicationIndex);
            if (applicationNode.getNodeType() == Node.ELEMENT_NODE) {
                Element applicationElement = (Element) applicationNode;
                applicationElements.add(applicationElement);
            }
        }
        return applicationElements;
    }

    /**
     * The method extracts all the resource elements from the provided application element
     * @param applicationElement The application element which contains all the resources that will be extracted
     * @return A list of resource elements
     */
    private List<Element> getResources(Element applicationElement){
        List<Element> resourceElements = new LinkedList<Element>();
        NodeList resourceNodeList = applicationElement.getElementsByTagName("resource");

        for (int resourceIndex = 0; resourceIndex < resourceNodeList.getLength(); resourceIndex++) {
            Node resourceNode = resourceNodeList.item(resourceIndex);
            if (resourceNode.getNodeType() == Node.ELEMENT_NODE) {
                Element resourceElement = (Element) resourceNode;
                resourceElements.add(resourceElement);
            }
        }
        return resourceElements;
    }

    /**
     * The method extracts all the method elements from the provided resource element
     * @param resourceElement The resource element which contains all the methods that will be extracted
     * @return A list of method elements
     */
    private List<Element> getMethods(Element resourceElement){
        List<Element> methodElements = new LinkedList<Element>();
        NodeList methodNodeList = resourceElement.getElementsByTagName("method");

        for (int methodIndex = 0; methodIndex < methodNodeList.getLength(); methodIndex++) {
            Node methodNode = methodNodeList.item(methodIndex);
            if (methodNode.getNodeType() == Node.ELEMENT_NODE) {
                Element methodElement = (Element) methodNode;
                methodElements.add(methodElement);
            }
        }
        return methodElements;
    }

    /**
     * The method provides the functionality to extract the resource base from a provided application element
     * @param applicationElement The application element that contains the resource base
     * @return The resource base from the application element
     * @throws MalformedURLException
     */
    private String resourceBase(Element applicationElement) throws MalformedURLException {
        final NodeList resourcesNodeList = applicationElement.getElementsByTagName("resources");
        for (int resourcesIndex = 0; resourcesIndex < resourcesNodeList.getLength(); resourcesIndex++) {
            Node resourcesNode = resourcesNodeList.item(resourcesIndex);
            if (resourcesNode.getNodeType() == Node.ELEMENT_NODE) {
                Element resourcesElement = (Element) resourcesNode;
                String resourceBase = resourcesElement.getAttribute("base");
                URL url = new URL(resourceBase);
                return url.getPath();
            }
        }
        return null;
    }
}
