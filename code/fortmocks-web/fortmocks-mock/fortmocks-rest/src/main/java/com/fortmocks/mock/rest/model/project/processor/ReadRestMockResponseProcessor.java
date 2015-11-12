package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestMockResponse;
import com.fortmocks.mock.rest.model.project.dto.RestMockResponseDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.ReadRestMockResponseInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.ReadRestMockResponseOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class ReadRestMockResponseProcessor extends AbstractRestProjectProcessor implements Processor<ReadRestMockResponseInput, ReadRestMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadRestMockResponseOutput> process(final Task<ReadRestMockResponseInput> task) {
        final ReadRestMockResponseInput input = task.getInput();
        final RestMockResponse restMockResponse = findRestMockResponseType(input.getRestProjectId(), input.getRestApplicationId(), input.getRestResourceId(), input.getRestMethodId(), input.getRestMockResponse());
        final RestMockResponseDto restMockResponseDto = restMockResponse != null ? mapper.map(restMockResponse, RestMockResponseDto.class) : null;
        return createResult(new ReadRestMockResponseOutput(restMockResponseDto));
    }
}
