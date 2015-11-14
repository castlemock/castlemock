package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.UpdateSoapOperationInput;
import com.fortmocks.mock.soap.model.project.processor.message.input.UpdateSoapProjectInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.UpdateSoapOperationOutput;
import com.fortmocks.mock.soap.model.project.processor.message.output.UpdateSoapProjectOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class UpdateSoapOperationProcessor extends AbstractSoapProjectProcessor implements Processor<UpdateSoapOperationInput, UpdateSoapOperationOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
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
