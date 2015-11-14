package com.fortmocks.mock.soap.model.project.service;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.domain.SoapPort;
import com.fortmocks.mock.soap.model.project.domain.SoapProject;
import com.fortmocks.mock.soap.model.project.service.message.input.CreateSoapPortsInput;
import com.fortmocks.mock.soap.model.project.service.message.output.CreateSoapPortsOutput;

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
