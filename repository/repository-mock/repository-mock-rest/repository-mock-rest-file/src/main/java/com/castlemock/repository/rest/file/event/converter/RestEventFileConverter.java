package com.castlemock.repository.rest.file.event.converter;

import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.repository.core.file.event.converter.EventFileConverter;
import com.castlemock.repository.rest.file.event.model.RestEventFile;

import java.util.Optional;

public final class RestEventFileConverter {

    private RestEventFileConverter() {

    }

    public static RestEvent toRestEvent(final RestEventFile restEventFile) {
        return EventFileConverter.toEvent(restEventFile, RestEvent.builder())
                .methodId(restEventFile.getMethodId())
                .projectId(restEventFile.getProjectId())
                .applicationId(restEventFile.getApplicationId())
                .resourceId(restEventFile.getResourceId())
                .request(RestRequestFileConverter.toRestRequest(restEventFile.getRequest()))
                .response(Optional.ofNullable(restEventFile.getResponse())
                        .map(RestResponseFileConverter::toRestResponse)
                        .orElse(null))
                .build();
    }


}
