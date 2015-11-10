package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.message.FindAllUsersInput;
import com.fortmocks.core.model.user.message.FindAllUsersOutput;
import com.fortmocks.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.mock.rest.model.project.message.FindAllRestProjectsInput;
import com.fortmocks.mock.rest.model.project.message.FindAllRestProjectsOutput;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class FindAllRestProjectsProcessor extends AbstractRestProjectProcessor implements Processor<FindAllRestProjectsInput, FindAllRestProjectsOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<FindAllRestProjectsOutput> process(final Task<FindAllRestProjectsInput> task) {
        final List<RestProjectDto> restProjects = findAll();
        final FindAllRestProjectsOutput output = new FindAllRestProjectsOutput();
        output.setRestProjects(restProjects);
        return createResult(output);
    }
}
