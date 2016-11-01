/*
 * Copyright 2016 Karl Dahlgren
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

package com.castlemock.web.basis.web.mvc.controller.system;

import com.castlemock.core.basis.model.system.service.dto.SystemInformationDto;
import com.castlemock.core.basis.model.system.service.message.input.GetSystemInformationInput;
import com.castlemock.core.basis.model.system.service.message.output.GetSystemInformationOutput;
import com.castlemock.web.basis.web.mvc.controller.AbstractViewController;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The controller {@link SystemController} provides functionality to retrieve information about
 * the system.
 * @author Karl Dahlgren
 * @since 1.7
 */
@Controller
@Scope("request")
@RequestMapping("/web/system")
public class SystemController extends AbstractViewController {

    private static final String PAGE = "basis/system/system";
    private static final String SYSTEM_INFORMATION = "systemInformation";

    /**
     * The method creates a view that displays information about the system which the application
     * is running on.
     * @return View with all the logged information
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        final GetSystemInformationOutput output = serviceProcessor.process(new GetSystemInformationInput());
        final SystemInformationDto systemInformationDto = output.getSystemInformation();
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(SYSTEM_INFORMATION, systemInformationDto);
        return model;
    }

}