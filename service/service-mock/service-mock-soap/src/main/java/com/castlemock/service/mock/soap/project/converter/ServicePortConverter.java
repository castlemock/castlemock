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

import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.core.utility.IdUtility;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseStatus;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapOperationIdentifier;
import com.castlemock.model.mock.soap.domain.SoapOperationIdentifyStrategy;
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapResponseStrategy;
import com.castlemock.service.mock.soap.project.converter.types.Attribute;
import com.castlemock.service.mock.soap.project.converter.types.Binding;
import com.castlemock.service.mock.soap.project.converter.types.BindingOperation;
import com.castlemock.service.mock.soap.project.converter.types.Message;
import com.castlemock.service.mock.soap.project.converter.types.Namespace;
import com.castlemock.service.mock.soap.project.converter.types.PortType;
import com.castlemock.service.mock.soap.project.converter.types.PortTypeOperation;
import com.castlemock.service.mock.soap.project.converter.types.PortTypeOperationInput;
import com.castlemock.service.mock.soap.project.converter.types.PortTypeOperationOutput;
import com.castlemock.service.mock.soap.project.converter.types.ServicePort;
import com.castlemock.service.mock.soap.project.converter.types.ServicePortAddress;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class ServicePortConverter {

    private static final Integer DEFAULT_RESPONSE_SEQUENCE_INDEX = 0;
    private static final String AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME = "Auto-generated mocked response";
    private static final Integer DEFAULT_HTTP_STATUS_CODE = 200;

    private ServicePortConverter(){

    }

    public static SoapPort toSoapPort(final ServicePort servicePort,
                                      final String projectId,
                                      final Set<Binding> bindings,
                                      final Set<PortType> portTypes,
                                      final Set<Message> messages,
                                      final Set<Namespace> namespaces,
                                      final boolean generateResponse){
        final Binding binding = bindings.stream()
                .filter(tmp -> servicePort.getBinding().getLocalName().equals(tmp.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unable to find the binding"));

        final PortType portType = portTypes.stream()
                .filter(tmp -> binding.getType().getLocalName().equals(tmp.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unable to find the port type"));

        final String portId = IdUtility.generateId();
        final List<SoapOperation> operations = binding.getOperations()
                .stream()
                .map(bindingOperation -> toSoapOperation(bindingOperation,portId, portType,
                        messages, namespaces, servicePort.getAddress(), generateResponse))
                .collect(Collectors.toList());

        return SoapPort.builder()
                .id(portId)
                .projectId(projectId)
                .name(servicePort.getName())
                .operations(operations)
                .uri(servicePort.getName())
                .invokeAddress(servicePort.getName())
                .build();
    }

    private static SoapOperation toSoapOperation(final BindingOperation bindingOperation,
                                                 final String portId,
                                                 final PortType portType,
                                                 final Set<Message> messages,
                                                 final Set<Namespace> namespaces,
                                                 final ServicePortAddress address,
                                                 final boolean generateResponse){
        final PortTypeOperation portTypeOperation = portType.getOperations().stream()
                .filter(p -> bindingOperation.getName().equals(p.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unable to find port type with the following name: " + bindingOperation.getName()));

        final Optional<Message> inputMessage = messages.stream()
                .filter(message -> portTypeOperation.getInput()
                        .map(PortTypeOperationInput::getMessage)
                        .map(Attribute::getLocalName)
                        .map(localName -> localName.equals(message.getName()))
                        .orElse(Boolean.FALSE))
                .findFirst();

        final Optional<Message> outputMessage = messages.stream()
                .filter(message -> portTypeOperation.getOutput()
                        .map(PortTypeOperationOutput::getMessage)
                        .map(Attribute::getLocalName)
                        .map(localName -> localName.equals(message.getName()))
                        .orElse(Boolean.FALSE))
                .findFirst();

        final SoapOperationIdentifier operationRequestIdentifier =
                BindingOperationConverter.toSoapOperationIdentifierInput(bindingOperation,
                        inputMessage.orElse(null), namespaces);

        final SoapOperationIdentifier operationResponseIdentifier =
                BindingOperationConverter.toSoapOperationIdentifierOutput(bindingOperation,
                        outputMessage.orElse(null), namespaces);

        final String defaultBody = SoapOperationIdentifierConverter.toDefaultBody(operationResponseIdentifier);

        final String operationId = IdUtility.generateId();
        final List<SoapMockResponse> mockResponses = generateResponse ?
                List.of(createSoapMockResponse(defaultBody, operationId)) : List.of();

        return SoapOperation.builder()
                .id(operationId)
                .portId(portId)
                .operationIdentifier(operationRequestIdentifier)
                .name(bindingOperation.getName())
                .httpMethod(HttpMethod.POST)
                .status(SoapOperationStatus.MOCKED)
                .responseStrategy(SoapResponseStrategy.RANDOM)
                .forwardedEndpoint(address.getLocation())
                .originalEndpoint(address.getLocation())
                .soapVersion(address.getVersion())
                .mockResponses(mockResponses)
                .defaultBody(defaultBody)
                .currentResponseSequenceIndex(DEFAULT_RESPONSE_SEQUENCE_INDEX)
                .identifyStrategy(SoapOperationIdentifyStrategy.ELEMENT_NAMESPACE)
                .simulateNetworkDelay(false)
                .mockOnFailure(false)
                .automaticForward(false)
                .build();
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
