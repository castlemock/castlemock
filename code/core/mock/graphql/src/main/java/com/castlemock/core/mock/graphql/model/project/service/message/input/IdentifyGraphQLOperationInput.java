package com.castlemock.core.mock.graphql.model.project.service.message.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLRequestQueryDto;

import java.util.List;

public class IdentifyGraphQLOperationInput implements Input {

    @NotNull
    private String graphQLProjectId;
    @NotNull
    private List<GraphQLRequestQueryDto> queries;

    public IdentifyGraphQLOperationInput(String graphQLProjectId, List<GraphQLRequestQueryDto> queries) {
        this.graphQLProjectId = graphQLProjectId;
        this.queries = queries;
    }

    public String getGraphQLProjectId() {
        return graphQLProjectId;
    }

    public void setGraphQLProjectId(String graphQLProjectId) {
        this.graphQLProjectId = graphQLProjectId;
    }

    public List<GraphQLRequestQueryDto> getQueries() {
        return queries;
    }

    public void setQueries(List<GraphQLRequestQueryDto> queries) {
        this.queries = queries;
    }
}
