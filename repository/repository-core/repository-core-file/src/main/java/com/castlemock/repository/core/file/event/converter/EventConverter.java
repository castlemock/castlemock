package com.castlemock.repository.core.file.event.converter;

import com.castlemock.model.core.event.Event;
import com.castlemock.repository.core.file.event.model.EventFile;

public final class EventConverter {

    private EventConverter() {

    }

    public static <B extends EventFile.Builder<B>> B toEventFile(final Event event, final B builder) {
        return builder
                .id(event.getId())
                .resourceName(event.getResourceName())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate());
    }

}
