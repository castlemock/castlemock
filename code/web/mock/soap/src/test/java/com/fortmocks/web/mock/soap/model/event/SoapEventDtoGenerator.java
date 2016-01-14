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

package com.fortmocks.web.mock.soap.model.event;

import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.event.dto.SoapRequestDto;
import com.fortmocks.core.mock.soap.model.event.dto.SoapResponseDto;
import com.fortmocks.core.mock.soap.model.project.domain.SoapOperationMethod;
import com.fortmocks.core.mock.soap.model.project.domain.SoapOperationType;

import java.util.Date;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapEventDtoGenerator {

    public static SoapEventDto generateSoapProjectDto(){
        final SoapEventDto soapEventDto = new SoapEventDto();
        soapEventDto.setId("SOAP EVENT");
        soapEventDto.setStartDate(new Date());
        soapEventDto.setEndDate(new Date());
        soapEventDto.setSoapOperationId("OperationId");

        SoapRequestDto soapRequestDto = new SoapRequestDto();
        soapRequestDto.setBody("Soap request body");
        soapRequestDto.setContentType("application/json");
        soapRequestDto.setSoapOperationName("ServiceName");
        soapRequestDto.setSoapOperationMethod(SoapOperationMethod.POST);
        soapRequestDto.setSoapOperationType(SoapOperationType.SOAP11);

        SoapResponseDto soapResponseDto = new SoapResponseDto();
        soapResponseDto.setHttpStatusCode(200);
        soapResponseDto.setBody("Soap response body");
        soapResponseDto.setMockResponseName("MockResponseName");

        soapEventDto.setSoapRequest(soapRequestDto);
        soapEventDto.setSoapResponse(soapResponseDto);
        return soapEventDto;
    }
}
