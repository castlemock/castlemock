/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.service.mock.soap.project.converter;

import com.castlemock.model.core.utility.IdUtility;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.service.mock.soap.project.converter.types.Binding;
import com.castlemock.service.mock.soap.project.converter.types.Message;
import com.castlemock.service.mock.soap.project.converter.types.Namespace;
import com.castlemock.service.mock.soap.project.converter.types.PortType;
import com.castlemock.service.mock.soap.project.converter.types.Service;
import com.castlemock.service.mock.soap.project.converter.types.WsdlBindingParser;
import com.castlemock.service.mock.soap.project.converter.types.WsdlMessageParser;
import com.castlemock.service.mock.soap.project.converter.types.WsdlNamespaceParser;
import com.castlemock.service.mock.soap.project.converter.types.WsdlPortTypeParser;
import com.castlemock.service.mock.soap.project.converter.types.WsdlServiceParser;
import org.w3c.dom.Document;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class DocumentConverter {

    private static final WsdlBindingParser BINDING_PARSER = new WsdlBindingParser();
    private static final WsdlMessageParser MESSAGE_PARSER = new WsdlMessageParser();
    private static final WsdlServiceParser SERVICE_PARSER = new WsdlServiceParser();
    private static final WsdlPortTypeParser PORT_TYPE_PARSER = new WsdlPortTypeParser();
    private static final WsdlNamespaceParser NAMESPACE_PARSER = new WsdlNamespaceParser();

    private static final String AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME = "Auto-generated mocked response";
    private static final Integer DEFAULT_HTTP_STATUS_CODE = 200;

    private DocumentConverter(){

    }

    /**
     * THe method provides the functionality to parse a document and extract all the SOAP ports from the SOAP ports.
     * The method will also generate mocked responses if the {@code generateResponse}
     * @param document The document which will be parsed
     * @param generateResponse Boolean value determining if a response should be generated for each extracted
     *                         operation.
     * @return A list of SOAP ports
     */
    public static Set<SoapPort> toSoapParts(final Document document,
                                        final String projectId,
                                        final boolean generateResponse){
        final Set<Binding> bindings = BINDING_PARSER.parseBindings(document);
        final Set<Message> messages = MESSAGE_PARSER.parseMessages(document);
        final Set<Service> services = SERVICE_PARSER.parseServices(document);
        final Set<PortType> portTypes = PORT_TYPE_PARSER.parsePortTypes(document);
        final Set<Namespace> namespaces = NAMESPACE_PARSER.parseNamespaces(document);

        final Set<SoapPort> ports = services.stream()
                .map(Service::getPorts)
                .flatMap(Collection::stream)
                .map(servicePort -> ServicePortConverter
                        .toSoapPort(servicePort, projectId, bindings, portTypes, messages, namespaces))
                .collect(Collectors.toSet());

        if(generateResponse){
            ports.stream()
                .map(SoapPort::getOperations)
                .flatMap(List::stream)
                .forEach(operation -> operation.getMockResponses()
                        .add(createSoapMockResponse(operation.getDefaultBody(), operation.getId())));
        }

        return ports;
    }

    private static SoapMockResponse createSoapMockResponse(final String defaultBody,
                                                           final String operationId){
        return SoapMockResponse.builder()
                .id(IdUtility.generateId())
                .operationId(operationId)
                .body(defaultBody)
                .status(SoapMockResponseStatus.ENABLED)
                .name(AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME)
                .httpStatusCode(DEFAULT_HTTP_STATUS_CODE)
                .usingExpressions(false)
                .build();
    }

}
