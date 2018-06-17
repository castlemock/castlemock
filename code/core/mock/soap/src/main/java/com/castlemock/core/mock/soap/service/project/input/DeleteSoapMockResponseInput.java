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

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class DeleteSoapMockResponseInput implements Input{

    @NotNull
    private final String soapProjectId;
    @NotNull
    private final String soapPortId;
    @NotNull
    private final String soapOperationId;
    @NotNull
    private final String soapMockResponseId;

    public DeleteSoapMockResponseInput(String soapProjectId, String soapPortId, String soapOperationId, String soapMockResponseId) {
        this.soapProjectId = soapProjectId;
        this.soapPortId = soapPortId;
        this.soapOperationId = soapOperationId;
        this.soapMockResponseId = soapMockResponseId;
    }

    public String getSoapProjectId() {
        return soapProjectId;
    }

    public String getSoapPortId() {
        return soapPortId;
    }

    public String getSoapOperationId() {
        return soapOperationId;
    }

    public String getSoapMockResponseId() {
        return soapMockResponseId;
    }

}
