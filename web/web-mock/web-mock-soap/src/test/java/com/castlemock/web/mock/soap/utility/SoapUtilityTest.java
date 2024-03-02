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

package com.castlemock.web.mock.soap.utility;

import com.castlemock.model.mock.soap.domain.SoapOperationIdentifier;
import com.castlemock.service.mock.soap.utility.SoapUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Karl Dahlgren
 * @since 1.35
 */
public class SoapUtilityTest {

    @Test
    public void testExtractSoapRequestName(){
        final String requestBody =
                """
                        <?xml version="1.0"?>
                        <soap:Envelope
                        xmlns:soap="http://www.w3.org/2003/05/soap-envelope/"
                        soap:encodingStyle="http://www.w3.org/2003/05/soap-encoding">
                        <soap:Body>
                          <m:GetPrice xmlns:m="http://www.w3schools.com/prices">
                            <m:Item>Apples</m:Item>
                          </m:GetPrice>
                        </soap:Body>
                        </soap:Envelope>\s""";

        final SoapOperationIdentifier operationIdentifier = SoapUtility.extractSoapRequestName(requestBody);
        Assertions.assertEquals("GetPrice", operationIdentifier.getName());
        Assertions.assertEquals("http://www.w3schools.com/prices", operationIdentifier.getNamespace().orElse(null));
    }

    @Test
    public void testExtractSoapRequestNameWithoutNamespaces(){
        final String requestBody =
                """
                        <?xml version="1.0"?>
                        <soap:Envelope
                        xmlns:soap="http://www.w3.org/2003/05/soap-envelope/">
                        <soap:Body>
                          <GetPrice xmlns="http://www.w3schools.com/prices">
                            <Item>Apples</Item>
                          </GetPrice>
                        </soap:Body>
                        </soap:Envelope>\s""";

        final SoapOperationIdentifier operationIdentifier = SoapUtility.extractSoapRequestName(requestBody);
        Assertions.assertEquals("GetPrice", operationIdentifier.getName());
        Assertions.assertEquals("http://www.w3schools.com/prices", operationIdentifier.getNamespace().orElse(null));
    }

    @Test
    public void testExtractSoapRequestNameWithHref(){
        final String requestBody =
                """
                        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
                        \t<soap:Body soap:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        \t\t<ns3:GetPrice xmlns:ns3="http://www.w3schools.com/prices">
                        \t\t\t<in href="#id1"/>
                        \t\t</ns3:GetPrice>
                        \t\t<ns3:GetPriceIn id="id1" xmlns:ns3="http://www.w3schools.com/prices" xsi:type="ns3:GetPriceIn">
                        \t\t</ns3:GetPriceIn>
                        \t</soap:Body>
                        </soapenv:Envelope>""";

        final SoapOperationIdentifier operationIdentifier = SoapUtility.extractSoapRequestName(requestBody);
        Assertions.assertEquals("GetPrice", operationIdentifier.getName());
        Assertions.assertEquals("http://www.w3schools.com/prices", operationIdentifier.getNamespace().orElse(null));
    }

    @Test
    public void testExtractSoapRequestNameNamespacesInElement(){
        final String requestBody =
                """
                        <?xml version="1.0"?>
                        <soap:Envelope
                        xmlns:soap="http://www.w3.org/2003/05/soap-envelope/">
                        <soap:Body>
                          <GetPrice>
                            <Item>Apples</Item>
                          </GetPrice>
                        </soap:Body>
                        </soap:Envelope>\s""";

        final SoapOperationIdentifier operationIdentifier = SoapUtility.extractSoapRequestName(requestBody);
        Assertions.assertEquals("GetPrice", operationIdentifier.getName());
        Assertions.assertNull(operationIdentifier.getNamespace().orElse(null));
    }

    @Test
    public void testExtractSoapSameNamespaceAndName(){
        final String requestBody =
                """
                        <?xml version="1.0"?>
                        <soap:Envelope
                        xmlns:soap="http://www.w3.org/2003/05/soap-envelope/"
                        soap:encodingStyle="http://www.w3.org/2003/05/soap-encoding">
                        <soap:Body>
                          <GetPrice xmlns:GetPrice="http://www.w3schools.com/prices">
                            <m:Item>Apples</m:Item>
                          </GetPrice>
                        </soap:Body>
                        </soap:Envelope>\s""";

        final SoapOperationIdentifier operationIdentifier = SoapUtility.extractSoapRequestName(requestBody);
        Assertions.assertEquals("GetPrice", operationIdentifier.getName());
        Assertions.assertNull(operationIdentifier.getNamespace().orElse(null));
    }

    @Test
    public void testExtractSoapRequestNameWithoutBodyNamespace(){
        final String requestBody =
                """
                        <?xml version="1.0"?>
                        <soap:Envelope
                        xmlns:soap="http://www.w3.org/2003/05/soap-envelope/">
                        <Body>
                          <GetPrice>
                            <Item>Apples</Item>
                          </GetPrice>
                        </Body>
                        </soap:Envelope>\s""";

        final SoapOperationIdentifier operationIdentifier = SoapUtility.extractSoapRequestName(requestBody);
        Assertions.assertEquals("GetPrice", operationIdentifier.getName());
        Assertions.assertNull(operationIdentifier.getNamespace().orElse(null));
    }

    @Test
    public void testExtractSoapRequestNameWithBodyNamespace(){
        final String requestBody =
                """
                        <?xml version="1.0"?>
                        <soapenv:Envelope
                        xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
                        <soap:Body xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                          <GetPrice>
                            <Item>Apples</Item>
                          </GetPrice>
                        </soap:Body>
                        </soapenv:Envelope>\s""";

        final SoapOperationIdentifier operationIdentifier = SoapUtility.extractSoapRequestName(requestBody);
        Assertions.assertEquals("GetPrice", operationIdentifier.getName());
        Assertions.assertNull(operationIdentifier.getNamespace().orElse(null));
    }

    @Test
    public void testExtractSoapRequestNameWithoutSoapNamespaces(){
        final String requestBody =
                """
                        <?xml version="1.0"?>
                        <Envelope>
                        <Body>
                          <GetPrice>
                            <Item>Apples</Item>
                          </GetPrice>
                        </Body>
                        </Envelope>\s""";

        final SoapOperationIdentifier operationIdentifier = SoapUtility.extractSoapRequestName(requestBody);
        Assertions.assertEquals("GetPrice", operationIdentifier.getName());
        Assertions.assertNull(operationIdentifier.getNamespace().orElse(null));
    }

    @Test
    public void testExtractSoapRequestNameInvalidRequestBody(){
        Assertions.assertThrows(IllegalStateException.class, () -> SoapUtility.extractSoapRequestName(""));
    }

}
