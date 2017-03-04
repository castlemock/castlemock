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

package com.castlemock.core.mock.rest.model.project.service.message.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.rest.model.RestDefinitionType;

import java.io.File;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ImportRestDefinitionInput implements Input {

    @NotNull
    private String restProjectId;
    @NotNull
    private boolean generateResponse;
    @NotNull
    private RestDefinitionType definitionType;

    private List<File> files;
    private String location;

    public ImportRestDefinitionInput(final String restProjectId, final List<File> files, String location,
                                     final boolean generateResponse, final RestDefinitionType definitionType) {
        this.restProjectId = restProjectId;
        this.files = files;
        this.location = location;
        this.generateResponse = generateResponse;
        this.definitionType = definitionType;
    }

    public String getRestProjectId() {
        return restProjectId;
    }

    public void setRestProjectId(String restProjectId) {
        this.restProjectId = restProjectId;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isGenerateResponse() {
        return generateResponse;
    }

    public void setGenerateResponse(boolean generateResponse) {
        this.generateResponse = generateResponse;
    }

    public RestDefinitionType getDefinitionType() {
        return definitionType;
    }

    public void setDefinitionType(RestDefinitionType definitionType) {
        this.definitionType = definitionType;
    }
}
