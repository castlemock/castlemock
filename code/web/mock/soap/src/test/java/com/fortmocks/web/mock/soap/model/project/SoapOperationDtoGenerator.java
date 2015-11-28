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

package com.fortmocks.web.mock.soap.model.project;

import com.fortmocks.core.mock.soap.model.project.domain.SoapOperationMethod;
import com.fortmocks.core.mock.soap.model.project.domain.SoapOperationStatus;
import com.fortmocks.core.mock.soap.model.project.domain.SoapOperationType;
import com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapOperationDtoGenerator {

    public static SoapOperationDto generateSoapOperationDto(){
        final SoapOperationDto soapOperationDto = new SoapOperationDto();
        soapOperationDto.setId(1L);
        soapOperationDto.setName("Soap operation name");
        soapOperationDto.setCurrentResponseSequenceIndex(1);
        soapOperationDto.setDefaultBody("Default body");
        soapOperationDto.setForwardedEndpoint("Forwarded event");
        soapOperationDto.setInvokeAddress("Invoke address");
        soapOperationDto.setOriginalEndpoint("Original endpoint");
        soapOperationDto.setSoapOperationMethod(SoapOperationMethod.POST);
        soapOperationDto.setSoapOperationStatus(SoapOperationStatus.MOCKED);
        soapOperationDto.setSoapOperationType(SoapOperationType.SOAP11);
        soapOperationDto.setUri("Soap uri");
        return soapOperationDto;
    }
}
