package com.castlemock.core.mock.graphql.service.project.output;

import com.castlemock.core.basis.model.Output;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLApplication;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLOperation;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLRequestQuery;

import java.util.Map;

public final class IdentifyGraphQLOperationOutput implements Output {

    @NotNull
    private final GraphQLApplication graphQLApplication;
    @NotNull
    private final Map<GraphQLRequestQuery, GraphQLOperation> operation;


    public IdentifyGraphQLOperationOutput(GraphQLApplication graphQLApplication,
                                          Map<GraphQLRequestQuery, GraphQLOperation> operation) {
        this.graphQLApplication = graphQLApplication;
        this.operation = operation;

    }

    public GraphQLApplication getGraphQLApplication() {
        return graphQLApplication;
    }

    public Map<GraphQLRequestQuery, GraphQLOperation> getOperation() {
        return operation;
    }
}
