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

package com.castlemock.service.mock.soap.project.input;

import com.castlemock.model.core.Input;
import com.castlemock.model.mock.soap.domain.SoapPort;

import java.util.List;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class DeleteSoapPortsInput implements Input{

    private final String projectId;
    private final List<SoapPort> ports;

    private DeleteSoapPortsInput(final Builder builder) {
        this.projectId = Objects.requireNonNull(builder.projectId);
        this.ports = Objects.requireNonNull(builder.ports);
    }

    public String getProjectId() {
        return projectId;
    }

    public List<SoapPort> getPorts() {
        return ports;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String projectId;
        private List<SoapPort> ports;

        private Builder(){

        }

        public Builder projectId(final String projectId){
            this.projectId = projectId;
            return this;
        }

        public Builder ports(final List<SoapPort> ports){
            this.ports = ports;
            return this;
        }

        public DeleteSoapPortsInput build(){
            return new DeleteSoapPortsInput(this);
        }
    }
}
