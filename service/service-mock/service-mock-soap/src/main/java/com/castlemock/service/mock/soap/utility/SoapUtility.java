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

package com.castlemock.service.mock.soap.utility;

import com.castlemock.model.mock.soap.domain.SoapOperationIdentifier;
import com.castlemock.service.core.utility.DocumentUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @author Karl Dahlgren
 * @since 1.35
 */
public class SoapUtility {

    private static final String DIVIDER = ":";
    private static final String VARIABLE = "#";
    private static final String BODY = "Body";
    private static final String XMLNS = "xmlns";
    private static final String ADDRESS_NAMESPACE = "address";
    private static final String LOCATION_NAMESPACE = "location";
    private static final Logger LOGGER = LoggerFactory.getLogger(SoapUtility.class);

    /**
     * Extract the SOAP address from a port element
     * @param portElement The port element that contains the address
     * @param namespace The namespace of the address
     * @return The SOAP port address
     */
    public static Optional<String> extractSoapAddress(final Element portElement,
                                                      final String namespace){
        return DocumentUtility.getElements(portElement, namespace, ADDRESS_NAMESPACE).stream()
                .map(element -> element.getAttribute(LOCATION_NAMESPACE))
                .filter(Objects::nonNull)
                .findFirst();
    }

    /**
     * The method extract the operation name from the SOAP body
     * @param request The body that contains the operation name
     * @return The extracted operation name
     */
    public static SoapOperationIdentifier extractSoapRequestName(final String request){
        try {
            final Document document = getDocument(request);
            final Element body = getBodyElement(document);
            final Node message = getRequestNode(body);

            final String serviceNameWithPrefix = message.getNodeName();
            final Map<String, Node> attributes = getAttributes(message);
            final ElementName elementName = getElementName(serviceNameWithPrefix);
            final String namespace = getNamespace(elementName, attributes);

            return SoapOperationIdentifier.builder()
                    .namespace(namespace)
                    .name(elementName.getLocalName())
                    .build();
        }catch(Exception exception){
            LOGGER.error("Unable to extract SOAP request name", exception);
            throw new IllegalStateException(exception.getMessage());
        }
    }

    private static Document getDocument(final String body) throws IOException, SAXException, ParserConfigurationException {
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        final InputSource inputSource = new InputSource(new StringReader(body));
        return documentBuilder.parse(inputSource);
    }

    private static Element getBodyElement(final Document document){
        return IntStream.range(0, document.getDocumentElement().getChildNodes().getLength())
                .mapToObj(index -> document.getDocumentElement().getChildNodes().item(index))
                .map(Node::getNodeName)
                .map(SoapUtility::getElementName)
                .filter(elementName -> elementName.getLocalName().equalsIgnoreCase(BODY))
                .findFirst()
                .flatMap(bodyElementName -> bodyElementName.getNamespace()
                        .map(namespace -> DocumentUtility.getElement(document, namespace + DIVIDER + BODY))
                        .orElseGet(() -> DocumentUtility.getElement(document, BODY)))
                .orElseThrow(() -> new IllegalArgumentException("Unable to extract the SOAP body"));
    }

    private static Node getRequestNode(final Node bodyElement){
        final NodeList bodyChildren = bodyElement.getChildNodes();

        if (bodyChildren.getLength() == 0) {
            throw new IllegalStateException("Invalid count of body children");
        }

        return IntStream.range(0, bodyChildren.getLength())
                .mapToObj(bodyChildren::item)
                .filter(node -> !node.getNodeName().contains(VARIABLE))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unable to extract the service name"));
    }

    private static String getNamespace(final ElementName elementName, final Map<String, Node> attributes){
        if(elementName.getNamespace().isPresent() &&
                attributes.containsKey(elementName.getNamespace().get())){
            Node namespaceNode = attributes.get(elementName.getNamespace().get());
            return namespaceNode.getNodeValue();
        } else if(attributes.containsKey(XMLNS)){
            Node namespaceNode = attributes.get(XMLNS);
            return namespaceNode.getNodeValue();
        }

        return null;
    }

    private static Map<String, Node> getAttributes(final Node bodyRequestNode){
        final Map<String, Node> attributes = new HashMap<>();

        if(bodyRequestNode instanceof Element){
            getAttributes((Element) bodyRequestNode, attributes);
        }

        return attributes;
    }

    private static void getAttributes(final Element element,
                                     final Map<String, Node> attributes){
        final TypeInfo typeInfo = element.getSchemaTypeInfo();

        if(typeInfo instanceof Node){
            Node node = (Node) typeInfo;
            NamedNodeMap nodeMap = node.getAttributes();

            for(int index = 0; index < nodeMap.getLength(); index++){
                final Node attributeNode = nodeMap.item(index);
                final String nodeName = attributeNode.getNodeName();
                final ElementName elementName = getElementName(nodeName);
                attributes.put(elementName.getLocalName(), attributeNode);
            }
        }

        Node parentNode = element.getParentNode();
        if(parentNode instanceof Element){
            getAttributes((Element) parentNode, attributes);
        }
    }

    /**
     * Returns the element name or prefix
     * @param element The element with both the name and namespace
     * @return Either the element name or namespace
     */
    private static ElementName getElementName(final String element){
        final String[] elementDivided = element.split(DIVIDER);

        if (elementDivided.length == 1) {
            return ElementName.builder()
                    .localName(elementDivided[0])
                    .build();
        }
        if (elementDivided.length == 2) {
            return ElementName.builder()
                    .namespace(elementDivided[0])
                    .localName(elementDivided[1])
                    .build();
        }

        throw new IllegalArgumentException("Unable to find the name or prefix in the XML element");
    }

}
