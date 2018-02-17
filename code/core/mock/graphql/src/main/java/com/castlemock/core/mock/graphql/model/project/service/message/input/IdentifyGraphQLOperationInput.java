package com.castlemock.core.mock.graphql.model.project.service.message.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.model.validation.NotNull;

public class IdentifyGraphQLOperationInput implements Input {

    @NotNull
    private String graphQLProjectId;
    @NotNull
    private String graphQLApplicationId;
    @NotNull
    private String graphQLOperationIdentifier;
    @NotNull
    private String uri;
    @NotNull
    private HttpMethod httpMethod;

    public IdentifyGraphQLOperationInput(String graphQLProjectId, String graphQLApplicationId,
                                         String graphQLOperationIdentifier,
                                         String uri, HttpMethod httpMethod) {
        this.graphQLProjectId = graphQLProjectId;
        this.graphQLApplicationId = graphQLApplicationId;
        this.graphQLOperationIdentifier = graphQLOperationIdentifier;
        this.uri = uri;
        this.httpMethod = httpMethod;
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

    public String getGraphQLOperationIdentifier() {
        return graphQLOperationIdentifier;
    }

    public void setGraphQLOperationIdentifier(String graphQLOperationIdentifier) {
        this.graphQLOperationIdentifier = graphQLOperationIdentifier;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }
}
