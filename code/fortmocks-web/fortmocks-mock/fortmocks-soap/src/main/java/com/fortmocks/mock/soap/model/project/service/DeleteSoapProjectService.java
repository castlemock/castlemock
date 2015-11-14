package com.fortmocks.mock.soap.model.project.service;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.service.message.input.DeleteSoapProjectInput;
import com.fortmocks.mock.soap.model.project.service.message.output.DeleteSoapProjectOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class DeleteSoapProjectService extends AbstractSoapProjectProcessor implements Service<DeleteSoapProjectInput, DeleteSoapProjectOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<DeleteSoapProjectOutput> process(final Task<DeleteSoapProjectInput> task) {
        final DeleteSoapProjectInput input = task.getInput();
        final Long soapProjectId = input.getSoapProjectId();
        delete(soapProjectId);
        return createResult(new DeleteSoapProjectOutput());
    }
}
