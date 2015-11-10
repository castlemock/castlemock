package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.message.FindUserInput;
import com.fortmocks.core.model.user.message.FindUserOutput;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.mock.rest.model.project.message.FindRestProjectInput;
import com.fortmocks.mock.rest.model.project.message.FindRestProjectOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class FindRestProjectProcessor extends AbstractRestProjectProcessor implements Processor<FindRestProjectInput, FindRestProjectOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<FindRestProjectOutput> process(final Task<FindRestProjectInput> task) {
        final FindRestProjectInput input = task.getInput();
        final RestProjectDto restProject = find(input.getRestProjectId());
        final FindRestProjectOutput output = new FindRestProjectOutput();
        output.setRestProject(restProject);
        return createResult(output);
    }
}
