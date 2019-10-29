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

public final class BindingOperation {

    private final String name;
    private final BindingOperationInput input;
    private final BindingOperationOutput output;

    private BindingOperation(final Builder builder){
        this.name = Objects.requireNonNull(builder.name);
        this.input = Objects.requireNonNull(builder.input);
        this.output = Objects.requireNonNull(builder.output);
    }

    public String getName() {
        return name;
    }

    public BindingOperationInput getInput() {
        return input;
    }

    public BindingOperationOutput getOutput() {
        return output;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private String name;
        private BindingOperationInput input;
        private BindingOperationOutput output;

        private Builder(){

        }

        public Builder name(final String name){
            this.name = name;
            return this;
        }

        public Builder input(final BindingOperationInput input){
            this.input = input;
            return this;
        }

        public Builder output(final BindingOperationOutput output){
            this.output = output;
            return this;
        }

        public BindingOperation build(){
            return new BindingOperation(this);
        }
    }

}
