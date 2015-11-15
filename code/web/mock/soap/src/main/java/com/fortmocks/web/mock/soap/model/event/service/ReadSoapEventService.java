package com.fortmocks.web.mock.soap.model.event.service;

import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.Service;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.event.service.message.input.ReadSoapEventInput;
import com.fortmocks.core.mock.soap.model.event.service.message.output.ReadSoapEventOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapEventService  extends AbstractSoapEventService implements Service<ReadSoapEventInput, ReadSoapEventOutput> {

    @Override
    public ServiceResult<ReadSoapEventOutput> process(ServiceTask<ReadSoapEventInput> serviceTask) {
        final ReadSoapEventInput input = serviceTask.getInput();
        final SoapEventDto soapEvent = find(input.getSoapEventId());
        return createServiceResult(new ReadSoapEventOutput(soapEvent));
    }
}
