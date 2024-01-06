package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestJsonPathExpression;
import com.castlemock.repository.rest.file.project.model.RestJsonPathExpressionFile;

public final class RestJsonPathExpressionConverter {

    private RestJsonPathExpressionConverter() {

    }

    public static RestJsonPathExpressionFile toRestJsonPathExpressionFile(final RestJsonPathExpression restJsonPathExpression) {
        return RestJsonPathExpressionFile.builder()
                .expression(restJsonPathExpression.getExpression())
                .build();
    }

}
