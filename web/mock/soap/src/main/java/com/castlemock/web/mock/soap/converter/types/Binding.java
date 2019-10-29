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

import java.util.Objects;
import java.util.Set;

public final class Binding {

    private final String name;
    private final Attribute type;
    private final Set<BindingOperation> operations;

    private Binding(final Builder builder){
        this.name = Objects.requireNonNull(builder.name);
        this.type = Objects.requireNonNull(builder.type);
        this.operations = Objects.requireNonNull(builder.operations);
    }

    public String getName() {
        return name;
    }

    public Attribute getType() {
        return type;
    }

    public Set<BindingOperation> getOperations() {
        return operations;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private String name;
        private Attribute type;
        private Set<BindingOperation> operations;

        private Builder(){

        }

        public Builder name(final String name){
            this.name = name;
            return this;
        }

        public Builder type(final Attribute type){
            this.type = type;
            return this;
        }

        public Builder operations(final Set<BindingOperation> operations){
            this.operations = operations;
            return this;
        }

        public Binding build(){
            return new Binding(this);
        }
    }
}
