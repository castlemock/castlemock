package com.castlemock.core.mock.graphql.model.project.service.message.output;

import com.castlemock.core.basis.model.Output;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLApplicationDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLOperationDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLProjectDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLRequestQueryDto;
import java.util.Map;

public class IdentifyGraphQLOperationOutput implements Output {

    @NotNull
    private GraphQLApplicationDto graphQLApplication;
    @NotNull
    private Map<GraphQLRequestQueryDto, GraphQLOperationDto> operation;


    public IdentifyGraphQLOperationOutput(GraphQLApplicationDto graphQLApplication,
                                          Map<GraphQLRequestQueryDto, GraphQLOperationDto> operation) {
        this.graphQLApplication = graphQLApplication;
        this.operation = operation;

    }

    public GraphQLApplicationDto getGraphQLApplication() {
        return graphQLApplication;
    }

    public void setGraphQLApplication(GraphQLApplicationDto graphQLApplication) {
        this.graphQLApplication = graphQLApplication;
    }

    public Map<GraphQLRequestQueryDto, GraphQLOperationDto> getOperation() {
        return operation;
    }

    public void setOperation(Map<GraphQLRequestQueryDto, GraphQLOperationDto> operation) {
        this.operation = operation;
    }
}
