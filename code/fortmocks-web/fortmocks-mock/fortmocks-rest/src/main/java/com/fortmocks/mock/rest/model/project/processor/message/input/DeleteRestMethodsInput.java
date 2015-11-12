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

package com.fortmocks.mock.rest.model.project.processor.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.rest.model.project.dto.RestMethodDto;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteRestMethodsInput implements Input{

    @NotNull
    private Long restProjectId;
    @NotNull
    private Long restApplicationId;
    @NotNull
    private Long restResourceId;
    @NotNull
    private List<RestMethodDto> restMethods;

    public DeleteRestMethodsInput(Long restProjectId, Long restApplicationId, Long restResourceId, List<RestMethodDto> restMethods) {
        this.restProjectId = restProjectId;
        this.restApplicationId = restApplicationId;
        this.restResourceId = restResourceId;
        this.restMethods = restMethods;
    }

    public Long getRestProjectId() {
        return restProjectId;
    }

    public void setRestProjectId(Long restProjectId) {
        this.restProjectId = restProjectId;
    }

    public Long getRestApplicationId() {
        return restApplicationId;
    }

    public void setRestApplicationId(Long restApplicationId) {
        this.restApplicationId = restApplicationId;
    }

    public Long getRestResourceId() {
        return restResourceId;
    }

    public void setRestResourceId(Long restResourceId) {
        this.restResourceId = restResourceId;
    }

    public List<RestMethodDto> getRestMethods() {
        return restMethods;
    }

    public void setRestMethods(List<RestMethodDto> restMethods) {
        this.restMethods = restMethods;
    }
}
