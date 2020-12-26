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


package com.castlemock.web.mock.soap.utility;

import java.util.Objects;
import java.util.Optional;

public class ElementName {

    private final String namespace;
    private final String localName;

    private ElementName(final Builder builder){
        this.namespace = builder.namespace;
        this.localName = Objects.requireNonNull(builder.localName);
    }

    public Optional<String> getNamespace() {
        return Optional.ofNullable(namespace);
    }

    public String getLocalName() {
        return localName;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private String namespace;
        private String localName;

        private Builder(){

        }

        public Builder namespace(final String namespace){
            this.namespace = namespace;
            return this;
        }

        public Builder localName(final String localName){
            this.localName = localName;
            return this;
        }

        public ElementName build(){
            return new ElementName(this);
        }
    }

}
