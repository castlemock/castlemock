package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestMethod;
import com.fortmocks.mock.rest.model.project.domain.RestMockResponse;
import com.fortmocks.mock.rest.model.project.dto.RestMockResponseDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.DeleteRestMockResponsesInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.DeleteRestMockResponsesOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class DeleteRestMockResponsesProcessor extends AbstractRestProjectProcessor implements Processor<DeleteRestMockResponsesInput, DeleteRestMockResponsesOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<DeleteRestMockResponsesOutput> process(final Task<DeleteRestMockResponsesInput> task) {
        final DeleteRestMockResponsesInput input = task.getInput();
        final RestMethod restMethod = findRestMethodType(input.getRestProjectId(), input.getRestApplicationId(), input.getRestResourceId(), input.getRestMethodId());
        for(final RestMockResponseDto restMockResponseDto : input.getRestMockResponses()){
            final RestMockResponse restMockResponse = findRestMockResponseType(input.getRestProjectId(), input.getRestApplicationId(), input.getRestResourceId(), input.getRestMethodId(), restMockResponseDto.getId());
            restMethod.getRestMockResponses().remove(restMockResponse);
        }
        save(input.getRestProjectId());
        return createResult(new DeleteRestMockResponsesOutput());
    }
}
