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

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Karl Dahlgren
 * @since 1.35
 */
public class XPathUtilityTest {

    @Test
    public void testIsValidXPathExprWithNameSpaces(){

        String body = """
                <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
                   <soap:Body>
                      <GetWhoISResponse xmlns="http://www.webservicex.net">
                         <GetWhoISResult>google.com is a good domain</GetWhoISResult>
                      </GetWhoISResponse>
                   </soap:Body>
                </soap:Envelope>""";

        String xpath = "//GetWhoISResponse/GetWhoISResult[text()='google.com is a good domain']";

        boolean validXPathValue = XPathUtility.isValidXPathExpr(body, xpath);
        Assert.assertTrue(validXPathValue);
    }

    @Test
    public void testIsValidXPathExprNumber(){
        final String body = """
                <entries>
                     <entry>
                        <value>10.50</value>
                     </entry>
                     <entry>
                        <value>1.50</value>
                     </entry>
                      <entry>
                        <value>10</value>
                     </entry>
                </entries>""";

        final String xpath = "sum(//value)";
        boolean validXPathValue = XPathUtility.isValidXPathExpr(body, xpath);
        Assert.assertTrue(validXPathValue);
    }

    @Test
    public void testIsValidXPathExprWithAttribute(){

        String body = """
                <soapenv:Envelope xmlns:cm="http://castlemock.com" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://schemas.xmlsoap.org/wsdl/">
                   <soapenv:Body>
                      <cm:getUser>
                         <username>admin</username>
                         <email>admin@castlemock.com</email>
                         <permissions>
                            <permission name="READ">The user can read</permission>
                            <permission name="WRITE">The user can write</permission>
                            <permission name="DELETE">The user can delete</permission>
                            <permission name="MODIFY">The user can modify</permission>
                         </permissions>
                      </cm:getUser>
                   </soapenv:Body>
                </soapenv:Envelope>""";

        String xpath = "//getUser/permissions/permission[@name='READ'][text()='The user can read']";

        boolean validXPathValue = XPathUtility.isValidXPathExpr(body, xpath);
        Assert.assertTrue(validXPathValue);
    }

    @Test
    public void testIsValidXPathExprWithAttributeMultipleExpression(){

        String body = """
                <soapenv:Envelope xmlns:cm="http://castlemock.com" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://schemas.xmlsoap.org/wsdl/">
                   <soapenv:Body>
                      <cm:getUser>
                         <username>admin</username>
                         <permissions>
                            <permission name="READ">The user can read</permission>
                            <permission name="WRITE">The user can write</permission>
                            <permission name="DELETE">The user can delete</permission>
                            <permission name="MODIFY">The user can modify</permission>
                         </permissions>
                      </cm:getUser>
                   </soapenv:Body>
                </soapenv:Envelope>""";

        String xpath = "//getUser/permissions/permission[@name='READ'][text()='The user can read' and //getUser/permissions/permission[@name='WRITE'][text()='The user can write']]";

        boolean validXPathValue = XPathUtility.isValidXPathExpr(body, xpath);
        Assert.assertTrue(validXPathValue);
    }

    @Test
    public void testIsValidXPathExprAttr(){

        String body = """
                <entries>
                    <entry key="mykey1" attr="attr1"/>
                    <entry key="mykey2" attr="attr2"/>
                    <otherentry key="mykey1" attr="attr3"/>
                    <entry key="mykey4"/>
                    <otherentry key="mykey4"/>
                </entries>""";

        String xpath = "//entry[@key]";

        boolean validXPathValue = XPathUtility.isValidXPathExpr(body, xpath);
        Assert.assertTrue(validXPathValue);

    }

    @Test
    public void testIsValidXPathExprNumbers(){

        String body = """
                <entries>
                \t<entry>
                \t\t1234
                \t</entry>
                </entries>""";

        String xpath = "//entry[text() = 1234]";

        boolean validXPathValue = XPathUtility.isValidXPathExpr(body, xpath);
        Assert.assertTrue(validXPathValue);

    }

    @Test
    public void testIsValidXPathExprTestActualWSInput(){

        String body = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://www.webservicex.net">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <web:GetWhoIS>
                         <!--Optional:-->
                         <web:HostName>castlemock.com</web:HostName>
                      </web:GetWhoIS>
                   </soapenv:Body>
                </soapenv:Envelope>""";

        String xpath = "//GetWhoIS/HostName[text() = 'castlemock.com']";

        boolean validXPathValue = XPathUtility.isValidXPathExpr(body, xpath);
        Assert.assertTrue(validXPathValue);
    }

    @Test
    public void testGetXPathValue(){
        final String body = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://www.webservicex.net">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <web:GetWhoIS>
                         <!--Optional:-->
                         <web:HostName>castlemock.com</web:HostName>
                      </web:GetWhoIS>
                   </soapenv:Body>
                </soapenv:Envelope>""";
        final String xpath = "//GetWhoIS/HostName/text()";

        assertEquals("castlemock.com", XPathUtility.getXPathValue(body, xpath).orElse(null));
    }

    @Test
    public void testGetXPathValueSubstring(){
        final String body = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://www.webservicex.net">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <web:GetWhoIS>
                         <!--Optional:-->
                         <web:HostName>castlemock.com</web:HostName>
                      </web:GetWhoIS>
                   </soapenv:Body>
                </soapenv:Envelope>""";
        final String xpath = "substring(//GetWhoIS/HostName/text(), 12, 3)";

        assertEquals("com", XPathUtility.getXPathValue(body, xpath).orElse(null));
    }

    @Test
    public void testGetXPathValueAttr(){
        final String body = """
                <entries>
                    <entry key="mykey1" attr="attr1"/>
                    <entry key="mykey2" attr="attr2"/>
                    <otherentry key="mykey1" attr="attr3"/>
                    <entry key="mykey4"/>
                    <otherentry key="mykey4"/>
                </entries>""";
        final String xpath = "//entry/@key";
        assertEquals("mykey1", XPathUtility.getXPathValue(body, xpath).orElse(null));
    }

    @Test
    public void testXPathWithQuotation(){
        final String body = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://www.webservicex.net">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <web:GetWhoIS>
                         <!--Optional:-->
                         <web:HostName>Before-After</web:HostName>
                      </web:GetWhoIS>
                   </soapenv:Body>
                </soapenv:Envelope>""";
        final String xpath = "substring-after(//GetWhoIS/HostName/text(), '-')";

        assertEquals("After", XPathUtility.getXPathValue(body, xpath).orElse(null));
    }

    @Test
    public void testXPathMathSum() {
        final String body = """
                <entries>
                     <entry>
                        <value>10.50</value>
                     </entry>
                     <entry>
                        <value>1.50</value>
                     </entry>
                      <entry>
                        <value>10</value>
                     </entry>
                </entries>""";

        final String xpath = "sum(//value)";
        assertEquals("22", XPathUtility.getXPathValue(body, xpath).orElse(null));
    }

}
