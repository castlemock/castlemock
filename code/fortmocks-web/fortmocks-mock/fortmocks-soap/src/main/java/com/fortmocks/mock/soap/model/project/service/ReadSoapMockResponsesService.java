package com.fortmocks.mock.soap.model.project.service;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.mock.soap.model.project.service.message.input.ReadSoapMockResponsesInput;
import com.fortmocks.mock.soap.model.project.service.message.output.ReadSoapMockResponsesOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapMockResponsesService extends AbstractSoapProjectProcessor implements Service<ReadSoapMockResponsesInput, ReadSoapMockResponsesOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
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
