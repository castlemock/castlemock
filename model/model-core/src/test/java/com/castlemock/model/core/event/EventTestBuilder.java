package com.castlemock.model.core.event;

import com.castlemock.model.core.utility.IdUtility;

import java.util.Date;

public final class EventTestBuilder {

    private EventTestBuilder(){
    }

    public static TestEvent.Builder builder(){
        return TestEvent.builder()
                .id(IdUtility.generateId())
                .startDate(new Date())
                .endDate(new Date())
                .resourceLink("Resource link")
                .resourceName("Resource name");
    }

    public static TestEvent build() {
        return builder().build();
    }

    public static class TestEvent extends Event {

        private TestEvent(final Builder builder) {
            super(builder);
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder extends Event.Builder<Builder> {

            private Builder() {
            }

            public TestEvent build(){
                return new TestEvent(this);
            }
        }

    }

}
