package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.UpdateCurrentMockResponseSequenceIndexInput;
import com.fortmocks.mock.soap.model.project.processor.message.input.UpdateSoapMockResponseInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.UpdateCurrentMockResponseSequenceIndexOutput;
import com.fortmocks.mock.soap.model.project.processor.message.output.UpdateSoapMockResponseOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class UpdateCurrentMockResponseSequenceIndexProcessor extends AbstractSoapProjectProcessor implements Processor<UpdateCurrentMockResponseSequenceIndexInput, UpdateCurrentMockResponseSequenceIndexOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
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
