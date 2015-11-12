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

package com.fortmocks.mock.soap.model.event.dto;

import com.fortmocks.mock.soap.model.project.domain.SoapOperationMethod;
import com.fortmocks.mock.soap.model.project.domain.SoapOperationType;
import org.dozer.Mapping;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapRequestDto {

    @Mapping("body")
    private String body;

    @Mapping("contextType")
    private String contextType;

    @Mapping("uri")
    private String uri;

    @Mapping("soapOperationMethod")
    private SoapOperationMethod soapOperationMethod;

    @Mapping("serviceName")
    private String serviceName;

    @Mapping("type")
    private SoapOperationType type;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContextType() {
        return contextType;
    }

    public void setContextType(String contextType) {
        this.contextType = contextType;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public SoapOperationType getType() {
        return type;
    }

    public void setType(SoapOperationType type) {
        this.type = type;
    }
}
