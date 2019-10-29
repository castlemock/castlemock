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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Karl Dahlgren
 * @since 1.35
 */
public class XPathUtilityTest {

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

        boolean validXPathValue = XPathUtility.isValidXPathExpr(body, xpath);
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

        boolean validXPathValue = XPathUtility.isValidXPathExpr(body, xpath);
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

        boolean validXPathValue = XPathUtility.isValidXPathExpr(body, xpath);
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

        boolean validXPathValue = XPathUtility.isValidXPathExpr(body, xpath);
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

        boolean validXPathValue = XPathUtility.isValidXPathExpr(body, xpath);
        Assert.assertTrue(validXPathValue);
    }

}
