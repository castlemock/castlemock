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

import com.castlemock.model.core.model.http.domain.HttpMethod;
import com.castlemock.model.mock.soap.domain.*;
import com.castlemock.service.mock.soap.project.converter.types.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class ServicePortConverter {

    private static final Integer DEFAULT_RESPONSE_SEQUENCE_INDEX = 0;

    private ServicePortConverter(){

    }

    public static SoapPort toSoapPort(final ServicePort servicePort,
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
                .map(bindingOperation -> toSoapOperation(bindingOperation, portType, messages, namespaces, servicePort.getAddress()))
                .collect(Collectors.toList());

        final SoapPort soapPort = new SoapPort();
        soapPort.setName(servicePort.getName());
        soapPort.setOperations(operations);
        soapPort.setUri(servicePort.getName());
        return soapPort;
    }

    private static SoapOperation toSoapOperation(final BindingOperation bindingOperation,
                                                 final PortType portType,
                                                 final Set<Message> messages,
                                                 final Set<Namespace> namespaces,
                                                 final ServicePortAddress address){
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
        soapOperation.setDefaultBody(SoapOperationIdentifierConverter.toDefaultBody(operationResponseIdentifier));
        soapOperation.setCurrentResponseSequenceIndex(DEFAULT_RESPONSE_SEQUENCE_INDEX);
        soapOperation.setIdentifyStrategy(SoapOperationIdentifyStrategy.ELEMENT_NAMESPACE);

        return soapOperation;
    }

}
