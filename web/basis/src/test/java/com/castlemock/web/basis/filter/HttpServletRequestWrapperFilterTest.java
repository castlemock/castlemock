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

package com.castlemock.web.basis.filter;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyInt;

/**
 * @author Karl Dahlgren
 * @since 1.18
 */
public class HttpServletRequestWrapperFilterTest {

    @Test
    public void testDoFilterHttpRequest() throws IOException, ServletException {
        final HttpServletRequestWrapperFilter filter = new HttpServletRequestWrapperFilter();
        final FilterConfig filterConfig = Mockito.mock(FilterConfig.class);
        filter.init(filterConfig);
        final ServletInputStream inputStream = Mockito.mock(ServletInputStream.class);
        final ServletRequest request = Mockito.mock(HttpServletRequest.class);
        final ServletResponse response = Mockito.mock(ServletResponse.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);

        Mockito.when(inputStream.read(Mockito.any(byte[].class), anyInt(), anyInt())).thenReturn(-1);
        Mockito.when(request.getInputStream()).thenReturn(inputStream);


        filter.doFilter(request, response, chain);

        Mockito.verify(chain, Mockito.times(1)).doFilter(Mockito.any(HttpServletRequestWrapper.class),
                Mockito.eq(response));
        filter.destroy();
    }

    @Test
    public void testDoFilterRequest() throws IOException, ServletException {
        final HttpServletRequestWrapperFilter filter = new HttpServletRequestWrapperFilter();
        final FilterConfig filterConfig = Mockito.mock(FilterConfig.class);
        filter.init(filterConfig);
        final ServletRequest request = Mockito.mock(ServletRequest.class);
        final ServletResponse response = Mockito.mock(ServletResponse.class);
        final FilterChain chain = Mockito.mock(FilterChain.class);

        filter.doFilter(request, response, chain);

        Mockito.verify(chain, Mockito.times(1))
                .doFilter(Mockito.eq(request), Mockito.eq(response));
        filter.destroy();
    }

}
