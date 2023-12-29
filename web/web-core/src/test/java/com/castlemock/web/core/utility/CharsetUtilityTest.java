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

package com.castlemock.web.core.utility;

import com.castlemock.model.core.http.HttpHeader;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.18
 */
public class CharsetUtilityTest {


    @Test
    public void testParseHttpHeaders(){
        final HttpHeader contentLength = HttpHeader.builder()
                .name("Content-Length")
                .value("100")
                .build();

        final HttpHeader contentEncoding = HttpHeader.builder()
                .name("Content-Encoding")
                .value("gzip")
                .build();

        final HttpHeader contentType = HttpHeader.builder()
                .name("Content-Type")
                .value("text/xml;charset=iso-8859-1")
                .build();

        final String charset = CharsetUtility.parseHttpHeaders(List.of(contentLength, contentEncoding, contentType));
        Assert.assertEquals("ISO-8859-1", charset);
    }

    @Test
    public void testParseHttpHeadersNull(){
        final String charset = CharsetUtility.parseHttpHeaders(null);
        Assert.assertEquals("UTF-8", charset);
    }

    @Test
    public void testParseHttpHeadersMissingHeader(){
        final HttpHeader contentLength = HttpHeader.builder()
                .name("Content-Length")
                .value("100")
                .build();

        final HttpHeader contentEncoding = HttpHeader.builder()
                .name("Content-Encoding")
                .value("gzip")
                .build();

        final String charset = CharsetUtility.parseHttpHeaders(List.of(contentEncoding, contentLength));
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
