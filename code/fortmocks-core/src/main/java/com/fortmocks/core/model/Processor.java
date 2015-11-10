package com.fortmocks.core.model;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public interface Processor<I extends Input, O extends Output> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    Result<O> process(Task<I> task);

}
