/*
 * Copyright 2018 Karl Dahlgren
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
package com.castlemock.web.mock.soap.converter.types;

import com.castlemock.core.mock.soap.model.project.domain.SoapVersion;

import java.util.Objects;

public final class ServicePortAddress {

    private final String location;
    private final SoapVersion version;

    private ServicePortAddress(final Builder builder){
        this.location = Objects.requireNonNull(builder.location);
        this.version = Objects.requireNonNull(builder.version);
    }

    public String getLocation() {
        return location;
    }

    public SoapVersion getVersion() {
        return version;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private String location;
        private SoapVersion version;

        private Builder(){

        }

        public Builder location(final String location){
            this.location = location;
            return this;
        }

        public Builder version(final SoapVersion version){
            this.version = version;
            return this;
        }

        public ServicePortAddress build(){
            return new ServicePortAddress(this);
        }
    }

}
