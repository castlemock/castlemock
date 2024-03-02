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

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyInt;

/**
 * @author Karl Dahlgren
 * @since 1.18
 */
class HttpServletRequestWrapperTest {

    @Test
    @DisplayName("Get input stream")
    void testGetInputStream() throws IOException {
        final ServletInputStream inputStream = Mockito.mock(ServletInputStream.class);
        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(inputStream.read(Mockito.any(byte[].class), anyInt(), anyInt())).thenReturn(-1);
        Mockito.when(request.getInputStream()).thenReturn(inputStream);

        final HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request);
        final ServletInputStream stream = wrapper.getInputStream();

        Assertions.assertNotNull(stream);
        Assertions.assertEquals(-1, stream.read());

        Mockito.verify(inputStream, Mockito.times(1)).read(Mockito.any(byte[].class), anyInt(), anyInt());
    }

    @Test
    @DisplayName("Get reader")
    void testGetReader() throws IOException {
        final ServletInputStream inputStream = Mockito.mock(ServletInputStream.class);
        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(inputStream.read(Mockito.any(byte[].class), anyInt(), anyInt())).thenReturn(-1);
        Mockito.when(request.getInputStream()).thenReturn(inputStream);

        final HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request);
        final BufferedReader reader = wrapper.getReader();

        Assertions.assertNotNull(reader);
    }

}
