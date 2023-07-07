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

package com.castlemock.web.core.filter;

import com.google.common.io.ByteStreams;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The {@link HttpServletRequestWrapper} is a HTTP Servlet request wrapper
 * class and is used to allow multiple reads of the input stream.
 * If the wrapper is not applied, then the request will only allow you
 * to read the input stream once, which in certain cases won't work.
 * For example, Spring will read the input stream when creating a multipart request.
 * In this case, without the wrapper, we wouldn't be able to read the
 * input stream again. This would prohibit us from extracting the SOAP body later on.
 * @author Karl Dahlgren
 * @since 1.18
 */
public class HttpServletRequestWrapper extends jakarta.servlet.http.HttpServletRequestWrapper {
    private byte[] bytes;

    HttpServletRequestWrapper(final HttpServletRequest request) throws IOException {
        super(request);

        final InputStream inputStream = request.getInputStream();
        this.bytes = ByteStreams.toByteArray(inputStream);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {
                // Not implemented
            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}