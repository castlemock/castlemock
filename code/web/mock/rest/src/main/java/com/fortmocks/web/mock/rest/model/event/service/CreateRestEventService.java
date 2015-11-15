package com.fortmocks.web.mock.rest.model.event.service;

import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.Service;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.mock.rest.model.event.dto.RestEventDto;
import com.fortmocks.core.mock.rest.model.event.service.message.input.CreateRestEventInput;
import com.fortmocks.core.mock.rest.model.event.service.message.output.CreateRestEventOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateRestEventService extends AbstractRestEventService implements Service<CreateRestEventInput, CreateRestEventOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<CreateRestEventOutput> process(ServiceTask<CreateRestEventInput> serviceTask) {
        final CreateRestEventInput input = serviceTask.getInput();
        final RestEventDto createdRestEvent = save(input.getRestEvent());
        return createServiceResult(new CreateRestEventOutput(createdRestEvent));
    }
}
