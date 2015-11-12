package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestApplication;
import com.fortmocks.mock.rest.model.project.domain.RestProject;
import com.fortmocks.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.CreateRestApplicationsInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.CreateRestApplicationsOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class CreateRestApplicationsProcessor extends AbstractRestProjectProcessor implements Processor<CreateRestApplicationsInput, CreateRestApplicationsOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<CreateRestApplicationsOutput> process(final Task<CreateRestApplicationsInput> task) {
        final CreateRestApplicationsInput input = task.getInput();
        final RestProject restProject = findType(input.getRestProjectId());

        for(RestApplicationDto restApplicationDto : input.getRestApplications()){
            final Long restApplicationId = getNextRestApplicationId();
            restApplicationDto.setId(restApplicationId);

            for(RestResourceDto restResourceDto : restApplicationDto.getRestResources()){
                final Long restResourceId = getNextRestResourceId();
                restResourceDto.setId(restResourceId);

                for(RestMethodDto restMethodDto : restResourceDto.getRestMethods()){
                    final Long restMethodId = getNextRestMethodId();
                    restMethodDto.setId(restMethodId);
                }
            }
            RestApplication restApplication = mapper.map(restApplicationDto, RestApplication.class);
            restProject.getRestApplications().add(restApplication);
        }

        return createResult(new CreateRestApplicationsOutput());
    }
}
