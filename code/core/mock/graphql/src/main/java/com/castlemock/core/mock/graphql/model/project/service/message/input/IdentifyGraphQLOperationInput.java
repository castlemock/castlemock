package com.castlemock.core.mock.graphql.model.project.service.message.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLRequestQuery;

import java.util.List;

public class IdentifyGraphQLOperationInput implements Input {

    @NotNull
    private String graphQLProjectId;
    @NotNull
    private String graphQLApplicationId;
    @NotNull
    private List<GraphQLRequestQuery> queries;

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

    public void setGraphQLProjectId(String graphQLProjectId) {
        this.graphQLProjectId = graphQLProjectId;
    }

    public String getGraphQLApplicationId() {
        return graphQLApplicationId;
    }

    public void setGraphQLApplicationId(String graphQLApplicationId) {
        this.graphQLApplicationId = graphQLApplicationId;
    }

    public List<GraphQLRequestQuery> getQueries() {
        return queries;
    }

    public void setQueries(List<GraphQLRequestQuery> queries) {
        this.queries = queries;
    }
}
