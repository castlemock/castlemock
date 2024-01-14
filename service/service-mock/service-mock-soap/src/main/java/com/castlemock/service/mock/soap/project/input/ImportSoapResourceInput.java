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

package com.castlemock.service.mock.soap.project.input;

import com.castlemock.model.core.Input;
import com.castlemock.model.mock.soap.domain.SoapResource;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public final class ImportSoapResourceInput implements Input {

    private final String projectId;
    private final SoapResource resource;
    private final String raw;

    private ImportSoapResourceInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.resource = Objects.requireNonNull(builder.resource, "resource");
        this.raw = Objects.requireNonNull(builder.raw, "raw");
    }

    public String getProjectId() {
        return projectId;
    }

    public SoapResource getResource() {
        return resource;
    }

    public String getRaw() {
        return raw;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String projectId;
        private SoapResource resource;
        private String raw;

        private Builder(){

        }

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder resource(final SoapResource resource){
            this.resource = resource;
            return this;
        }

        public Builder raw(final String raw){
            this.raw = raw;
            return this;
        }

        public ImportSoapResourceInput build(){
            return new ImportSoapResourceInput(this);
        }
    }
}
