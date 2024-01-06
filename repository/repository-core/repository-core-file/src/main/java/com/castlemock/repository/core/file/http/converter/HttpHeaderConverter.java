package com.castlemock.repository.core.file.http.converter;

import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.repository.core.file.http.model.HttpHeaderFile;

public final class HttpHeaderConverter {

    private HttpHeaderConverter() {

    }

    public static HttpHeaderFile toHttpHeaderFile(final HttpHeader httpHeader) {
        return HttpHeaderFile.builder()
                .name(httpHeader.getName())
                .value(httpHeader.getValue())
                .build();
    }

}
