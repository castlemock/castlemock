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

package com.castlemock.web.core.controller;

import com.castlemock.core.basis.model.ServiceProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;

/**
 * The AbstractController provides functionality that are shared among all the controllers in Castle Mock
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractController {

    @Value("${server.mode.demo}")
    protected boolean demoMode;
    @Value("${server.endpoint.address:}")
    protected String endpointAddress;
    @Autowired
    protected ServiceProcessor serviceProcessor;

    protected static final String CONTENT_TYPE = "Content-Type";
    protected static final String ACCEPT_HEADER = "Accept";
    protected static final String EMPTY = "";
    protected static final String MOCK = "mock";
    protected static final String PROJECT = "project";
    protected static final String SLASH = "/";
    protected static final String SPACE = " ";

    protected static final int DEFAULT_ECHO_RESPONSE_CODE = 200;

    protected AbstractController(final ServiceProcessor serviceProcessor){
        this.serviceProcessor = Objects.requireNonNull(serviceProcessor);
    }

}
