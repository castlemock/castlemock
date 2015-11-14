package com.fortmocks.mock.soap.model.event.service;

import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.mock.soap.model.event.service.message.input.CreateSoapEventInput;
import com.fortmocks.mock.soap.model.event.service.message.input.ReadSoapEventInput;
import com.fortmocks.mock.soap.model.event.service.message.output.CreateSoapEventOutput;
import com.fortmocks.mock.soap.model.event.service.message.output.ReadSoapEventOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateSoapEventService extends AbstractSoapEventProcessor implements Service<CreateSoapEventInput, CreateSoapEventOutput> {

    @Override
    public Result<CreateSoapEventOutput> process(Task<CreateSoapEventInput> task) {
        final CreateSoapEventInput input = task.getInput();
        final SoapEventDto createdSoapEvent = save(input.getSoapEvent());
        return createResult(new CreateSoapEventOutput(createdSoapEvent));
    }
}
