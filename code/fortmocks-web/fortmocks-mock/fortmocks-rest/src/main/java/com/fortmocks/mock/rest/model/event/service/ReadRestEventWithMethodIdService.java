package com.fortmocks.mock.rest.model.event.service;

import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.event.domain.RestEvent;
import com.fortmocks.mock.rest.model.event.service.message.input.ReadRestEventWithMethodIdInput;
import com.fortmocks.mock.rest.model.event.service.message.output.ReadRestEventWithMethodIdOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadRestEventWithMethodIdService extends AbstractRestEventProcessor implements Service<ReadRestEventWithMethodIdInput, ReadRestEventWithMethodIdOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadRestEventWithMethodIdOutput> process(Task<ReadRestEventWithMethodIdInput> task) {
        final ReadRestEventWithMethodIdInput input = task.getInput();
        final List<RestEvent> events = new ArrayList<RestEvent>();
        for(RestEvent event : findAllTypes()){
            if(event.getRestMethodId().equals(input.getRestMethodId())){
                events.add(event);
            }
        }
        return createResult(new ReadRestEventWithMethodIdOutput(toDtoList(events)));
    }
}
