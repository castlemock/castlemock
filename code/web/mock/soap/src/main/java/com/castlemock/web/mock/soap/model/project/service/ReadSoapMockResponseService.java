package com.castlemock.web.mock.soap.model.project.service;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.castlemock.core.mock.soap.model.project.service.message.input.ReadSoapMockResponseInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.ReadSoapMockResponseOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapMockResponseService extends AbstractSoapProjectService implements Service<ReadSoapMockResponseInput, ReadSoapMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ReadSoapMockResponseOutput> process(ServiceTask<ReadSoapMockResponseInput> serviceTask) {
        final ReadSoapMockResponseInput input = serviceTask.getInput();
        final SoapMockResponseDto soapMockResponseDto = repository.findSoapMockResponse(input.getSoapProjectId(), input.getSoapPortId(), input.getSoapOperationId(), input.getSoapMockResponseId());
        return createServiceResult(new ReadSoapMockResponseOutput(soapMockResponseDto));
    }
}
