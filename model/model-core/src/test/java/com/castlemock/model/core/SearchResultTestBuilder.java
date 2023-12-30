package com.castlemock.model.core;

public final class SearchResultTestBuilder {

    private SearchResultTestBuilder() {

    }

    public static SearchResult.Builder builder() {
        return SearchResult.builder()
                .title("Title")
                .description("Description")
                .link("link");
    }

    public static SearchResult build() {
        return builder().build();
    }

}
