/*
 * Copyright 2015 Karl Dahlgren
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

package com.castlemock.app.config;

import com.castlemock.web.basis.web.AbstractController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Logging Interceptor is responsible for logging all the incoming requests and outgoing responses. The logs
 * should provide detailed information on which resources has been requested and when the requested process has been
 * finished.
 * @author Karl Dahlgren
 * @since 1.0
 * @see MvcConfig
 * @see AbstractController
 */
public class LoggingInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    /**
     * The method handles incoming requests and logs them if debug mode is enabled. The method will log the following
     * things:
     *  1. The request URI
     *  2. The request method
     *  3. The controller responsible for handling the request
     * @param request The incoming request. The request will be parsed and logged
     * @param response The outgoing response
     * @param handler The handler contains information about the method and controller that will process the incoming request
     * @return Always returns true
     * @see AbstractController
     */
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        if(LOGGER.isDebugEnabled()){
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Start processing the following request: " + request.getRequestURI() + " (" + request.getMethod() + ").");
            if(handler instanceof HandlerMethod){
                final HandlerMethod handlerMethod = (HandlerMethod) handler;
                stringBuilder.append("This request is going to be processed by the following controller: " + handlerMethod.getBeanType().getSimpleName());
            }
            LOGGER.debug(stringBuilder.toString());
        }
        return true;
    }

    /**
     * The method handles incoming requests and logs them if debug mode is enabled. The method will log the following
     * things:
     *  1. The request URI
     *  2. The request method
     *  3. The controller responsible for handling the request
     * @param request The incoming request. The request will be parsed and logged
     * @param response The outgoing response
     * @param handler The handler contains information about the method and controller that has processed the incoming request
     * @param exception Outgoing exception
     * @see AbstractController
     */
    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception exception) {
        if(LOGGER.isDebugEnabled()){
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Finished processing the following request: " + request.getRequestURI() + " (" + request.getMethod() + ").");
            if(handler instanceof HandlerMethod){
                final HandlerMethod handlerMethod = (HandlerMethod) handler;
                stringBuilder.append("This request was processed by the following controller: " + handlerMethod.getBeanType().getSimpleName());
            }
            LOGGER.debug(stringBuilder.toString());
        }
    }
}
