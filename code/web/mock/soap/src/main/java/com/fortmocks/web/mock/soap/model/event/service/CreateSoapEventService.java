package com.fortmocks.web.mock.soap.model.event.service;

import com.fortmocks.core.basis.model.Service;
import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.basis.model.event.dto.EventDto;
import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.event.service.message.input.CreateSoapEventInput;
import com.fortmocks.core.mock.soap.model.event.service.message.output.CreateSoapEventOutput;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class CreateSoapEventService extends AbstractSoapEventService implements Service<CreateSoapEventInput, CreateSoapEventOutput> {

    @Value("${soap.event.max}")
    private Integer soapMaxEventCount;

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<CreateSoapEventOutput> process(ServiceTask<CreateSoapEventInput> serviceTask) {
        final CreateSoapEventInput input = serviceTask.getInput();
        final SoapEventDto soapEventDto = input.getSoapEvent();
        if(count() >= soapMaxEventCount){
            EventDto eventDto = getOldestEvent();
            soapEventDto.setId(eventDto.getId());

        }
        final SoapEventDto createdSoapEvent = save(soapEventDto);
        return createServiceResult(new CreateSoapEventOutput(createdSoapEvent));
    }
}
