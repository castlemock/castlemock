package com.castlemock.core.mock.graphql.service.project.output;

import com.castlemock.core.basis.model.Output;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLApplication;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLOperation;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLRequestQuery;
import java.util.Map;

public class IdentifyGraphQLOperationOutput implements Output {

    @NotNull
    private GraphQLApplication graphQLApplication;
    @NotNull
    private Map<GraphQLRequestQuery, GraphQLOperation> operation;


    public IdentifyGraphQLOperationOutput(GraphQLApplication graphQLApplication,
                                          Map<GraphQLRequestQuery, GraphQLOperation> operation) {
        this.graphQLApplication = graphQLApplication;
        this.operation = operation;

    }

    public GraphQLApplication getGraphQLApplication() {
        return graphQLApplication;
    }

    public void setGraphQLApplication(GraphQLApplication graphQLApplication) {
        this.graphQLApplication = graphQLApplication;
    }

    public Map<GraphQLRequestQuery, GraphQLOperation> getOperation() {
        return operation;
    }

    public void setOperation(Map<GraphQLRequestQuery, GraphQLOperation> operation) {
        this.operation = operation;
    }
}
