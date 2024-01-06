package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestJsonPathExpression;
import com.castlemock.repository.rest.file.project.model.RestJsonPathExpressionFile;

public final class RestJsonPathExpressionFileConverter {

    private RestJsonPathExpressionFileConverter() {

    }

    public static RestJsonPathExpression toRestJsonPathExpression(final RestJsonPathExpressionFile restJsonPathExpressionFile) {
        return RestJsonPathExpression.builder()
                .expression(restJsonPathExpressionFile.getExpression())
                .build();
    }

}
