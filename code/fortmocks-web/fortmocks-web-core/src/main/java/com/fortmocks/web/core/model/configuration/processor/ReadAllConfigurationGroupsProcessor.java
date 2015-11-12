package com.fortmocks.web.core.model.configuration.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.core.model.configuration.dto.ConfigurationGroupDto;
import com.fortmocks.core.model.configuration.processor.message.input.ReadAllConfigurationGroupsInput;
import com.fortmocks.core.model.configuration.processor.message.output.ReadAllConfigurationGroupsOutput;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class ReadAllConfigurationGroupsProcessor extends AbstractConfigurationGroupProcessor implements Processor<ReadAllConfigurationGroupsInput, ReadAllConfigurationGroupsOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadAllConfigurationGroupsOutput> process(final Task<ReadAllConfigurationGroupsInput> task) {
        final List<ConfigurationGroupDto> configurationGroups = findAll();
        final ReadAllConfigurationGroupsOutput output = new ReadAllConfigurationGroupsOutput();
        output.setConfigurationGroups(configurationGroups);
        return createResult(output);
    }
}
