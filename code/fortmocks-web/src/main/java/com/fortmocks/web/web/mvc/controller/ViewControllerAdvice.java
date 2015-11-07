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

package com.fortmocks.web.web.mvc.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * The GlobalControllerExceptionManager handles all the uncaught web exceptions
 * @author Karl Dahlgren
 * @since 1.0
 */
@ControllerAdvice
public class ViewControllerAdvice extends AbstractViewController {

    private static final String PAGE = "core/error";
    private static final String TITLE = "title";
    private static final String MESSAGE = "message";
    private static final Logger LOGGER = Logger.getLogger(ViewControllerAdvice.class);

    /**
     * Handles uncaught exceptions and creates an error view that will be displayed to the user.
     * @param request The request that originally caused the exception
     * @param exception The uncaught exception
     * @return Returns a view that displays the exception message
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorViewHandler(HttpServletRequest request, Exception exception)  {
        LOGGER.error("The following request failed: " + request.getRequestURI() + " (" + request.getMethod() + ")");
        LOGGER.error(exception.getMessage(), exception);
        ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(TITLE, "An error occurred");
        model.addObject(MESSAGE, exception.getMessage());
        return model;
    }

}
