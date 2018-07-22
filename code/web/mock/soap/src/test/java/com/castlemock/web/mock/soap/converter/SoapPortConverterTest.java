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

package com.castlemock.web.mock.soap.converter;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.project.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SoapPortConverterTest {


    @Test
    public void testGetSoapPorts() throws IOException, SAXException, ParserConfigurationException {
        try {
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setNamespaceAware(true);

            final InputStream inputStream =
                    SoapPortConverterTest.class.getResourceAsStream("ServiceExample1.wsdl");

            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final Document document = documentBuilder.parse(inputStream);
            document.getDocumentElement().normalize();

            final List<SoapPort> fileSoapPorts = SoapPortConverter.getSoapPorts(document, true);

            Assert.assertEquals(1, fileSoapPorts.size());

            final SoapPort soapPort = fileSoapPorts.get(0);

            Assert.assertEquals("ServiceExample.Endpoint", soapPort.getName());
            Assert.assertEquals("ServiceExample.Endpoint", soapPort.getUri());
            Assert.assertEquals(1, soapPort.getOperations().size());

            SoapOperation soapOperation = soapPort.getOperations().get(0);

            Assert.assertEquals("ServiceExample", soapOperation.getName());
            Assert.assertEquals(SoapResponseStrategy.RANDOM, soapOperation.getResponseStrategy());
            Assert.assertEquals(SoapOperationStatus.MOCKED, soapOperation.getStatus());
            Assert.assertEquals(SoapVersion.SOAP11, soapOperation.getSoapVersion());
            Assert.assertEquals(HttpMethod.POST, soapOperation.getHttpMethod());

            SoapOperationIdentifier operationIdentifier = soapOperation.getOperationIdentifier();

            Assert.assertNotNull(operationIdentifier);
            Assert.assertEquals("Request", operationIdentifier.getName());
            Assert.assertEquals("http://Services/ServiceExample/ServiceExample/1/Schema",
                    operationIdentifier.getNamespace());

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


}
