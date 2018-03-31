/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.basis.web.rest.controller;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.Output;
import com.castlemock.web.basis.web.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

public abstract class AbstractRestController extends AbstractController {


    protected <O extends Output, I extends Input> O process(final I input,
                                                            final HttpServletRequest request,
                                                            final HttpServletResponse response,
                                                            final Principal principal){

        return this.serviceProcessor.process(input);
    }

}
