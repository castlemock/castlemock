package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.Service;
import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.service.mock.soap.project.input.ReadSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.output.ReadSoapMockResponseOutput;

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
        return createServiceResult(ReadSoapMockResponseOutput.builder()
                .mockResponse(this.mockResponseRepository.findOne(input.getSoapMockResponseId())
                        .orElse(null))
                .build());
    }
}
