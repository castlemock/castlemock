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

import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.model.http.domain.HttpParameter;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.util.*;

/**
 * @author Karl Dahlgren
 * @since 1.1
 * @see HttpMessageSupport
 */
public class HttpMessageSupportTest {


    @Test
    public void testExtractHttpHeadersRequest(){
        final List<String> headerNames = Arrays.asList("Content-Type", "Accept", "Content-Length");

        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(httpServletRequest.getHeaderNames()).thenReturn(Collections.enumeration(headerNames));
        Mockito.when(httpServletRequest.getHeader("Content-Type")).thenReturn("application/xml");
        Mockito.when(httpServletRequest.getHeader("Accept")).thenReturn("application/json");
        Mockito.when(httpServletRequest.getHeader("Content-Length")).thenReturn("1024");

        List<HttpHeader> httpHeaders = HttpMessageSupport.extractHttpHeaders(httpServletRequest);

        Assert.assertEquals(headerNames.size(), httpHeaders.size());

        HttpHeader contentTypeHeader = httpHeaders.get(0);
        HttpHeader acceptHeader = httpHeaders.get(1);
        HttpHeader contentLengthHeader = httpHeaders.get(2);

        Assert.assertEquals("Content-Type", contentTypeHeader.getName());
        Assert.assertEquals("application/xml", contentTypeHeader.getValue());

        Assert.assertEquals("Accept", acceptHeader.getName());
        Assert.assertEquals("application/json", acceptHeader.getValue());

        Assert.assertEquals("Content-Length", contentLengthHeader.getName());
        Assert.assertEquals("1024", contentLengthHeader.getValue());

    }

    @Test
    public void testExtractHttpHeadersConnection(){
        final Map<String, List<String>> headers = new HashMap<>();
        headers.put("Content-Type", Arrays.asList("application/xml", "application/json"));
        headers.put("Accept", Arrays.asList("application/json"));
        headers.put("Content-Length", Arrays.asList("1024"));
        headers.put("Transfer-Encoding", Arrays.asList("gzip"));

        final HttpURLConnection httpURLConnection = Mockito.mock(HttpURLConnection.class);
        Mockito.when(httpURLConnection.getHeaderFields()).thenReturn(headers);

        List<HttpHeader> httpHeaders = HttpMessageSupport.extractHttpHeaders(httpURLConnection);

        // Should ignore the Content-Length and Transfer-Encoding headers
        Assert.assertEquals(3, httpHeaders.size());

        HttpHeader acceptHeader = httpHeaders.get(0);
        HttpHeader contentTypeXmlHeader = httpHeaders.get(1);
        HttpHeader contentTypeJsonHeader = httpHeaders.get(2);

        Assert.assertEquals("Accept", acceptHeader.getName());
        Assert.assertEquals("application/json", acceptHeader.getValue());

        Assert.assertEquals("Content-Type", contentTypeXmlHeader.getName());
        Assert.assertEquals("application/xml", contentTypeXmlHeader.getValue());

        Assert.assertEquals("Content-Type", contentTypeJsonHeader.getName());
        Assert.assertEquals("application/json", contentTypeJsonHeader.getValue());

    }


    @Test
    @SuppressWarnings("varargs")
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
            final String noValue = null;
            Mockito.when(reader.readLine()).thenReturn(readerOutput, noValue);
        } catch (IOException e) {
            Assert.fail("Unable to mock readLine method for BufferedReader");
        }

        final String output = HttpMessageSupport.getBody(httpServletRequest);
        Assert.assertEquals(readerOutput, output);
    }

    @Test
    public void testExtractParameters(){
        final List<String> parameterNames = Arrays.asList("Parameter1", "Parameter2");

        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(httpServletRequest.getParameterNames()).thenReturn(Collections.enumeration(parameterNames));
        Mockito.when(httpServletRequest.getParameter("Parameter1")).thenReturn("Value1");
        Mockito.when(httpServletRequest.getParameter("Parameter2")).thenReturn("Value2");

        List<HttpParameter> parameters = HttpMessageSupport.extractParameters(httpServletRequest);

        Assert.assertEquals(parameterNames.size(), parameters.size());

        HttpParameter parameter1 = parameters.get(0);
        HttpParameter parameter2 = parameters.get(1);

        Assert.assertEquals("Parameter1", parameter1.getName());
        Assert.assertEquals("Value1", parameter1.getValue());

        Assert.assertEquals("Parameter2", parameter2.getName());
        Assert.assertEquals("Value2", parameter2.getValue());
    }

    @Test
    public void testBuildParameterUri(){

        HttpParameter parameter1 = new HttpParameter();
        parameter1.setName("Parameter1");
        parameter1.setValue("Value1");

        HttpParameter parameter2 = new HttpParameter();
        parameter2.setName("Parameter2");
        parameter2.setValue("Value2");

        String uri = HttpMessageSupport.buildParameterUri(Arrays.asList(parameter1, parameter2));

        Assert.assertEquals("?Parameter1=Value1&Parameter2=Value2", uri);
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
    public void testExtractContentEncodingAll(){
        final HttpURLConnection httpURLConnection = Mockito.mock(HttpURLConnection.class);
        Mockito.when(httpURLConnection.getContentEncoding()).thenReturn("gzip/deflate");
        List<ContentEncoding> contentEncodings = HttpMessageSupport.extractContentEncoding(httpURLConnection);

        Assert.assertEquals(2, contentEncodings.size());
        Assert.assertEquals(true, contentEncodings.contains(ContentEncoding.GZIP));
        Assert.assertEquals(true, contentEncodings.contains(ContentEncoding.DEFLATE));
    }

    @Test
    public void testExtractContentEncodingDeflate(){
        final HttpURLConnection httpURLConnection = Mockito.mock(HttpURLConnection.class);
        Mockito.when(httpURLConnection.getContentEncoding()).thenReturn("deflate");
        List<ContentEncoding> contentEncodings = HttpMessageSupport.extractContentEncoding(httpURLConnection);

        Assert.assertEquals(1, contentEncodings.size());
        Assert.assertEquals(false, contentEncodings.contains(ContentEncoding.GZIP));
        Assert.assertEquals(true, contentEncodings.contains(ContentEncoding.DEFLATE));
    }
}
