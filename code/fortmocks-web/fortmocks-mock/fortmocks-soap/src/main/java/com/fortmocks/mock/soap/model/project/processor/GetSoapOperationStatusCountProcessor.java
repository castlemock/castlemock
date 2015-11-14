package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.mock.soap.model.project.domain.SoapOperationStatus;
import com.fortmocks.mock.soap.model.project.domain.SoapPort;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.GetSoapOperationStatusCountInput;
import com.fortmocks.mock.soap.model.project.processor.message.input.ReadSoapOperationInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.GetSoapOperationStatusCountOutput;
import com.fortmocks.mock.soap.model.project.processor.message.output.ReadSoapOperationOutput;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class GetSoapOperationStatusCountProcessor extends AbstractSoapProjectProcessor implements Processor<GetSoapOperationStatusCountInput, GetSoapOperationStatusCountOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<GetSoapOperationStatusCountOutput> process(final Task<GetSoapOperationStatusCountInput> task) {
        final GetSoapOperationStatusCountInput input = task.getInput();
        final SoapPort soapPort = findSoapPortType(input.getSoapProjectId(), input.getSoapPortId());
        final Map<SoapOperationStatus, Integer> soapOperationStatuses = getSoapOperationStatusCount(soapPort.getSoapOperations());
        return createResult(new GetSoapOperationStatusCountOutput(soapOperationStatuses));
    }
}
