/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.web.mock.soap.model;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.55
 */
public class UpdateSoapPortRequest {

    private String uri;

    public UpdateSoapPortRequest(){

    }

    private UpdateSoapPortRequest(final Builder builder){
        this.uri = Objects.requireNonNull(builder.uri);
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String uri;

        private Builder() {
        }

        public Builder uri(final String uri) {
            this.uri = uri;
            return this;
        }

        public UpdateSoapPortRequest build() {
            return new UpdateSoapPortRequest(this);
        }
    }
}
