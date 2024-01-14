/*
 * Copyright 2024 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
