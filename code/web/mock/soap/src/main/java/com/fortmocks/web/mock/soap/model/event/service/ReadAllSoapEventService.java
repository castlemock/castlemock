package com.fortmocks.web.mock.soap.model.event.service;

import com.fortmocks.core.basis.model.Service;
import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.event.service.message.input.ReadAllSoapEventInput;
import com.fortmocks.core.mock.soap.model.event.service.message.output.ReadAllSoapEventOutput;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadAllSoapEventService extends AbstractSoapEventService implements Service<ReadAllSoapEventInput, ReadAllSoapEventOutput> {

    @Override
    public ServiceResult<ReadAllSoapEventOutput> process(final ServiceTask<ReadAllSoapEventInput> serviceTask) {
        final ReadAllSoapEventInput input = serviceTask.getInput();
        final List<SoapEventDto> soapEvents = findAll();
        return createServiceResult(new ReadAllSoapEventOutput(soapEvents));
    }
}
