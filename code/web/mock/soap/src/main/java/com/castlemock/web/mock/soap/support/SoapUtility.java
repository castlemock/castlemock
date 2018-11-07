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

import com.castlemock.core.mock.soap.model.project.domain.SoapOperationIdentifier;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class SoapUtility {

    private static final String DIVIDER = ":";
    private static final String VARIABLE = "#";
    private static final String BODY = "Body";
    private static final String XMLNS = "xmlns";
    private static final Logger LOGGER = Logger.getLogger(SoapUtility.class);

    /**
     * The method extract the operation name from the SOAP body
     * @param body The body that contains the operation name
     * @return The extracted operation name
     */
    public static SoapOperationIdentifier extractSoapRequestName(final String body){
        try {
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final InputSource inputSource = new InputSource(new StringReader(body));
            final Document document = documentBuilder.parse(inputSource);
            final String rootName = document.getDocumentElement().getNodeName();
            final String prefix = getElement(rootName, 0);

            NodeList nodeList = document.getElementsByTagName(prefix + DIVIDER + BODY);
            Node bodyNode = nodeList.item(0);

            if(bodyNode == null){
                // Unable to extract the body. Try to extract the
                // body without the namespace
                LOGGER.trace("Unable to extract the SOAP request body. " +
                        "Trying to extract the body without the namespace");
                nodeList = document.getElementsByTagName(BODY);
                bodyNode = nodeList.item(0);
            }

            final NodeList bodyChildren = bodyNode.getChildNodes();

            if (bodyChildren.getLength() == 0) {
                throw new IllegalStateException("Invalid count of body children");
            }

            Node bodyRequestNode = null;

            for (int index = 0; index < bodyChildren.getLength(); index++) {
                if (!bodyChildren.item(index).getNodeName().contains(VARIABLE)) {
                    bodyRequestNode = bodyChildren.item(index);
                }
            }

            if (bodyRequestNode == null) {
                throw new IllegalStateException("Unable to extract the service name");
            }

            String serviceNameWithPrefix = bodyRequestNode.getNodeName();
            Map<String, Node> attributes = new HashMap<>();

            if(bodyRequestNode instanceof Element){
                getAttributes((Element) bodyRequestNode, attributes);
            }

            final String namespacePrefix = getElement(serviceNameWithPrefix, 0);
            final String name = getElement(serviceNameWithPrefix, 1);
            String namespace = null;
            if(attributes.containsKey(namespacePrefix)){
                Node namespaceNode = attributes.get(namespacePrefix);
                namespace = namespaceNode.getNodeValue();
            } else if(attributes.containsKey(XMLNS)){
                Node namespaceNode = attributes.get(XMLNS);
                namespace = namespaceNode.getNodeValue();
            }

            final SoapOperationIdentifier operationIdentifier = new SoapOperationIdentifier();
            operationIdentifier.setNamespace(namespace);
            operationIdentifier.setName(name);

            return operationIdentifier;
        }catch(Exception exception){
            LOGGER.error("Unable to extract SOAP request name", exception);
            throw new IllegalStateException(exception.getMessage());
        }
    }



    private static void getAttributes(final Element element,
                                     final Map<String, Node> attributes){
        final TypeInfo typeInfo = element.getSchemaTypeInfo();

        if(typeInfo instanceof Node){
            Node node = (Node) typeInfo;
            NamedNodeMap nodeMap = node.getAttributes();

            for(int index = 0; index < nodeMap.getLength(); index++){
                Node attributeNode = nodeMap.item(index);
                String nodeName = attributeNode.getNodeName();
                final String name = getElement(nodeName, 1);
                attributes.put(name, attributeNode);
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
     * @param index Index is used to indicate what should be retrieved. Index 0 = namespace, Index 1 = element name
     * @return Either the element name or namespace
     */
    public static String getElement(String element, int index){
        Preconditions.checkArgument(index >= 0, "The index can't be less than zero");
        Preconditions.checkArgument(index <= 1, "The index can't be more than one");

        final String[] elementDivided = element.split(DIVIDER);

        if (elementDivided.length == 1) {
            return elementDivided[0];
        }
        if (elementDivided.length == 2) {
            return elementDivided[index];
        }

        throw new IllegalArgumentException("Unable to find the name or prefix in the XML element");
    }


    public static boolean isValidXPathExpr(String body, String xpathExpr) {
        try {
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final InputSource inputSource = new InputSource(new StringReader(body));
            final Document document = documentBuilder.parse(inputSource);

            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList evaluate = (NodeList) xPath.compile(xpathExpr).evaluate(document, XPathConstants.NODESET);
            return evaluate.getLength() > 0;

        } catch (Exception exception) {
            LOGGER.error("Unable to evaluate xpath expression", exception);
            return false;
        }
    }

}
