package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestApplication;
import com.fortmocks.mock.rest.model.project.domain.RestProject;
import com.fortmocks.mock.rest.model.project.dto.RestApplicationDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.DeleteRestApplicationsInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.DeleteRestApplicationsOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class DeleteRestApplicationsProcessor extends AbstractRestProjectProcessor implements Processor<DeleteRestApplicationsInput, DeleteRestApplicationsOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<DeleteRestApplicationsOutput> process(final Task<DeleteRestApplicationsInput> task) {
        final DeleteRestApplicationsInput input = task.getInput();
        final RestProject restProject = findType(input.getRestProjectId());
        for(final RestApplicationDto restApplicationDto : input.getRestApplications()){
            final RestApplication restApplication = findRestApplicationType(input.getRestProjectId(), restApplicationDto.getId());
            restProject.getRestApplications().remove(restApplication);
        }
        save(input.getRestProjectId());
        return createResult(new DeleteRestApplicationsOutput());
    }
}
