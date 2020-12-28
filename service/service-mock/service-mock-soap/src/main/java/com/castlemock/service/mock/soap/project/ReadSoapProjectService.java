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

package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.Service;
import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapOperationStatus;
import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapPort;
import com.castlemock.model.mock.soap.domain.SoapProject;
import com.castlemock.model.mock.soap.domain.SoapResource;
import com.castlemock.service.mock.soap.project.input.ReadSoapProjectInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapProjectOutput;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapProjectService extends AbstractSoapProjectService implements Service<ReadSoapProjectInput, ReadSoapProjectOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ReadSoapProjectOutput> process(final ServiceTask<ReadSoapProjectInput> serviceTask) {
        final ReadSoapProjectInput input = serviceTask.getInput();
        final SoapProject soapProject = find(input.getProjectId());
        final List<SoapResource> resources = this.resourceRepository.findWithProjectId(input.getProjectId());
        final List<SoapPort> ports = this.portRepository.findWithProjectId(input.getProjectId());
        soapProject.setResources(resources);
        soapProject.setPorts(ports);
        for(final SoapPort soapPort : soapProject.getPorts()){
            final List<SoapOperation> operations = this.operationRepository.findWithPortId(soapPort.getId());
            final Map<SoapOperationStatus, Integer> soapOperationStatusCount = getSoapOperationStatusCount(operations);
            soapPort.setStatusCount(soapOperationStatusCount);
        }
        return createServiceResult(ReadSoapProjectOutput.builder()
                .project(soapProject)
                .build());
    }


    /**
     * Count the operation statuses
     * @param soapOperations The list of operations, which status will be counted
     * @return The result of the status count
     */
    private Map<SoapOperationStatus, Integer> getSoapOperationStatusCount(final List<SoapOperation> soapOperations){
        Preconditions.checkNotNull(soapOperations, "The operation list cannot be null");
        final Map<SoapOperationStatus, Integer> statuses = new HashMap<SoapOperationStatus, Integer>();

        for(SoapOperationStatus soapOperationStatus : SoapOperationStatus.values()){
            statuses.put(soapOperationStatus, 0);
        }
        for(SoapOperation soapOperation : soapOperations){
            SoapOperationStatus soapOperationStatus = soapOperation.getStatus();
            statuses.put(soapOperationStatus, statuses.get(soapOperationStatus)+1);
        }
        return statuses;
    }
}
