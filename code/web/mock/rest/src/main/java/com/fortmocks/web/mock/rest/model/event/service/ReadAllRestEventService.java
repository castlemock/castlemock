package com.fortmocks.web.mock.rest.model.event.service;

import com.fortmocks.core.basis.model.Service;
import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.ServiceTask;
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
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ReadAllRestEventOutput> process(ServiceTask<ReadAllRestEventInput> serviceTask) {
        final List<RestEventDto> restEvents = findAll();
        return createServiceResult(new ReadAllRestEventOutput(restEvents));
    }
}
