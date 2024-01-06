package com.castlemock.repository.rest.file.event.converter;

import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.repository.core.file.event.converter.EventConverter;
import com.castlemock.repository.rest.file.event.model.RestEventFile;

public final class RestEventConverter {

    private RestEventConverter() {

    }

    public static RestEventFile toRestEventFile(final RestEvent restEvent) {
        return EventConverter.toEventFile(restEvent, RestEventFile.builder())
                .methodId(restEvent.getMethodId())
                .projectId(restEvent.getProjectId())
                .applicationId(restEvent.getApplicationId())
                .resourceId(restEvent.getResourceId())
                .request(RestRequestConverter.toRestRequestFile(restEvent.getRequest()))
                .response(restEvent.getResponse()
                        .map(RestResponseConverter::toRestResponseFile)
                        .orElse(null))
                .build();
    }


}
