package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.mock.soap.model.project.processor.message.input.UpdateSoapMockResponseStatusInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.UpdateSoapMockResponseStatusOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateSoapMockResponseStatusService extends AbstractSoapProjectProcessor implements Service<UpdateSoapMockResponseStatusInput, UpdateSoapMockResponseStatusOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<UpdateSoapMockResponseStatusOutput> process(final Task<UpdateSoapMockResponseStatusInput> task) {
        final UpdateSoapMockResponseStatusInput input = task.getInput();
        final SoapMockResponse soapMockResponse = findSoapMockResponseType(input.getSoapProjectId(), input.getSoapPortId(), input.getSoapOperationId(), input.getSoapMockResponseId());
        soapMockResponse.setSoapMockResponseStatus(input.getSoapMockResponseStatus());
        save(input.getSoapProjectId());
        return createResult(new UpdateSoapMockResponseStatusOutput());
    }
}
