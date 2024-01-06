package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestParameterQuery;
import com.castlemock.repository.rest.file.project.model.RestParameterQueryFile;

public final class RestParameterQueryConverter {

    private RestParameterQueryConverter() {

    }

    public static RestParameterQueryFile toRestParameterQuery(final RestParameterQuery restParameterQuery) {
        return RestParameterQueryFile.builder()
                .parameter(restParameterQuery.getParameter())
                .query(restParameterQuery.getQuery())
                .matchAny(restParameterQuery.getMatchAny())
                .matchCase(restParameterQuery.getMatchCase())
                .matchRegex(restParameterQuery.getMatchRegex())
                .urlEncoded(restParameterQuery.getUrlEncoded())
                .build();
    }

}
