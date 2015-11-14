package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.ReadAllSoapProjectsInput;
import com.fortmocks.mock.soap.model.project.processor.message.input.ReadSoapMockResponseInput;
import com.fortmocks.mock.soap.model.project.processor.message.input.ReadSoapOperationInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.ReadAllSoapProjectsOutput;
import com.fortmocks.mock.soap.model.project.processor.message.output.ReadSoapMockResponseOutput;
import com.fortmocks.mock.soap.model.project.processor.message.output.ReadSoapOperationOutput;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class ReadSoapMockResponseProcessor extends AbstractSoapProjectProcessor implements Processor<ReadSoapMockResponseInput, ReadSoapMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadSoapMockResponseOutput> process(final Task<ReadSoapMockResponseInput> task) {
        final ReadSoapMockResponseInput input = task.getInput();
        final SoapMockResponse soapMockResponse = findSoapMockResponseType(input.getSoapProjectId(), input.getSoapPortId(), input.getSoapOperationId(), input.getSoapMockResponseId());
        final SoapMockResponseDto soapMockResponseDto = soapMockResponse != null ? mapper.map(soapMockResponse, SoapMockResponseDto.class) : null;
        return createResult(new ReadSoapMockResponseOutput(soapMockResponseDto));
    }
}
