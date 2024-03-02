/*
 * Copyright 2017 Karl Dahlgren
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

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class ReadSoapResourceInput implements Input {

    private final String projectId;
    private final String resourceId;

    public ReadSoapResourceInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId, "projectId");
        this.resourceId = Objects.requireNonNull(builder.resourceId, "resourceId");
    }

    public String getProjectId() {
        return projectId;
    }

    public String getResourceId() {
        return resourceId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ReadSoapResourceInput that = (ReadSoapResourceInput) o;
        return Objects.equals(projectId, that.projectId) && Objects.equals(resourceId, that.resourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, resourceId);
    }

    @Override
    public String toString() {
        return "ReadSoapResourceInput{" +
                "projectId='" + projectId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String projectId;
        private String resourceId;

        private Builder(){

        }

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder resourceId(final String resourceId){
            this.resourceId = resourceId;
            return this;
        }

        public ReadSoapResourceInput build(){
            return new ReadSoapResourceInput(this);
        }
    }
}
