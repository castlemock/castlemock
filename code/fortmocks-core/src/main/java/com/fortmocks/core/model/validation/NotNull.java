package com.fortmocks.core.model.validation;

import java.lang.annotation.*;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface NotNull {
}
