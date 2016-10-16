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

package com.castlemock.web.basis.web.mvc.controller.event;

import com.castlemock.web.basis.model.event.service.EventServiceFacadeImpl;
import com.castlemock.web.basis.web.mvc.controller.AbstractViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The {@link ClearEventController} provides the functionality to clear all the logs
 * from the server.
 * @author Karl Dahlgren
 * @since 1.7
 */
@Controller
@Scope("request")
@RequestMapping("/web/event")
public class ClearEventController extends AbstractViewController {

    private static final String PAGE = "basis/event/clearEvents";

    @Autowired
    private EventServiceFacadeImpl eventServiceFacade;

    /**
     * The method creates a confirmation page for clearing all the logs.
     * @return A confirmation view to clear all logs
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "clear", method = RequestMethod.GET)
    public ModelAndView getClearAllConfirmationPage() {
        final ModelAndView model = createPartialModelAndView(PAGE);
        return model;
    }

    /**
     * The method clear all the logs.
     * @return A view that redirects the user to the event page
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "clear/confirm", method = RequestMethod.GET)
    public ModelAndView clearAll() {
        eventServiceFacade.clearAll();
        return redirect("/event");
    }

}