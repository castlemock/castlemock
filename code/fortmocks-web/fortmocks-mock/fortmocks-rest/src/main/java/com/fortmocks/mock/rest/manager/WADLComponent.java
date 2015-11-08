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

package com.fortmocks.mock.rest.manager;

import com.fortmocks.mock.rest.model.event.dto.RestRequestDto;
import com.fortmocks.mock.rest.model.project.RestMethodStatus;
import com.fortmocks.mock.rest.model.project.RestMethodType;
import com.fortmocks.mock.rest.model.project.RestResponseStrategy;
import com.fortmocks.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.web.core.manager.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * WADLComponent provides functionality to parse a WADL file
 * @author Karl Dahlgren
 * @since 1.0
 */
@Component
public class WADLComponent {

    @Autowired
    private FileManager fileManager;

    /**
     * The method provides the functionality to download a WADL file from a specific URL, download it and generate
     * new REST applications.
     * @param wadlUrl The URL from where the WADL file can be downloaded
     * @param generateResponse Boolean value for if a default response should be generated for each new method. The
     *                         value true will cause the method to generate a default response for each new method. No
     *                         default responses will be created if the variable is set to false.
     * @return  The extracted ports from the downloaded WADL file.
     */
    public List<RestApplicationDto> createApplication(final String wadlUrl, final boolean generateResponse) {
        return null;
    }

    /**
     * Parse an WADL file and creates new ports based on the WADL file
     * @param files The list of WADL files
     * @param generateResponse Boolean value for if a default response should be generated for each new method. The
     *                         value true will cause the method to generate a default response for each new method. No
     *                         default responses will be created if the variable is set to false.
     * @return The extracted ports from the WADL file.
     */
    public List<RestApplicationDto> createApplication(final List<MultipartFile> files, final boolean generateResponse){

        try {
            List<File> uploadedFiles = fileManager.uploadFiles(files);
            List<RestApplicationDto> applications = new LinkedList<RestApplicationDto>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            for(File file : uploadedFiles){
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
                        restApplicationDto.getRestResources().add(restResourceDto);

                        final List<Element> methodElements = getMethods(resourceElement);
                        for(Element methodElement : methodElements){
                            final String methodName = methodElement.getAttribute("id");
                            final String methodType = methodElement.getAttribute("name");

                            final RestMethodDto restMethodDto = new RestMethodDto();
                            restMethodDto.setName(methodName);
                            restMethodDto.setRestMethodType(RestMethodType.valueOf(methodType));
                            restMethodDto.setRestMethodStatus(RestMethodStatus.MOCKED);
                            restMethodDto.setRestResponseStrategy(RestResponseStrategy.RANDOM);
                            restResourceDto.getRestMethods().add(restMethodDto);
                        }
                    }
                }
            }
            return applications;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }


        return null;
    }

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

