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

package com.fortmocks.web.mock.rest.web.mvc.controller;

import com.fortmocks.web.basis.web.mvc.controller.AbstractViewController;
import org.apache.log4j.Logger;

/**
 * The class operates as a shared base for all the view related to the REST module
 * @author Karl Dahlgren
 * @since 1.0
 * @see AbstractViewController
 */
public class AbstractRestViewController extends AbstractViewController {

    protected static final String REST = "rest";
    protected static final String APPLICATION = "application";
    protected static final String RESOURCE = "resource";

    protected static final String REST_PROJECT = "restProject";
    protected static final String REST_PROJECT_ID = "restProjectId";

    protected static final String REST_APPLICATION = "restApplication";
    protected static final String REST_APPLICATIONS = "restApplications";
    protected static final String REST_APPLICATION_ID = "restApplicationId";

    protected static final String REST_RESOURCE = "restResource";
    protected static final String REST_RESOURCES = "restResources";
    protected static final String REST_RESOURCE_ID = "restResourceId";

    protected static final String REST_METHOD = "restMethod";
    protected static final String REST_METHODS = "restMethods";
    protected static final String REST_METHOD_ID = "restMethodId";

    protected static final String REST_EVENTS = "restEvents";

    protected static final String REST_MOCK_RESPONSE = "restMockResponse";
    protected static final String REST_MOCK_RESPONSES = "restMockResponses";

    protected static final String REST_METHOD_TYPES = "restMethodTypes";
    protected static final String REST_METHOD_STATUSES = "restMethodStatuses";
    protected static final String REST_RESPONSE_STRATEGIES = "restResponsestrategies";

    protected static final String REST_CONTENT_TYPES = "restContentTypes";

    private static final Logger LOGGER = Logger.getLogger(AbstractRestViewController.class);

    /**
     * The method provides the functionality to create the address which is used to invoke a REST service
     * @param protocol THe protocol
     * @param serverPort The server port
     * @param projectId The id of the project
     * @param applicationId The id of the application
     * @param resourceUri The resource uri
     * @return A URL based on all the incoming parameters
     */
    protected String getRestInvokeAddress(final String protocol, int serverPort, final String projectId, final String applicationId, final String resourceUri){
        try {
            final String hostAddress = getHostAddress();
            return protocol + hostAddress + ":" + serverPort + getContext() + SLASH + MOCK + SLASH + REST + SLASH + PROJECT + SLASH + projectId + SLASH + APPLICATION + SLASH + applicationId + resourceUri;
        } catch (Exception exception) {
            LOGGER.error("Unable to generate invoke URL", exception);
            throw new IllegalStateException("Unable to generate invoke URL for " + projectId);
        }
    }

}
