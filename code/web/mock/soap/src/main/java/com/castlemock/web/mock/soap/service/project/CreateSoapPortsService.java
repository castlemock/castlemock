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
import com.castlemock.core.mock.soap.model.project.domain.*;
import com.castlemock.core.mock.soap.service.project.input.CreateSoapPortsInput;
import com.castlemock.core.mock.soap.service.project.output.CreateSoapPortsOutput;
import com.castlemock.web.mock.soap.converter.SoapPortConverter;
import com.castlemock.web.mock.soap.converter.SoapPortConverterResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateSoapPortsService extends AbstractSoapProjectService implements Service<CreateSoapPortsInput, CreateSoapPortsOutput> {

    @Autowired
    private SoapPortConverter soapPortConverter;

    /**
     *
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

        Set<SoapPortConverterResult> results = null;
        try {
            if(input.getFiles() != null){
                results = soapPortConverter.getSoapPorts(input.getFiles(), input.isGenerateResponse());
            } else if(input.getLocation() != null){
                results = soapPortConverter.getSoapPorts(input.getLocation(), input.isGenerateResponse(), input.isIncludeImports());
            } else {
                throw new IllegalArgumentException("Neither files or links were provided when importing SOAP ports");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Unable to parse the WSDL file", e);
        }

        for(SoapPortConverterResult result : results){
            for(SoapPort newSoapPort : result.getPorts()){
                newSoapPort.setProjectId(soapProjectId);
                SoapPort existingSoapPort = this.portRepository.findWithName(soapProjectId, newSoapPort.getName());

                if(existingSoapPort == null){
                    SoapPort savedSoapPort = this.portRepository.save(newSoapPort);

                    for(SoapOperation soapOperation : newSoapPort.getOperations()){
                        soapOperation.setPortId(savedSoapPort.getId());
                        SoapOperation savedSoapOperation = this.operationRepository.save(soapOperation);
                        for(SoapMockResponse soapMockResponse : soapOperation.getMockResponses()){
                            soapMockResponse.setOperationId(savedSoapOperation.getId());
                            this.mockResponseRepository.save(soapMockResponse);
                        }
                    }

                    continue;
                }

                for(SoapOperation newSoapOperation : newSoapPort.getOperations()){
                    SoapOperation existingSoapOperation =
                            this.operationRepository.findWithName(existingSoapPort.getId(), newSoapOperation.getName());

                    if(existingSoapOperation != null){
                        existingSoapOperation.setOriginalEndpoint(newSoapOperation.getOriginalEndpoint());
                        existingSoapOperation.setSoapVersion(newSoapOperation.getSoapVersion());
                        this.operationRepository.update(existingSoapOperation.getId(), existingSoapOperation);
                    } else {
                        newSoapOperation.setPortId(existingSoapPort.getId());
                        SoapOperation savedSoapOperation = this.operationRepository.save(newSoapOperation);
                        for(SoapMockResponse soapMockResponse : newSoapOperation.getMockResponses()){
                            soapMockResponse.setOperationId(savedSoapOperation.getId());
                            this.mockResponseRepository.save(soapMockResponse);
                        }
                    }
                }
            }
        }

        final Collection<SoapResource> wsdlSoapResources =
                this.resourceRepository.findSoapResources(soapProjectId, SoapResourceType.WSDL, SoapResourceType.WSDL_IMPORT);

        for(SoapResource wsdlSoapResource : wsdlSoapResources){
            this.resourceRepository.delete(wsdlSoapResource.getId());
        }

        for(SoapPortConverterResult result : results){
            SoapResource soapResource = new SoapResource();
            soapResource.setProjectId(soapProjectId);
            soapResource.setName(result.getName());
            soapResource.setType(result.getResourceType());
            this.resourceRepository.saveSoapResource(soapResource, result.getDefinition());
        }

        return createServiceResult(new CreateSoapPortsOutput());
    }

}
