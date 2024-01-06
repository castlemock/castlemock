package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestParameterQuery;
import com.castlemock.repository.rest.file.project.model.RestParameterQueryFile;

public final class RestParameterQueryFileConverter {

    private RestParameterQueryFileConverter() {

    }

    public static RestParameterQuery toRestParameterQuery(final RestParameterQueryFile restParameterQueryFile) {
        return RestParameterQuery.builder()
                .parameter(restParameterQueryFile.getParameter())
                .query(restParameterQueryFile.getQuery())
                .matchAny(restParameterQueryFile.getMatchAny())
                .matchCase(restParameterQueryFile.getMatchCase())
                .matchRegex(restParameterQueryFile.getMatchRegex())
                .urlEncoded(restParameterQueryFile.getUrlEncoded())
                .build();
    }

}
