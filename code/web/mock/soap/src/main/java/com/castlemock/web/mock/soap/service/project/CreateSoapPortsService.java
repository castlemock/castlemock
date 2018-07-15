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
import com.castlemock.web.basis.support.FileRepositorySupport;
import com.castlemock.web.mock.soap.converter.SoapPortConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;

import javax.wsdl.WSDLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateSoapPortsService extends AbstractSoapProjectService implements Service<CreateSoapPortsInput, CreateSoapPortsOutput> {

    @Autowired
    private FileRepositorySupport fileRepositorySupport;

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
        List<SoapPort> soapPorts = null;
        try {
            soapPorts = createPorts(input.getFiles(), input.isGenerateResponse());
        } catch (WSDLException e) {
            throw new IllegalStateException("Unable to parse the WSDL file");
        }

        for(SoapPort newSoapPort : soapPorts){
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

        // Should only be one WSDL file
        final Collection<SoapResource> wsdlSoapResources =
                this.resourceRepository.findSoapResources(soapProjectId, SoapResourceType.WSDL);

        for(SoapResource wsdlSoapResource : wsdlSoapResources){
            this.resourceRepository.delete(wsdlSoapResource.getId());
        }

        for(File file : input.getFiles()){
            String resource = fileRepositorySupport.read(file);
            SoapResource soapResource = new SoapResource();
            soapResource.setProjectId(soapProjectId);
            soapResource.setName(file.getName());
            soapResource.setType(SoapResourceType.WSDL);
            this.resourceRepository.saveSoapResource(soapResource, resource);
        }

        return createServiceResult(new CreateSoapPortsOutput());
    }




    /**
     * The method creates ports based on provided definitions.
     * @param files Definitions used to create new ports.
     * @param generateResponse Boolean value for if a default response should be generated for each new operation
     * @return List of ports generated from the provided definitions.
     * @throws WSDLException Throws an WSDLException if WSDL parsing failed.
     */
    private List<SoapPort> createPorts(final List<File> files, final boolean generateResponse) throws WSDLException {
        try {
            final List<SoapPort> soapPorts = new ArrayList<>();
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setNamespaceAware(true);

            for(File file : files){
                final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                final Document document = documentBuilder.parse(file);
                document.getDocumentElement().normalize();

                final List<SoapPort> fileSoapPorts = SoapPortConverter.getSoapPorts(document, generateResponse);
                soapPorts.addAll(fileSoapPorts);
            }
            return soapPorts;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable parse WSDL file", e);
        }
    }




}
