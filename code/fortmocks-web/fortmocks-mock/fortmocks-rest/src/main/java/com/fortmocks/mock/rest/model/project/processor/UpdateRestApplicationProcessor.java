package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestApplication;
import com.fortmocks.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.UpdateRestApplicationInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.UpdateRestApplicationOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class UpdateRestApplicationProcessor extends AbstractRestProjectProcessor implements Processor<UpdateRestApplicationInput, UpdateRestApplicationOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<UpdateRestApplicationOutput> process(final Task<UpdateRestApplicationInput> task) {
        final UpdateRestApplicationInput input = task.getInput();
        final RestApplication restApplication = findRestApplicationType(input.getRestProjectId(), input.getRestApplicationId());
        final RestApplicationDto updatedRestApplication = input.getRestApplication();
        restApplication.setName(updatedRestApplication.getName());
        save(input.getRestProjectId());
        return createResult(new UpdateRestApplicationOutput());
    }
}
