package com.fortmocks.web.core.model.configuration.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.configuration.dto.ConfigurationGroupDto;
import com.fortmocks.core.model.configuration.message.FindAllConfigurationGroupsInput;
import com.fortmocks.core.model.configuration.message.FindAllConfigurationGroupsOutput;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.message.FindAllUsersInput;
import com.fortmocks.core.model.user.message.FindAllUsersOutput;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class FindAllConfigurationGroupsProcessor extends AbstractConfigurationGroupProcessor implements Processor<FindAllConfigurationGroupsInput, FindAllConfigurationGroupsOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<FindAllConfigurationGroupsOutput> process(final Task<FindAllConfigurationGroupsInput> task) {
        final List<ConfigurationGroupDto> configurationGroups = findAll();
        final FindAllConfigurationGroupsOutput output = new FindAllConfigurationGroupsOutput();
        output.setConfigurationGroups(configurationGroups);
        return createResult(output);
    }
}
