package com.fortmocks.core.mock.soap.model.event.service;

import com.fortmocks.core.basis.model.Result;
import com.fortmocks.core.basis.model.Service;
import com.fortmocks.core.basis.model.Task;
import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.event.service.message.input.ReadAllSoapEventInput;
import com.fortmocks.core.mock.soap.model.event.service.message.output.ReadAllSoapEventOutput;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadAllSoapEventService extends AbstractSoapEventProcessor implements Service<ReadAllSoapEventInput, ReadAllSoapEventOutput> {

    @Override
    public Result<ReadAllSoapEventOutput> process(final Task<ReadAllSoapEventInput> task) {
        final ReadAllSoapEventInput input = task.getInput();
        final List<SoapEventDto> soapEvents = findAll();
        return createResult(new ReadAllSoapEventOutput(soapEvents));
    }
}
