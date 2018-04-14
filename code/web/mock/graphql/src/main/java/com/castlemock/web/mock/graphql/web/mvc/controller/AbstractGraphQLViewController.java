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

package com.castlemock.web.mock.graphql.web.mvc.controller;

import com.castlemock.core.mock.graphql.model.project.domain.GraphQLOperationStatus;
import com.castlemock.web.basis.web.mvc.controller.AbstractViewController;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * The class operates as a shared base for all the view related to the Graph QL module
 * @author Karl Dahlgren
 * @since 1.19
 * @see AbstractViewController
 */
public abstract class AbstractGraphQLViewController extends AbstractViewController {

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
    protected static final String GRAPHQL_ENUM_TYPE = "graphQLEnumType";
    protected static final String GRAPHQL_OPERATION_STATUSES = "graphQLOperationStatus";


    private static final Logger LOGGER = Logger.getLogger(AbstractGraphQLViewController.class);


    /**
     * The method returns a list of REST methods statuses. The method will only return DISABLED and MOCKED
     * if the server is configured to run on demo mode. If not configured to run on demo mode,
     * all the REST method statuses will be returned.
     * @return A list of REST method statuses
     */
    protected List<GraphQLOperationStatus> getGraphQLOperationStatuses(){
        List<GraphQLOperationStatus> statuses = new LinkedList<GraphQLOperationStatus>();
        statuses.add(GraphQLOperationStatus.MOCKED);
        statuses.add(GraphQLOperationStatus.DISABLED);

        if(!demoMode) {
            //statuses.add(GraphQLOperationStatus.FORWARDED);
        }

        return statuses;
    }


    /**
     * The method provides the functionality to create the address which is used to invoke a GraphQL service
     * @param protocol THe protocol
     * @param serverPort The server port
     * @param projectId The id of the project
     * @param applicationId The id of the application
     * @return A URL based on all the incoming parameters
     */
    protected String getGraphQLInvokeAddress(final String protocol, int serverPort, final String projectId, final String applicationId){
        try {
            final String hostAddress = getHostAddress();
            return protocol + hostAddress + ":" + serverPort + getContext() + SLASH + MOCK + SLASH + GRAPHQL + SLASH + PROJECT + SLASH + projectId + SLASH + APPLICATION + SLASH + applicationId;
        } catch (Exception exception) {
            LOGGER.error("Unable to generate invoke URL", exception);
            throw new IllegalStateException("Unable to generate invoke URL for " + projectId);
        }
    }


}
