package com.fortmocks.web.mock.soap.model.event.service;

import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.Service;
import com.fortmocks.core.basis.model.ServiceTask;
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
    public ServiceResult<ReadSoapEventsByOperationIdOutput> process(ServiceTask<ReadSoapEventsByOperationIdInput> serviceTask) {
        final ReadSoapEventsByOperationIdInput input = serviceTask.getInput();
        final List<SoapEventDto> events = new ArrayList<SoapEventDto>();
        for(SoapEvent event : findAllTypes()){
            if(event.getSoapOperationId().equals(input.getOperationId())){
                SoapEventDto soapEventDto = mapper.map(event, SoapEventDto.class);
                events.add(soapEventDto);
            }
        }
        return createServiceResult(new ReadSoapEventsByOperationIdOutput(events));
    }
}
