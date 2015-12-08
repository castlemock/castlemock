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

package com.fortmocks.web.mock.soap.manager;

import com.fortmocks.core.mock.soap.model.project.domain.*;
import com.fortmocks.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.web.basis.manager.FileManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.wsdl.WSDLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * WSDLComponent provides functionality to parse a WSDL file
 * @author Karl Dahlgren
 * @since 1.0
 */
@Component
public class WSDLComponent {

    @Autowired
    private FileManager fileManager;
    private static final String AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME = "Auto-generated mocked response";
    private static final Integer DEFAULT_RESPONSE_SEQUENCE_INDEX = 0;
    private static final Integer DEFAULT_HTTP_STATUS_CODE = 200;
    private static final Logger LOGGER = Logger.getLogger(WSDLComponent.class);
    private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
    private static final String SOAP_11_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap/";
    private static final String SOAP_12_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap12/";

    /**
     * The method provides the functionality to download a WSDL file from a specific URL, download it and generate
     * new SOAP ports.
     * @param wsdlURL The URL from where the WSDL file can be downloaded
     * @param generateResponse Boolean value for if a default response should be generated for each new operation. The
     *                         value true will cause the method to generate a default response for each new service. No
     *                         default responses will be created if the variable is set to false.
     * @return  The extracted ports from the downloaded WSDL file.
     */
    public List<SoapPortDto> createSoapPorts(final String wsdlURL, final boolean generateResponse) {
        List<File> uploadedFiles = new ArrayList<File>();
        try {
            uploadedFiles = fileManager.uploadFiles(wsdlURL);
            return createPorts(uploadedFiles, generateResponse);
        } catch (Exception exception) {
            LOGGER.error("Unable to parse WSDL", exception);
            throw new IllegalStateException("Unable to parse the WSDL file");
        } finally {
            fileManager.deleteUploadedFiles(uploadedFiles);
        }
    }

    /**
     * Parse an WSDL file and creates new ports based on the WSDL file
     * @param files The list of WSDL files
     * @param generateResponse Boolean value for if a default response should be generated for each new operation. The
     *                         value true will cause the method to generate a default response for each new service. No
     *                         default responses will be created if the variable is set to false.
     * @return The extracted ports from the WSDL file.
     */
    public List<SoapPortDto> createSoapPorts(final List<MultipartFile> files, final boolean generateResponse){
        List<File> uploadedFiles = new ArrayList<File>();
        try {
            uploadedFiles = fileManager.uploadFiles(files);
            return createPorts(uploadedFiles, generateResponse);
        } catch (Exception exception) {
            LOGGER.error("Unable to parse WSDL", exception);
            throw new IllegalStateException("Unable to parse WSDL", exception);
        } finally {
            fileManager.deleteUploadedFiles(uploadedFiles);
        }
    }

    /**
     * The method creates ports based on provided definitions.
     * @param files Definitions used to create new ports.
     * @param generateResponse Boolean value for if a default response should be generated for each new operation
     * @return List of ports generated from the provided definitions.
     * @throws WSDLException Throws an WSDLException if WSDL parsing failed.
     */
    private List<SoapPortDto> createPorts(final List<File> files, final boolean generateResponse) throws WSDLException {
        try {
            final List<SoapPortDto> soapPorts = new ArrayList<>();
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setNamespaceAware(true);

            for(File file : files){
                final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                final Document document = documentBuilder.parse(file);
                document.getDocumentElement().normalize();

                final List<SoapPortDto> fileSoapPorts = getSoapPorts(document, generateResponse);
                soapPorts.addAll(fileSoapPorts);
            }
            return soapPorts;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable parse WSDL file", e);
        }
    }

    /**
     * THe method provides the functionality to parse a document and extract all the SOAP ports from the SOAP ports.
     * The method will also generate mocked responses if the {@code generateResponse}
     * @param document The document which will be parsed
     * @param generateResponse Boolean value determining if a response should be generated for each extracted
     *                         operation.
     * @return A list of SOAP ports
     */
    private List<SoapPortDto> getSoapPorts(final Document document, final boolean generateResponse){
        final List<SoapPortDto> soapPorts = new ArrayList<SoapPortDto>();
        final NodeList serviceNodeList = document.getDocumentElement().getElementsByTagNameNS(WSDL_NAMESPACE, "service");
        for (int serviceIndex = 0; serviceIndex < serviceNodeList.getLength(); serviceIndex++) {
            final Node serviceNode = serviceNodeList.item(serviceIndex);
            if (serviceNode.getNodeType() == Node.ELEMENT_NODE) {
                final Element serviceElement = (Element) serviceNode;
                final NodeList portNodeList = serviceElement.getElementsByTagNameNS(WSDL_NAMESPACE, "port");
                for (int portIndex = 0; portIndex < portNodeList.getLength(); portIndex++) {
                    Node portNode = portNodeList.item(portIndex);
                    if (portNode.getNodeType() == Node.ELEMENT_NODE) {
                        final Element portElement = (Element) portNode;

                        String soapOperationAddress = extractSoapAddress(portElement, SOAP_11_NAMESPACE);
                        SoapOperationType soapOperationType = SoapOperationType.SOAP11;

                        if(soapOperationAddress == null){
                            soapOperationAddress = extractSoapAddress(portElement, SOAP_12_NAMESPACE);
                            soapOperationType = SoapOperationType.SOAP12;
                        }
                        if(soapOperationAddress == null){
                            // The port element is not a SOAP port
                            continue;
                        }

                        final String portName = getAttribute(portElement, "name");
                        final List<SoapOperationDto> soapOperations = getSoapOperations(document, portName, soapOperationAddress, soapOperationType, generateResponse);
                        final SoapPortDto soapPort = new SoapPortDto();
                        soapPort.setName(portName);
                        soapPort.setSoapOperations(soapOperations);
                        soapPort.setUrlPath(portName);
                        soapPorts.add(soapPort);

                    }
                }
            }
        }
        return soapPorts;
    }

    /**
     * The method provides the functionality to extract SOAP operation from a document
     * @param document The document which will be parsed
     * @param soapPortName The SOAP port name which the operations belongs to
     * @param soapOperationAddress The address that will be assigned as the default address to the operations
     * @param soapOperationType The SOAP operation type (SOAP 11 or SOAP 12)
     * @param generateResponse Boolean value determining if a response should be generated for each extracted
      *                         operation.
     * @return A list of extracted SOAP operations
     */
    private List<SoapOperationDto> getSoapOperations(final Document document, final String soapPortName, final String soapOperationAddress, final SoapOperationType soapOperationType, final boolean generateResponse){
        final List<SoapOperationDto> soapOperations = new LinkedList<SoapOperationDto>();
        final Element bindingElement = findElement(document, WSDL_NAMESPACE, "binding", soapPortName);
        if(bindingElement == null){
            return soapOperations;
        }
        final String bindingType = getAttribute(bindingElement, "type");

        if(bindingType == null){
            return soapOperations;
        }

        final Element portTypeElement = findElement(document, WSDL_NAMESPACE, "portType", bindingType);

        if(portTypeElement == null){
            return soapOperations;
        }

        final NodeList operationNodeList = portTypeElement.getElementsByTagNameNS(WSDL_NAMESPACE, "operation");
        for (int operationIndex = 0; operationIndex < operationNodeList.getLength(); operationIndex++) {
            final Node operationNode = operationNodeList.item(operationIndex);
            if (operationNode.getNodeType() == Node.ELEMENT_NODE) {
                final Element operationElement = (Element) operationNode;
                final String operationName = getAttribute(operationElement, "name");
                final SoapOperationDto soapOperation = new SoapOperationDto();
                final String defaultBody = generateDefaultBody(operationName, operationElement.getNamespaceURI());
                soapOperation.setName(operationName);
                soapOperation.setSoapOperationMethod(SoapOperationMethod.POST);
                soapOperation.setSoapOperationStatus(SoapOperationStatus.MOCKED);
                soapOperation.setSoapResponseStrategy(SoapResponseStrategy.RANDOM);
                soapOperation.setForwardedEndpoint(new String());
                soapOperation.setOriginalEndpoint(soapOperationAddress);
                soapOperation.setSoapOperationType(soapOperationType);
                soapOperation.setSoapMockResponses(new ArrayList<SoapMockResponseDto>());
                soapOperation.setDefaultBody(defaultBody);
                soapOperation.setCurrentResponseSequenceIndex(DEFAULT_RESPONSE_SEQUENCE_INDEX);
                if(generateResponse){
                    final SoapMockResponseDto mockResponse = new SoapMockResponseDto();
                    mockResponse.setBody(soapOperation.getDefaultBody());
                    mockResponse.setSoapMockResponseStatus(SoapMockResponseStatus.ENABLED);
                    mockResponse.setName(AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME);
                    mockResponse.setHttpStatusCode(DEFAULT_HTTP_STATUS_CODE);
                    soapOperation.getSoapMockResponses().add(mockResponse);
                }

                soapOperations.add(soapOperation);
            }
        }
        return soapOperations;
    }

    /**
     * Search and find for a specific element
     * @param document The document that will be parsed
     * @param namespace The element namespace
     * @param type The type or name of the element
     * @param identifier The identifier is used to determine if the element is the correct element being searched for.
     * @return An element that matches the provided search criteria
     */
    private Element findElement(final Document document, final String namespace, final String type, final String identifier){
        final NodeList nodeList = document.getElementsByTagNameNS(namespace, type);
        for (int index = 0; index < nodeList.getLength(); index++) {
            Node node = nodeList.item(index);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getAttribute("name");
                if(name.equals(identifier)){
                    return element;
                }
            }
        }
        return null;
    }

    /**
     * Extracts an attribute from an element. The method will also remove namespace prefix
     * if it is present.
     * @param element The element which the attribute will be extracted from
     * @param name The name of the attribute that will be extracted
     * @return The attribute value
     */
    private String getAttribute(Element element, String name){
        final String value = element.getAttribute(name);
        if(value == null){
            return null;
        }
        String[] splitValues = value.split(":");
        if(splitValues.length == 1) {
            return splitValues[0];
        }
        return splitValues[1];
    }

    /**
     * Extract the SOAP address from a port element
     * @param portElement The port element that contains the address
     * @param namespace The namespace of the address
     * @return The SOAP port address
     */
    private String extractSoapAddress(Element portElement, String namespace){
        NodeList addressElements = portElement.getElementsByTagNameNS(namespace, "address");


        for (int addressIndex = 0; addressIndex < addressElements.getLength(); addressIndex++) {
            Node soapAddressNode = addressElements.item(addressIndex);
            if (soapAddressNode.getNodeType() == Node.ELEMENT_NODE) {
                Element soapAddressElement = (Element) soapAddressNode;
                String soapAddress = soapAddressElement.getAttribute("location");
                if(soapAddress != null){
                    return soapAddress;
                }
            }
        }
        return null;
    }

    /**
     * The method provides the functionality to generate a new mocked response
     * @param name The operation contains all the information that is needed to compose a operation default body
     * @param targetNamespace The target namespace that the response will use
     * @return A string value of the response
     */
    public static String generateDefaultBody(final String name, final String targetNamespace){
        final String prefix = "web";
        final String resultElement = name + "Result";
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:" + prefix + "=\"" + targetNamespace + "\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <" + prefix + ":" + resultElement + ">?</" + prefix + ":" + resultElement + ">\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }
}

