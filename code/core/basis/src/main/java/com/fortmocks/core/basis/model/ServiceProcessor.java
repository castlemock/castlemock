package com.fortmocks.core.basis.model;

/**
 * The ServiceProcessor is responsible for processing input message and route them to the service responsible
 * for processing and handling the incoming message. The ServiceProcessor is also responsible for validating
 * both the input and output message.
 * @author Karl Dahlgren
 * @since 1.0
 */
public interface ServiceProcessor {

    <I extends Input, O extends Output> O process(final I input);

}
