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

package com.fortmocks.core.mock.soap.model.event.dto;

import com.fortmocks.core.basis.model.event.dto.EventDto;
import com.fortmocks.core.mock.soap.model.project.domain.SoapOperation;
import org.dozer.Mapping;

import java.util.Date;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapEventDto extends EventDto {

    @Mapping("soapRequest")
    private SoapRequestDto soapRequest;

    @Mapping("soapResponse")
    private SoapResponseDto soapResponse;

    @Mapping("soapProjectId")
    private String soapProjectId;

    @Mapping("soapPortId")
    private String soapPortId;

    @Mapping("soapOperationId")
    private String soapOperationId;

    /**
     * Default constructor for the SOAP event DTO
     */
    public SoapEventDto() {
    }

    /**
     * Default constructor for the SOAP event DTO
     */
    public SoapEventDto(final EventDto eventDto) {
        super(eventDto);
    }

    /**
     * Constructor for the SOAP event DTO
     * @param soapRequest The SOAP request that the event is representing
     * @param soapProjectId The id of the SOAP project that is affected by the provided SOAP request
     * @param soapPortId The id of the SOAP port that is affected by the provided SOAP request
     * @param soapOperationId The id of the SOAP operation that is affected by the provided SOAP request
     * @see SoapOperation
     */
    public SoapEventDto(final String resourceName, final SoapRequestDto soapRequest, final String soapProjectId, final String soapPortId, final String soapOperationId) {
        super(resourceName);
        this.soapRequest = soapRequest;
        this.soapProjectId = soapProjectId;
        this.soapPortId = soapPortId;
        this.soapOperationId = soapOperationId;
    }

    /**
     * The finish method is used to sent the response that was sent back, but was also
     * to set the date/time for when the event ended.
     * @param soapResponse
     */
    public void finish(final SoapResponseDto soapResponse) {
        this.soapResponse = soapResponse;
        setEndDate(new Date());
    }

    /**
     * Returns the SOAP request
     * @return The SOAP request
     */
    public SoapRequestDto getSoapRequest() {
        return soapRequest;
    }

    /**
     * Sets a new value to the SOAP request variable
     * @param soapRequest The new SOAP request variable
     */
    public void setSoapRequest(SoapRequestDto soapRequest) {
        this.soapRequest = soapRequest;
    }

    /**
     * Returns the SOAP response variable
     * @return The SOAP response variable. The SOAP response will be null if the event has not yet finished
     */
    public SoapResponseDto getSoapResponse() {
        return soapResponse;
    }

    /**
     * Set a new value to the Soap response variable
     * @param soapResponse The new SOAP response value
     */
    public void setSoapResponse(SoapResponseDto soapResponse) {
        this.soapResponse = soapResponse;
    }

    /**
     * Returns the SOAP operation id
     * @return The SOAP operation id
     */
    public String getSoapOperationId() {
        return soapOperationId;
    }

    /**
     * Set a new value to the SOAP operation id
     * @param soapOperationId The new value to the SOAP operation id
     */
    public void setSoapOperationId(String soapOperationId) {
        this.soapOperationId = soapOperationId;
    }

    /**
     * The SOAP project id is used to identify the project for which the
     * event spans from
     * @return The id of the project which the event affected
     */
    public String getSoapProjectId() {
        return soapProjectId;
    }

    /**
     * Set a new value to the SOAP project id
     * @param soapProjectId The new SOAP project id
     */
    public void setSoapProjectId(String soapProjectId) {
        this.soapProjectId = soapProjectId;
    }

    /**
     * The SOAP port id is used to identify the port for which the
     * event spans from
     * @return The id of the port which the event affected
     */
    public String getSoapPortId() {
        return soapPortId;
    }

    /**
     * Sets a new value to the SOAP port id
     * @param soapPortId The new SOAP port id
     */
    public void setSoapPortId(String soapPortId) {
        this.soapPortId = soapPortId;
    }
}
