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

package com.castlemock.web.mock.graphql.web.view;


import com.castlemock.web.mock.graphql.web.AbstractControllerTest;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractGraphQLViewControllerTest extends AbstractControllerTest {

    protected static final String QUERY = "query";
    protected static final String MUTATION = "mutation";
    protected static final String SUBSCRIPTION = "subscription";
    protected static final String GRAPHQL = "graphql";
    protected static final String GRAPHQL_PROJECT_ID = "graphQLProjectId";
    protected static final String GRAPHQL_APPLICATION_ID = "graphQLApplicationId";
    protected static final String GRAPHQL_APPLICATIONS = "graphQLApplications";

    protected static final String GRAPHQL_PROJECT = "graphQLProject";
    protected static final String GRAPHQL_APPLICATION = "graphQLApplication";
    protected static final String GRAPHQL_QUERY = "graphQLQuery";
    protected static final String GRAPHQL_MUTATION = "graphQLMutation";
    protected static final String GRAPHQL_SUBSCRIPTION = "graphQLSubscription";
    protected static final String GRAPHQL_OBJECT_TYPE = "graphQLObjectType";
    protected static final String GRAPHQL_OBJECT = "object";
    protected static final String GRAPHQL_ENUM = "enum";
    protected static final String GRAPHQL_ENUM_TYPE = "graphQLEnumType";
    protected static final String GRAPHQL_OPERATION_STATUSES = "graphQLOperationStatus";
    protected static final String SERVICE_URL = "/web/graphql/";

    protected static final Integer GLOBAL_VIEW_MODEL_COUNT = 8;

}
