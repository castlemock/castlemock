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
package com.castlemock.service.mock.soap.project.converter.types;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.TypeInfo;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public final class WsdlNamespaceParser extends WsdlParser {

    public Set<Namespace> parseNamespaces(final Document document){
        final TypeInfo typeInfo = document.getDocumentElement().getSchemaTypeInfo();
        final Set<Namespace> namespaces = new HashSet<>();
        if(typeInfo instanceof Node){
            Node node = (Node) typeInfo;
            NamedNodeMap nodeMap = node.getAttributes();

            IntStream.range(0, nodeMap.getLength())
                    .forEach(index -> {
                        Node attributeNode = nodeMap.item(index);
                        namespaces.add(Namespace.builder()
                                .name(attributeNode.getNodeName())
                                .localName(attributeNode.getLocalName())
                                .value(attributeNode.getNodeValue())
                                .build()); });
        }

        return namespaces;
    }

}
