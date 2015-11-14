package com.fortmocks.mock.rest.model.event.service;

import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.event.dto.RestEventDto;
import com.fortmocks.mock.rest.model.event.service.message.input.ReadRestEventInput;
import com.fortmocks.mock.rest.model.event.service.message.output.ReadRestEventOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadRestEventService extends AbstractRestEventProcessor implements Service<ReadRestEventInput, ReadRestEventOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadRestEventOutput> process(Task<ReadRestEventInput> task) {
        final ReadRestEventInput input = task.getInput();
        final RestEventDto restEvent = find(input.getRestEventId());
        return createResult(new ReadRestEventOutput(restEvent));
    }
}
