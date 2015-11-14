package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.processor.message.input.CreateRecordedSoapMockResponseInput;
import com.fortmocks.mock.soap.model.project.processor.message.input.CreateSoapMockResponseInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.CreateRecordedSoapMockResponseOutput;
import com.fortmocks.mock.soap.model.project.processor.message.output.CreateSoapMockResponseOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class CreateRecordedSoapMockResponseProcessor extends AbstractSoapProjectProcessor implements Processor<CreateRecordedSoapMockResponseInput, CreateRecordedSoapMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<CreateRecordedSoapMockResponseOutput> process(final Task<CreateRecordedSoapMockResponseInput> task) {
        final CreateRecordedSoapMockResponseInput input = task.getInput();
        final SoapOperation soapOperation = findSoapOperationType(input.getSoapOperationId());
        final Long soapProjectId = findSoapProjectType(input.getSoapOperationId());
        final SoapMockResponse soapMockResponse = mapper.map(input.getSoapMockResponseDto(), SoapMockResponse.class);
        soapOperation.getSoapMockResponses().add(soapMockResponse);
        save(soapProjectId);
        return createResult(new CreateRecordedSoapMockResponseOutput());
    }
}
