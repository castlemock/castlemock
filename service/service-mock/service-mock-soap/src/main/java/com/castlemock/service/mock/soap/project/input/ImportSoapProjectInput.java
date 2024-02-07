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

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class ImportSoapProjectInput implements Input {

    private final String projectRaw;

    private ImportSoapProjectInput(final Builder builder) {
        this.projectRaw = Objects.requireNonNull(builder.projectRaw, "projectRaw");
    }

    public String getProjectRaw() {
        return projectRaw;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ImportSoapProjectInput that = (ImportSoapProjectInput) o;
        return Objects.equals(projectRaw, that.projectRaw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectRaw);
    }

    @Override
    public String toString() {
        return "ImportSoapProjectInput{" +
                "projectRaw='" + projectRaw + '\'' +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String projectRaw;

        private Builder(){

        }

        public Builder projectRaw(final String projectRaw){
            this.projectRaw = projectRaw;
            return this;
        }

        public ImportSoapProjectInput build(){
            return new ImportSoapProjectInput(this);
        }
    }

}
