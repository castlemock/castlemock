package com.castlemock.model.mock.rest.domain;

public final class RestParameterQueryMatchTestBuilder {

    private RestParameterQuery query;
    private String match;

    private RestParameterQueryMatchTestBuilder() {
        this.query = RestParameterQueryTestBuilder.builder().build();
        this.match = "";
    }

    public RestParameterQueryMatchTestBuilder query(final RestParameterQuery query) {
        this.query = query;
        return this;
    }

    public RestParameterQueryMatchTestBuilder match(final String match) {
        this.match = match;
        return this;
    }

    public static RestParameterQueryMatchTestBuilder builder(){
        return new RestParameterQueryMatchTestBuilder();
    }

    public RestParameterQueryMatch build() {
        return RestParameterQueryMatch.builder()
                .match(match)
                .query(query)
                .build();
    }
}
