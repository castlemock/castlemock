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

package com.castlemock.web.mock.soap.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

/**
 * The {@link SoapHttpServletRequestWrapperFilter} is a filter
 * that will wrap all incoming SOAP requests with the
 * {@link SoapHttpServletRequestWrapper}.
 * @author Karl Dahlgren
 * @since 1.18
 */
public class SoapHttpServletRequestWrapperFilter implements Filter {
    public void init(FilterConfig config)
            throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws java.io.IOException, ServletException {
        if(request instanceof HttpServletRequest){
            // Wrap the incoming request if it is a HTTP Servlet request
            final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            final SoapHttpServletRequestWrapper requestWrapper = new SoapHttpServletRequestWrapper(httpServletRequest);
            chain.doFilter(requestWrapper,response);
        } else {
            // Don't wrap the request if it is not a HTTP Servlet request
            chain.doFilter(request,response);
        }
    }

    public void destroy( ){

    }
}