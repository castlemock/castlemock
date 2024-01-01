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

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The {@link HttpServletRequestWrapperFilter} is a filter
 * that will wrap all incoming SOAP requests with the
 * {@link HttpServletRequestWrapper}.
 * @author Karl Dahlgren
 * @since 1.18
 */
public class HttpServletRequestWrapperFilter implements Filter {
    public void init(final FilterConfig config) {
    }

    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws java.io.IOException, ServletException {
        if(request instanceof HttpServletRequest httpServletRequest){
            // Wrap the incoming request if it is a HTTP Servlet request
            final HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(httpServletRequest);
            chain.doFilter(requestWrapper,response);
        } else {
            // Don't wrap the request if it is not a HTTP Servlet request
            chain.doFilter(request,response);
        }
    }

    public void destroy( ){

    }
}