package com.castlemock.repository.soap.file.project.converter;

import com.castlemock.model.mock.soap.domain.SoapXPathExpression;
import com.castlemock.repository.soap.file.project.model.SoapXPathExpressionFile;

public final class SoapXPathExpressionConverter {

    private SoapXPathExpressionConverter() {

    }

    public static SoapXPathExpressionFile toSoapXPathExpressionFile(final SoapXPathExpression soapXPathExpression) {
        return SoapXPathExpressionFile.builder()
                .expression(soapXPathExpression.getExpression())
                .build();
    }

}
