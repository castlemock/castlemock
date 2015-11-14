package com.fortmocks.mock.soap.model.project.service;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.service.message.input.UpdateCurrentMockResponseSequenceIndexInput;
import com.fortmocks.mock.soap.model.project.service.message.output.UpdateCurrentMockResponseSequenceIndexOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class UpdateCurrentMockResponseSequenceIndexService extends AbstractSoapProjectProcessor implements Service<UpdateCurrentMockResponseSequenceIndexInput, UpdateCurrentMockResponseSequenceIndexOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<UpdateCurrentMockResponseSequenceIndexOutput> process(final Task<UpdateCurrentMockResponseSequenceIndexInput> task) {
        final UpdateCurrentMockResponseSequenceIndexInput input = task.getInput();
        final SoapOperation soapOperation = findSoapOperationType(input.getSoapOperationId());
        final Long soapProjectId = findSoapProjectType(input.getSoapOperationId());
        soapOperation.setCurrentResponseSequenceIndex(input.getCurrentResponseSequenceIndex());
        save(soapProjectId);
        return createResult(new UpdateCurrentMockResponseSequenceIndexOutput());
    }
}
