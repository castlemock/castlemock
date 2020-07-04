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

package com.castlemock.web.mock.soap.converter;

import com.castlemock.core.mock.soap.model.project.domain.SoapOperationIdentifier;
import com.castlemock.web.mock.soap.converter.types.*;

import java.util.Optional;
import java.util.Set;

public final class BindingOperationConverter {

    private BindingOperationConverter(){

    }

    public static SoapOperationIdentifier toSoapOperationIdentifierInput(final BindingOperation bindingOperation,
                                                                         final Message inputMessage,
                                                                         final Set<Namespace> namespaces){
        return bindingOperation.getInput()
                .flatMap(BindingOperationInput::getBody)
                .map(body -> findInputMessage(body, inputMessage))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(inputMessagePart -> MessagePartConverter.toSoapOperationIdentifier(inputMessagePart, namespaces))
                .orElseGet(() -> toSoapOperationIdentifier(bindingOperation));
    }

    public static SoapOperationIdentifier toSoapOperationIdentifierOutput(final BindingOperation bindingOperation,
                                                                         final Message outputMessage,
                                                                         final Set<Namespace> namespaces){
        return bindingOperation.getOutput()
                .flatMap(BindingOperationOutput::getBody)
                .map(body -> findOutputMessage(body, outputMessage))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(outputMessagePart -> MessagePartConverter.toSoapOperationIdentifier(outputMessagePart, namespaces))
                .orElseGet(() -> toSoapOperationIdentifier(bindingOperation));
    }

    private static Optional<MessagePart> findInputMessage(final BindingOperationInputBody body, final Message message){
        return body.getParts()
                .map(parts -> findMessage(message, parts))
                .orElseGet(() -> Optional.ofNullable(message)
                        .flatMap(parts -> parts.getParts()
                                .stream()
                                .findFirst()));
    }

    private static Optional<MessagePart> findOutputMessage(final BindingOperationOutputBody body, final Message message){
        return body.getParts()
                .map(parts -> findMessage(message, parts))
                .orElseGet(() -> Optional.ofNullable(message)
                        .flatMap(parts -> parts.getParts()
                                .stream()
                                .findFirst()));
    }

    private static Optional<MessagePart> findMessage(final Message message, final String parts){
        return Optional.ofNullable(message)
                .flatMap(value -> value.getParts()
                        .stream()
                        .filter(messagePart -> parts.equals(messagePart.getName()))
                        .findFirst());
    }

    private static SoapOperationIdentifier toSoapOperationIdentifier(final BindingOperation bindingOperation){
        return SoapOperationIdentifier.builder()
                .name(bindingOperation.getName())
                .namespace(null)
                .build();
    }

}
