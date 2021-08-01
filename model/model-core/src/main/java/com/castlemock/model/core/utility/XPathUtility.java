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

package com.castlemock.model.core.utility;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.35
 */
public final class XPathUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(XPathUtility.class);

    private XPathUtility(){

    }

    public static Optional<String> getXPathValue(final String body,
                                                final String xpathExpr) {
        try {
            final Document document = createDocument(body);
            final XPath xPath = createXPath();
            final String evaluate = (String) xPath.compile(xpathExpr).evaluate(document, XPathConstants.STRING);

            if (evaluate == null) {
                return Optional.empty();
            }

            return Optional.ofNullable(evaluate);
        } catch (Exception exception) {
            LOGGER.error("Unable to evaluate xpath expression", exception);
            return Optional.empty();
        }
    }

    public static boolean isValidXPathExpr(final String body,
                                           final String xpathExpr) {
        try {
            final Document document = createDocument(body);
            final XPath xPath = createXPath();

            // NODESET
            try {
                final NodeList evaluate = (NodeList) xPath.compile(xpathExpr).evaluate(document, XPathConstants.NODESET);
                return evaluate != null && evaluate.getLength() > 0;
            } catch (Exception ignored) { }

            // NUMBER
            try {
                final Number evaluate = (Number) xPath.compile(xpathExpr).evaluate(document, XPathConstants.NUMBER);
                return evaluate != null;
            } catch (Exception ignored) { }

            // STRING
            try {
                final String evaluate = (String) xPath.compile(xpathExpr).evaluate(document, XPathConstants.STRING);
                return evaluate != null;
            } catch (Exception ignored) { }

            // NODE
            try {
                final Node evaluate = (Node) xPath.compile(xpathExpr).evaluate(document, XPathConstants.NODE);
                return evaluate != null;
            } catch (Exception ignored) { }

            // BOOLEAN
            try {
                final Boolean evaluate = (Boolean) xPath.compile(xpathExpr).evaluate(document, XPathConstants.BOOLEAN);
                return evaluate != null;
            } catch (Exception ignored) { }

        } catch (Exception exception) {
            LOGGER.error("Unable to evaluate xpath expression", exception);
            return false;
        }

        return false;
    }

    private static Document createDocument(final String body) throws ParserConfigurationException, IOException, SAXException {
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        final InputSource inputSource = new InputSource(new StringReader(body));
        return documentBuilder.parse(inputSource);
    }

    private static XPath createXPath() {
        return XPathFactory.newInstance().newXPath();
    }

}
