package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestApplication;
import com.fortmocks.mock.rest.model.project.domain.RestResource;
import com.fortmocks.mock.rest.model.project.processor.message.input.DeleteRestResourceInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.DeleteRestResourceOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class DeleteRestResourceProcessor extends AbstractRestProjectProcessor implements Processor<DeleteRestResourceInput, DeleteRestResourceOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<DeleteRestResourceOutput> process(final Task<DeleteRestResourceInput> task) {
        final DeleteRestResourceInput input = task.getInput();
        final RestApplication restApplication = findRestApplicationType(input.getRestProjectId(), input.getRestApplicationId());
        final RestResource restResource = findRestResourceType(input.getRestProjectId(), input.getRestApplicationId(), input.getRestResourceId());
        restApplication.getRestResources().remove(restResource);
        save(input.getRestProjectId());
        return createResult(new DeleteRestResourceOutput());
    }
}
