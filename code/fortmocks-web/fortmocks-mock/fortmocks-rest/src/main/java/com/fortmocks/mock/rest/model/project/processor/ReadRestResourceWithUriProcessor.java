package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestApplication;
import com.fortmocks.mock.rest.model.project.domain.RestResource;
import com.fortmocks.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.ReadRestResourceWithUriInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.ReadRestResourceWithUriOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class ReadRestResourceWithUriProcessor extends AbstractRestProjectProcessor implements Processor<ReadRestResourceWithUriInput, ReadRestResourceWithUriOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadRestResourceWithUriOutput> process(final Task<ReadRestResourceWithUriInput> task) {
        final ReadRestResourceWithUriInput input = task.getInput();
        final RestApplication restApplication = findRestApplicationType(input.getRestProjectId(), input.getRestApplicationId());
        final String resourceUri = input.getRestResourceUri();
        RestResourceDto restResourceDto = null;
        for(RestResource restResource : restApplication.getRestResources()){
            if(resourceUri.equalsIgnoreCase(restResource.getUri())){
                restResourceDto = restResource != null ? mapper.map(restResource, RestResourceDto.class) : null;
            }
        }
        return createResult(new ReadRestResourceWithUriOutput(restResourceDto));
    }
}
