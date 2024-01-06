package com.castlemock.repository.core.file.http.converter;

import com.castlemock.model.core.http.HttpParameter;
import com.castlemock.repository.core.file.http.model.HttpParameterFile;

public final class HttpParameterFileConverter {

    private HttpParameterFileConverter() {

    }

    public static HttpParameter toHttpParameter(final HttpParameterFile httpParameterFile) {
        return HttpParameter.builder()
                .name(httpParameterFile.getName())
                .value(httpParameterFile.getValue())
                .build();
    }

}
