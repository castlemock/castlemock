package com.castlemock.repository.core.file.http.converter;

import com.castlemock.model.core.http.HttpParameter;
import com.castlemock.repository.core.file.http.model.HttpParameterFile;

public final class HttpParameterConverter {

    private HttpParameterConverter() {

    }

    public static HttpParameterFile toHttpParameterFile(final HttpParameter httpParameter) {
        return HttpParameterFile.builder()
                .name(httpParameter.getName())
                .value(httpParameter.getValue())
                .build();
    }

}
