package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapPort;
import com.fortmocks.mock.soap.model.project.domain.SoapProject;
import com.fortmocks.mock.soap.model.project.processor.message.input.DeleteSoapPortInput;
import com.fortmocks.mock.soap.model.project.processor.message.input.DeleteSoapProjectInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.DeleteSoapPortOutput;
import com.fortmocks.mock.soap.model.project.processor.message.output.DeleteSoapProjectOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class DeleteSoapPortProcessor extends AbstractSoapProjectProcessor implements Processor<DeleteSoapPortInput, DeleteSoapPortOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<DeleteSoapPortOutput> process(final Task<DeleteSoapPortInput> task) {
        final DeleteSoapPortInput input = task.getInput();
        final SoapProject soapProject = findType(input.getSoapProjectId());
        final SoapPort soapPort = findSoapPortType(input.getSoapProjectId(), input.getSoapPortId());
        soapProject.getSoapPorts().remove(soapPort);
        save(input.getSoapProjectId());
        return createResult(new DeleteSoapPortOutput());
    }
}
