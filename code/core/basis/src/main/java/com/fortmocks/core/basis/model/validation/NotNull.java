/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fortmocks.core.basis.model.validation;

import java.lang.annotation.*;

/**
 * The NotNull annotation will be used to enforce that variables are not null in a {@link com.fortmocks.core.basis.model.Message}
 * class, such as an {@link com.fortmocks.core.basis.model.Input} or {@link com.fortmocks.core.basis.model.Output} message.
 * @author Karl Dahlgren
 * @since 1.0
 * @see com.fortmocks.core.basis.model.Message
 * @see com.fortmocks.core.basis.model.Input
 * @see com.fortmocks.core.basis.model.Output
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface NotNull {
}
