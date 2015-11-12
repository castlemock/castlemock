package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestMethod;
import com.fortmocks.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.ReadRestMethodInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.ReadRestMethodOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class ReadRestMethodProcessor extends AbstractRestProjectProcessor implements Processor<ReadRestMethodInput, ReadRestMethodOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadRestMethodOutput> process(final Task<ReadRestMethodInput> task) {
        final ReadRestMethodInput input = task.getInput();
        final RestMethod restMethod = findRestMethodType(input.getRestProjectId(), input.getRestApplicationId(), input.getRestResourceId(), input.getRestMethodId());
        final RestMethodDto restMethodDto = restMethod != null ? mapper.map(restMethod, RestMethodDto.class) : null;
        return createResult(new ReadRestMethodOutput(restMethodDto));
    }
}
