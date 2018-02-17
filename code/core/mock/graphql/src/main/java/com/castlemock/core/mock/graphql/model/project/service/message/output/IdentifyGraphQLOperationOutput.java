package com.castlemock.core.mock.graphql.model.project.service.message.output;

import com.castlemock.core.basis.model.Output;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLOperationDto;

public class IdentifyGraphQLOperationOutput implements Output {

    @NotNull
    private String graphQLProjectId;

    @NotNull
    private String graphQLApplicationId;

    @NotNull
    private String graphQLOperationId;

    @NotNull
    private GraphQLOperationDto graphQLOperation;

    public IdentifyGraphQLOperationOutput(String graphQLProjectId, String graphQLApplicationId,
                                          String graphQLOperationId, GraphQLOperationDto graphQLOperation) {
        this.graphQLProjectId = graphQLProjectId;
        this.graphQLApplicationId = graphQLApplicationId;
        this.graphQLOperationId = graphQLOperationId;
        this.graphQLOperation = graphQLOperation;
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

    public String getGraphQLOperationId() {
        return graphQLOperationId;
    }

    public void setGraphQLOperationId(String graphQLOperationId) {
        this.graphQLOperationId = graphQLOperationId;
    }

    public GraphQLOperationDto getGraphQLOperation() {
        return graphQLOperation;
    }

    public void setGraphQLOperation(GraphQLOperationDto graphQLOperation) {
        this.graphQLOperation = graphQLOperation;
    }
}
