package com.castlemock.repository.soap.file.project.converter;

import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.repository.core.file.http.converter.HttpHeaderConverter;
import com.castlemock.repository.soap.file.project.model.SoapMockResponseFile;

import java.util.stream.Collectors;

public final class SoapMockResponseConverter {

    private SoapMockResponseConverter() {

    }

    public static SoapMockResponseFile toSoapMockResponseFile(final SoapMockResponse soapMockResponse) {
        return SoapMockResponseFile.builder()
                .id(soapMockResponse.getId())
                .body(soapMockResponse.getBody())
                .usingExpressions(soapMockResponse.getUsingExpressions())
                .status(soapMockResponse.getStatus())
                .operationId(soapMockResponse.getOperationId())
                .contentEncodings(soapMockResponse.getContentEncodings())
                .httpStatusCode(soapMockResponse.getHttpStatusCode())
                .name(soapMockResponse.getName())
                .httpHeaders(soapMockResponse.getHttpHeaders()
                        .stream()
                        .map(HttpHeaderConverter::toHttpHeaderFile)
                        .collect(Collectors.toList()))
                .xpathExpressions(soapMockResponse.getXpathExpressions()
                        .stream()
                        .map(SoapXPathExpressionConverter::toSoapXPathExpressionFile)
                        .collect(Collectors.toList()))
                .build();
    }
}
