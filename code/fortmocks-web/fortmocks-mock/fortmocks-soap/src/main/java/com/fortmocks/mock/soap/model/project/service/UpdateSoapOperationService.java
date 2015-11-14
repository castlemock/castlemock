package com.fortmocks.mock.soap.model.project.service;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.mock.soap.model.project.service.message.input.UpdateSoapOperationInput;
import com.fortmocks.mock.soap.model.project.service.message.output.UpdateSoapOperationOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateSoapOperationService extends AbstractSoapProjectProcessor implements Service<UpdateSoapOperationInput, UpdateSoapOperationOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<UpdateSoapOperationOutput> process(final Task<UpdateSoapOperationInput> task) {
        final UpdateSoapOperationInput input = task.getInput();
        final SoapOperationDto updatedSoapOperation = input.getUpdatedSoapOperation();
        final SoapOperation soapOperation = findSoapOperationType(input.getSoapProjectId(), input.getSoapPortId(), input.getSoapOperationId());
        soapOperation.setSoapOperationStatus(updatedSoapOperation.getSoapOperationStatus());
        soapOperation.setForwardedEndpoint(updatedSoapOperation.getForwardedEndpoint());
        soapOperation.setSoapResponseStrategy(updatedSoapOperation.getSoapResponseStrategy());
        save(input.getSoapProjectId());
        return createResult(new UpdateSoapOperationOutput());
    }
}
