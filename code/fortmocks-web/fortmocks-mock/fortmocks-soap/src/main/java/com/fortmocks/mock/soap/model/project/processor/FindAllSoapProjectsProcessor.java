package com.fortmocks.mock.soap.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.model.project.message.FindAllSoapProjectsInput;
import com.fortmocks.mock.soap.model.project.message.FindAllSoapProjectsOutput;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class FindAllSoapProjectsProcessor extends AbstractSoapProjectProcessor implements Processor<FindAllSoapProjectsInput, FindAllSoapProjectsOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<FindAllSoapProjectsOutput> process(final Task<FindAllSoapProjectsInput> task) {
        final List<SoapProjectDto> soapProjects = findAll();
        final FindAllSoapProjectsOutput output = new FindAllSoapProjectsOutput();
        output.setSoapProjects(soapProjects);
        return createResult(output);
    }
}
