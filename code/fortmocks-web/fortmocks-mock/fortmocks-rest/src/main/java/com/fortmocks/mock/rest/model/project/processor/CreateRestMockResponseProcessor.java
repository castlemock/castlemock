package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.*;
import com.fortmocks.mock.rest.model.project.dto.RestMockResponseDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.CreateRestMockResponseInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.CreateRestMockResponseOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class CreateRestMockResponseProcessor extends AbstractRestProjectProcessor implements Processor<CreateRestMockResponseInput, CreateRestMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<CreateRestMockResponseOutput> process(final Task<CreateRestMockResponseInput> task) {
        final CreateRestMockResponseInput input = task.getInput();
        final RestMethod restMethod = findRestMethodType(input.getRestProjectId(), input.getRestApplicationId(), input.getRestResourceId(), input.getRestMethodId());
        final RestMockResponse restMockResponse = mapper.map(input.getRestMockResponse(), RestMockResponse.class);
        final Long restMockResponseId = getNextRestMockResponseId();
        restMockResponse.setId(restMockResponseId);
        restMethod.getRestMockResponses().add(restMockResponse);
        save(input.getRestProjectId());
        return createResult(new CreateRestMockResponseOutput(mapper.map(restMockResponse, RestMockResponseDto.class)));
    }
}
