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

package com.fortmocks.mock.rest.model.project.service.message.output;

import com.fortmocks.core.model.Output;
import com.fortmocks.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.core.model.validation.NotNull;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestResourceOutput implements Output {

    @NotNull
    private RestResourceDto createdRestResource;

    public CreateRestResourceOutput(RestResourceDto createdRestResource) {
        this.createdRestResource = createdRestResource;
    }

    public RestResourceDto getCreatedRestResource() {
        return createdRestResource;
    }

    public void setCreatedRestResource(RestResourceDto createdRestResource) {
        this.createdRestResource = createdRestResource;
    }
}
