package com.fortmocks.mock.soap.model.project.service;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.service.message.input.DeleteSoapMockResponseInput;
import com.fortmocks.mock.soap.model.project.service.message.output.DeleteSoapMockResponseOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class DeleteSoapMockResponseService extends AbstractSoapProjectProcessor implements Service<DeleteSoapMockResponseInput, DeleteSoapMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<DeleteSoapMockResponseOutput> process(final Task<DeleteSoapMockResponseInput> task) {
        final DeleteSoapMockResponseInput input = task.getInput();
        final SoapOperation soapOperation = findSoapOperationType(input.getSoapProjectId(), input.getSoapPortId(), input.getSoapOperationId());
        SoapMockResponse foundSoapMockResponse = null;
        for(SoapMockResponse soapMockResponse : soapOperation.getSoapMockResponses()){
            if(soapMockResponse.getId().equals(input.getSoapMockResponseId())){
                foundSoapMockResponse = soapMockResponse;
                break;
            }
        }

        soapOperation.getSoapMockResponses().remove(foundSoapMockResponse);
        save(input.getSoapProjectId());
        return createResult(new DeleteSoapMockResponseOutput());
    }
}
