package com.castlemock.core.mock.rest.model.project.domain;

public final class RestHeaderQueryTestBuilder {

    private String header;
    private String query;
    private Boolean matchCase;
    private Boolean matchAny;
    private Boolean matchRegex;

    private RestHeaderQueryTestBuilder() {
        this.header = "Content-Type";
        this.query = "json/application";
        this.matchCase = Boolean.FALSE;
        this.matchAny = Boolean.FALSE;
        this.matchRegex = Boolean.FALSE;
    }

    public RestHeaderQueryTestBuilder header(final String header) {
        this.header = header;
        return this;
    }

    public RestHeaderQueryTestBuilder query(final String query) {
        this.query = query;
        return this;
    }

    public RestHeaderQueryTestBuilder matchCase(final Boolean matchCase) {
        this.matchCase = matchCase;
        return this;
    }

    public RestHeaderQueryTestBuilder matchAny(final Boolean matchAny) {
        this.matchAny = matchAny;
        return this;
    }

    public RestHeaderQueryTestBuilder matchRegex(final Boolean matchRegex) {
        this.matchRegex = matchRegex;
        return this;
    }

    public static RestHeaderQueryTestBuilder builder(){
        return new RestHeaderQueryTestBuilder();
    }

    public RestHeaderQuery build() {
        return RestHeaderQuery.builder()
                .header(header)
                .query(query)
                .matchCase(matchCase)
                .matchAny(matchAny)
                .matchRegex(matchRegex)
                .build();
    }
}
