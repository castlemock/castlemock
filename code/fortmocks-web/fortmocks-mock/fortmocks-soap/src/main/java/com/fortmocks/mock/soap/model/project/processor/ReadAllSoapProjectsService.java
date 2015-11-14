package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.model.project.processor.message.input.ReadAllSoapProjectsInput;
import com.fortmocks.mock.soap.model.project.processor.message.output.ReadAllSoapProjectsOutput;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadAllSoapProjectsService extends AbstractSoapProjectProcessor implements Service<ReadAllSoapProjectsInput, ReadAllSoapProjectsOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadAllSoapProjectsOutput> process(final Task<ReadAllSoapProjectsInput> task) {
        final List<SoapProjectDto> soapProjects = findAll();
        final ReadAllSoapProjectsOutput output = new ReadAllSoapProjectsOutput();
        output.setSoapProjects(soapProjects);
        return createResult(output);
    }
}
