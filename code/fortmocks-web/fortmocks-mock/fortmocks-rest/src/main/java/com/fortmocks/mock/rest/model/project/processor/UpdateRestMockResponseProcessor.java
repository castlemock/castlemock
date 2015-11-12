package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestMockResponse;
import com.fortmocks.mock.rest.model.project.dto.RestMockResponseDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.UpdateRestMockResponseInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.UpdateRestMockResponseOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class UpdateRestMockResponseProcessor extends AbstractRestProjectProcessor implements Processor<UpdateRestMockResponseInput, UpdateRestMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<UpdateRestMockResponseOutput> process(final Task<UpdateRestMockResponseInput> task) {
        final UpdateRestMockResponseInput input = task.getInput();
        final RestMockResponseDto updatedRestMockResponse = input.getRestMockResponse();
        final RestMockResponse restMockResponse = findRestMockResponseType(input.getRestProjectId(), input.getRestApplicationId(), input.getRestResourceId(), input.getRestMethodId(), input.getRestMockResponseId());
        restMockResponse.setName(updatedRestMockResponse.getName());
        restMockResponse.setBody(updatedRestMockResponse.getBody());
        restMockResponse.setHttpStatusCode(updatedRestMockResponse.getHttpStatusCode());
        restMockResponse.setRestContentType(updatedRestMockResponse.getRestContentType());
        save(input.getRestProjectId());
        return createResult(new UpdateRestMockResponseOutput());
    }
}
