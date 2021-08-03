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

package com.castlemock.service.mock.rest.project.input;

import com.castlemock.model.core.Input;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.core.http.HttpParameter;
import com.castlemock.model.core.validation.NotNull;

import java.util.*;

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
    @NotNull
    private final Map<String, Set<String>> httpParameters;

    private IdentifyRestMethodInput(final Builder builder) {
        this.restProjectId = Objects.requireNonNull(builder.restProjectId);
        this.restApplicationId = Objects.requireNonNull(builder.restApplicationId);
        this.restResourceUri = Objects.requireNonNull(builder.restResourceUri);
        this.httpMethod = Objects.requireNonNull(builder.httpMethod);
        this.httpParameters = builder.httpParameters != null ? convert(builder.httpParameters) : Collections.emptyMap();
    }

    private static Map<String, Set<String>> convert(List<HttpParameter> httpParameters) {
        Map<String, Set<String>> out = new HashMap<>();
        for(HttpParameter httpParam:httpParameters){
            if(!out.containsKey(httpParam.getName())){
                out.put(httpParam.getName(), new HashSet<>());
            }

            out.get(httpParam.getName()).add(httpParam.getValue());
        }
        return out;
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

    public Map<String, Set<String>> getHttpParameters(){ return httpParameters; }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String restProjectId;
        private String restApplicationId;
        private String restResourceUri;
        private HttpMethod httpMethod;
        private List<HttpParameter> httpParameters;

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

        public Builder httpParameters(final List<HttpParameter>  httpParameters){
            this.httpParameters = httpParameters;
            return this;
        }

        public IdentifyRestMethodInput build(){
            return new IdentifyRestMethodInput(this);
        }

    }
}
