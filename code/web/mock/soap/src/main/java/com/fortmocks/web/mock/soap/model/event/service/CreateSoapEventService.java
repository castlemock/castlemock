package com.fortmocks.web.mock.soap.model.event.service;

import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.Service;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.event.service.message.input.CreateSoapEventInput;
import com.fortmocks.core.mock.soap.model.event.service.message.output.CreateSoapEventOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateSoapEventService extends AbstractSoapEventService implements Service<CreateSoapEventInput, CreateSoapEventOutput> {

    @Override
    public ServiceResult<CreateSoapEventOutput> process(ServiceTask<CreateSoapEventInput> serviceTask) {
        final CreateSoapEventInput input = serviceTask.getInput();
        final SoapEventDto createdSoapEvent = save(input.getSoapEvent());
        return createServiceResult(new CreateSoapEventOutput(createdSoapEvent));
    }
}
