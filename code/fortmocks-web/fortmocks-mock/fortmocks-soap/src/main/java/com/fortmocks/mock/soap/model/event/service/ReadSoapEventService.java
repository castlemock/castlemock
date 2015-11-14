package com.fortmocks.mock.soap.model.event.service;

import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.mock.soap.model.event.service.message.input.ReadSoapEventInput;
import com.fortmocks.mock.soap.model.event.service.message.output.ReadSoapEventOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapEventService  extends AbstractSoapEventProcessor implements Service<ReadSoapEventInput, ReadSoapEventOutput> {

    @Override
    public Result<ReadSoapEventOutput> process(Task<ReadSoapEventInput> task) {
        final ReadSoapEventInput input = task.getInput();
        final SoapEventDto soapEvent = find(input.getSoapEventId());
        return createResult(new ReadSoapEventOutput(soapEvent));
    }
}
