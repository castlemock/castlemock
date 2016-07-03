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
import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.soap.model.project.domain.SoapVersion;
import com.castlemock.core.mock.soap.model.project.dto.SoapOperationDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapPortDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapProjectDto;
import com.castlemock.core.mock.soap.model.project.service.message.input.IdentifySoapOperationInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.IdentifySoapOperationOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class IdentifySoapOperationService extends AbstractSoapProjectService implements Service<IdentifySoapOperationInput, IdentifySoapOperationOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<IdentifySoapOperationOutput> process(final ServiceTask<IdentifySoapOperationInput> serviceTask) {
        final IdentifySoapOperationInput input = serviceTask.getInput();
        final SoapProjectDto project = repository.findOne(input.getSoapProjectId());
        SoapPortDto soapPort = null;
        SoapOperationDto soapOperationDto = null;
        for(SoapPortDto tempSoapPort : project.getPorts()){
            if(tempSoapPort.getUri().equals(input.getUri())){
                soapPort = tempSoapPort;
                soapOperationDto = findSoapOperation(tempSoapPort, input.getHttpMethod(), input.getType(), input.getSoapOperationIdentifier());
                break;
            }
        }
        if(soapOperationDto == null){
            throw new IllegalArgumentException("Unable to identify SOAP operation: " + input.getUri());
        }

        return createServiceResult(new IdentifySoapOperationOutput(project.getId(), soapPort.getId(), soapOperationDto.getId(), soapOperationDto));
    }

    /**
     * The method finds a specific SOAP operation in a SOAP port and with specific attributes
     * @param soapPort The SOAP port that is responsible for the SOAP operation
     * @param httpMethod The SOAP operation method
     * @param soapOperationVersion The SOAP operation type
     * @param soapOperationInputMessageName The SOAP operation input message name. The identifier for the SOAP operation
     * @return The SOAP operation that matches the search criteria. Null otherwise
     */
    private SoapOperationDto findSoapOperation(SoapPortDto soapPort, HttpMethod httpMethod, SoapVersion soapOperationVersion, String soapOperationInputMessageName){
        for(SoapOperationDto soapOperation : soapPort.getOperations()){
            if(soapOperation.getHttpMethod().equals(httpMethod) && soapOperation.getSoapVersion().equals(soapOperationVersion) && soapOperation.getIdentifier().equalsIgnoreCase(soapOperationInputMessageName)){
                return soapOperation;
            }
        }
        return null;
    }
}
