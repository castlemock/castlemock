package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestXPathExpression;
import com.castlemock.repository.rest.file.project.model.RestXPathExpressionFile;

public final class RestXPathExpressionFileConverter {

    private RestXPathExpressionFileConverter() {

    }

    public static RestXPathExpression toRestXPathExpression(final RestXPathExpressionFile restXPathExpressionFile) {
        return RestXPathExpression.builder()
                .expression(restXPathExpressionFile.getExpression())
                .build();
    }

}
