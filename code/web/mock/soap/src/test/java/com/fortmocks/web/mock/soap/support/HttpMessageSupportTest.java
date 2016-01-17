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

package com.fortmocks.web.mock.soap.support;

import com.fortmocks.web.basis.manager.HttpMessageSupport;
import com.fortmocks.web.mock.soap.model.SoapException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

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

    @Test(expected = SoapException.class)
    public void testGetBodyRequestIOError(){
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        try {
            Mockito.when(httpServletRequest.getReader()).thenThrow(new IOException());
        } catch (IOException e) {
            Assert.fail("Unable to mock getReader method for HttpServletRequest");
        }
        HttpMessageSupport.getBody(httpServletRequest);
    }

    @Test(expected = SoapException.class)
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

    @Test(expected = SoapException.class)
    public void testExtractSoapRequestNameInvalidRequestBody(){
        HttpMessageSupport.extractSoapRequestName(new String());
    }
}
