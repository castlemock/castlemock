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

package com.castlemock.web.mock.soap.converter;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.utility.compare.UrlUtility;
import com.castlemock.core.mock.soap.model.project.domain.*;
import com.castlemock.web.basis.manager.FileManager;
import com.castlemock.web.mock.soap.support.DocumentUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class SoapPortConverter {

    private static final String AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME = "Auto-generated mocked response";
    private static final Integer DEFAULT_RESPONSE_SEQUENCE_INDEX = 0;
    private static final Integer DEFAULT_HTTP_STATUS_CODE = 200;

    private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
    private static final String SOAP_11_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap/";
    private static final String SOAP_12_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap12/";

    private static final String PORT_NAMESPACE = "port";
    private static final String NAME_NAMESPACE = "name";
    private static final String ELEMENT_NAMESPACE = "element";
    private static final String BINDING_NAMESPACE = "binding";
    private static final String SERVICE_NAMESPACE = "service";
    private static final String IMPORT_NAMESPACE = "import";
    private static final String TYPE_NAMESPACE = "type";
    private static final String LOCATION_NAMESPACE = "location";
    private static final String PORT_TYPE_NAMESPACE = "portType";
    private static final String OPERATION_NAMESPACE = "operation";
    private static final String PART_NAMESPACE = "part";
    private static final String INPUT_NAMESPACE = "input";
    private static final String MESSAGE_NAMESPACE = "message";

    @Autowired
    private FileManager fileManager;

    public Set<SoapPortConverterResult> getSoapPorts(final List<File> files,
                                       final boolean generateResponse){
        final Map<String, WSDLDocument> documents = this.getDocuments(files, SoapResourceType.WSDL);
        return getResults(documents, generateResponse);
    }

    public Set<SoapPortConverterResult> getSoapPorts(final String location,
                                                     final boolean generateResponse,
                                                     final boolean loadExternal){
        try {
            final List<File> files = this.fileManager.uploadFiles(location);
            final Map<String, WSDLDocument> documents = this.getDocuments(files, SoapResourceType.WSDL);

            if(loadExternal){
                for(WSDLDocument document : documents.values()){
                    loadExternal(location, document, documents);
                }
            }

            return getResults(documents, generateResponse);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Set<SoapPortConverterResult> getResults(final Map<String, WSDLDocument> documents,
                                                    final boolean generateResponse){
        final Set<SoapPortConverterResult> results = new HashSet<>();
        for(Map.Entry<String, WSDLDocument> documentEntry : documents.entrySet()){
            final String name = documentEntry.getKey();
            final WSDLDocument wsdlDocument = documentEntry.getValue();
            final Document document = wsdlDocument.getDocument();
            final Set<SoapPort> ports = parseDocument(document, generateResponse);

            final String content = DocumentUtility.toString(document);
            final SoapPortConverterResult result = SoapPortConverterResult.builder()
                    .name(name)
                    .ports(ports)
                    .definition(content)
                    .resourceType(wsdlDocument.getResourceType())
                    .build();

            results.add(result);
        }
        return results;
    }

    private Map<String, WSDLDocument> getDocuments(final List<File> files,
                                                   final SoapResourceType resourceType){
        try {
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setNamespaceAware(true);

            final Map<String, WSDLDocument> documents = new HashMap<>();
            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            for(File file : files){
                final Document document = documentBuilder.parse(file);
                document.getDocumentElement().normalize();
                documents.put(file.getName(), WSDLDocument.builder()
                        .document(document)
                        .definition(resourceType)
                        .build());
            }

            return documents;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void loadExternal(final String url,
                              final WSDLDocument wsdlDocument,
                              final Map<String, WSDLDocument> documents){
        try {
            final Set<String> externalPaths = this.getImports(url, wsdlDocument);
            for(String externalPath : externalPaths){
                final List<File> files = fileManager.uploadFiles(externalPath);
                final Map<String, WSDLDocument> externalDocuments =
                        this.getDocuments(files, SoapResourceType.WSDL_IMPORT);
                documents.putAll(externalDocuments);

                for(WSDLDocument externalWsdlDocument : externalDocuments.values()){
                    loadExternal(externalPath, externalWsdlDocument, documents);
                }
            }
        } catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Set<String> getImports(final String url,
                                     final WSDLDocument wsdlDocument){
        final Document document = wsdlDocument.getDocument();
        final NodeList importNodeList = document.getElementsByTagNameNS(WSDL_NAMESPACE, IMPORT_NAMESPACE);

        if(importNodeList == null){
            return Collections.emptySet();
        }

        final List<Element> importElements = DocumentUtility.getElements(importNodeList);

        return importElements.stream()
                .map(importElement -> DocumentUtility.getAttribute(importElement, LOCATION_NAMESPACE))
                .map(location -> UrlUtility.getPath(url, location))
                .collect(Collectors.toSet());
    }

    /**
     * THe method provides the functionality to parse a document and extract all the SOAP ports from the SOAP ports.
     * The method will also generate mocked responses if the {@code generateResponse}
     * @param document The document which will be parsed
     * @param generateResponse Boolean value determining if a response should be generated for each extracted
     *                         operation.
     * @return A list of SOAP ports
     */
    private Set<SoapPort> parseDocument(final Document document,
                               final boolean generateResponse){
        final Set<SoapPort> soapPorts = new HashSet<>();
        final NodeList serviceNodeList =
                document.getDocumentElement().getElementsByTagNameNS(WSDL_NAMESPACE, SERVICE_NAMESPACE);
        final List<Element> serviceElements = DocumentUtility.getElements(serviceNodeList);
        for (Element serviceElement : serviceElements) {
            final NodeList portNodeList = serviceElement.getElementsByTagNameNS(WSDL_NAMESPACE, PORT_NAMESPACE);
            final List<Element> portElements = DocumentUtility.getElements(portNodeList);
            for (Element portElement : portElements) {
                String soapOperationAddress = DocumentUtility.extractSoapAddress(portElement, SOAP_11_NAMESPACE);
                SoapVersion soapOperationVersion = SoapVersion.SOAP11;

                if(soapOperationAddress == null){
                    soapOperationAddress = DocumentUtility.extractSoapAddress(portElement, SOAP_12_NAMESPACE);
                    soapOperationVersion = SoapVersion.SOAP12;
                }
                if(soapOperationAddress == null){
                    // The port element is not a SOAP port
                    continue;
                }

                final String portName = DocumentUtility.getAttribute(portElement, NAME_NAMESPACE);
                final String portBinding = DocumentUtility.getAttribute(portElement, BINDING_NAMESPACE);
                final List<SoapOperation> soapOperations =
                        getSoapOperations(document, portBinding,
                                soapOperationAddress, soapOperationVersion,
                                generateResponse);
                final SoapPort soapPort = new SoapPort();
                soapPort.setName(portName);
                soapPort.setOperations(soapOperations);
                soapPort.setUri(portName);
                soapPorts.add(soapPort);

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
    private List<SoapOperation> getSoapOperations(final Document document,
                                                         final String soapPortBinding,
                                                         final String soapOperationAddress,
                                                         final SoapVersion soapVersion,
                                                         final boolean generateResponse){
        final List<SoapOperation> soapOperations = new LinkedList<SoapOperation>();
        final Element bindingElement = DocumentUtility.findElement(document, WSDL_NAMESPACE, BINDING_NAMESPACE, soapPortBinding);
        if(bindingElement == null){
            return soapOperations;
        }
        final String bindingType = DocumentUtility.getAttribute(bindingElement, TYPE_NAMESPACE);

        if(bindingType == null){
            return soapOperations;
        }

        final Element portTypeElement = DocumentUtility.findElement(document, WSDL_NAMESPACE, PORT_TYPE_NAMESPACE, bindingType);

        if(portTypeElement == null){
            return soapOperations;
        }

        final NodeList operationNodeList = portTypeElement.getElementsByTagNameNS(WSDL_NAMESPACE, OPERATION_NAMESPACE);
        final List<Element> operationElements = DocumentUtility.getElements(operationNodeList);

        // harpipl
        final Map<String, Element> messages = DocumentUtility.findMessages(document);
        // Attributes
        final Map<String, Node> namespaces = DocumentUtility.getAttributes(document);

        for (Element operationElement : operationElements) {
            final String operationName = DocumentUtility.getAttribute(operationElement, NAME_NAMESPACE);
            final SoapOperation soapOperation = new SoapOperation();
            final String defaultBody = generateDefaultBody(operationName, operationElement.getNamespaceURI());
            final SoapOperationIdentifier operationIdentifier =
                    getSoapOperationIdentifier(messages, namespaces, operationElement);


            soapOperation.setName(operationName);
            soapOperation.setHttpMethod(HttpMethod.POST);
            soapOperation.setOperationIdentifier(operationIdentifier);
            soapOperation.setStatus(SoapOperationStatus.MOCKED);
            soapOperation.setResponseStrategy(SoapResponseStrategy.RANDOM);
            soapOperation.setForwardedEndpoint(soapOperationAddress);
            soapOperation.setOriginalEndpoint(soapOperationAddress);
            soapOperation.setSoapVersion(soapVersion);
            soapOperation.setMockResponses(new ArrayList<SoapMockResponse>());
            soapOperation.setDefaultBody(defaultBody);
            soapOperation.setCurrentResponseSequenceIndex(DEFAULT_RESPONSE_SEQUENCE_INDEX);
            soapOperation.setIdentifyStrategy(SoapOperationIdentifyStrategy.ELEMENT_NAMESPACE);
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
        return soapOperations;
    }

    private SoapOperationIdentifier getSoapOperationIdentifier(final Map<String, Element> messages,
                                             final Map<String, Node> attributes,
                                             final Element operationElement){
        final NodeList inputNodeList = operationElement.getElementsByTagNameNS(WSDL_NAMESPACE, INPUT_NAMESPACE);
        final List<Element> inputsNodes = DocumentUtility.getElements(inputNodeList);
        for (final Element inputElement : inputsNodes) {

            String inputMessageName = DocumentUtility.getAttribute(inputElement, MESSAGE_NAMESPACE);
            final Element messageElement = messages.get(inputMessageName);
            if (messageElement != null) {

                final NodeList partNodeList = messageElement.getElementsByTagNameNS(WSDL_NAMESPACE, PART_NAMESPACE);
                final List<Element> partElements = DocumentUtility.getElements(partNodeList);

                for(Element partElement : partElements){
                    String inputMessageElement = partElement.getAttribute(ELEMENT_NAMESPACE);
                    String[] messageElementParts = inputMessageElement.split(":");

                    String name = null;
                    String namespace = null;
                    if(messageElementParts.length == 1) {
                        name = messageElementParts[0];
                    } else if(messageElementParts.length == 2) {
                        name = messageElementParts[1];
                        String namespaceName = messageElementParts[0];
                        Node namespaceNode = attributes.get(namespaceName);

                        if(namespaceNode != null){
                            namespace = namespaceNode.getNodeValue();
                        }
                    } else {
                        name = inputMessageName;
                    }

                    final SoapOperationIdentifier operationIdentifier = new SoapOperationIdentifier();
                    operationIdentifier.setName(name);
                    operationIdentifier.setNamespace(namespace);
                    return operationIdentifier;
                }
            }
        }
        return new SoapOperationIdentifier();
    }


    /**
     * The method provides the functionality to generate a new mocked response
     * @param name The operation contains all the information that is needed to compose a operation default body
     * @param targetNamespace The target namespace that the response will use
     * @return A string value of the response
     */
    private static String generateDefaultBody(final String name, final String targetNamespace){
        final String prefix = "web";
        final String resultElement = name + "Result";
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:" +
                prefix + "=\"" + targetNamespace + "\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <" + prefix + ":" + resultElement + ">?</" + prefix + ":" + resultElement + ">\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }
}
