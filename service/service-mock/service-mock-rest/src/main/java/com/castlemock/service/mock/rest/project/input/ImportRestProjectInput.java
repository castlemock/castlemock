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
import com.castlemock.model.core.validation.NotNull;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class ImportRestProjectInput implements Input {

    @NotNull
    private final String projectRaw;

    private ImportRestProjectInput(final Builder builder) {
        this.projectRaw = Objects.requireNonNull(builder.projectRaw, "projectRaw");
    }

    public String getProjectRaw() {
        return projectRaw;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private String projectRaw;

        public Builder projectRaw(final String projectRaw){
            this.projectRaw = projectRaw;
            return this;
        }

        public ImportRestProjectInput build(){
            return new ImportRestProjectInput(this);
        }

    }

}
