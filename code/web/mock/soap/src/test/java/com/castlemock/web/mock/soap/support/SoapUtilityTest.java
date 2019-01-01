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
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Karl Dahlgren
 * @since 1.35
 */
public class SoapUtilityTest {

    @Test
    public void testExtractSoapRequestName(){
        final String requestBody =
                "<?xml version=\"1.0\"?>\n" +
                        "<soap:Envelope\n" +
                        "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope/\"\n" +
                        "soap:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\n" +
                        "<soap:Body>\n" +
                        "  <m:GetPrice xmlns:m=\"http://www.w3schools.com/prices\">\n" +
                        "    <m:Item>Apples</m:Item>\n" +
                        "  </m:GetPrice>\n" +
                        "</soap:Body>\n" +
                        "</soap:Envelope> ";

        final SoapOperationIdentifier operationIdentifier = SoapUtility.extractSoapRequestName(requestBody);
        Assert.assertEquals("GetPrice", operationIdentifier.getName());
        Assert.assertEquals("http://www.w3schools.com/prices", operationIdentifier.getNamespace());
    }

    @Test
    public void testExtractSoapRequestNameWithoutNamespaces(){
        final String requestBody =
                "<?xml version=\"1.0\"?>\n" +
                        "<soap:Envelope\n" +
                        "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope/\">\n" +
                        "<soap:Body>\n" +
                        "  <GetPrice xmlns=\"http://www.w3schools.com/prices\">\n" +
                        "    <Item>Apples</Item>\n" +
                        "  </GetPrice>\n" +
                        "</soap:Body>\n" +
                        "</soap:Envelope> ";

        final SoapOperationIdentifier operationIdentifier = SoapUtility.extractSoapRequestName(requestBody);
        Assert.assertEquals("GetPrice", operationIdentifier.getName());
        Assert.assertEquals("http://www.w3schools.com/prices", operationIdentifier.getNamespace());
    }

    @Test
    public void testExtractSoapRequestNameNamespacesInElement(){
        final String requestBody =
                "<?xml version=\"1.0\"?>\n" +
                        "<soap:Envelope\n" +
                        "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope/\">\n" +
                        "<soap:Body>\n" +
                        "  <GetPrice>\n" +
                        "    <Item>Apples</Item>\n" +
                        "  </GetPrice>\n" +
                        "</soap:Body>\n" +
                        "</soap:Envelope> ";

        final SoapOperationIdentifier operationIdentifier = SoapUtility.extractSoapRequestName(requestBody);
        Assert.assertEquals("GetPrice", operationIdentifier.getName());
        Assert.assertNull(operationIdentifier.getNamespace());
    }

    @Test
    public void testExtractSoapSameNamespaceAndName(){
        final String requestBody =
                "<?xml version=\"1.0\"?>\n" +
                        "<soap:Envelope\n" +
                        "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope/\"\n" +
                        "soap:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\n" +
                        "<soap:Body>\n" +
                        "  <GetPrice xmlns:GetPrice=\"http://www.w3schools.com/prices\">\n" +
                        "    <m:Item>Apples</m:Item>\n" +
                        "  </GetPrice>\n" +
                        "</soap:Body>\n" +
                        "</soap:Envelope> ";

        final SoapOperationIdentifier operationIdentifier = SoapUtility.extractSoapRequestName(requestBody);
        Assert.assertEquals("GetPrice", operationIdentifier.getName());
        Assert.assertNull(operationIdentifier.getNamespace());
    }

    @Test
    public void testExtractSoapRequestNameWithoutBodyNamespace(){
        final String requestBody =
                "<?xml version=\"1.0\"?>\n" +
                        "<soap:Envelope\n" +
                        "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope/\">\n" +
                        "<Body>\n" +
                        "  <GetPrice>\n" +
                        "    <Item>Apples</Item>\n" +
                        "  </GetPrice>\n" +
                        "</Body>\n" +
                        "</soap:Envelope> ";

        final SoapOperationIdentifier operationIdentifier = SoapUtility.extractSoapRequestName(requestBody);
        Assert.assertEquals("GetPrice", operationIdentifier.getName());
        Assert.assertNull(operationIdentifier.getNamespace());
    }

    @Test
    public void testExtractSoapRequestNameWithoutSoapNamespaces(){
        final String requestBody =
                "<?xml version=\"1.0\"?>\n" +
                        "<Envelope>\n" +
                        "<Body>\n" +
                        "  <GetPrice>\n" +
                        "    <Item>Apples</Item>\n" +
                        "  </GetPrice>\n" +
                        "</Body>\n" +
                        "</Envelope> ";

        final SoapOperationIdentifier operationIdentifier = SoapUtility.extractSoapRequestName(requestBody);
        Assert.assertEquals("GetPrice", operationIdentifier.getName());
        Assert.assertNull(operationIdentifier.getNamespace());
    }

    @Test(expected = IllegalStateException.class)
    public void testExtractSoapRequestNameInvalidRequestBody(){
        SoapUtility.extractSoapRequestName(new String());
    }

}
