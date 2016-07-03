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

package com.castlemock.core.mock.soap.model.project.service.message.input;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.soap.model.project.domain.SoapVersion;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class IdentifySoapOperationInput implements Input {

    @NotNull
    private String soapProjectId;
    @NotNull
    private String soapOperationIdentifier;
    @NotNull
    private String uri;
    @NotNull
    private HttpMethod httpMethod;
    @NotNull
    private SoapVersion type;

    public IdentifySoapOperationInput(String soapProjectId, String soapOperationIdentifier, String uri, HttpMethod httpMethod, SoapVersion type) {
        this.soapProjectId = soapProjectId;
        this.soapOperationIdentifier = soapOperationIdentifier;
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.type = type;
    }

    public String getSoapProjectId() {
        return soapProjectId;
    }

    public void setSoapProjectId(String soapProjectId) {
        this.soapProjectId = soapProjectId;
    }

    public String getSoapOperationIdentifier() {
        return soapOperationIdentifier;
    }

    public void setSoapOperationIdentifier(String soapOperationIdentifier) {
        this.soapOperationIdentifier = soapOperationIdentifier;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public SoapVersion getType() {
        return type;
    }

    public void setType(SoapVersion type) {
        this.type = type;
    }
}
