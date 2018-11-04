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

public final class MessagePart {

    private final String name;
    private final Attribute element;

    private MessagePart(final Builder builder){
        this.name = Objects.requireNonNull(builder.name);
        this.element = Objects.requireNonNull(builder.element);
    }

    public String getName() {
        return name;
    }

    public Attribute getElement() {
        return element;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private String name;
        private Attribute element;

        private Builder(){

        }

        public Builder name(final String name){
            this.name = name;
            return this;
        }

        public Builder element(final Attribute element){
            this.element = element;
            return this;
        }

        public MessagePart build(){
            return new MessagePart(this);
        }
    }

}
