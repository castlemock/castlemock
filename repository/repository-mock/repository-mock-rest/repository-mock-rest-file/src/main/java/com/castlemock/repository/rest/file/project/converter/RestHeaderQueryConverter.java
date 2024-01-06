package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestHeaderQuery;
import com.castlemock.repository.rest.file.project.model.RestHeaderQueryFile;

public final class RestHeaderQueryConverter {

    private RestHeaderQueryConverter() {

    }

    public static RestHeaderQueryFile toRestHeaderQuery(final RestHeaderQuery restHeaderQuery) {
        return RestHeaderQueryFile.builder()
                .header(restHeaderQuery.getHeader())
                .query(restHeaderQuery.getQuery())
                .matchAny(restHeaderQuery.getMatchAny())
                .matchCase(restHeaderQuery.getMatchCase())
                .matchRegex(restHeaderQuery.getMatchRegex())
                .build();
    }

}
