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

import java.util.Optional;

public final class BindingOperationInput {

    private final BindingOperationInputBody body;

    private BindingOperationInput(final Builder builder){
        this.body = builder.body;
    }

    public Optional<BindingOperationInputBody> getBody() {
        return Optional.ofNullable(body);
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private BindingOperationInputBody body;

        private Builder() {

        }

        public Builder body(final BindingOperationInputBody body) {
            this.body = body;
            return this;
        }

        public BindingOperationInput build() {
            return new BindingOperationInput(this);
        }
    }
}

