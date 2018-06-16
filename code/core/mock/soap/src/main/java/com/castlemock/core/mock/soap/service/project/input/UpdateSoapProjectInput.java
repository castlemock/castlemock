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
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateSoapProjectInput implements Input {

    @NotNull
    private String soapProjectId;
    @NotNull
    private SoapProject soapProject;

    public UpdateSoapProjectInput(String soapProjectId, SoapProject soapProject) {
        this.soapProjectId = soapProjectId;
        this.soapProject = soapProject;
    }

    public String getSoapProjectId() {
        return soapProjectId;
    }

    public void setSoapProjectId(String soapProjectId) {
        this.soapProjectId = soapProjectId;
    }

    public SoapProject getSoapProject() {
        return soapProject;
    }

    public void setSoapProject(SoapProject soapProject) {
        this.soapProject = soapProject;
    }
}
