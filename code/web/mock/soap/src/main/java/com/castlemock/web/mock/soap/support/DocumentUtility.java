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

package com.castlemock.web.mock.soap.support;

import org.w3c.dom.*;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.*;

public class DocumentUtility {


    private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
    private static final String NAME_NAMESPACE = "name";
    private static final String MESSAGE_NAMESPACE = "message";
    private static final String ADDRESS_NAMESPACE = "address";
    private static final String LOCATION_NAMESPACE = "location";


    /**
     * Search and find for a specific element
     * @param document The document that will be parsed
     * @param namespace The element namespace
     * @param type The type or name of the element
     * @param identifier The identifier is used to determine if the element is the correct element being searched for.
     * @return An element that matches the provided search criteria
     */
    public static Element findElement(final Document document, final String namespace,
                                       final String type, final String identifier){
        final NodeList nodeList = document.getElementsByTagNameNS(namespace, type);
        for (int index = 0; index < nodeList.getLength(); index++) {
            Node node = nodeList.item(index);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getAttribute(NAME_NAMESPACE);
                if(name.equals(identifier)){
                    return element;
                }
            }
        }
        return null;
    }


    public static Element findElement(final Element element, final String namespace,
                                      final String type, final String identifier){
        final NodeList nodeList = element.getElementsByTagNameNS(namespace, type);
        for (int index = 0; index < nodeList.getLength(); index++) {
            Node node = nodeList.item(index);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element subElement = (Element) node;
                String name = subElement.getAttribute(NAME_NAMESPACE);
                if(name.equals(identifier)){
                    return subElement;
                }
            }
        }
        return null;
    }

    public static Map<String, Element> findMessages(final Document document){
        Map<String, Element> messages = new HashMap<String, Element>();

        final NodeList nodeList =
                document.getDocumentElement().getElementsByTagNameNS(WSDL_NAMESPACE, MESSAGE_NAMESPACE);
        for (int index = 0; index < nodeList.getLength(); index++) {
            Node node = nodeList.item(index);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String name = element.getAttribute(NAME_NAMESPACE);
                messages.put(name, element);
            }
        }
        return messages;
    }

    /**
     * Extracts an attribute from an element. The method will also remove namespace prefix
     * if it is present.
     * @param element The element which the attribute will be extracted from
     * @param name The name of the attribute that will be extracted
     * @return The attribute value
     */
    public static String getAttribute(Element element, String name){
        final String value = element.getAttribute(name);
        if(value == null){
            return null;
        }
        String[] splitValues = value.split(":");
        if(splitValues.length == 1) {
            return splitValues[0];
        }
        return splitValues[1];
    }



    /**
     * Extract the SOAP address from a port element
     * @param portElement The port element that contains the address
     * @param namespace The namespace of the address
     * @return The SOAP port address
     */
    public static String extractSoapAddress(Element portElement, String namespace){
        NodeList addressElements = portElement.getElementsByTagNameNS(namespace, ADDRESS_NAMESPACE);


        for (int addressIndex = 0; addressIndex < addressElements.getLength(); addressIndex++) {
            Node soapAddressNode = addressElements.item(addressIndex);
            if (soapAddressNode.getNodeType() == Node.ELEMENT_NODE) {
                Element soapAddressElement = (Element) soapAddressNode;
                String soapAddress = soapAddressElement.getAttribute(LOCATION_NAMESPACE);
                if(soapAddress != null){
                    return soapAddress;
                }
            }
        }
        return null;
    }


    public static List<Element> getElements(final Element element,
                                            final String namespace,
                                            final String type){
        final NodeList nodeList = element.getElementsByTagNameNS(namespace, type);
        return getElements(nodeList);
    }

    public static Optional<Element> getElement(final Element element,
                                               final String namespace,
                                               final String type){
        final NodeList nodeList = element.getElementsByTagNameNS(namespace, type);

        if(nodeList.getLength() == 0){
            return Optional.empty();
        }

        return Optional.ofNullable(getElements(nodeList).get(0));
    }

    public static List<Element> getElements(final NodeList nodeList){
        final List<Element> elements = new ArrayList<>();
        for (int serviceIndex = 0; serviceIndex < nodeList.getLength(); serviceIndex++) {
            final Node node = nodeList.item(serviceIndex);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                final Element element = (Element) node;
                elements.add(element);
            }
        }

        return elements;
    }

    public static Map<String, Node> getAttributes(final Document document){
        final TypeInfo typeInfo = document.getDocumentElement().getSchemaTypeInfo();
        final Map<String, Node> attributes = new HashMap<>();

        if(typeInfo instanceof Node){
            Node node = (Node) typeInfo;
            NamedNodeMap nodeMap = node.getAttributes();

            for(int index = 0; index < nodeMap.getLength(); index++){
                Node attributeNode = nodeMap.item(index);
                String value = attributeNode.getNodeValue();
                String localName = attributeNode.getLocalName();
                String name = attributeNode.getNodeName();

                attributes.put(localName, attributeNode);
            }
        }

        return attributes;
    }

    public static String toString(final Document doc) {
        try {
            final StringWriter sw = new StringWriter();
            final TransformerFactory tf = TransformerFactory.newInstance();
            final Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }

}
