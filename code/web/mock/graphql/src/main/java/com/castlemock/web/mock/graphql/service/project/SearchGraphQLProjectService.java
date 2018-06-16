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

package com.castlemock.web.mock.graphql.service.project;

import com.castlemock.core.basis.model.*;
import com.castlemock.core.mock.graphql.model.project.domain.*;
import com.castlemock.core.mock.graphql.service.project.input.SearchGraphQLProjectInput;
import com.castlemock.core.mock.graphql.service.project.output.SearchGraphQLProjectOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * The service provides the functionality to search for REST resources.
 * @author Karl Dahlgren
 * @since 1.19
 */
@org.springframework.stereotype.Service
public class SearchGraphQLProjectService extends AbstractGraphQLProjectService implements Service<SearchGraphQLProjectInput, SearchGraphQLProjectOutput> {


    @Autowired
    private MessageSource messageSource;

    private static final String SLASH = "/";
    private static final String GRAPHQL = "graphql";
    private static final String PROJECT = "project";
    private static final String APPLICATION = "application";
    private static final String OBJECT_TYPE = "object";
    private static final String ENUM_TYPE = "enum";
    private static final String QUERY = "query";
    private static final String MUTATION = "mutation";
    private static final String SUBSCRIPTION = "subscription";
    private static final String COMMA = ", ";
    private static final String GRAPHQL_TYPE = "GraphQL";

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<SearchGraphQLProjectOutput> process(final ServiceTask<SearchGraphQLProjectInput> serviceTask) {
        final SearchGraphQLProjectInput input = serviceTask.getInput();
        final SearchQuery searchQuery = input.getSearchQuery();
        final List<SearchResult> searchResults = new ArrayList<>();

        final List<GraphQLProject> projects = this.repository.search(searchQuery);
        final List<GraphQLApplication> applications = this.applicationRepository.search(searchQuery);
        final List<GraphQLObjectType> objectTypes = this.objectTypeRepository.search(searchQuery);
        final List<GraphQLEnumType> enumTypes = this.enumTypeRepository.search(searchQuery);
        final List<GraphQLQuery> queries = this.queryRepository.search(searchQuery);
        final List<GraphQLMutation> mutations = this.mutationRepository.search(searchQuery);
        final List<GraphQLSubscription> subscriptions = this.subscriptionRepository.search(searchQuery);

        final String projectTypeLocale = messageSource.getMessage("general.type.project", null , LocaleContextHolder.getLocale());
        final String applicationTypeLocale = messageSource.getMessage("graphql.type.application", null , LocaleContextHolder.getLocale());
        final String queryTypeLocale = messageSource.getMessage("graphql.type.query", null , LocaleContextHolder.getLocale());
        final String mutationTypeLocale = messageSource.getMessage("graphql.type.mutation", null , LocaleContextHolder.getLocale());
        final String subscriptionTypeLocale = messageSource.getMessage("graphql.type.subscription", null , LocaleContextHolder.getLocale());
        final String objectTypeLocale = messageSource.getMessage("graphql.type.object", null , LocaleContextHolder.getLocale());
        final String enumTypeLocale = messageSource.getMessage("graphql.type.enum", null , LocaleContextHolder.getLocale());

        for(GraphQLProject project : projects){
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(project.getName());
            searchResult.setLink(GRAPHQL + SLASH + PROJECT + SLASH + project.getId());
            searchResult.setDescription(GRAPHQL_TYPE + COMMA + projectTypeLocale);
            searchResults.add(searchResult);
        }

        for(GraphQLApplication application : applications){
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(application.getName());
            searchResult.setLink(GRAPHQL + SLASH + PROJECT + SLASH + application.getProjectId()
                    + SLASH + APPLICATION + SLASH + application.getId());
            searchResult.setDescription(GRAPHQL_TYPE + COMMA + applicationTypeLocale);
            searchResults.add(searchResult);
        }

        for(GraphQLObjectType objectType : objectTypes){
            final String projectId = this.applicationRepository.getProjectId(objectType.getApplicationId());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(objectType.getName());
            searchResult.setLink(GRAPHQL + SLASH + PROJECT + SLASH + projectId
                    + SLASH + APPLICATION + SLASH + objectType.getApplicationId()
                    + SLASH + OBJECT_TYPE + SLASH + objectType.getId());
            searchResult.setDescription(GRAPHQL_TYPE + COMMA + objectTypeLocale);
            searchResults.add(searchResult);
        }

        for(GraphQLEnumType enumType : enumTypes){
            final String projectId = this.applicationRepository.getProjectId(enumType.getApplicationId());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(enumType.getName());
            searchResult.setLink(GRAPHQL + SLASH + PROJECT + SLASH + projectId
                    + SLASH + APPLICATION + SLASH + enumType.getApplicationId()
                    + SLASH + ENUM_TYPE + SLASH + enumType.getId());
            searchResult.setDescription(GRAPHQL_TYPE + COMMA + enumTypeLocale);
            searchResults.add(searchResult);
        }

        for(GraphQLQuery query : queries){
            final String projectId = this.applicationRepository.getProjectId(query.getApplicationId());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(query.getName());
            searchResult.setLink(GRAPHQL + SLASH + PROJECT + SLASH + projectId
                    + SLASH + APPLICATION + SLASH + query.getApplicationId()
                    + SLASH + QUERY + SLASH + query.getId());
            searchResult.setDescription(GRAPHQL_TYPE + COMMA + queryTypeLocale);
            searchResults.add(searchResult);
        }

        for(GraphQLMutation mutation : mutations){
            final String projectId = this.applicationRepository.getProjectId(mutation.getApplicationId());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(mutation.getName());
            searchResult.setLink(GRAPHQL + SLASH + PROJECT + SLASH + projectId
                    + SLASH + APPLICATION + SLASH + mutation.getApplicationId()
                    + SLASH + MUTATION + SLASH + mutation.getId());
            searchResult.setDescription(GRAPHQL_TYPE + COMMA + mutationTypeLocale);
            searchResults.add(searchResult);
        }

        for(GraphQLSubscription subscription : subscriptions){
            final String projectId = this.applicationRepository.getProjectId(subscription.getApplicationId());
            final SearchResult searchResult = new SearchResult();
            searchResult.setTitle(subscription.getName());
            searchResult.setLink(GRAPHQL + SLASH + PROJECT + SLASH + projectId
                    + SLASH + APPLICATION + SLASH + subscription.getApplicationId()
                    + SLASH + SUBSCRIPTION + SLASH + subscription.getId());
            searchResult.setDescription(GRAPHQL_TYPE + COMMA + subscriptionTypeLocale);
            searchResults.add(searchResult);
        }


        return createServiceResult(new SearchGraphQLProjectOutput(searchResults));
    }


}
