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

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class CreateSoapPortsInput implements Input {

    private final String projectId;

    private final Boolean generateResponse;

    private final Boolean includeImports;

    private final List<File> files;
    private final String location;


    private CreateSoapPortsInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.includeImports = builder.includeImports;
        this.generateResponse = builder.generateResponse;
        this.files = builder.files;
        this.location = builder.location;
    }

    public String getProjectId() {
        return projectId;
    }

    public Optional<Boolean> getGenerateResponse() {
        return Optional.ofNullable(generateResponse);
    }

    public List<File> getFiles() {
        return Optional.ofNullable(files)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    public Optional<String> getLocation() {
        return Optional.ofNullable(location);
    }

    public Optional<Boolean> getIncludeImports() {
        return Optional.ofNullable(includeImports);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreateSoapPortsInput that = (CreateSoapPortsInput) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(generateResponse, that.generateResponse) &&
                Objects.equals(includeImports, that.includeImports) && Objects.equals(files, that.files) &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, generateResponse, includeImports, files, location);
    }

    @Override
    public String toString() {
        return "CreateSoapPortsInput{" +
                "projectId='" + projectId + '\'' +
                ", generateResponse=" + generateResponse +
                ", includeImports=" + includeImports +
                ", files=" + files +
                ", location='" + location + '\'' +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String projectId;
        private boolean includeImports;
        private Boolean generateResponse;
        private List<File> files;
        private String location;

        private Builder(){

        }

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder includeImports(final boolean includeImports){
            this.includeImports = includeImports;
            return this;
        }

        public Builder generateResponse(final Boolean generateResponse){
            this.generateResponse = generateResponse;
            return this;
        }

        public Builder files(final List<File> files){
            this.files = files;
            return this;
        }

        public Builder location(final String location){
            this.location = location;
            return this;
        }

        public CreateSoapPortsInput build(){
            return new CreateSoapPortsInput(this);
        }

    }
}
