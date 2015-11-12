package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.CreateRestProjectInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.CreateRestProjectOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class CreateRestProjectProcessor extends AbstractRestProjectProcessor implements Processor<CreateRestProjectInput, CreateRestProjectOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<CreateRestProjectOutput> process(final Task<CreateRestProjectInput> task) {
        final CreateRestProjectInput input = task.getInput();
        final RestProjectDto restProject = input.getRestProject();
        final RestProjectDto savedRestProject = save(restProject);
        final CreateRestProjectOutput output = new CreateRestProjectOutput();
        output.setSavedRestProject(savedRestProject);
        return createResult(output);
    }
}
