package com.fortmocks.core.basis.model.validation.validator;

import com.fortmocks.core.basis.model.Message;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class Validator {

    public abstract <M extends Message> void validateMessage(final M message);

}
