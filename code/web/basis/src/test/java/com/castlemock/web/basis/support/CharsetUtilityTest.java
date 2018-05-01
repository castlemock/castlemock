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

package com.castlemock.web.basis.support;

import com.castlemock.core.basis.model.http.domain.HttpHeader;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.18
 */
public class CharsetUtilityTest {


    @Test
    public void testParseHttpHeaders(){
        final List<HttpHeader> httpHeaders = new ArrayList<>();

        HttpHeader contentLength = new HttpHeader();
        contentLength.setName("Content-Length");
        contentLength.setValue("100");

        HttpHeader contentEncoding = new HttpHeader();
        contentEncoding.setName("Content-Encoding");
        contentEncoding.setValue("gzip");

        HttpHeader contentType = new HttpHeader();
        contentEncoding.setName("Content-Type");
        contentEncoding.setValue("text/xml;charset=iso-8859-1");

        httpHeaders.add(contentLength);
        httpHeaders.add(contentEncoding);
        httpHeaders.add(contentType);

        final String charset = CharsetUtility.parseHttpHeaders(httpHeaders);
        Assert.assertEquals("ISO-8859-1", charset);
    }

    @Test
    public void testParseHttpHeadersNull(){
        final String charset = CharsetUtility.parseHttpHeaders(null);
        Assert.assertEquals("UTF-8", charset);
    }

    @Test
    public void testParseHttpHeadersMissingHeader(){
        final List<HttpHeader> httpHeaders = new ArrayList<>();

        HttpHeader contentLength = new HttpHeader();
        contentLength.setName("Content-Length");
        contentLength.setValue("100");

        HttpHeader contentEncoding = new HttpHeader();
        contentEncoding.setName("Content-Encoding");
        contentEncoding.setValue("gzip");

        httpHeaders.add(contentLength);
        httpHeaders.add(contentEncoding);

        final String charset = CharsetUtility.parseHttpHeaders(httpHeaders);
        Assert.assertEquals("UTF-8", charset);
    }

    @Test
    public void testParseContentTypeUTF8(){
        final String contentType = "text/xml;charset=utf-8";
        final String charset = CharsetUtility.parseContentType(contentType);
        Assert.assertEquals("UTF-8", charset);
    }

    @Test
    public void testParseContentTypeISO(){
        final String contentType = "text/xml;charset=iso-8859-1";
        final String charset = CharsetUtility.parseContentType(contentType);
        Assert.assertEquals("ISO-8859-1", charset);
    }

    @Test
    public void testParseContentTypeNull(){
        final String charset = CharsetUtility.parseContentType(null);
        Assert.assertEquals("UTF-8", charset);
    }

    @Test
    public void testParseContentTypeMissingCharset(){
        final String charset = CharsetUtility.parseContentType("text/xml");
        Assert.assertEquals("UTF-8", charset);
    }

    @Test
    public void testParseContentTypeInvalidString(){
        final String charset = CharsetUtility.parseContentType("InvalidString");
        Assert.assertEquals("UTF-8", charset);
    }

}
