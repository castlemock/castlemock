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

package com.castlemock.web.mock.soap.model.project.service;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.dto.SoapOperationDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapPortDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapProjectDto;
import com.castlemock.core.mock.soap.model.project.service.message.input.CreateSoapPortsInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.CreateSoapPortsOutput;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateSoapPortsService extends AbstractSoapProjectService implements Service<CreateSoapPortsInput, CreateSoapPortsOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<CreateSoapPortsOutput> process(final ServiceTask<CreateSoapPortsInput> serviceTask) {
        final CreateSoapPortsInput input = serviceTask.getInput();
        final String soapProjectId = input.getSoapProjectId();

        for(SoapPortDto newSoapPort : input.getSoapPorts()){
            SoapPortDto existingSoapPort = repository.findSoapPortWithName(soapProjectId, newSoapPort.getName());

            if(existingSoapPort == null){
                repository.saveSoapPort(soapProjectId, newSoapPort);
                continue;
            }

            for(SoapOperationDto newSoapOperation : newSoapPort.getOperations()){
                SoapOperationDto existingSoapOperation = repository.findSoapOperationWithName(soapProjectId, existingSoapPort.getId(), newSoapOperation.getName());

                if(existingSoapOperation != null){
                    existingSoapOperation.setOriginalEndpoint(newSoapOperation.getOriginalEndpoint());
                    existingSoapOperation.setSoapVersion(newSoapOperation.getSoapVersion());
                    repository.updateSoapOperation(soapProjectId, existingSoapPort.getId(), existingSoapOperation.getId(), existingSoapOperation);
                } else {
                    repository.saveSoapOperation(soapProjectId, existingSoapPort.getId(), newSoapOperation);
                }
            }
        }
        return createServiceResult(new CreateSoapPortsOutput());
    }
}
