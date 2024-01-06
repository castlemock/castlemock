package com.castlemock.repository.soap.file.project.converter;

import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.repository.core.file.http.converter.HttpHeaderFileConverter;
import com.castlemock.repository.soap.file.project.model.SoapMockResponseFile;

import java.util.stream.Collectors;

public final class SoapMockResponseFileConverter {

    private SoapMockResponseFileConverter() {

    }

    public static SoapMockResponse toSoapMockResponse(final SoapMockResponseFile soapMockResponseFile) {
        return SoapMockResponse.builder()
                .id(soapMockResponseFile.getId())
                .body(soapMockResponseFile.getBody())
                .usingExpressions(soapMockResponseFile.getUsingExpressions())
                .status(soapMockResponseFile.getStatus())
                .operationId(soapMockResponseFile.getOperationId())
                .contentEncodings(soapMockResponseFile.getContentEncodings())
                .httpStatusCode(soapMockResponseFile.getHttpStatusCode())
                .name(soapMockResponseFile.getName())
                .httpHeaders(soapMockResponseFile.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderFileConverter::toHttpHeader)
                        .collect(Collectors.toList()))
                .xpathExpressions(soapMockResponseFile.getXpathExpressions()
                        .stream()
                        .map(SoapXPathExpressionFileConverter::toSoapXPathExpression)
                        .collect(Collectors.toList()))
                .build();
    }
}
