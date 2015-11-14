package com.fortmocks.mock.soap.model.event.service;

import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.event.domain.SoapEvent;
import com.fortmocks.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.mock.soap.model.event.service.message.input.ReadAllSoapEventInput;
import com.fortmocks.mock.soap.model.event.service.message.output.ReadAllSoapEventOutput;
import com.fortmocks.web.core.model.AbstractProcessor;

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
