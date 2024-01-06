package com.castlemock.repository.core.file.http.converter;

import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.repository.core.file.http.model.HttpHeaderFile;

public final class HttpHeaderFileConverter {

    private HttpHeaderFileConverter() {

    }

    public static HttpHeader toHttpHeader(final HttpHeaderFile httpHeaderFile) {
        return HttpHeader.builder()
                .name(httpHeaderFile.getName())
                .value(httpHeaderFile.getValue())
                .build();
    }

}
