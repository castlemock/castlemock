package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestApplication;
import com.fortmocks.mock.rest.model.project.domain.RestProject;
import com.fortmocks.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.CreateRestApplicationInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.CreateRestApplicationOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class CreateRestApplicationProcessor extends AbstractRestProjectProcessor implements Processor<CreateRestApplicationInput, CreateRestApplicationOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<CreateRestApplicationOutput> process(final Task<CreateRestApplicationInput> task) {
        final CreateRestApplicationInput input = task.getInput();
        final RestProject restProject = findType(input.getRestProjectId());
        final Long restApplicationId = getNextRestApplicationId();
        final RestApplicationDto restApplication = input.getRestApplication();
        restApplication.setId(restApplicationId);

        final RestApplication createdRestApplication = mapper.map(restApplication, RestApplication.class);
        restProject.getRestApplications().add(createdRestApplication);
        save(input.getRestProjectId());
        return createResult(new CreateRestApplicationOutput(mapper.map(createdRestApplication, RestApplicationDto.class)));
    }
}
