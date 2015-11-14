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

package com.fortmocks.web.mock.soap.web.mvc.command.mockresponse;


import com.fortmocks.core.mock.soap.model.project.dto.SoapMockResponseDto;

import java.util.List;

/**
 * The DeleteMockResponsesCommand is a command class and is used to identify which mock responses
 * will be deleted from the server.
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteSoapMockResponsesCommand {

    private List<SoapMockResponseDto> soapMockResponses;

    /**
     * A list of mock responses that will be deleted from the database
     * @return A list of mock responses that will be deleted
     */
    public List<SoapMockResponseDto> getSoapMockResponses() {
        return soapMockResponses;
    }

    /**
     * Sets a new value to the mock responses variable
     * @param soapMockResponses The new value for mock responses
     */
    public void setSoapMockResponses(List<SoapMockResponseDto> soapMockResponses) {
        this.soapMockResponses = soapMockResponses;
    }
}

