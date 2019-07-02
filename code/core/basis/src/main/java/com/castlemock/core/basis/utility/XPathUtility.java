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
package com.castlemock.core.basis.utility;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Karl Dahlgren
 * @since 1.35
 */
public final class XPathUtility {

    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
    private static final Logger LOGGER = Logger.getLogger(XPathUtility.class);
    private static final XPath X_PATH = XPathFactory.newInstance().newXPath();

    private XPathUtility() {

    }

    public static boolean isValidXPathExpr(final String body, final String xpathExpr) {
        final Document document;
        final XPathExpression xPathExpression;
        boolean result = false;

        try {
            document = parseBody(body);
        } catch (ParserConfigurationException | IOException | SAXException exception) {
            LOGGER.error("Unable to parse body", exception);
            return result;
        }

        try {
            xPathExpression = X_PATH.compile(xpathExpr);
        } catch (XPathExpressionException exception) {
            LOGGER.error("Unable to compile expression", exception);
            return result;
        }

        return tryEvaluateNodeset(xPathExpression, document) || tryEvaluateNumber(xPathExpression, document);
    }

    private static Document parseBody(final String body) throws ParserConfigurationException, IOException, SAXException {
        final DocumentBuilder documentBuilder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
        final InputSource inputSource = new InputSource(new StringReader(body));
        final Document document = documentBuilder.parse(inputSource);

        return document;
    }

    private static boolean tryEvaluateNodeset(final XPathExpression xPathExpression, final Document document) {
        NodeList evaluate = null;

        try {
            evaluate = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException exception) {
            LOGGER.warn("Unable to evaluate xpath expression as NODESET", exception);
        }

        return evaluate != null && evaluate.getLength() > 0;
    }

    private static boolean tryEvaluateNumber(final XPathExpression xPathExpression, final Document document) {
        Double evaluate = null;

        try {
            evaluate = (Double) xPathExpression.evaluate(document, XPathConstants.NUMBER);
        } catch (XPathExpressionException exception) {
            LOGGER.warn("Unable to evaluate xpath expression as NUMBER", exception);
        }

        return evaluate != null && evaluate > 0;
    }
}
