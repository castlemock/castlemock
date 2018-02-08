/*
 * Copyright 2016 Karl Dahlgren
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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * @author Karl Dahlgren
 * @since 1.1
 * @see HttpMessageSupport
 */
public class HttpMessageSupportTest {

    @Test
    public void testGetBody(){
        final String readerOutput = "This is the output from the reader";
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        final BufferedReader reader = Mockito.mock(BufferedReader.class);
        try {
            Mockito.when(httpServletRequest.getReader()).thenReturn(reader);
        } catch (IOException e) {
            Assert.fail("Unable to mock getReader method for HttpServletRequest");
        }

        try {
            Mockito.when(reader.readLine()).thenReturn(readerOutput, null);
        } catch (IOException e) {
            Assert.fail("Unable to mock readLine method for BufferedReader");
        }

        final String output = HttpMessageSupport.getBody(httpServletRequest);
        Assert.assertEquals(readerOutput, output);
    }


    @Test
    public void testGetMTOMBody(){
        String body = "------=_Part_64_1526053806.1517665317492\n" +
                "Content-Type: text/xml; charset=UTF-8\n" +
                "Content-Transfer-Encoding: 8bit\n" +
                "Content-ID: <test@castlemock.org>\n" +
                "\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cas=\"http://castlemock.org/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <cas:TestService>\n" +
                "         <Variable1>?</Variable1>\n" +
                "         <Variable2>\n" +
                "            <Variable1>?</Variable1>\n" +
                "            <Variable2>?</Variable2>\n" +
                "            <files/>\n" +
                "         </Variable2>\n" +
                "      </cas:Test>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>\n" +
                "------=_Part_64_1526053806.1517665317492\n" +
                "Content-Type: text/plain; charset=us-ascii; name=\"example\"\n" +
                "Content-ID: <example>\n" +
                "Content-Disposition: attachment; name=\"example.txt\"; filename=\"example.txt\"\n" +
                "\n" +
                "This is an example\n" +
                "------=_Part_24_1742827313.1517654770545--";
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        final Reader reader = new StringReader(body);
        final BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            Mockito.when(httpServletRequest.getReader()).thenReturn(bufferedReader);
        } catch (IOException e) {
            Assert.fail("Unable to mock getReader method for HttpServletRequest");
        }

        final String output = HttpMessageSupport.getBody(httpServletRequest);
        Assert.assertEquals(body, output);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetBodyRequestIOError(){
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        try {
            Mockito.when(httpServletRequest.getReader()).thenThrow(new IOException());
        } catch (IOException e) {
            Assert.fail("Unable to mock getReader method for HttpServletRequest");
        }
        HttpMessageSupport.getBody(httpServletRequest);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetBodyReaderIOError(){
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        final BufferedReader reader = Mockito.mock(BufferedReader.class);
        try {
            Mockito.when(httpServletRequest.getReader()).thenReturn(reader);
        } catch (IOException e) {
            Assert.fail("Unable to mock getReader method for HttpServletRequest");
        }

        try {
            Mockito.when(reader.readLine()).thenThrow(new IOException());
        } catch (IOException e) {
            Assert.fail("Unable to mock readLine method for BufferedReader");
        }
        HttpMessageSupport.getBody(httpServletRequest);
    }

    @Test
    public void testGetElement(){
        final String oneInput = "First";
        final String twoInput = "First:Second";

        Assert.assertEquals("First", HttpMessageSupport.getElement(oneInput, 0));
        Assert.assertEquals("First", HttpMessageSupport.getElement(oneInput, 1));
        Assert.assertEquals("First", HttpMessageSupport.getElement(twoInput, 0));
        Assert.assertEquals("Second", HttpMessageSupport.getElement(twoInput, 1));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testGetElementInvalidIndexLessThanZero(){
        Assert.assertEquals("First", HttpMessageSupport.getElement("First", -1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetElementInvalidIndexMoreThanOne(){
        Assert.assertEquals("First", HttpMessageSupport.getElement("First", 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetElementInvalidElement(){
        Assert.assertEquals("First", HttpMessageSupport.getElement("First:Second:Third", 1));
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

        final String requestName = HttpMessageSupport.extractSoapRequestName(requestBody);
        Assert.assertEquals("GetPrice", requestName);
    }

    @Test(expected = IllegalStateException.class)
    public void testExtractSoapRequestNameInvalidRequestBody(){
        HttpMessageSupport.extractSoapRequestName(new String());
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

        boolean validXPathValue = HttpMessageSupport.isValidXPathExpr(body, xpath);
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

        boolean validXPathValue = HttpMessageSupport.isValidXPathExpr(body, xpath);
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

        boolean validXPathValue = HttpMessageSupport.isValidXPathExpr(body, xpath);
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

        boolean validXPathValue = HttpMessageSupport.isValidXPathExpr(body, xpath);
        Assert.assertTrue(validXPathValue);

    }
}
