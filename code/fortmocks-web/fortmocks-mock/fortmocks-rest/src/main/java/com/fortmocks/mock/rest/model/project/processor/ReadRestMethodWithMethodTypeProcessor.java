package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestMethod;
import com.fortmocks.mock.rest.model.project.domain.RestResource;
import com.fortmocks.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.ReadRestMethodWithMethodTypeInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.ReadRestMethodWithMethodTypeOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class ReadRestMethodWithMethodTypeProcessor extends AbstractRestProjectProcessor implements Processor<ReadRestMethodWithMethodTypeInput, ReadRestMethodWithMethodTypeOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadRestMethodWithMethodTypeOutput> process(final Task<ReadRestMethodWithMethodTypeInput> task) {
        final ReadRestMethodWithMethodTypeInput input = task.getInput();
        final String[] restResourceUriParts = input.getRestResourceUri().split(SLASH);
        final RestResource restResource = findRestResourceType(input.getRestProjectId(), input.getRestApplicationId(), restResourceUriParts);
        RestMethodDto restMethodDto = null;
        if(restResource != null){
            for(RestMethod restMethod : restResource.getRestMethods()){
                if(input.getRestMethodType().equals(restMethod.getRestMethodType())) {
                    restMethodDto = mapper.map(restMethod, RestMethodDto.class);
                }
            }
        }

        return createResult(new ReadRestMethodWithMethodTypeOutput(restMethodDto));
    }
}
