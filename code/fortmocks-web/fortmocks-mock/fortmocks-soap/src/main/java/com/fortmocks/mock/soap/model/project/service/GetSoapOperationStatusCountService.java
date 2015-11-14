package com.fortmocks.mock.soap.model.project.service;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapOperationStatus;
import com.fortmocks.mock.soap.model.project.domain.SoapPort;
import com.fortmocks.mock.soap.model.project.service.message.input.GetSoapOperationStatusCountInput;
import com.fortmocks.mock.soap.model.project.service.message.output.GetSoapOperationStatusCountOutput;

import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class GetSoapOperationStatusCountService extends AbstractSoapProjectProcessor implements Service<GetSoapOperationStatusCountInput, GetSoapOperationStatusCountOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
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
