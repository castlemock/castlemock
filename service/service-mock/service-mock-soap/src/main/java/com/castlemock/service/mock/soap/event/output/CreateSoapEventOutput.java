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

package com.castlemock.service.mock.soap.event.output;

import com.castlemock.model.core.Output;
import com.castlemock.model.mock.soap.domain.SoapEvent;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class CreateSoapEventOutput implements Output {

    private final SoapEvent createdSoapEvent;

    private CreateSoapEventOutput(final SoapEvent createdSoapEvent) {
        this.createdSoapEvent = Objects.requireNonNull(createdSoapEvent, "createdSoapEvent");
    }

    public SoapEvent getCreatedSoapEvent() {
        return createdSoapEvent;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private SoapEvent createdSoapEvent;

        public Builder createdSoapEvent(final SoapEvent createdSoapEvent){
            this.createdSoapEvent = createdSoapEvent;
            return this;
        }

        public CreateSoapEventOutput build(){
            return new CreateSoapEventOutput(this.createdSoapEvent);
        }

    }
}
