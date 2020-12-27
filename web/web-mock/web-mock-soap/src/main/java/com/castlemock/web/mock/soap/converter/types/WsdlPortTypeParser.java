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

package com.castlemock.web.mock.soap.converter.types;

import com.castlemock.web.basis.utility.DocumentUtility;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class WsdlPortTypeParser extends WsdlParser {

    private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
    private static final String PORT_TYPE_NAMESPACE = "portType";
    private static final String NAME_NAMESPACE = "name";
    private static final String INPUT_NAMESPACE = "input";
    private static final String OUTPUT_NAMESPACE = "output";
    private static final String MESSAGE_NAMESPACE = "message";
    private static final String OPERATION_NAMESPACE = "operation";

    public Set<PortType> parsePortTypes(final Document document){
        final List<Element> portTypesElement =
                DocumentUtility.getElements(document, WSDL_NAMESPACE, PORT_TYPE_NAMESPACE);
        return portTypesElement.stream()
                .map(this::parsePortType)
                .collect(Collectors.toSet());
    }

    private PortType parsePortType(final Element portTypeElement){
        final List<Element> operationElements =
                DocumentUtility.getElements(portTypeElement, WSDL_NAMESPACE, OPERATION_NAMESPACE);
        final String name = DocumentUtility.getAttribute(portTypeElement, NAME_NAMESPACE)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find port type name"));
        final Set<PortTypeOperation> operations = operationElements.stream()
                .map(this::parseOperation)
                .collect(Collectors.toSet());
        return PortType.builder()
                .name(name)
                .operations(operations)
                .build();
    }

    private PortTypeOperation parseOperation(final Element operationElement){
        final String name = DocumentUtility.getAttribute(operationElement, NAME_NAMESPACE)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find operation name"));
        return PortTypeOperation.builder()
                .name(name)
                .input(parseInput(operationElement).orElse(null))
                .output(parseOutput(operationElement).orElse(null))
                .build();
    }

    private Optional<PortTypeOperationInput> parseInput(final Element operationElement){
        return DocumentUtility.getElement(operationElement, WSDL_NAMESPACE, INPUT_NAMESPACE)
                .flatMap(element -> this.getAttribute(element, MESSAGE_NAMESPACE))
                .map(message -> PortTypeOperationInput.builder()
                        .message(message)
                        .build());
    }

    private Optional<PortTypeOperationOutput> parseOutput(final Element operationElement){
        return DocumentUtility.getElement(operationElement, WSDL_NAMESPACE, OUTPUT_NAMESPACE)
                .flatMap(element -> this.getAttribute(element, MESSAGE_NAMESPACE))
                .map(message -> PortTypeOperationOutput.builder()
                        .message(message)
                        .build());
    }
}
