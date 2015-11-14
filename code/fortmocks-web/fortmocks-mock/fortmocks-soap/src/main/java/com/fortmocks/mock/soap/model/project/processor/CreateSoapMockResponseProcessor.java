package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.CreateSoapMockResponseInput;
import com.fortmocks.mock.soap.model.project.processor.message.input.CreateSoapProjectInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.CreateSoapMockResponseOutput;
import com.fortmocks.mock.soap.model.project.processor.message.output.CreateSoapProjectOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class CreateSoapMockResponseProcessor extends AbstractSoapProjectProcessor implements Processor<CreateSoapMockResponseInput, CreateSoapMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<CreateSoapMockResponseOutput> process(final Task<CreateSoapMockResponseInput> task) {
        final CreateSoapMockResponseInput input = task.getInput();
        final SoapOperation soapOperation = findSoapOperationType(input.getSoapProjectId(), input.getSoapPortId(), input.getSoapOperationId());
        final SoapMockResponse soapMockResponse = mapper.map(input.getSoapMockResponseDto(), SoapMockResponse.class);
        final Long soapMockResponseId = getNextSoapMockResponseId();
        soapMockResponse.setId(soapMockResponseId);
        soapOperation.getSoapMockResponses().add(soapMockResponse);
        save(input.getSoapProjectId());
        return createResult(new CreateSoapMockResponseOutput());
    }
}
