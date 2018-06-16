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

package com.castlemock.web.mock.soap.service.project;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.domain.SoapMockResponse;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.service.message.input.ReadSoapOperationInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.ReadSoapOperationOutput;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapOperationService extends AbstractSoapProjectService implements Service<ReadSoapOperationInput, ReadSoapOperationOutput> {

    private static final Logger LOGGER = Logger.getLogger(ReadSoapOperationService.class);

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ReadSoapOperationOutput> process(final ServiceTask<ReadSoapOperationInput> serviceTask) {
        final ReadSoapOperationInput input = serviceTask.getInput();
        final SoapOperation soapOperation = this.operationRepository.findOne(input.getSoapOperationId());
        final List<SoapMockResponse> mockResponses = this.mockResponseRepository.findWithOperationId(input.getSoapOperationId());
        soapOperation.setMockResponses(mockResponses);

        if(soapOperation.getDefaultXPathMockResponseId() != null){
            // Iterate through all the mocked responses to identify
            // which has been set to be the default XPath mock response.
            boolean defaultXpathMockResponseFound = false;
            for(SoapMockResponse mockResponse : mockResponses){
                if(mockResponse.getId().equals(soapOperation.getDefaultXPathMockResponseId())){
                    soapOperation.setDefaultXPathResponseName(mockResponse.getName());
                    defaultXpathMockResponseFound = true;
                    break;
                }
            }

            if(!defaultXpathMockResponseFound){
                // Unable to find the default XPath mock response.
                // Log only an error message for now.
                LOGGER.error("Unable to find the default XPath mock response with the following id: " +
                        soapOperation.getDefaultXPathMockResponseId());
            }
        }



        return createServiceResult(new ReadSoapOperationOutput(soapOperation));
    }
}
