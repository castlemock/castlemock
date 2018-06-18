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

package com.castlemock.web.mock.soap.service.project;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.project.domain.*;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapResource;
import com.castlemock.core.mock.soap.service.project.input.CreateSoapPortsInput;
import com.castlemock.core.mock.soap.service.project.output.CreateSoapPortsOutput;
import com.castlemock.web.basis.support.FileRepositorySupport;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.wsdl.WSDLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateSoapPortsService extends AbstractSoapProjectService implements Service<CreateSoapPortsInput, CreateSoapPortsOutput> {

    private static final Logger LOGGER = Logger.getLogger(CreateSoapPortsService.class);
    private static final String AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME = "Auto-generated mocked response";
    private static final Integer DEFAULT_RESPONSE_SEQUENCE_INDEX = 0;
    private static final Integer DEFAULT_HTTP_STATUS_CODE = 200;
    private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
    private static final String SOAP_11_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap/";
    private static final String SOAP_12_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap12/";

    @Autowired
    private FileRepositorySupport fileRepositorySupport;

    /**
     *
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<CreateSoapPortsOutput> process(final ServiceTask<CreateSoapPortsInput> serviceTask) {
        final CreateSoapPortsInput input = serviceTask.getInput();
        final String soapProjectId = input.getSoapProjectId();
        List<SoapPort> soapPorts = null;
        try {
            soapPorts = createPorts(input.getFiles(), input.isGenerateResponse());
        } catch (WSDLException e) {
            throw new IllegalStateException("Unable to parse the WSDL file");
        }

        for(SoapPort newSoapPort : soapPorts){
            newSoapPort.setProjectId(soapProjectId);
            SoapPort existingSoapPort = this.portRepository.findWithName(soapProjectId, newSoapPort.getName());

            if(existingSoapPort == null){
                SoapPort savedSoapPort = this.portRepository.save(newSoapPort);

                for(SoapOperation soapOperation : newSoapPort.getOperations()){
                    soapOperation.setPortId(savedSoapPort.getId());
                    SoapOperation savedSoapOperation = this.operationRepository.save(soapOperation);
                    for(SoapMockResponse soapMockResponse : soapOperation.getMockResponses()){
                        soapMockResponse.setOperationId(savedSoapOperation.getId());
                        this.mockResponseRepository.save(soapMockResponse);
                    }
                }

                continue;
            }

            for(SoapOperation newSoapOperation : newSoapPort.getOperations()){
                SoapOperation existingSoapOperation =
                        this.operationRepository.findWithName(existingSoapPort.getId(), newSoapOperation.getName());

                if(existingSoapOperation != null){
                    existingSoapOperation.setOriginalEndpoint(newSoapOperation.getOriginalEndpoint());
                    existingSoapOperation.setSoapVersion(newSoapOperation.getSoapVersion());
                    this.operationRepository.update(existingSoapOperation.getId(), existingSoapOperation);
                } else {
                    newSoapOperation.setPortId(existingSoapPort.getId());
                    SoapOperation savedSoapOperation = this.operationRepository.save(newSoapOperation);
                    for(SoapMockResponse soapMockResponse : newSoapOperation.getMockResponses()){
                        soapMockResponse.setOperationId(savedSoapOperation.getId());
                        this.mockResponseRepository.save(soapMockResponse);
                    }
                }
            }
        }

        // Should only be one WSDL file
        final Collection<SoapResource> wsdlSoapResources =
                this.resourceRepository.findSoapResources(soapProjectId, SoapResourceType.WSDL);

        for(SoapResource wsdlSoapResource : wsdlSoapResources){
            this.resourceRepository.delete(wsdlSoapResource.getId());
        }

        for(File file : input.getFiles()){
            String resource = fileRepositorySupport.read(file);
            SoapResource soapResource = new SoapResource();
            soapResource.setProjectId(soapProjectId);
            soapResource.setName(file.getName());
            soapResource.setType(SoapResourceType.WSDL);
            this.resourceRepository.saveSoapResource(soapResource, resource);
        }

        return createServiceResult(new CreateSoapPortsOutput());
    }




    /**
     * The method creates ports based on provided definitions.
     * @param files Definitions used to create new ports.
     * @param generateResponse Boolean value for if a default response should be generated for each new operation
     * @return List of ports generated from the provided definitions.
     * @throws WSDLException Throws an WSDLException if WSDL parsing failed.
     */
    private List<SoapPort> createPorts(final List<File> files, final boolean generateResponse) throws WSDLException {
        try {
            final List<SoapPort> soapPorts = new ArrayList<>();
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setNamespaceAware(true);

            for(File file : files){
                final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                final Document document = documentBuilder.parse(file);
                document.getDocumentElement().normalize();

                final List<SoapPort> fileSoapPorts = getSoapPorts(document, generateResponse);
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
    private List<SoapPort> getSoapPorts(final Document document, final boolean generateResponse){
        final List<SoapPort> soapPorts = new ArrayList<SoapPort>();
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
                        SoapVersion soapOperationVersion = SoapVersion.SOAP11;

                        if(soapOperationAddress == null){
                            soapOperationAddress = extractSoapAddress(portElement, SOAP_12_NAMESPACE);
                            soapOperationVersion = SoapVersion.SOAP12;
                        }
                        if(soapOperationAddress == null){
                            // The port element is not a SOAP port
                            continue;
                        }

                        final String portName = getAttribute(portElement, "name");
                        final String portBinding = getAttribute(portElement, "binding");
                        final List<SoapOperation> soapOperations = getSoapOperations(document, portBinding, soapOperationAddress, soapOperationVersion, generateResponse);
                        final SoapPort soapPort = new SoapPort();
                        soapPort.setName(portName);
                        soapPort.setOperations(soapOperations);
                        soapPort.setUri(portName);
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
     * @param soapPortBinding The SOAP port binding which the operations belongs to
     * @param soapOperationAddress The address that will be assigned as the default address to the operations
     * @param soapVersion The SOAP operation version (SOAP 11 or SOAP 12)
     * @param generateResponse Boolean value determining if a response should be generated for each extracted
     *                         operation.
     * @return A list of extracted SOAP operations
     */
    private List<SoapOperation> getSoapOperations(final Document document, final String soapPortBinding, final String soapOperationAddress, final SoapVersion soapVersion, final boolean generateResponse){
        final List<SoapOperation> soapOperations = new LinkedList<SoapOperation>();
        final Element bindingElement = findElement(document, WSDL_NAMESPACE, "binding", soapPortBinding);
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

        // harpipl
        final Map<String, Element> messages = findMessages(document);

        for (int operationIndex = 0; operationIndex < operationNodeList.getLength(); operationIndex++) {
            final Node operationNode = operationNodeList.item(operationIndex);
            if (operationNode.getNodeType() == Node.ELEMENT_NODE) {
                final Element operationElement = (Element) operationNode;
                final String operationName = getAttribute(operationElement, "name");
                final SoapOperation soapOperation = new SoapOperation();
                final String defaultBody = generateDefaultBody(operationName, operationElement.getNamespaceURI());
                final String inputMessageName = getInputMessageName(messages, operationElement);
                final String identifier = inputMessageName != null && !inputMessageName.isEmpty() ? inputMessageName : operationName;

                soapOperation.setName(operationName);
                soapOperation.setIdentifier(identifier);
                soapOperation.setHttpMethod(HttpMethod.POST);
                soapOperation.setStatus(SoapOperationStatus.MOCKED);
                soapOperation.setResponseStrategy(SoapResponseStrategy.RANDOM);
                soapOperation.setForwardedEndpoint(soapOperationAddress);
                soapOperation.setOriginalEndpoint(soapOperationAddress);
                soapOperation.setSoapVersion(soapVersion);
                soapOperation.setMockResponses(new ArrayList<SoapMockResponse>());
                soapOperation.setDefaultBody(defaultBody);
                soapOperation.setCurrentResponseSequenceIndex(DEFAULT_RESPONSE_SEQUENCE_INDEX);
                if(generateResponse){
                    final SoapMockResponse mockResponse = new SoapMockResponse();
                    mockResponse.setBody(soapOperation.getDefaultBody());
                    mockResponse.setStatus(SoapMockResponseStatus.ENABLED);
                    mockResponse.setName(AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME);
                    mockResponse.setHttpStatusCode(DEFAULT_HTTP_STATUS_CODE);
                    soapOperation.getMockResponses().add(mockResponse);
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

    // harpipl
    private Map<String, Element> findMessages(final Document document){
        Map<String, Element> messages = new HashMap<String, Element>();

        final NodeList nodeList = document.getDocumentElement().getElementsByTagNameNS(WSDL_NAMESPACE, "message");
        for (int index = 0; index < nodeList.getLength(); index++) {
            Node node = nodeList.item(index);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String name = element.getAttribute("name");
                messages.put(name, element);
            }
        }
        return messages;
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

    private String getInputMessageName(Map<String, Element> messages, Element operationElement){
        String inputMessageName = null;
        final NodeList inputNodeList = operationElement.getElementsByTagNameNS(WSDL_NAMESPACE, "input");
        for (int inputIndex = 0; inputIndex < inputNodeList.getLength(); inputIndex++) {
            final Node inputNode = inputNodeList.item(inputIndex);
            if (inputNode.getNodeType() == Node.ELEMENT_NODE) {
                final Element inputElement = (Element) inputNode;
                inputMessageName = getAttribute(inputElement, "name");

                final Element messageElement = messages.get(inputMessageName);
                if (messageElement != null) {

                    final NodeList portNodeList = messageElement.getElementsByTagNameNS(WSDL_NAMESPACE, "part");
                    for (int partIndex = 0; partIndex < portNodeList.getLength(); partIndex++) {
                        Node partNode = portNodeList.item(partIndex);
                        if (partNode.getNodeType() == Node.ELEMENT_NODE) {
                            final Element partElement = (Element) partNode;

                            inputMessageName = partElement.getAttribute("name");
                            break;
                        }
                    }
                }
            }
        }
        return inputMessageName;
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
