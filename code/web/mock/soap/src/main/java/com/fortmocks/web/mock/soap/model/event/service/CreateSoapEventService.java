package com.fortmocks.web.mock.soap.model.event.service;

import com.fortmocks.core.basis.model.Result;
import com.fortmocks.core.basis.model.Service;
import com.fortmocks.core.basis.model.Task;
import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.event.service.message.input.CreateSoapEventInput;
import com.fortmocks.core.mock.soap.model.event.service.message.output.CreateSoapEventOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateSoapEventService extends AbstractSoapEventProcessor implements Service<CreateSoapEventInput, CreateSoapEventOutput> {

    @Override
    public Result<CreateSoapEventOutput> process(Task<CreateSoapEventInput> task) {
        final CreateSoapEventInput input = task.getInput();
        final SoapEventDto createdSoapEvent = save(input.getSoapEvent());
        return createResult(new CreateSoapEventOutput(createdSoapEvent));
    }
}
