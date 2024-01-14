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

package com.castlemock.service.mock.rest.project.output;

import com.castlemock.model.core.Output;
import com.castlemock.model.mock.rest.domain.RestApplication;

import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class ReadRestApplicationOutput implements Output{

    private final RestApplication restApplication;

    private ReadRestApplicationOutput(final RestApplication restApplication) {
        this.restApplication = restApplication;
    }

    public Optional<RestApplication> getRestApplication() {
        return Optional.ofNullable(restApplication);
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private RestApplication restApplication;

        public Builder restApplication(final RestApplication restApplication){
            this.restApplication = restApplication;
            return this;
        }

        public ReadRestApplicationOutput build(){
            return new ReadRestApplicationOutput(this.restApplication);
        }

    }

}
