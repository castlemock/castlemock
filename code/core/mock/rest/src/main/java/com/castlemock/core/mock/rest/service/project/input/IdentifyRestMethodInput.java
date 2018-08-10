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

package com.castlemock.core.mock.rest.service.project.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.model.validation.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class IdentifyRestMethodInput implements Input {

    @NotNull
    private final String restProjectId;
    @NotNull
    private final String restApplicationId;
    @NotNull
    private final String restResourceUri;
    @NotNull
    private final HttpMethod httpMethod;

    private IdentifyRestMethodInput(final String restProjectId,
                                   final String restApplicationId,
                                   final String restResourceUri,
                                   final HttpMethod httpMethod) {
        this.restProjectId = restProjectId;
        this.restApplicationId = restApplicationId;
        this.restResourceUri = restResourceUri;
        this.httpMethod = httpMethod;
    }

    public String getRestProjectId() {
        return restProjectId;
    }

    public String getRestApplicationId() {
        return restApplicationId;
    }

    public String getRestResourceUri() {
        return restResourceUri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String restProjectId;
        private String restApplicationId;
        private String restResourceUri;
        private HttpMethod httpMethod;

        public Builder restProjectId(final String restProjectId){
            this.restProjectId = restProjectId;
            return this;
        }

        public Builder restApplicationId(final String restApplicationId){
            this.restApplicationId = restApplicationId;
            return this;
        }

        public Builder restResourceUri(final String restResourceUri){
            this.restResourceUri = restResourceUri;
            return this;
        }

        public Builder httpMethod(final HttpMethod httpMethod){
            this.httpMethod = httpMethod;
            return this;
        }

        public IdentifyRestMethodInput build(){
            return new IdentifyRestMethodInput(this.restProjectId, this.restApplicationId,
                    this.restResourceUri, this.httpMethod);
        }

    }
}
