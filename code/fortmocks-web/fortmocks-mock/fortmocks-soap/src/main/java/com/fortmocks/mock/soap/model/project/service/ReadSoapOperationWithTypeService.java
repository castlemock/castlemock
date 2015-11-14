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
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.mock.soap.model.project.service.message.input.ReadSoapOperationWithTypeInput;
import com.fortmocks.mock.soap.model.project.service.message.output.ReadSoapOperationWithTypeOutput;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapOperationWithTypeService extends AbstractSoapProjectProcessor implements Service<ReadSoapOperationWithTypeInput, ReadSoapOperationWithTypeOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadSoapOperationWithTypeOutput> process(final Task<ReadSoapOperationWithTypeInput> task) {
        final ReadSoapOperationWithTypeInput input = task.getInput();
        final List<SoapOperation> soapOperations = findSoapOperationTypeWithSoapProjectId(input.getSoapProjectId());
        SoapOperationDto soapOperationDto = null;
        for(SoapOperation soapOperation : soapOperations){
            if(soapOperation.getUri().equals(input.getUri()) && soapOperation.getSoapOperationMethod().equals(input.getSoapOperationMethod()) && soapOperation.getSoapOperationType().equals(input.getType()) && soapOperation.getName().equalsIgnoreCase(input.getName())){
                soapOperationDto = mapper.map(soapOperation, SoapOperationDto.class);
            }
        }
        return createResult(new ReadSoapOperationWithTypeOutput(soapOperationDto));
    }
}
