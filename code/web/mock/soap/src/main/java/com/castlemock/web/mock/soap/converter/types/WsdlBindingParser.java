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

import com.castlemock.web.basis.support.DocumentUtility;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class WsdlBindingParser extends WsdlParser {

    private static final String SOAP_11_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap/";
    private static final String SOAP_12_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap12/";

    private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
    private static final String BINDING_NAMESPACE = "binding";
    private static final String NAME_NAMESPACE = "name";
    private static final String OPERATION_NAMESPACE = "operation";
    private static final String PARTS_NAMESPACE = "parts";
    private static final String INPUT_NAMESPACE = "input";
    private static final String OUTPUT_NAMESPACE = "output";
    private static final String BODY_NAMESPACE = "body";
    private static final String TYPE_NAMESPACE = "type";

    public Set<Binding> parseBindings(final Document document){
        final List<Element> bindingElements =
                DocumentUtility.getElements(document, WSDL_NAMESPACE, BINDING_NAMESPACE);
        return bindingElements.stream()
                .map(this::parseBinding)
                .collect(Collectors.toSet());
    }

    private Binding parseBinding(final Element bindingElement){
        final String name = DocumentUtility.getAttribute(bindingElement, NAME_NAMESPACE)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find binding name"));
        final Attribute type = this.getAttribute(bindingElement, TYPE_NAMESPACE)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find type attribute"));

        final List<Element> operationElements =
                DocumentUtility.getElements(bindingElement, WSDL_NAMESPACE, OPERATION_NAMESPACE);
        final Set<BindingOperation> operations = operationElements.stream()
                .map(this::parseOperation)
                .collect(Collectors.toSet());
        return Binding.builder()
                .name(name)
                .type(type)
                .operations(operations)
                .build();
    }

    private BindingOperation parseOperation(final Element operationElement){
        final String name = DocumentUtility.getAttribute(operationElement, NAME_NAMESPACE)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find operation name"));
        return BindingOperation.builder()
                .name(name)
                .input(parseInput(operationElement))
                .output(parseOutput(operationElement))
                .build();
    }

    private BindingOperationInput parseInput(final Element operationElement){
        final Optional<Element> inputElement =
                DocumentUtility.getElement(operationElement, WSDL_NAMESPACE, INPUT_NAMESPACE);

        return inputElement
                .map(element -> BindingOperationInput.builder()
                        .body(parseInputBody(element).orElse(null))
                        .build())
                .orElseThrow(() -> new IllegalArgumentException("Unable to find operation input"));
    }

    private BindingOperationOutput parseOutput(final Element operationElement){
        final Optional<Element> outputElement =
                DocumentUtility.getElement(operationElement, WSDL_NAMESPACE, OUTPUT_NAMESPACE);

        return outputElement
                .map(element -> BindingOperationOutput.builder()
                        .body(parseOutputBody(element).orElse(null))
                        .build())
                .orElseThrow(() -> new IllegalArgumentException("Unable to find operation output"));
    }

    private Optional<BindingOperationInputBody> parseInputBody(final Element inputElement){
        return DocumentUtility.getElement(inputElement, SOAP_11_NAMESPACE, BODY_NAMESPACE)
                        .map(element -> {
                            final String parts = DocumentUtility.getAttribute(element, PARTS_NAMESPACE)
                                    .orElse(null);
                            return Optional.of(BindingOperationInputBody.builder()
                                    .parts(parts)
                                    .build());
                        })
                        .orElseGet(() -> {
                            return DocumentUtility.getElement(inputElement, SOAP_12_NAMESPACE, BODY_NAMESPACE)
                                    .map(element -> {
                                        final String parts = DocumentUtility.getAttribute(element, PARTS_NAMESPACE)
                                                .orElse(null);
                                        return Optional.of(BindingOperationInputBody.builder()
                                                .parts(parts)
                                                .build());
                                    }).orElse(Optional.empty());
                        });
    }

    private Optional<BindingOperationOutputBody> parseOutputBody(final Element outputElement){
        return DocumentUtility.getElement(outputElement, SOAP_11_NAMESPACE, BODY_NAMESPACE)
                        .map(element -> {
                            final String parts = DocumentUtility.getAttribute(element, PARTS_NAMESPACE)
                                    .orElse(null);
                            return Optional.of(BindingOperationOutputBody.builder()
                                    .parts(parts)
                                    .build());
                }).orElseGet(() -> {
                    return DocumentUtility.getElement(outputElement, SOAP_12_NAMESPACE, BODY_NAMESPACE)
                            .map(element -> {
                                final String parts = DocumentUtility.getAttribute(element, PARTS_NAMESPACE)
                                        .orElse(null);
                                return Optional.of(BindingOperationOutputBody.builder()
                                        .parts(parts)
                                        .build());
                            })
                            .orElse(Optional.empty());
        });
    }
}
