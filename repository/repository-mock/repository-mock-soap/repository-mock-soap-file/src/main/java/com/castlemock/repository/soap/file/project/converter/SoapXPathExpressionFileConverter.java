package com.castlemock.repository.soap.file.project.converter;

import com.castlemock.model.mock.soap.domain.SoapXPathExpression;
import com.castlemock.repository.soap.file.project.model.SoapXPathExpressionFile;

public final class SoapXPathExpressionFileConverter {

    private SoapXPathExpressionFileConverter() {

    }

    public static SoapXPathExpression toSoapXPathExpression(final SoapXPathExpressionFile soapXPathExpressionFile) {
        return SoapXPathExpression.builder()
                .expression(soapXPathExpressionFile.getExpression())
                .build();
    }

}
