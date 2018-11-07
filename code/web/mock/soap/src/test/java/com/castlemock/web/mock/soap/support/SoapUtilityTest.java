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
import org.junit.Ignore;
import org.junit.Test;

public class SoapUtilityTest {


    @Test
    public void testGetElement(){
        final String oneInput = "First";
        final String twoInput = "First:Second";

        Assert.assertEquals("First", SoapUtility.getElement(oneInput, 0));
        Assert.assertEquals("First", SoapUtility.getElement(oneInput, 1));
        Assert.assertEquals("First", SoapUtility.getElement(twoInput, 0));
        Assert.assertEquals("Second", SoapUtility.getElement(twoInput, 1));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testGetElementInvalidIndexLessThanZero(){
        Assert.assertEquals("First", SoapUtility.getElement("First", -1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetElementInvalidIndexMoreThanOne(){
        Assert.assertEquals("First", SoapUtility.getElement("First", 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetElementInvalidElement(){
        Assert.assertEquals("First", SoapUtility.getElement("First:Second:Third", 1));
    }

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
    @Ignore
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

    @Test
    public void testIsValidXPathExprWithNameSpaces(){

        String body = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "   <soap:Body>\n" +
                "      <GetWhoISResponse xmlns=\"http://www.webservicex.net\">\n" +
                "         <GetWhoISResult>google.com is a good domain</GetWhoISResult>\n" +
                "      </GetWhoISResponse>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>";

        String xpath = "//GetWhoISResponse/GetWhoISResult[text()='google.com is a good domain']";

        boolean validXPathValue = SoapUtility.isValidXPathExpr(body, xpath);
        Assert.assertTrue(validXPathValue);
    }

    @Test
    public void testIsValidXPathExprWithAttribute(){

        String body = "<soapenv:Envelope xmlns:cm=\"http://castlemock.com\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://schemas.xmlsoap.org/wsdl/\">\n" +
                "   <soapenv:Body>\n" +
                "      <cm:getUser>\n" +
                "         <username>admin</username>\n" +
                "         <email>admin@castlemock.com</email>\n" +
                "         <permissions>\n" +
                "            <permission name=\"READ\">The user can read</permission>\n" +
                "            <permission name=\"WRITE\">The user can write</permission>\n" +
                "            <permission name=\"DELETE\">The user can delete</permission>\n" +
                "            <permission name=\"MODIFY\">The user can modify</permission>\n" +
                "         </permissions>\n" +
                "      </cm:getUser>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        String xpath = "//getUser/permissions/permission[@name='READ'][text()='The user can read']";

        boolean validXPathValue = SoapUtility.isValidXPathExpr(body, xpath);
        Assert.assertTrue(validXPathValue);
    }

    @Test
    public void testIsValidXPathExprAttr(){

        String body = "<entries>\n" +
                "    <entry key=\"mykey1\" attr=\"attr1\"/>\n" +
                "    <entry key=\"mykey2\" attr=\"attr2\"/>\n" +
                "    <otherentry key=\"mykey1\" attr=\"attr3\"/>\n" +
                "    <entry key=\"mykey4\"/>\n" +
                "    <otherentry key=\"mykey4\"/>\n" +
                "</entries>";

        String xpath = "//entry[@key]";

        boolean validXPathValue = SoapUtility.isValidXPathExpr(body, xpath);
        Assert.assertTrue(validXPathValue);

    }

    @Test
    public void testIsValidXPathExprNumbers(){

        String body = "<entries>\n" +
                "\t<entry>\n" +
                "\t\t1234\n" +
                "\t</entry>\n" +
                "</entries>";

        String xpath = "//entry[text() = 1234]";

        boolean validXPathValue = SoapUtility.isValidXPathExpr(body, xpath);
        Assert.assertTrue(validXPathValue);

    }

    @Test
    public void testIsValidXPathExprTestActualWSInput(){

        String body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://www.webservicex.net\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <web:GetWhoIS>\n" +
                "         <!--Optional:-->\n" +
                "         <web:HostName>google.com</web:HostName>\n" +
                "      </web:GetWhoIS>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        String xpath = "//GetWhoIS/HostName[text() = 'google.com']";

        boolean validXPathValue = SoapUtility.isValidXPathExpr(body, xpath);
        Assert.assertTrue(validXPathValue);
    }

}
