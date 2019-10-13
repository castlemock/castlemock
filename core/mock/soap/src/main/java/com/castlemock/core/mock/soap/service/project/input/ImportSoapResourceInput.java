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

package com.castlemock.core.mock.soap.service.project.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.soap.model.project.domain.SoapResource;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public final class ImportSoapResourceInput implements Input {

    private final String projectId;
    @NotNull
    private final SoapResource resource;
    @NotNull
    private final String raw;

    private ImportSoapResourceInput(final Builder builder) {
        this.projectId = builder.projectId;
        this.resource = Objects.requireNonNull(builder.resource);
        this.raw = Objects.requireNonNull(builder.raw);
    }

    public Optional<String> getProjectId() {
        return Optional.ofNullable(projectId);
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
