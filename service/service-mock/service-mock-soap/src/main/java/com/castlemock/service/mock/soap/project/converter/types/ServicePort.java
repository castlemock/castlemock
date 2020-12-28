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
package com.castlemock.service.mock.soap.project.converter.types;

import java.util.Objects;

public class ServicePort {

    private final String name;
    private final Attribute binding;
    private final ServicePortAddress address;

    private ServicePort(final Builder builder){
        this.name = Objects.requireNonNull(builder.name);
        this.binding = Objects.requireNonNull(builder.binding);
        this.address = Objects.requireNonNull(builder.address);
    }

    public String getName() {
        return name;
    }

    public Attribute getBinding() {
        return binding;
    }

    public ServicePortAddress getAddress() {
        return address;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private String name;
        private Attribute binding;
        private ServicePortAddress address;

        private Builder(){

        }

        public Builder name(final String name){
            this.name = name;
            return this;
        }

        public Builder binding(final Attribute binding){
            this.binding = binding;
            return this;
        }

        public Builder address(final ServicePortAddress address){
            this.address = address;
            return this;
        }

        public ServicePort build(){
            return new ServicePort(this);
        }
    }

}
