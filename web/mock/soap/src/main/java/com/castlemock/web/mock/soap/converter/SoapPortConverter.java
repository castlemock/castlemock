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
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponseStatus;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationIdentifier;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationIdentifyStrategy;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationStatus;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapResourceType;
import com.castlemock.core.mock.soap.model.project.domain.SoapResponseStrategy;
import com.castlemock.web.basis.manager.FileManager;
import com.castlemock.web.basis.support.DocumentUtility;
import com.castlemock.web.basis.utility.UrlUtility;
import com.castlemock.web.mock.soap.converter.types.Attribute;
import com.castlemock.web.mock.soap.converter.types.Binding;
import com.castlemock.web.mock.soap.converter.types.BindingOperation;
import com.castlemock.web.mock.soap.converter.types.BindingOperationInputBody;
import com.castlemock.web.mock.soap.converter.types.BindingOperationOutputBody;
import com.castlemock.web.mock.soap.converter.types.Message;
import com.castlemock.web.mock.soap.converter.types.MessagePart;
import com.castlemock.web.mock.soap.converter.types.Namespace;
import com.castlemock.web.mock.soap.converter.types.PortType;
import com.castlemock.web.mock.soap.converter.types.PortTypeOperation;
import com.castlemock.web.mock.soap.converter.types.Service;
import com.castlemock.web.mock.soap.converter.types.ServicePort;
import com.castlemock.web.mock.soap.converter.types.ServicePortAddress;
import com.castlemock.web.mock.soap.converter.types.WsdlBindingParser;
import com.castlemock.web.mock.soap.converter.types.WsdlMessageParser;
import com.castlemock.web.mock.soap.converter.types.WsdlNamespaceParser;
import com.castlemock.web.mock.soap.converter.types.WsdlPortTypeParser;
import com.castlemock.web.mock.soap.converter.types.WsdlServiceParser;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SoapPortConverter {

    private static final String AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME = "Auto-generated mocked response";
    private static final Integer DEFAULT_RESPONSE_SEQUENCE_INDEX = 0;
    private static final Integer DEFAULT_HTTP_STATUS_CODE = 200;

    private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
    private static final String IMPORT_NAMESPACE = "import";
    private static final String LOCATION_NAMESPACE = "location";

    private static final WsdlBindingParser BINDING_PARSER = new WsdlBindingParser();
    private static final WsdlMessageParser MESSAGE_PARSER = new WsdlMessageParser();
    private static final WsdlServiceParser SERVICE_PARSER = new WsdlServiceParser();
    private static final WsdlPortTypeParser PORT_TYPE_PARSER = new WsdlPortTypeParser();
    private static final WsdlNamespaceParser NAMESPACE_PARSER = new WsdlNamespaceParser();

    private static final Logger LOGGER = LoggerFactory.getLogger(SoapPortConverter.class);

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
            final Map<String, WSDLDocument> allDocuments = new HashMap<>(documents);

            if(loadExternal){
                documents.values()
                        .forEach(document -> loadExternal(location, document, allDocuments));
            }

            return getResults(allDocuments, generateResponse);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Set<SoapPortConverterResult> getResults(final Map<String, WSDLDocument> documents,
                                                    final boolean generateResponse){
        try {
            return documents.entrySet().stream()
                    .map(documentEntry -> {
                        final String name = documentEntry.getKey();
                        final WSDLDocument wsdlDocument = documentEntry.getValue();
                        final Document document = wsdlDocument.getDocument();
                        final Set<SoapPort> ports = parseDocument(document, generateResponse);

                        final String content = DocumentUtility.toString(document);
                        return SoapPortConverterResult.builder()
                                .name(name)
                                .ports(ports)
                                .definition(content)
                                .resourceType(wsdlDocument.getResourceType())
                                .build();
                    })
                    .collect(Collectors.toSet());
        }catch (Exception exception) {
            LOGGER.error("Unable to parse WSDL: " + exception.getMessage(), exception);
            throw exception;
        }
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
        final List<Element> importElements = DocumentUtility.getElements(document, WSDL_NAMESPACE, IMPORT_NAMESPACE);

        return importElements.stream()
                .map(importElement -> DocumentUtility.getAttribute(importElement, LOCATION_NAMESPACE))
                .filter(Optional::isPresent)
                .map(Optional::get)
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


        final Set<Binding> bindings = BINDING_PARSER.parseBindings(document);
        final Set<Message> messages = MESSAGE_PARSER.parseMessages(document);
        final Set<Service> services = SERVICE_PARSER.parseServices(document);
        final Set<PortType> portTypes = PORT_TYPE_PARSER.parsePortTypes(document);
        final Set<Namespace> namespaces = NAMESPACE_PARSER.parseNamespaces(document);

        final Set<SoapPort> ports = services.stream()
                .map(Service::getPorts)
                .flatMap(Collection::stream)
                .map(servicePort -> createSoapPort(servicePort, bindings, portTypes, messages, namespaces))
                .collect(Collectors.toSet());

        if(generateResponse){
            ports.stream()
                    .map(SoapPort::getOperations)
                    .flatMap(List::stream)
                    .forEach(operation -> operation.getMockResponses()
                            .add(createSoapMockResponse(operation.getDefaultBody())));
        }

        return ports;
    }

    private SoapPort createSoapPort(final ServicePort servicePort,
                                    final Set<Binding> bindings,
                                    final Set<PortType> portTypes,
                                    final Set<Message> messages,
                                    final Set<Namespace> namespaces){

        final Binding binding = bindings.stream()
                .filter(tmp -> servicePort.getBinding().getLocalName().equals(tmp.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unable to find the binding"));

        final PortType portType = portTypes.stream()
                .filter(tmp -> binding.getType().getLocalName().equals(tmp.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unable to find the port type"));

        final List<SoapOperation> operations = binding.getOperations().stream()
                .map(b -> Maps.immutableEntry(
                        b,
                        portType.getOperations().stream()
                                .filter(p -> b.getName().equals(p.getName()))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Unable to map"))))
                .map(entry -> createSoapOperation(
                        entry.getKey(),
                        entry.getValue(),
                        messages,
                        namespaces,
                        servicePort.getAddress()))
                .collect(Collectors.toList());

        final SoapPort soapPort = new SoapPort();
        soapPort.setName(servicePort.getName());
        soapPort.setOperations(operations);
        soapPort.setUri(servicePort.getName());
        return soapPort;
    }

    private SoapOperation createSoapOperation(final BindingOperation bindingOperation,
                                              final PortTypeOperation portTypeOperation,
                                              final Set<Message> messages,
                                              final Set<Namespace> namespaces,
                                              final ServicePortAddress address){
        final Message inputMessage = messages.stream()
                .filter(message -> portTypeOperation.getInput().getMessage().getLocalName().equals(message.getName()))
                .findFirst()
                .orElseThrow((() -> new IllegalArgumentException("Unable to find the input message")));

        final Message outputMessage = messages.stream()
                .filter(message -> portTypeOperation.getOutput().getMessage().getLocalName().equals(message.getName()))
                .findFirst()
                .orElseThrow((() -> new IllegalArgumentException("Unable to find the input message")));

        final SoapOperationIdentifier operationRequestIdentifier = bindingOperation.getInput().getBody()
                .map(BindingOperationInputBody::getParts)
                .map(optional -> optional.orElse(null))
                .map(parts -> inputMessage.getParts()
                        .stream()
                        .filter(messagePart -> parts.equals(messagePart.getName()))
                        .findFirst())
                .orElseGet(() -> inputMessage.getParts()
                        .stream()
                        .findFirst())
                .map(inputMessagePart -> createSoapOperationIdentifier(inputMessagePart, namespaces))
                .orElseGet(() -> createSoapOperationIdentifier(bindingOperation, namespaces));

        final SoapOperationIdentifier operationResponseIdentifier = bindingOperation.getOutput().getBody()
                .map(BindingOperationOutputBody::getParts)
                .map(optional -> optional.orElse(null))
                .map(parts -> outputMessage.getParts().stream()
                        .filter(messagePart -> parts.equals(messagePart.getName()))
                        .findFirst())
                .orElse(outputMessage.getParts().stream()
                        .findFirst())
                .map(outputMessagePart -> createSoapOperationIdentifier(outputMessagePart, namespaces))
                .orElseGet(() -> createSoapOperationIdentifier(bindingOperation, namespaces));


        final SoapOperation soapOperation = new SoapOperation();

        soapOperation.setOperationIdentifier(operationRequestIdentifier);
        soapOperation.setName(bindingOperation.getName());
        soapOperation.setHttpMethod(HttpMethod.POST);
        soapOperation.setStatus(SoapOperationStatus.MOCKED);
        soapOperation.setResponseStrategy(SoapResponseStrategy.RANDOM);
        soapOperation.setForwardedEndpoint(address.getLocation());
        soapOperation.setOriginalEndpoint(address.getLocation());
        soapOperation.setSoapVersion(address.getVersion());
        soapOperation.setMockResponses(new ArrayList<>());
        soapOperation.setDefaultBody(generateDefaultBody(operationResponseIdentifier));
        soapOperation.setCurrentResponseSequenceIndex(DEFAULT_RESPONSE_SEQUENCE_INDEX);
        soapOperation.setIdentifyStrategy(SoapOperationIdentifyStrategy.ELEMENT_NAMESPACE);

        return soapOperation;
    }

    private static SoapOperationIdentifier createSoapOperationIdentifier(final BindingOperation bindingOperation,
                                                                         final Set<Namespace> namespaces){
        return SoapOperationIdentifier.builder()
                .name(bindingOperation.getName())
                .namespace(null)
                .build();
    }

    private static SoapOperationIdentifier createSoapOperationIdentifier(final MessagePart messagePart,
                                                                         final Set<Namespace> namespaces){
        return messagePart.getElement()
                .map(attribute -> createSoapOperationIdentifier(attribute, namespaces))
                .orElseGet(() -> SoapOperationIdentifier.builder()
                        .name(messagePart.getName())
                        .namespace(null)
                        .build());
    }

    private static SoapOperationIdentifier createSoapOperationIdentifier(final Attribute attribute,
                                                                         final Set<Namespace> namespaces){
        final String name = attribute.getLocalName();
        final String namespace = attribute.getNamespace()
                .map(namespaceName -> namespaces.stream()
                        .filter(namespace1 -> namespace1.getLocalName().equals(namespaceName))
                        .findFirst()
                        .map(Namespace::getValue).orElse(null))
                .orElse(null);

        return SoapOperationIdentifier.builder()
                .name(name)
                .namespace(namespace)
                .build();
    }

    private static SoapMockResponse createSoapMockResponse(final String defaultBody){
        final SoapMockResponse mockResponse = new SoapMockResponse();
        mockResponse.setBody(defaultBody);
        mockResponse.setStatus(SoapMockResponseStatus.ENABLED);
        mockResponse.setName(AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME);
        mockResponse.setHttpStatusCode(DEFAULT_HTTP_STATUS_CODE);
        return mockResponse;
    }


    /**
     * The method provides the functionality to generate a new mocked response
     * @return A string value of the response
     */
    private static String generateDefaultBody(final SoapOperationIdentifier operationResponseIdentifier){
        final String prefix = "web";
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:" +
                prefix + "=\"" + operationResponseIdentifier.getNamespace() + "\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <" + prefix + ":" + operationResponseIdentifier.getName() + ">?</" + prefix + ":" +
                operationResponseIdentifier.getName() + ">\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

}
