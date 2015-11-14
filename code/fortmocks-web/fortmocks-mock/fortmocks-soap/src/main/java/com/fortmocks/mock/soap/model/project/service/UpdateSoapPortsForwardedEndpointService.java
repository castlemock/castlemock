package com.fortmocks.mock.soap.model.project.service;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.domain.SoapPort;
import com.fortmocks.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.mock.soap.model.project.service.message.input.UpdateSoapPortsForwardedEndpointInput;
import com.fortmocks.mock.soap.model.project.service.message.output.UpdateSoapPortsForwardedEndpointOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateSoapPortsForwardedEndpointService extends AbstractSoapProjectProcessor implements Service<UpdateSoapPortsForwardedEndpointInput, UpdateSoapPortsForwardedEndpointOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<UpdateSoapPortsForwardedEndpointOutput> process(final Task<UpdateSoapPortsForwardedEndpointInput> task) {
        final UpdateSoapPortsForwardedEndpointInput input = task.getInput();
        for(SoapPortDto soapPortDto : input.getSoapPorts()){
            SoapPort soapPort = findSoapPortType(input.getSoapProjectId(), soapPortDto.getId());
            for(SoapOperation soapOperation : soapPort.getSoapOperations()){
                soapOperation.setForwardedEndpoint(input.getForwardedEndpoint());
            }
        }
        save(input.getSoapProjectId());
        return createResult(new UpdateSoapPortsForwardedEndpointOutput());
    }
}
