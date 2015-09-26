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

package com.fortmocks.war.base.web.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletContext;

/**
 * The AbstractController provides functionality that are shared among all the controllers in Fort Mocks
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractController {

    @Autowired
    private ServletContext servletContext;

    protected static final String CONTENT_TYPE = "Content-Type";
    protected static final String COMMAND = "command";
    protected static final String DIVIDER = ":";
    protected static final String EMPTY = "";
    protected static final String EVENTS = "events";
    protected static final String EVENT = "event";
    protected static final String MOCK = "mock";
    protected static final String NEW_LINE = System.lineSeparator();
    protected static final String PROJECT = "project";
    protected static final String PROJECTS = "projects";
    protected static final String PROJECT_TYPES = "projectTypes";
    protected static final String DOMAIN_NAME_STRATEGIES = "domainNameStrategies";
    protected static final String ROLES = "roles";
    protected static final String SLASH = "/";
    protected static final String SPACE = " ";
    protected static final String USER = "user";
    protected static final String USERS = "users";
    protected static final String USER_STATUSES = "userStatuses";
    protected static final String FILE_UPLOAD_FORM = "uploadForm";

    private static final String ANONYMOUS_USER = "Anonymous";
    /**
     * Returns the current application context for Fort Mocks. For example the /fortmocks in http://localhost:8080/fortmocks
     * @return The current application context
     */
    protected String getContext(){
        return servletContext.getContextPath();
    }

    /**
     * Get the current logged in user username
     * @return The username of the current logged in user
     * @see com.fortmocks.core.base.model.user.User
     */
    protected String getLoggedInUsername(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ANONYMOUS_USER; // Should never happened except during unit tests
        }
        return authentication.getName();
    }


}
