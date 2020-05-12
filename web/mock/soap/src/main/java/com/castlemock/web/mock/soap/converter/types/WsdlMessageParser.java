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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class WsdlMessageParser extends WsdlParser {

    private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
    private static final String NAME_NAMESPACE = "name";
    private static final String ELEMENT_NAMESPACE = "element";
    private static final String TYPE_NAMESPACE = "type";
    private static final String PART_NAMESPACE = "part";
    private static final String MESSAGE_NAMESPACE = "message";

    public Set<Message> parseMessages(final Document document){
        final List<Element> portTypesElement = DocumentUtility.getElements(document, WSDL_NAMESPACE, MESSAGE_NAMESPACE);
        return portTypesElement.stream()
                .map(this::parseMessage)
                .collect(Collectors.toSet());
    }

    private Message parseMessage(final Element messageElement){
        final String name = DocumentUtility.getAttribute(messageElement, NAME_NAMESPACE)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find message name"));
        final List<Element> partElements = DocumentUtility.getElements(messageElement, WSDL_NAMESPACE, PART_NAMESPACE);
        final Set<MessagePart> parts = partElements.stream()
                .map(this::parseMessagePart)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return Message.builder()
                .name(name)
                .parts(parts)
                .build();
    }

    private MessagePart parseMessagePart(final Element messageElement){

        final String name = DocumentUtility.getAttribute(messageElement, NAME_NAMESPACE).orElse(null);
        /*
        final Attribute element = this.getAttribute(messageElement, ELEMENT_NAMESPACE)
                .orElseGet(() -> this.getAttribute(messageElement, TYPE_NAMESPACE)
                        .orElseThrow(() -> new IllegalArgumentException("Unable to find either element or type attribute")));
        */
        final Attribute element = this.getAttribute(messageElement, ELEMENT_NAMESPACE).orElse(null);

        if(name == null || element == null){
            return null;
        }

        return MessagePart.builder()
                .name(name)
                .element(element)
                .build();
    }
}
