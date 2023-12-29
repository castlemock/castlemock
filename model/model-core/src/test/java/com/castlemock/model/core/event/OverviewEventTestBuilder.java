package com.castlemock.model.core.event;

import com.castlemock.model.core.utility.IdUtility;

import java.util.Date;

public final class OverviewEventTestBuilder {

    private OverviewEventTestBuilder(){
    }

    public static OverviewEvent.Builder builder(){
        return OverviewEvent.builder()
                .id(IdUtility.generateId())
                .startDate(new Date())
                .endDate(new Date())
                .resourceLink("Resource link")
                .resourceName("Resource name")
                .type("test");
    }

    public static OverviewEvent build() {
        return builder().build();
    }

}
