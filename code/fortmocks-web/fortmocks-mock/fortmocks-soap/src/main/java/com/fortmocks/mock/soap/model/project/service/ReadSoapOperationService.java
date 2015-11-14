package com.fortmocks.mock.soap.model.project.service;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.mock.soap.model.project.service.message.input.ReadSoapOperationInput;
import com.fortmocks.mock.soap.model.project.service.message.output.ReadSoapOperationOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapOperationService extends AbstractSoapProjectProcessor implements Service<ReadSoapOperationInput, ReadSoapOperationOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadSoapOperationOutput> process(final Task<ReadSoapOperationInput> task) {
        final ReadSoapOperationInput input = task.getInput();
        final SoapOperation soapOperation = findSoapOperationType(input.getSoapProjectId(), input.getSoapPortId(), input.getSoapOperationId());
        final SoapOperationDto soapOperationDto = soapOperation != null ? mapper.map(soapOperation, SoapOperationDto.class) : null;
        return createResult(new ReadSoapOperationOutput(soapOperationDto));
    }
}
