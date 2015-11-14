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

package com.fortmocks.core.mock.soap.model.project.dto;

import com.fortmocks.core.mock.soap.model.project.domain.SoapMockResponseStatus;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapMockResponseDtoGenerator {

    public static SoapMockResponseDto generateSoapMockResponseDto(){
        final SoapMockResponseDto soapMockResponseDto = new SoapMockResponseDto();
        soapMockResponseDto.setName("Soap mock response name");
        soapMockResponseDto.setBody("Soap mock response body");
        soapMockResponseDto.setId(1L);
        soapMockResponseDto.setSoapMockResponseStatus(SoapMockResponseStatus.ENABLED);
        return soapMockResponseDto;
    }
}
