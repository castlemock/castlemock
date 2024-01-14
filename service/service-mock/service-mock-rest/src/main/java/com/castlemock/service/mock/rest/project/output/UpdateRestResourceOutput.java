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
import com.castlemock.model.core.validation.NotNull;
import com.castlemock.model.mock.rest.domain.RestResource;

import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateRestResourceOutput implements Output {

    @NotNull
    private final RestResource updatedRestResource;

    private UpdateRestResourceOutput(final RestResource updatedRestResource) {
        this.updatedRestResource = updatedRestResource;
    }

    public Optional<RestResource> getUpdatedRestResource() {
        return Optional.ofNullable(updatedRestResource);
    }


    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private RestResource updatedRestResource;

        public Builder updatedRestResource(final RestResource updatedRestResource){
            this.updatedRestResource = updatedRestResource;
            return this;
        }

        public UpdateRestResourceOutput build(){
            return new UpdateRestResourceOutput(this.updatedRestResource);
        }

    }
}
