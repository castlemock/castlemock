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

package com.castlemock.core.mock.soap.service.project.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.validation.NotNull;

import java.io.File;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class CreateSoapPortsInput implements Input {

    @NotNull
    private final String soapProjectId;
    @NotNull
    private final boolean generateResponse;
    @NotNull
    private final List<File> files;

    public CreateSoapPortsInput(String soapProjectId, boolean generateResponse, List<File> files) {
        this.soapProjectId = soapProjectId;
        this.generateResponse = generateResponse;
        this.files = files;
    }

    public String getSoapProjectId() {
        return soapProjectId;
    }

    public boolean isGenerateResponse() {
        return generateResponse;
    }

    public List<File> getFiles() {
        return files;
    }

}
