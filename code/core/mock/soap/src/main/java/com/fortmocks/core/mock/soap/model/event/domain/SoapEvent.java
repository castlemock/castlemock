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

package com.fortmocks.core.mock.soap.model.event.domain;


import com.fortmocks.core.basis.model.event.domain.Event;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class SoapEvent extends Event {

    private SoapRequest soapRequest;
    private SoapResponse soapResponse;
    private String soapProjectId;
    private String soapPortId;
    private String soapOperationId;


    @XmlElement
    public SoapRequest getSoapRequest() {
        return soapRequest;
    }

    public void setSoapRequest(SoapRequest soapRequest) {
        this.soapRequest = soapRequest;
    }

    @XmlElement
    public SoapResponse getSoapResponse() {
        return soapResponse;
    }

    public void setSoapResponse(SoapResponse soapResponse) {
        this.soapResponse = soapResponse;
    }

    @XmlElement
    public String getSoapProjectId() {
        return soapProjectId;
    }

    public void setSoapProjectId(String soapProjectId) {
        this.soapProjectId = soapProjectId;
    }

    @XmlElement
    public String getSoapPortId() {
        return soapPortId;
    }

    public void setSoapPortId(String soapPortId) {
        this.soapPortId = soapPortId;
    }

    @XmlElement
    public String getSoapOperationId() {
        return soapOperationId;
    }

    public void setSoapOperationId(String soapOperationId) {
        this.soapOperationId = soapOperationId;
    }


}
