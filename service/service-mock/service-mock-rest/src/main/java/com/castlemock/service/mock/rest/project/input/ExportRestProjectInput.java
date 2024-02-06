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

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class ExportRestProjectInput implements Input {

    private final String projectId;

    private ExportRestProjectInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
    }

    public String getProjectId() {
        return projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExportRestProjectInput that = (ExportRestProjectInput) o;
        return Objects.equals(projectId, that.projectId);
    }

    @Override
    public String toString() {
        return "ExportRestProjectInput{" +
                "projectId='" + projectId + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId);
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String projectId;

        private Builder() {
        }

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public ExportRestProjectInput build(){
            return new ExportRestProjectInput(this);
        }

    }

}
