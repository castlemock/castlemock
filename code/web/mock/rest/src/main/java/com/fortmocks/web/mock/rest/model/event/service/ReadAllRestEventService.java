package com.fortmocks.web.mock.rest.model.event.service;

import com.fortmocks.core.basis.model.Result;
import com.fortmocks.core.basis.model.Service;
import com.fortmocks.core.basis.model.Task;
import com.fortmocks.core.mock.rest.model.event.dto.RestEventDto;
import com.fortmocks.core.mock.rest.model.event.service.message.input.ReadAllRestEventInput;
import com.fortmocks.core.mock.rest.model.event.service.message.output.ReadAllRestEventOutput;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadAllRestEventService extends AbstractRestEventService implements Service<ReadAllRestEventInput, ReadAllRestEventOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadAllRestEventOutput> process(Task<ReadAllRestEventInput> task) {
        final List<RestEventDto> restEvents = findAll();
        return createResult(new ReadAllRestEventOutput(restEvents));
    }
}
