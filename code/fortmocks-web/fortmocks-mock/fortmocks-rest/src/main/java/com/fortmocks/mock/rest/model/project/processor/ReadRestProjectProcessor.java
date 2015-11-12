package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.ReadRestProjectInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.ReadRestProjectOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class ReadRestProjectProcessor extends AbstractRestProjectProcessor implements Processor<ReadRestProjectInput, ReadRestProjectOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadRestProjectOutput> process(final Task<ReadRestProjectInput> task) {
        final ReadRestProjectInput input = task.getInput();
        final RestProjectDto restProject = find(input.getRestProjectId());
        final ReadRestProjectOutput output = new ReadRestProjectOutput();
        output.setRestProject(restProject);
        return createResult(output);
    }
}
