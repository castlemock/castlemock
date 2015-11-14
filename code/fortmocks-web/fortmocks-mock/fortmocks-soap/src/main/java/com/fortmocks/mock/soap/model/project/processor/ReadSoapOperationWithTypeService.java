package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.ReadSoapOperationWithTypeInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.ReadSoapOperationWithTypeOutput;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapOperationWithTypeService extends AbstractSoapProjectProcessor implements Service<ReadSoapOperationWithTypeInput, ReadSoapOperationWithTypeOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadSoapOperationWithTypeOutput> process(final Task<ReadSoapOperationWithTypeInput> task) {
        final ReadSoapOperationWithTypeInput input = task.getInput();
        final List<SoapOperation> soapOperations = findSoapOperationTypeWithSoapProjectId(input.getSoapProjectId());
        SoapOperationDto soapOperationDto = null;
        for(SoapOperation soapOperation : soapOperations){
            if(soapOperation.getUri().equals(input.getUri()) && soapOperation.getSoapOperationMethod().equals(input.getSoapOperationMethod()) && soapOperation.getSoapOperationType().equals(input.getType()) && soapOperation.getName().equalsIgnoreCase(input.getName())){
                soapOperationDto = mapper.map(soapOperation, SoapOperationDto.class);
            }
        }
        return createResult(new ReadSoapOperationWithTypeOutput(soapOperationDto));
    }
}
