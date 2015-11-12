package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestMethod;
import com.fortmocks.mock.rest.model.project.domain.RestResource;
import com.fortmocks.mock.rest.model.project.processor.message.input.DeleteRestMethodInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.DeleteRestMethodOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class DeleteRestMethodProcessor extends AbstractRestProjectProcessor implements Processor<DeleteRestMethodInput, DeleteRestMethodOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<DeleteRestMethodOutput> process(final Task<DeleteRestMethodInput> task) {
        final DeleteRestMethodInput input = task.getInput();
        final RestResource restResource = findRestResourceType(input.getRestProjectId(), input.getRestApplicationId(), input.getRestResourceId());
        final RestMethod restMethod = findRestMethodType(input.getRestProjectId(), input.getRestApplicationId(), input.getRestResourceId(), input.getRestMethodId());
        restResource.getRestMethods().remove(restMethod);
        save(input.getRestProjectId());
        return createResult(new DeleteRestMethodOutput());
    }
}
