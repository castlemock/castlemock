package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.ReadAllSoapProjectsInput;
import com.fortmocks.mock.soap.model.project.processor.message.input.ReadSoapMockResponsesInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.ReadAllSoapProjectsOutput;
import com.fortmocks.mock.soap.model.project.processor.message.output.ReadSoapMockResponsesOutput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class ReadSoapMockResponsesProcessor extends AbstractSoapProjectProcessor implements Processor<ReadSoapMockResponsesInput, ReadSoapMockResponsesOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadSoapMockResponsesOutput> process(final Task<ReadSoapMockResponsesInput> task) {
        final ReadSoapMockResponsesInput input = task.getInput();
        final SoapOperation soapOperation = findSoapOperationType(input.getSoapOperationId());
        final List<SoapMockResponseDto> soapMockResponses = new ArrayList<SoapMockResponseDto>();
        for(SoapMockResponse soapMockResponse : soapOperation.getSoapMockResponses()){
            if(soapMockResponse.getSoapMockResponseStatus().equals(input.getStatus())){
                final SoapMockResponseDto soapMockResponseDto = mapper.map(soapMockResponse, SoapMockResponseDto.class);
                soapMockResponses.add(soapMockResponseDto);
            }
        }

        return createResult(new ReadSoapMockResponsesOutput(soapMockResponses));
    }
}
