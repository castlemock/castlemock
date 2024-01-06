package com.castlemock.repository.rest.file.project.converter;

import com.castlemock.model.mock.rest.domain.RestXPathExpression;
import com.castlemock.repository.rest.file.project.model.RestXPathExpressionFile;

public final class RestXPathExpressionConverter {

    private RestXPathExpressionConverter() {

    }

    public static RestXPathExpressionFile toRestXPathExpressionFile(final RestXPathExpression restXPathExpression) {
        return RestXPathExpressionFile.builder()
                .expression(restXPathExpression.getExpression())
                .build();
    }

}
