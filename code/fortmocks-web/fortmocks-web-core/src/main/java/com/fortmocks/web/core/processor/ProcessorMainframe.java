package com.fortmocks.web.core.processor;

import com.fortmocks.core.model.*;
import com.fortmocks.core.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Component
public class ProcessorMainframe {

    @Autowired
    private ProcessorRegistry processorRegistry;

    private static final String UNKNOWN_USER = "Unknown";

    public <I extends Input, O extends Output> O process(I input){
        final Processor<I,O> processor = processorRegistry.getProcessor(input);
        final Task<I> task = new Task<I>();
        task.setInput(input);
        task.setExecuter(getLoggedInUsername());
        final Result<O> result = processor.process(task);
        return result.getOutput();
    }

    /**
     * Get the current logged in user username
     * @return The username of the current logged in user
     * @see User
     */
    protected String getLoggedInUsername(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return UNKNOWN_USER; // Should never happened except during unit tests
        }
        return authentication.getName();
    }

}
