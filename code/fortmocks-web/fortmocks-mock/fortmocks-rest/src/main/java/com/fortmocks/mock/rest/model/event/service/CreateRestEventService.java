package com.fortmocks.mock.rest.model.event.service;

import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.event.dto.RestEventDto;
import com.fortmocks.mock.rest.model.event.service.message.input.CreateRestEventInput;
import com.fortmocks.mock.rest.model.event.service.message.input.ReadRestEventInput;
import com.fortmocks.mock.rest.model.event.service.message.output.CreateRestEventOutput;
import com.fortmocks.mock.rest.model.event.service.message.output.ReadRestEventOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRestEventService extends AbstractRestEventProcessor implements Service<CreateRestEventInput, CreateRestEventOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<CreateRestEventOutput> process(Task<CreateRestEventInput> task) {
        final CreateRestEventInput input = task.getInput();
        final RestEventDto createdRestEvent = save(input.getRestEvent());
        return createResult(new CreateRestEventOutput(createdRestEvent));
    }
}
