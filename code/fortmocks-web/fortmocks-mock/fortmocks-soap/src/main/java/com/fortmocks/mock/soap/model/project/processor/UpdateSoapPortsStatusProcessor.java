package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.UpdateSoapOperationsStatusInput;
import com.fortmocks.mock.soap.model.project.processor.message.input.UpdateSoapPortsStatusInput;
import com.fortmocks.mock.soap.model.project.processor.message.input.UpdateSoapProjectInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.UpdateSoapOperationsStatusOutput;
import com.fortmocks.mock.soap.model.project.processor.message.output.UpdateSoapPortsStatusOutput;
import com.fortmocks.mock.soap.model.project.processor.message.output.UpdateSoapProjectOutput;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class UpdateSoapPortsStatusProcessor extends AbstractSoapProjectProcessor implements Processor<UpdateSoapPortsStatusInput, UpdateSoapPortsStatusOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<UpdateSoapPortsStatusOutput> process(final Task<UpdateSoapPortsStatusInput> task) {
        final UpdateSoapPortsStatusInput input = task.getInput();
        final List<SoapOperation> soapOperations = findSoapOperationType(input.getSoapProjectId(), input.getSoapPortId());
        for(SoapOperation soapOperation : soapOperations){
            soapOperation.setSoapOperationStatus(input.getSoapOperationStatus());
        }
        save(input.getSoapProjectId());
        return createResult(new UpdateSoapPortsStatusOutput());
    }
}
