package com.castlemock.core.mock.graphql.service.project.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLRequestQuery;

import java.util.List;

public final class IdentifyGraphQLOperationInput implements Input {

    @NotNull
    private final String graphQLProjectId;
    @NotNull
    private final String graphQLApplicationId;
    @NotNull
    private final List<GraphQLRequestQuery> queries;

    public IdentifyGraphQLOperationInput(String graphQLProjectId,
                                         String graphQLApplicationId,
                                         List<GraphQLRequestQuery> queries) {
        this.graphQLProjectId = graphQLProjectId;
        this.graphQLApplicationId = graphQLApplicationId;
        this.queries = queries;
    }

    public String getGraphQLProjectId() {
        return graphQLProjectId;
    }

    public String getGraphQLApplicationId() {
        return graphQLApplicationId;
    }

    public List<GraphQLRequestQuery> getQueries() {
        return queries;
    }

}
