package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestMethod;
import com.fortmocks.mock.rest.model.project.domain.RestMockResponse;
import com.fortmocks.mock.rest.model.project.processor.message.input.DeleteRestMockResponseInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.DeleteRestMockResponseOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class DeleteRestMockResponseProcessor extends AbstractRestProjectProcessor implements Processor<DeleteRestMockResponseInput, DeleteRestMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<DeleteRestMockResponseOutput> process(final Task<DeleteRestMockResponseInput> task) {
        final DeleteRestMockResponseInput input = task.getInput();
        final RestMockResponse restMockResponse = findRestMockResponseType(input.getRestProjectId(), input.getRestApplicationId(), input.getRestResourceId(), input.getRestMethodId(), input.getRestMockResponseId());
        final RestMethod restMethod = findRestMethodType(input.getRestProjectId(), input.getRestApplicationId(), input.getRestResourceId(), input.getRestMethodId());
        restMethod.getRestMockResponses().remove(restMockResponse);
        save(input.getRestProjectId());
        return createResult(new DeleteRestMockResponseOutput());
    }
}
