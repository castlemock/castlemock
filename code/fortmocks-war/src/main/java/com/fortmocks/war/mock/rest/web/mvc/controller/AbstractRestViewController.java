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

package com.fortmocks.war.mock.rest.web.mvc.controller;

import com.fortmocks.war.base.web.mvc.controller.AbstractViewController;
import com.fortmocks.core.mock.rest.model.project.service.RestProjectService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The class operates as a shared base for all the view related to the REST module
 * @author Karl Dahlgren
 * @since 1.0
 * @see AbstractViewController
 */
public class AbstractRestViewController extends AbstractViewController {

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

    protected static final String REST_MOCK_RESPONSE = "restMockResponse";
    protected static final String REST_MOCK_RESPONSES = "restMockResponses";

    protected static final String REST_METHOD_TYPES = "restMethodTypes";
    protected static final String REST_METHOD_STATUSES = "restMethodStatuses";
    protected static final String REST_RESPONSE_STRATEGIES = "restResponsestrategies";

    @Autowired
    protected RestProjectService restProjectService;


}
