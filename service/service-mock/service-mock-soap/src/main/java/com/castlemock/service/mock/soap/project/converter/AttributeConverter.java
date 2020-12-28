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

import com.castlemock.model.mock.soap.domain.SoapOperationIdentifier;
import com.castlemock.service.mock.soap.project.converter.types.Attribute;
import com.castlemock.service.mock.soap.project.converter.types.Namespace;

import java.util.Set;

public final class AttributeConverter {

    public AttributeConverter(){

    }

    public static SoapOperationIdentifier toSoapOperationIdentifier(final Attribute attribute,
                                                                    final Set<Namespace> namespaces){
        final String name = attribute.getLocalName();
        final String namespace = attribute.getNamespace()
                .flatMap(namespaceName -> namespaces.stream()
                        .filter(namespace1 -> namespace1.getLocalName().equals(namespaceName))
                        .findFirst()
                        .map(Namespace::getValue))
                .orElse(null);

        return SoapOperationIdentifier.builder()
                .name(name)
                .namespace(namespace)
                .build();
    }

}
