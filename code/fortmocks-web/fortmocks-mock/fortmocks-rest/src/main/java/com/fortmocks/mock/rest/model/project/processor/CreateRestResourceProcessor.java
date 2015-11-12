package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestApplication;
import com.fortmocks.mock.rest.model.project.domain.RestResource;
import com.fortmocks.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.CreateRestResourceInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.CreateRestResourceOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class CreateRestResourceProcessor extends AbstractRestProjectProcessor implements Processor<CreateRestResourceInput, CreateRestResourceOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<CreateRestResourceOutput> process(final Task<CreateRestResourceInput> task) {
        final CreateRestResourceInput input = task.getInput();
        final RestApplication restApplication = findRestApplicationType(input.getRestProjectId(), input.getRestApplicationId());
        final Long restResourceId = getNextRestResourceId();
        final RestResource restResource = mapper.map(input.getRestResource(), RestResource.class);
        restResource.setId(restResourceId);
        restApplication.getRestResources().add(restResource);
        save(input.getRestProjectId());
        return createResult(new CreateRestResourceOutput(mapper.map(restResource, RestResourceDto.class)));
    }
}
