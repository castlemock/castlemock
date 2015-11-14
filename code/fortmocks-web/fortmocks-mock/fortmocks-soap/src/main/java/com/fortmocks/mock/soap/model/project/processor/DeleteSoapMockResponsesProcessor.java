package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.DeleteSoapMockResponseInput;
import com.fortmocks.mock.soap.model.project.processor.message.input.DeleteSoapMockResponsesInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.DeleteSoapMockResponseOutput;
import com.fortmocks.mock.soap.model.project.processor.message.output.DeleteSoapMockResponsesOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class DeleteSoapMockResponsesProcessor extends AbstractSoapProjectProcessor implements Processor<DeleteSoapMockResponsesInput, DeleteSoapMockResponsesOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<DeleteSoapMockResponsesOutput> process(final Task<DeleteSoapMockResponsesInput> task) {
        final DeleteSoapMockResponsesInput input = task.getInput();
        final SoapOperation soapOperation = findSoapOperationType(input.getSoapProjectId(), input.getSoapPortId(), input.getSoapOperationId());
        for(SoapMockResponseDto soapMockResponseDto : input.getMockResponses()){
            final SoapMockResponse soapMockResponse = new SoapMockResponse();
            soapMockResponse.setId(soapMockResponseDto.getId());
            soapOperation.getSoapMockResponses().remove(soapMockResponse);
        }

        save(input.getSoapProjectId());
        return createResult(new DeleteSoapMockResponsesOutput());
    }
}
