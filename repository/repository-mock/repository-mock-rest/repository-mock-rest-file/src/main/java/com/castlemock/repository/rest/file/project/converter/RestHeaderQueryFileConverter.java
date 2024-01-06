package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestHeaderQuery;
import com.castlemock.repository.rest.file.project.model.RestHeaderQueryFile;

public final class RestHeaderQueryFileConverter {

    private RestHeaderQueryFileConverter() {

    }

    public static RestHeaderQuery toRestHeaderQuery(final RestHeaderQueryFile restHeaderQueryFile) {
        return RestHeaderQuery.builder()
                .header(restHeaderQueryFile.getHeader())
                .query(restHeaderQueryFile.getQuery())
                .matchAny(restHeaderQueryFile.getMatchAny())
                .matchCase(restHeaderQueryFile.getMatchCase())
                .matchRegex(restHeaderQueryFile.getMatchRegex())
                .build();
    }

}
