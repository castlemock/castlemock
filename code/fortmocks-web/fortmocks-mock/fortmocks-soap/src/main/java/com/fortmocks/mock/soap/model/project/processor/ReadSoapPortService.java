package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.domain.SoapPort;
import com.fortmocks.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.ReadSoapPortInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.ReadSoapPortOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadSoapPortService extends AbstractSoapProjectProcessor implements Service<ReadSoapPortInput, ReadSoapPortOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadSoapPortOutput> process(final Task<ReadSoapPortInput> task) {
        final ReadSoapPortInput input = task.getInput();
        final SoapPort soapPort = findSoapPortType(input.getSoapProjectId(), input.getSoapPortId());
        final SoapPortDto soapPortDto = soapPort != null ? mapper.map(soapPort, SoapPortDto.class) : null;
        return createResult(new ReadSoapPortOutput(soapPortDto));
    }
}
