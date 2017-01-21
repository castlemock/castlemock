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

package com.castlemock.web.mock.rest.web.mvc.controller;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractRestControllerTest extends AbstractControllerTest {

    protected static final String SERVICE_URL = "/web/rest/";

    protected static final String REST_MOCK_RESPONSE = "restMockResponse";
    protected static final String REST_MOCK_RESPONSE_STRATEGIES = "restMockResponseStrategies";
    protected static final String RESPONSE = "response";

    protected static final String REST_PROJECT = "restProject";
    protected static final String METHOD = "method";
    protected static final String RESOURCE = "resource";

    protected static final String REST_PROJECT_ID = "restProjectId";
    protected static final String REST_RESOURCE_ID = "restResourceId";
    protected static final String REST_METHOD_ID = "restMethodId";

    protected static final String REST_RESOURCE = "restResource";
    protected static final String REST_METHOD = "restMethod";

    protected static final String APPLICATION = "application";
    protected static final String REST_APPLICATION = "restApplication";
    protected static final String REST_APPLICATION_ID = "restApplicationId";

    protected static final String EVENT = "event";

    protected static final Integer GLOBAL_VIEW_MODEL_COUNT = 5;

}
