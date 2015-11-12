package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestApplication;
import com.fortmocks.mock.rest.model.project.domain.RestResource;
import com.fortmocks.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.DeleteRestResourcesInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.DeleteRestResourcesOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class DeleteRestResourcesProcessor extends AbstractRestProjectProcessor implements Processor<DeleteRestResourcesInput, DeleteRestResourcesOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<DeleteRestResourcesOutput> process(final Task<DeleteRestResourcesInput> task) {
        final DeleteRestResourcesInput input = task.getInput();
        final RestApplication restApplication = findRestApplicationType(input.getRestProjectId(), input.getRestApplicationId());
        for(final RestResourceDto restResourceDto : input.getRestResources()){
            final RestResource restResource = findRestResourceType(input.getRestProjectId(), input.getRestApplicationId(), restResourceDto.getId());
            restApplication.getRestResources().remove(restResource);
        }

        save(input.getRestProjectId());
        return createResult(new DeleteRestResourcesOutput());
    }
}
