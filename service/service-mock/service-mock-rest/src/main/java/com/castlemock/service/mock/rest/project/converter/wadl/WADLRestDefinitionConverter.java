/*
 * Copyright 2017 Karl Dahlgren
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


package com.castlemock.service.mock.rest.project.converter.wadl;

import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.core.utility.IdUtility;
import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.model.mock.rest.domain.RestResponseStrategy;
import com.castlemock.service.core.manager.FileManager;
import com.castlemock.service.core.utility.DocumentUtility;
import com.castlemock.service.mock.rest.project.converter.AbstractRestDefinitionConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The {@link WADLRestDefinitionConverter} class provides functionality related to WADL.
 * @author Karl Dahlgren
 * @since 1.10
 */
public class WADLRestDefinitionConverter extends AbstractRestDefinitionConverter {

    private final FileManager fileManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(WADLRestDefinitionConverter.class);


    public WADLRestDefinitionConverter(final FileManager fileManager){
        this.fileManager = fileManager;
    }

    /**
     * The method is responsible for parsing a {@link File} and converting into a list of {@link RestApplication}.
     * @param file The {@link File} be parsed and converted into a list of {@link RestApplication}.
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A list of {@link RestApplication} based on the provided file.
     */
    @Override
    public List<RestApplication> convert(final File file, final String projectId, final boolean generateResponse){
        List<RestApplication> applications = new LinkedList<RestApplication>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();

            List<Element> applicationElements = getApplications(document);

            for(Element applicationElement : applicationElements){
                final String applicationName = file.getName().replace(".wadl", "");
                final Optional<String> baseUri = getResourceBase(applicationElement);
                final RestApplication restApplication = RestApplication.builder()
                        .name(applicationName)
                        .id(IdUtility.generateId())
                        .projectId(projectId)
                        .build();
                applications.add(restApplication);

                final List<Element> resourceElements = getResources(applicationElement);
                for(Element resourceElement : resourceElements){
                    final String resourceName = resourceElement.getAttribute("path");
                    final RestResource restResource = RestResource.builder()
                            .id(IdUtility.generateId())
                            .applicationId(restApplication.getId())
                            .name(resourceName)
                            .uri(baseUri.orElse("") + resourceName)
                            .build();
                    restApplication.getResources().add(restResource);

                    final List<Element> methodElements = getMethods(resourceElement);
                    for(Element methodElement : methodElements){
                        final String methodName = methodElement.getAttribute("id");
                        final String methodType = methodElement.getAttribute("name");

                        final List<RestMockResponse> mockResponses = new ArrayList<>();
                        final String methodId = UUID.randomUUID().toString();
                        if(generateResponse){
                            mockResponses.add(generateResponse(methodId));
                        }

                        restResource.getMethods().add(RestMethod.builder()
                                .id(methodId)
                                .resourceId(restResource.getId())
                                .name(methodName)
                                .httpMethod(HttpMethod.valueOf(methodType))
                                .status(RestMethodStatus.MOCKED)
                                .responseStrategy(RestResponseStrategy.RANDOM)
                                .mockResponses(mockResponses)
                                .simulateNetworkDelay(false)
                                .automaticForward(false)
                                .build());
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable parse WADL file", e);
        }

        return applications;
    }

    /**
     * The convert method provides the functionality to convert the provided {@link File} into
     * a list of {@link RestApplication}.
     *
     * @param location         The location of the definition file
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A list of {@link RestApplication} based on the provided file.
     */
    @Override
    public List<RestApplication> convert(final String location,
                                         final String projectId,
                                         boolean generateResponse) {

        final List<RestApplication> restApplications = new ArrayList<>();
        List<File> files = null;
        try {
            files = fileManager.uploadFiles(location);

            for(File file : files){
                List<RestApplication> convertedRestApplications = convert(file, projectId, generateResponse);
                restApplications.addAll(convertedRestApplications);
            }
        } catch (IOException | URISyntaxException e) {
            LOGGER.error("Unable to download file file: " + location, e);
        } finally {
            if(files != null){
                for(File uploadedFile : files){
                    boolean deletionResult = fileManager.deleteFile(uploadedFile);
                    if(deletionResult){
                        LOGGER.debug("Deleted the following WADL file: " + uploadedFile.getName());
                    } else {
                        LOGGER.warn("Unable to delete the following WADL file: " + uploadedFile.getName());
                    }

                }
            }
        }
        return restApplications;
    }

    /**
     * The method extracts all the application elements from the provided document
     * @param document The document which contains all the application that will be extracted
     * @return A list of application elements
     */
    private List<Element> getApplications(Document document){
        return DocumentUtility.getElements(document, "application");
    }

    /**
     * The method extracts all the resource elements from the provided application element
     * @param applicationElement The application element which contains all the resources that will be extracted
     * @return A list of resource elements
     */
    private List<Element> getResources(final Element applicationElement){
        return DocumentUtility.getElements(applicationElement, "resource");
    }

    /**
     * The method extracts all the method elements from the provided resource element
     * @param resourceElement The resource element which contains all the methods that will be extracted
     * @return A list of method elements
     */
    private List<Element> getMethods(final Element resourceElement){
        return DocumentUtility.getElements(resourceElement, "method");
    }

    /**
     * The method provides the functionality to extract the resource base from a provided application element
     * @param applicationElement The application element that contains the resource base
     * @return The resource base from the application element
     */
    private Optional<String> getResourceBase(final Element applicationElement) {
        return DocumentUtility.getElement(applicationElement, "resources")
                .map(resourcesElement -> resourcesElement.getAttribute("base"))
                .filter(resourceBase -> !resourceBase.isEmpty())
                .flatMap(resourceBase -> {
                    try {
                        final URL url = new URI(resourceBase).toURL();
                        return Optional.of(url.getPath());
                    } catch (MalformedURLException | URISyntaxException e) {
                        LOGGER.error("Unable to create an URL for the following URL " + resourceBase);
                        return Optional.empty();
                    }
                });
    }



}
