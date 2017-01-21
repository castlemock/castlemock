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

package com.castlemock.web.basis.web.mvc.controller;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.ServletContext;
import java.util.Locale;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractControllerTest {


    @Mock
    protected MessageSource messageSource;
    @Mock
    private ServletContext servletContext;
    protected MockMvc mockMvc;
    protected static final String CONTEXT = "/castlemock";
    protected static final String PARTIAL = "partial";
    protected static final String INDEX = "index";
    protected static final String SLASH = "/";
    protected static final String PROJECT = "project";
    protected static final String PROJECT_ID = "projectId";
    protected static final String PROJECT_TYPES = "projectTypes";
    protected static final Integer GLOBAL_VIEW_MODEL_COUNT = 5;

    @Before
    public void initiateTest() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(getController()).build();
        when(servletContext.getContextPath()).thenReturn(CONTEXT);

        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class))).thenReturn("Empty string");
    }

    protected abstract AbstractController getController();

}
