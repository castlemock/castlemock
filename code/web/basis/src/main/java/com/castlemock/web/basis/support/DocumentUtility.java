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

package com.castlemock.web.basis.support;

import org.w3c.dom.*;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DocumentUtility {

    /**
     * Extracts an attribute from an element. The method will also remove namespace prefix
     * if it is present.
     * @param element The element which the attribute will be extracted from
     * @param name The name of the attribute that will be extracted
     * @return The attribute value
     */
    public static Optional<String> getAttribute(final Element element,
                                      final String name){
        final String value = element.getAttribute(name);
        if(value == null || value.isEmpty()){
            return Optional.empty();
        }
        String[] splitValues = value.split(":");
        if(splitValues.length == 1) {
            return Optional.of(splitValues[0]);
        }
        return Optional.of(splitValues[1]);
    }

    public static Optional<Element> getElement(final Document document,
                                               final String type){
        final NodeList nodeList = document.getElementsByTagName(type);

        if(nodeList.getLength() == 0){
            return Optional.empty();
        }

        return Optional.ofNullable(getElements(nodeList).get(0));
    }

    public static Optional<Element> getElement(final Element element,
                                               final String type){
        final NodeList nodeList = element.getElementsByTagName(type);

        if(nodeList.getLength() == 0){
            return Optional.empty();
        }

        return Optional.ofNullable(getElements(nodeList).get(0));
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

    public static Optional<Element> getElement(final Document document,
                                               final String namespace,
                                               final String type){
        final NodeList nodeList = document.getElementsByTagNameNS(namespace, type);

        if(nodeList.getLength() == 0){
            return Optional.empty();
        }

        return Optional.ofNullable(getElements(nodeList).get(0));
    }

    public static List<Element> getElements(final Document document,
                                            final String type){
        final NodeList nodeList = document.getElementsByTagName(type);
        return getElements(nodeList);
    }

    public static List<Element> getElements(final Element element,
                                            final String type){
        final NodeList nodeList = element.getElementsByTagName(type);
        return getElements(nodeList);
    }

    public static List<Element> getElements(final Document document,
                                            final String namespace,
                                            final String type){
        final NodeList nodeList = document.getElementsByTagNameNS(namespace, type);
        return getElements(nodeList);
    }

    public static List<Element> getElements(final Element element,
                                            final String namespace,
                                            final String type){
        final NodeList nodeList = element.getElementsByTagNameNS(namespace, type);
        return getElements(nodeList);
    }

    public static List<Element> getElements(final NodeList nodeList){
        return IntStream.range(0, nodeList.getLength())
                .mapToObj(nodeList::item)
                .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
                .map(node -> (Element) node)
                .collect(Collectors.toList());
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
