package com.castlemock.repository.core.file.event.converter;

import com.castlemock.model.core.event.Event;
import com.castlemock.repository.core.file.event.model.EventFile;

public final class EventFileConverter {

    private EventFileConverter() {

    }

    public static <B extends Event.Builder<B>> B toEvent(final EventFile eventFile, final B builder) {
        return builder
                .id(eventFile.getId())
                .resourceName(eventFile.getResourceName())
                .startDate(eventFile.getStartDate())
                .endDate(eventFile.getEndDate());
    }

}
