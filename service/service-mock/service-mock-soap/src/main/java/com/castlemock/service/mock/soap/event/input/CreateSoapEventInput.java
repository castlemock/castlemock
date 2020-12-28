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

package com.castlemock.service.mock.soap.event.input;

import com.castlemock.model.core.model.Input;
import com.castlemock.model.mock.soap.domain.SoapEvent;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class CreateSoapEventInput implements Input {

    private final SoapEvent soapEvent;

    private CreateSoapEventInput(SoapEvent soapEvent) {
        this.soapEvent = soapEvent;
    }

    public SoapEvent getSoapEvent() {
        return soapEvent;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private SoapEvent soapEvent;

        public Builder soapEvent(final SoapEvent soapEvent){
            this.soapEvent = soapEvent;
            return this;
        }

        public CreateSoapEventInput build(){
            return new CreateSoapEventInput(this.soapEvent);
        }

    }

}
