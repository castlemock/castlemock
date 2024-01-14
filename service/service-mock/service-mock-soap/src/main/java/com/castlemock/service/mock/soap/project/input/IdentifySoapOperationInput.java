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

package com.castlemock.service.mock.soap.project.input;

import com.castlemock.model.core.Input;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.soap.domain.SoapOperationIdentifier;
import com.castlemock.model.mock.soap.domain.SoapVersion;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class IdentifySoapOperationInput implements Input {

    private final String projectId;
    private final SoapOperationIdentifier operationIdentifier;
    private final String uri;
    private final HttpMethod httpMethod;
    private final SoapVersion type;

    private IdentifySoapOperationInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.operationIdentifier = Objects.requireNonNull(builder.operationIdentifier);
        this.uri = Objects.requireNonNull(builder.uri);
        this.httpMethod = Objects.requireNonNull(builder.httpMethod);
        this.type = Objects.requireNonNull(builder.type);
    }

    public String getProjectId() {
        return projectId;
    }

    public SoapOperationIdentifier getOperationIdentifier() {
        return operationIdentifier;
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public SoapVersion getType() {
        return type;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private String projectId;
        private SoapOperationIdentifier operationIdentifier;
        private String uri;
        private HttpMethod httpMethod;
        private SoapVersion type;

        private Builder(){

        }

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder operationIdentifier(final SoapOperationIdentifier operationIdentifier){
            this.operationIdentifier = operationIdentifier;
            return this;
        }

        public Builder uri(final String uri){
            this.uri = uri;
            return this;
        }

        public Builder httpMethod(final HttpMethod httpMethod){
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder type(final SoapVersion type){
            this.type = type;
            return this;
        }

        public IdentifySoapOperationInput build(){
            return new IdentifySoapOperationInput(this);
        }
    }

}
