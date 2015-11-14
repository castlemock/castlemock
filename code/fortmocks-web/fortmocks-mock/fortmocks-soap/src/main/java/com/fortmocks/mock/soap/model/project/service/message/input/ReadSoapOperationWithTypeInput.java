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

package com.fortmocks.mock.soap.model.project.service.message.input;

import com.fortmocks.core.model.Input;
import com.fortmocks.core.model.validation.NotNull;
import com.fortmocks.mock.soap.model.project.domain.SoapOperationMethod;
import com.fortmocks.mock.soap.model.project.domain.SoapOperationType;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadSoapOperationWithTypeInput implements Input {

    @NotNull
    private Long soapProjectId;
    @NotNull
    private String name;
    @NotNull
    private String uri;
    @NotNull
    private SoapOperationMethod soapOperationMethod;
    @NotNull
    private SoapOperationType type;

    public ReadSoapOperationWithTypeInput(Long soapProjectId, String name, String uri, SoapOperationMethod soapOperationMethod, SoapOperationType type) {
        this.soapProjectId = soapProjectId;
        this.name = name;
        this.uri = uri;
        this.soapOperationMethod = soapOperationMethod;
        this.type = type;
    }

    public Long getSoapProjectId() {
        return soapProjectId;
    }

    public void setSoapProjectId(Long soapProjectId) {
        this.soapProjectId = soapProjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public SoapOperationMethod getSoapOperationMethod() {
        return soapOperationMethod;
    }

    public void setSoapOperationMethod(SoapOperationMethod soapOperationMethod) {
        this.soapOperationMethod = soapOperationMethod;
    }

    public SoapOperationType getType() {
        return type;
    }

    public void setType(SoapOperationType type) {
        this.type = type;
    }
}
