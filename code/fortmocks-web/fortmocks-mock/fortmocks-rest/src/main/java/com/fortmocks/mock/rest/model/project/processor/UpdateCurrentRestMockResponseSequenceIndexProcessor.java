package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestMethod;
import com.fortmocks.mock.rest.model.project.processor.message.input.UpdateCurrentRestMockResponseSequenceIndexInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.UpdateCurrentRestMockResponseSequenceIndexOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class UpdateCurrentRestMockResponseSequenceIndexProcessor extends AbstractRestProjectProcessor implements Processor<UpdateCurrentRestMockResponseSequenceIndexInput, UpdateCurrentRestMockResponseSequenceIndexOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<UpdateCurrentRestMockResponseSequenceIndexOutput> process(final Task<UpdateCurrentRestMockResponseSequenceIndexInput> task) {
        final UpdateCurrentRestMockResponseSequenceIndexInput input = task.getInput();
        final RestMethod restMethod = findRestMethodByRestMethodId(input.getRestMethodId());
        final Long restProjectId = findRestProjectIdForRestMethod(input.getRestMethodId());
        restMethod.setCurrentResponseSequenceIndex(input.getCurrentRestMockResponseSequenceIndex());
        save(restProjectId);
        return createResult(new UpdateCurrentRestMockResponseSequenceIndexOutput());
    }
}
