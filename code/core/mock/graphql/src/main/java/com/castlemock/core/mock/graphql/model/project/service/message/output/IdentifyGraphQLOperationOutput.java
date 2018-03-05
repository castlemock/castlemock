package com.castlemock.core.mock.graphql.model.project.service.message.output;

import com.castlemock.core.basis.model.Output;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLOperationDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLProjectDto;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLRequestQueryDto;
import java.util.Map;

public class IdentifyGraphQLOperationOutput implements Output {

    @NotNull
    private GraphQLProjectDto graphQLProject;
    @NotNull
    private Map<GraphQLRequestQueryDto, GraphQLOperationDto> operation;


    public IdentifyGraphQLOperationOutput(GraphQLProjectDto graphQLProject,
                                          Map<GraphQLRequestQueryDto, GraphQLOperationDto> operation) {
        this.graphQLProject = graphQLProject;
        this.operation = operation;

    }

    public GraphQLProjectDto getGraphQLProject() {
        return graphQLProject;
    }

    public void setGraphQLProject(GraphQLProjectDto graphQLProject) {
        this.graphQLProject = graphQLProject;
    }

    public Map<GraphQLRequestQueryDto, GraphQLOperationDto> getOperation() {
        return operation;
    }

    public void setOperation(Map<GraphQLRequestQueryDto, GraphQLOperationDto> operation) {
        this.operation = operation;
    }
}
