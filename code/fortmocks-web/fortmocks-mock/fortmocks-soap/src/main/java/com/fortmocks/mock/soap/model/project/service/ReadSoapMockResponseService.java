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

package com.fortmocks.mock.soap.model.project.service;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.mock.soap.model.project.service.message.input.ReadSoapMockResponseInput;
import com.fortmocks.mock.soap.model.project.service.message.output.ReadSoapMockResponseOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapMockResponseService extends AbstractSoapProjectProcessor implements Service<ReadSoapMockResponseInput, ReadSoapMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadSoapMockResponseOutput> process(final Task<ReadSoapMockResponseInput> task) {
        final ReadSoapMockResponseInput input = task.getInput();
        final SoapMockResponse soapMockResponse = findSoapMockResponseType(input.getSoapProjectId(), input.getSoapPortId(), input.getSoapOperationId(), input.getSoapMockResponseId());
        final SoapMockResponseDto soapMockResponseDto = soapMockResponse != null ? mapper.map(soapMockResponse, SoapMockResponseDto.class) : null;
        return createResult(new ReadSoapMockResponseOutput(soapMockResponseDto));
    }
}
