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
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationIdentifier;
import com.castlemock.core.mock.soap.model.project.domain.SoapVersion;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class IdentifySoapOperationInput implements Input {

    @NotNull
    private final String soapProjectId;
    @NotNull
    private final SoapOperationIdentifier soapOperationIdentifier;
    @NotNull
    private final String uri;
    @NotNull
    private final HttpMethod httpMethod;
    @NotNull
    private final SoapVersion type;

    public IdentifySoapOperationInput(final String soapProjectId,
                                      final SoapOperationIdentifier soapOperationIdentifier,
                                      final String uri,
                                      final HttpMethod httpMethod,
                                      final SoapVersion type) {
        this.soapProjectId = soapProjectId;
        this.soapOperationIdentifier = soapOperationIdentifier;
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.type = type;
    }

    public String getSoapProjectId() {
        return soapProjectId;
    }

    public SoapOperationIdentifier getSoapOperationIdentifier() {
        return soapOperationIdentifier;
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public SoapVersion getType() {
        return type;
    }

}
