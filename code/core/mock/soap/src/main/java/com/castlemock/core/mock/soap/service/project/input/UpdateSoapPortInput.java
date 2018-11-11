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
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class UpdateSoapPortInput implements Input {

    @NotNull
    private final String projectId;
    @NotNull
    private final String portId;
    @NotNull
    private final SoapPort soapPort;

    public UpdateSoapPortInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.portId = Objects.requireNonNull(builder.portId);
        this.soapPort = Objects.requireNonNull(builder.port);
    }

    public String getProjectId() {
        return projectId;
    }

    public String getPortId() {
        return portId;
    }

    public SoapPort getSoapPort() {
        return soapPort;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String projectId;
        private String portId;
        private SoapPort port;

        private Builder(){

        }

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder portId(final String portId){
            this.portId = portId;
            return this;
        }

        public Builder port(final SoapPort port){
            this.port = port;
            return this;
        }

        public UpdateSoapPortInput build(){
            return new UpdateSoapPortInput(this);
        }
    }
}
