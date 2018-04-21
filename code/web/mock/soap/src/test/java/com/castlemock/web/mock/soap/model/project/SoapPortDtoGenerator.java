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

package com.castlemock.web.mock.soap.model.project;

import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.dto.SoapOperationDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapPortDto;

import java.util.ArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapPortDtoGenerator {

    public static SoapPortDto generateSoapPortDto(){
        final SoapPortDto soapPortDto = new SoapPortDto();
        soapPortDto.setId("SOAP PORT");
        soapPortDto.setName("Soap port name");
        soapPortDto.setUri("UrlPath");
        soapPortDto.setOperations(new ArrayList<SoapOperationDto>());
        return soapPortDto;
    }

    public static SoapPort generateSoapPort(){
        final SoapPort soapPort = new SoapPort();
        soapPort.setId("SOAP PORT");
        soapPort.setName("Soap port name");
        soapPort.setUri("UrlPath");
        return soapPort;
    }
}
