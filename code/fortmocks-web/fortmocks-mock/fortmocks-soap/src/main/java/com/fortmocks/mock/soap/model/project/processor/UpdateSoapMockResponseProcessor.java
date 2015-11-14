package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.UpdateSoapMockResponseInput;
import com.fortmocks.mock.soap.model.project.processor.message.input.UpdateSoapOperationInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.UpdateSoapMockResponseOutput;
import com.fortmocks.mock.soap.model.project.processor.message.output.UpdateSoapOperationOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class UpdateSoapMockResponseProcessor extends AbstractSoapProjectProcessor implements Processor<UpdateSoapMockResponseInput, UpdateSoapMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<UpdateSoapMockResponseOutput> process(final Task<UpdateSoapMockResponseInput> task) {
        final UpdateSoapMockResponseInput input = task.getInput();
        final SoapMockResponseDto updatedSoapMockResponse = input.getSoapMockResponseDto();
        final SoapMockResponse soapMockResponse = findSoapMockResponseType(input.getSoapProjectId(), input.getSoapPortId(), input.getSoapOperationId(), input.getSoapMockResponseId());
        soapMockResponse.setName(updatedSoapMockResponse.getName());
        soapMockResponse.setBody(updatedSoapMockResponse.getBody());
        soapMockResponse.setHttpStatusCode(updatedSoapMockResponse.getHttpStatusCode());
        return createResult(new UpdateSoapMockResponseOutput());
    }
}
