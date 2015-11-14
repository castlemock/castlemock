package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.domain.SoapPort;
import com.fortmocks.mock.soap.model.project.domain.SoapProject;
import com.fortmocks.mock.soap.model.project.processor.message.input.DeleteSoapMockResponseInput;
import com.fortmocks.mock.soap.model.project.processor.message.input.DeleteSoapPortInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.DeleteSoapMockResponseOutput;
import com.fortmocks.mock.soap.model.project.processor.message.output.DeleteSoapPortOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class DeleteSoapMockResponseProcessor extends AbstractSoapProjectProcessor implements Processor<DeleteSoapMockResponseInput, DeleteSoapMockResponseOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
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
