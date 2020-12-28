package com.castlemock.model.mock.rest.domain;

public final class RestParameterQueryTestBuilder {

    private String parameter;
    private String query;
    private Boolean matchCase;
    private Boolean matchAny;
    private Boolean matchRegex;

    private RestParameterQueryTestBuilder() {
        this.parameter = "user";
        this.query = "karl";
        this.matchCase = Boolean.FALSE;
        this.matchAny = Boolean.FALSE;
        this.matchRegex = Boolean.FALSE;
    }

    public RestParameterQueryTestBuilder parameter(final String parameter) {
        this.parameter = parameter;
        return this;
    }

    public RestParameterQueryTestBuilder query(final String query) {
        this.query = query;
        return this;
    }

    public RestParameterQueryTestBuilder matchCase(final Boolean matchCase) {
        this.matchCase = matchCase;
        return this;
    }

    public RestParameterQueryTestBuilder matchAny(final Boolean matchAny) {
        this.matchAny = matchAny;
        return this;
    }

    public RestParameterQueryTestBuilder matchRegex(final Boolean matchRegex) {
        this.matchRegex = matchRegex;
        return this;
    }

    public static RestParameterQueryTestBuilder builder(){
        return new RestParameterQueryTestBuilder();
    }

    public RestParameterQuery build() {
        return RestParameterQuery.builder()
                .parameter(parameter)
                .query(query)
                .matchCase(matchCase)
                .matchAny(matchAny)
                .matchRegex(matchRegex)
                .build();
    }

}
