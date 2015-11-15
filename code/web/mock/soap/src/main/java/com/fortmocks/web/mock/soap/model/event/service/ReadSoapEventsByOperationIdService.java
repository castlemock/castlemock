package com.fortmocks.web.mock.soap.model.event.service;

import com.fortmocks.core.basis.model.Result;
import com.fortmocks.core.basis.model.Service;
import com.fortmocks.core.basis.model.Task;
import com.fortmocks.core.mock.soap.model.event.domain.SoapEvent;
import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.event.service.message.output.ReadSoapEventsByOperationIdOutput;
import com.fortmocks.core.mock.soap.model.event.service.message.input.ReadSoapEventsByOperationIdInput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapEventsByOperationIdService extends AbstractSoapEventService implements Service<ReadSoapEventsByOperationIdInput, ReadSoapEventsByOperationIdOutput> {

    @Override
    public Result<ReadSoapEventsByOperationIdOutput> process(Task<ReadSoapEventsByOperationIdInput> task) {
        final ReadSoapEventsByOperationIdInput input = task.getInput();
        final List<SoapEventDto> events = new ArrayList<SoapEventDto>();
        for(SoapEvent event : findAllTypes()){
            if(event.getSoapOperationId().equals(input.getOperationId())){
                SoapEventDto soapEventDto = mapper.map(event, SoapEventDto.class);
                events.add(soapEventDto);
            }
        }
        return createResult(new ReadSoapEventsByOperationIdOutput(events));
    }
}
