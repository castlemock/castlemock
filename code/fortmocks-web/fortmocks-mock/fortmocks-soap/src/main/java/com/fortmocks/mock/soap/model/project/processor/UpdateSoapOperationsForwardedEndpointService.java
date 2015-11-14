package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.UpdateSoapOperationsForwardedEndpointInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.UpdateSoapOperationsForwardedEndpointOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateSoapOperationsForwardedEndpointService extends AbstractSoapProjectProcessor implements Service<UpdateSoapOperationsForwardedEndpointInput, UpdateSoapOperationsForwardedEndpointOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<UpdateSoapOperationsForwardedEndpointOutput> process(final Task<UpdateSoapOperationsForwardedEndpointInput> task) {
        final UpdateSoapOperationsForwardedEndpointInput input = task.getInput();
        for(SoapOperationDto soapOperationDto : input.getSoapOperations()){
            SoapOperation soapOperation = findSoapOperationType(input.getSoapProjectId(), input.getSoapPortId(), soapOperationDto.getId());
            soapOperation.setForwardedEndpoint(input.getForwardedEndpoint());
        }
        save(input.getSoapProjectId());
        return createResult(new UpdateSoapOperationsForwardedEndpointOutput());
    }
}
