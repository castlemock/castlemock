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

package com.castlemock.core.mock.rest.service.project.output;

import com.castlemock.core.basis.model.Output;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class ReadRestResourceOutput implements Output{

    @NotNull
    private final RestResource restResource;

    private ReadRestResourceOutput(RestResource restResource) {
        this.restResource = restResource;
    }

    public RestResource getRestResource() {
        return restResource;
    }


    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private RestResource restResource;

        public Builder restResource(final RestResource restResource){
            this.restResource = restResource;
            return this;
        }

        public ReadRestResourceOutput build(){
            return new ReadRestResourceOutput(this.restResource);
        }

    }
}
