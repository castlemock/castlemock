package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestMethod;
import com.fortmocks.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.UpdateRestMethodInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.UpdateRestMethodOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class UpdateRestMethodProcessor extends AbstractRestProjectProcessor implements Processor<UpdateRestMethodInput, UpdateRestMethodOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<UpdateRestMethodOutput> process(final Task<UpdateRestMethodInput> task) {
        final UpdateRestMethodInput input = task.getInput();
        final RestMethodDto updatedRestMethod = input.getRestMethod();
        final RestMethod restMethod = findRestMethodType(input.getRestProjectId(), input.getRestApplicationId(), input.getRestResourceId(), input.getRestMethodId());
        restMethod.setName(updatedRestMethod.getName());
        restMethod.setRestMethodType(updatedRestMethod.getRestMethodType());
        restMethod.setRestResponseStrategy(updatedRestMethod.getRestResponseStrategy());
        restMethod.setRestMethodStatus(updatedRestMethod.getRestMethodStatus());
        restMethod.setForwardedEndpoint(updatedRestMethod.getForwardedEndpoint());
        save(input.getRestProjectId());
        return createResult(new UpdateRestMethodOutput());
    }
}
