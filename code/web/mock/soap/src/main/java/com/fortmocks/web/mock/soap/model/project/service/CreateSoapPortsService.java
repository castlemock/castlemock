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

package com.fortmocks.web.mock.soap.model.project.service;

import com.fortmocks.core.basis.model.Service;
import com.fortmocks.core.basis.model.Result;
import com.fortmocks.core.basis.model.Task;
import com.fortmocks.core.mock.soap.model.project.domain.SoapProject;
import com.fortmocks.core.mock.soap.model.project.service.message.output.CreateSoapPortsOutput;
import com.fortmocks.core.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.core.mock.soap.model.project.domain.SoapPort;
import com.fortmocks.core.mock.soap.model.project.service.message.input.CreateSoapPortsInput;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateSoapPortsService extends AbstractSoapProjectProcessor implements Service<CreateSoapPortsInput, CreateSoapPortsOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<CreateSoapPortsOutput> process(final Task<CreateSoapPortsInput> task) {
        final CreateSoapPortsInput input = task.getInput();
        final SoapProject soapProject = findType(input.getSoapProjectId());
        final List<SoapPort> soapPortTypes = toDtoList(input.getSoapPorts(), SoapPort.class);

        for(SoapPort newSoapPort : soapPortTypes){
            SoapPort existingSoapPort = findSoapPortWithName(soapProject, newSoapPort.getName());

            if(existingSoapPort == null){
                generateId(newSoapPort);
                soapProject.getSoapPorts().add(newSoapPort);
                continue;
            }

            final LinkedList<SoapOperation> soapOperations = new LinkedList<SoapOperation>();
            for(SoapOperation newSoapOperation : newSoapPort.getSoapOperations()){
                SoapOperation existingSoapOperation = findSoapOperationWithName(existingSoapPort, newSoapOperation.getName());

                if(existingSoapOperation != null){
                    existingSoapOperation.setOriginalEndpoint(newSoapOperation.getOriginalEndpoint());
                    existingSoapOperation.setSoapOperationType(newSoapOperation.getSoapOperationType());
                    soapOperations.add(existingSoapOperation);
                } else {
                    generateId(newSoapOperation);
                    soapOperations.add(newSoapOperation);
                }
            }
            existingSoapPort.setSoapOperations(soapOperations);
        }

        save(soapProject);
        return createResult(new CreateSoapPortsOutput());
    }
}
