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

package com.castlemock.web.core.utility;

import com.castlemock.model.core.http.HttpContentEncoding;
import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.core.http.HttpParameter;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

        Set<HttpHeader> httpHeaders = HttpMessageSupport.extractHttpHeaders(httpServletRequest);

        Assertions.assertEquals(headerNames.size(), httpHeaders.size());

        HttpHeader contentTypeHeader = httpHeaders.stream()
                .filter(parameter -> parameter.getName().equals("Content-Type"))
                .findFirst()
                .orElse(null);
        HttpHeader acceptHeader = httpHeaders.stream()
                .filter(parameter -> parameter.getName().equals("Accept"))
                .findFirst()
                .orElse(null);
        HttpHeader contentLengthHeader = httpHeaders.stream()
                .filter(parameter -> parameter.getName().equals("Content-Length"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(contentTypeHeader);
        Assertions.assertNotNull(acceptHeader);
        Assertions.assertNotNull(contentLengthHeader);

        Assertions.assertEquals("Content-Type", contentTypeHeader.getName());
        Assertions.assertEquals("application/xml", contentTypeHeader.getValue());

        Assertions.assertEquals("Accept", acceptHeader.getName());
        Assertions.assertEquals("application/json", acceptHeader.getValue());

        Assertions.assertEquals("Content-Length", contentLengthHeader.getName());
        Assertions.assertEquals("1024", contentLengthHeader.getValue());

    }

    @Test
    public void testExtractHttpHeadersConnection(){
        final Map<String, List<String>> headers = new HashMap<>();
        headers.put("Content-Type", List.of("application/xml", "application/json"));
        headers.put("Accept", List.of("application/json"));
        headers.put("Content-Length", List.of("1024"));
        headers.put("Transfer-Encoding", List.of("gzip"));

        final HttpURLConnection httpURLConnection = Mockito.mock(HttpURLConnection.class);
        Mockito.when(httpURLConnection.getHeaderFields()).thenReturn(headers);

        List<HttpHeader> httpHeaders = HttpMessageSupport.extractHttpHeaders(httpURLConnection);

        // Should ignore the Content-Length and Transfer-Encoding headers
        Assertions.assertEquals(3, httpHeaders.size());

        HttpHeader acceptHeader = httpHeaders.getFirst();
        HttpHeader contentTypeXmlHeader = httpHeaders.get(1);
        HttpHeader contentTypeJsonHeader = httpHeaders.get(2);

        Assertions.assertEquals("Accept", acceptHeader.getName());
        Assertions.assertEquals("application/json", acceptHeader.getValue());

        Assertions.assertEquals("Content-Type", contentTypeXmlHeader.getName());
        Assertions.assertEquals("application/xml", contentTypeXmlHeader.getValue());

        Assertions.assertEquals("Content-Type", contentTypeJsonHeader.getName());
        Assertions.assertEquals("application/json", contentTypeJsonHeader.getValue());

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
            Assertions.fail("Unable to mock getReader method for HttpServletRequest");
        }

        try {
            Mockito.when(reader.readLine()).thenReturn(readerOutput, (String) null);
        } catch (IOException e) {
            Assertions.fail("Unable to mock readLine method for BufferedReader");
        }

        final String output = HttpMessageSupport.getBody(httpServletRequest);
        Assertions.assertEquals(readerOutput, output);
    }

    @Test
    public void testExtractParameters(){
        final List<String> parameterNames = Arrays.asList("Parameter1", "Parameter2");

        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(httpServletRequest.getParameterNames()).thenReturn(Collections.enumeration(parameterNames));
        Mockito.when(httpServletRequest.getParameterValues("Parameter1")).thenReturn(new String[]{"Value1"});
        Mockito.when(httpServletRequest.getParameterValues("Parameter2")).thenReturn(new String[]{"Value2"});

        Set<HttpParameter> parameters = HttpMessageSupport.extractParameters(httpServletRequest);

        Assertions.assertEquals(parameterNames.size(), parameters.size());

        HttpParameter parameter1 = parameters.stream()
                .filter(parameter -> parameter.getName().equals("Parameter1"))
                .findFirst()
                .orElse(null);
        HttpParameter parameter2 = parameters.stream()
                .filter(parameter -> parameter.getName().equals("Parameter2"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(parameter1);
        Assertions.assertNotNull(parameter2);

        Assertions.assertEquals("Parameter1", parameter1.getName());
        Assertions.assertEquals("Value1", parameter1.getValue());

        Assertions.assertEquals("Parameter2", parameter2.getName());
        Assertions.assertEquals("Value2", parameter2.getValue());
    }

    @Test
    public void testExtractParametersMultiple(){
        final List<String> parameterNames = List.of("Parameter1");

        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(httpServletRequest.getParameterNames()).thenReturn(Collections.enumeration(parameterNames));
        Mockito.when(httpServletRequest.getParameterValues("Parameter1")).thenReturn(new String[]{"Value1", "Value2"});
        Set<HttpParameter> parameters = HttpMessageSupport.extractParameters(httpServletRequest);

        Assertions.assertEquals(2, parameters.size());

        HttpParameter parameter1 = parameters.stream()
                .filter(parameter -> parameter.getName().equals("Parameter1"))
                .filter(parameter -> parameter.getValue().equals("Value1"))
                .findFirst()
                .orElse(null);
        HttpParameter parameter2 = parameters.stream()
                .filter(parameter -> parameter.getName().equals("Parameter1"))
                .filter(parameter -> parameter.getValue().equals("Value2"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(parameter1);
        Assertions.assertNotNull(parameter2);

        Assertions.assertEquals("Parameter1", parameter1.getName());
        Assertions.assertEquals("Value1", parameter1.getValue());

        Assertions.assertEquals("Parameter1", parameter2.getName());
        Assertions.assertEquals("Value2", parameter2.getValue());
    }

    @Test
    public void testBuildParameterUri(){
        final HttpParameter parameter1 = HttpParameter.builder()
                .name("Parameter1")
                .value("Value1")
                .build();

        final HttpParameter parameter2 =HttpParameter.builder()
                .name("Parameter2")
                .value("Value2")
                .build();

        final String uri = HttpMessageSupport.buildParameterUri(Set.of(parameter1, parameter2));

        Assertions.assertTrue(uri.equals("?Parameter1=Value1&Parameter2=Value2") ||
                uri.equals("?Parameter2=Value2&Parameter1=Value1"));
    }


    @Test
    public void testGetMTOMBody(){
        String body = """
                ------=_Part_64_1526053806.1517665317492
                Content-Type: text/xml; charset=UTF-8
                Content-Transfer-Encoding: 8bit
                Content-ID: <test@castlemock.org>

                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cas="http://castlemock.org/">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <cas:TestService>
                         <Variable1>?</Variable1>
                         <Variable2>
                            <Variable1>?</Variable1>
                            <Variable2>?</Variable2>
                            <files/>
                         </Variable2>
                      </cas:Test>
                   </soapenv:Body>
                </soapenv:Envelope>
                ------=_Part_64_1526053806.1517665317492
                Content-Type: text/plain; charset=us-ascii; name="example"
                Content-ID: <example>
                Content-Disposition: attachment; name="example.txt"; filename="example.txt"

                This is an example
                ------=_Part_24_1742827313.1517654770545--""";
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        final Reader reader = new StringReader(body);
        final BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            Mockito.when(httpServletRequest.getReader()).thenReturn(bufferedReader);
        } catch (IOException e) {
            Assertions.fail("Unable to mock getReader method for HttpServletRequest");
        }

        final String output = HttpMessageSupport.getBody(httpServletRequest);
        Assertions.assertEquals(body, output);
    }

    @Test
    public void testGetBodyRequestIOError(){
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        try {
            Mockito.when(httpServletRequest.getReader()).thenThrow(new IOException());
        } catch (IOException e) {
            Assertions.fail("Unable to mock getReader method for HttpServletRequest");
        }
        assertThrows(IllegalStateException.class, () -> HttpMessageSupport.getBody(httpServletRequest));
    }

    @Test
    public void testGetBodyReaderIOError(){
        final HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        final BufferedReader reader = Mockito.mock(BufferedReader.class);
        try {
            Mockito.when(httpServletRequest.getReader()).thenReturn(reader);
        } catch (IOException e) {
            Assertions.fail("Unable to mock getReader method for HttpServletRequest");
        }

        try {
            Mockito.when(reader.readLine()).thenThrow(new IOException());
        } catch (IOException e) {
            Assertions.fail("Unable to mock readLine method for BufferedReader");
        }
        assertThrows(IllegalStateException.class, () -> HttpMessageSupport.getBody(httpServletRequest));
    }

    @Test
    public void testExtractContentEncodingAll(){
        final HttpURLConnection httpURLConnection = Mockito.mock(HttpURLConnection.class);
        Mockito.when(httpURLConnection.getContentEncoding()).thenReturn("gzip/deflate");
        List<HttpContentEncoding> contentEncodings = HttpMessageSupport.extractContentEncoding(httpURLConnection);

        Assertions.assertEquals(2, contentEncodings.size());
        Assertions.assertTrue(contentEncodings.contains(HttpContentEncoding.GZIP));
        Assertions.assertTrue(contentEncodings.contains(HttpContentEncoding.DEFLATE));
    }

    @Test
    public void testExtractContentEncodingDeflate(){
        final HttpURLConnection httpURLConnection = Mockito.mock(HttpURLConnection.class);
        Mockito.when(httpURLConnection.getContentEncoding()).thenReturn("deflate");
        List<HttpContentEncoding> contentEncodings = HttpMessageSupport.extractContentEncoding(httpURLConnection);

        Assertions.assertEquals(1, contentEncodings.size());
        Assertions.assertFalse(contentEncodings.contains(HttpContentEncoding.GZIP));
        Assertions.assertTrue(contentEncodings.contains(HttpContentEncoding.DEFLATE));
    }
}
