package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestResource;
import com.fortmocks.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.ReadRestResourceInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.ReadRestResourceOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class ReadRestResourceProcessor extends AbstractRestProjectProcessor implements Processor<ReadRestResourceInput, ReadRestResourceOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadRestResourceOutput> process(final Task<ReadRestResourceInput> task) {
        final ReadRestResourceInput input = task.getInput();
        final RestResource restResource = findRestResourceType(input.getRestProjectId(), input.getRestApplicationId(), input.getRestResourceId());
        final RestResourceDto restResourceDto = restResource != null ? mapper.map(restResource, RestResourceDto.class) : null;
        return createResult(new ReadRestResourceOutput(restResourceDto));
    }
}
