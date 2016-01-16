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

import com.fortmocks.core.basis.model.http.HttpMethod;
import com.fortmocks.core.basis.model.Service;
import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.mock.soap.model.project.domain.*;
import com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.core.mock.soap.model.project.service.message.input.IdentifySoapOperationInput;
import com.fortmocks.core.mock.soap.model.project.service.message.output.IdentifySoapOperationOutput;

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
        final SoapProject project= findType(input.getSoapProjectId());
        SoapPort soapPort = null;
        SoapOperationDto soapOperationDto = null;
        for(SoapPort tempSoapPort : project.getPorts()){
            if(tempSoapPort.getUri().equals(input.getUri())){
                soapPort = tempSoapPort;
                soapOperationDto = findSoapOperation(tempSoapPort, input.getHttpMethod(), input.getType(), input.getSoapOperationIdentifier());
                break;
            }
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
    private SoapOperationDto findSoapOperation(SoapPort soapPort, HttpMethod httpMethod, SoapVersion soapOperationVersion, String soapOperationInputMessageName){
        for(SoapOperation soapOperation : soapPort.getOperations()){
            if(soapOperation.getHttpMethod().equals(httpMethod) && soapOperation.getSoapVersion().equals(soapOperationVersion) && soapOperation.getIdentifier().equalsIgnoreCase(soapOperationInputMessageName)){
                return mapper.map(soapOperation, SoapOperationDto.class);
            }
        }
        return null;
    }
}
