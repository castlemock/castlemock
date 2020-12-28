/*
 * Copyright 2015 Karl Dahlgren
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

package com.castlemock.service.mock.rest.event.input;

import com.castlemock.model.core.Input;
import com.castlemock.model.core.validation.NotNull;
import com.castlemock.model.mock.rest.domain.RestEvent;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class CreateRestEventInput implements Input {

    @NotNull
    private final RestEvent restEvent;

    private CreateRestEventInput(RestEvent restEvent) {
        this.restEvent = restEvent;
    }

    public RestEvent getRestEvent() {
        return restEvent;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private RestEvent restEvent;

        public Builder restEvent(final RestEvent restEvent){
            this.restEvent = restEvent;
            return this;
        }

        public CreateRestEventInput build(){
            return new CreateRestEventInput(this.restEvent);
        }

    }
}
