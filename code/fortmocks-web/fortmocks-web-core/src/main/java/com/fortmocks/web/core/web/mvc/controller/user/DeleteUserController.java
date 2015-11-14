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

package com.fortmocks.web.core.web.mvc.controller.user;

import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.service.message.input.DeleteUserInput;
import com.fortmocks.core.model.user.service.message.input.ReadUserInput;
import com.fortmocks.core.model.user.service.message.output.ReadUserOutput;
import com.fortmocks.web.core.web.mvc.controller.AbstractViewController;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The controller DeleteUserController is a controller that provides the
 * functionality to delete users
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/user")
public class DeleteUserController extends AbstractViewController {

    private static final String PAGE = "core/user/deleteUser";

    /**
     * The method retrieves a user from the database and creates a view to display the
     * retrieved user.
     * @param userId The id of the user that should be retrieved
     * @return A view that displays the user
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{userId}/delete", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final Long userId) {
        final ReadUserOutput readUserOutput = processorMainframe.process(new ReadUserInput(userId));
        final UserDto userDto = readUserOutput.getUser();
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(USER, userDto);
        return model;
    }

    /**
     * The method delete provides the functionality to delete a user from the database.
     * @param userId The id of the user that will be deleted
     * @return Redirects the user to the main user page that displays all the users.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{userId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable final Long userId) {
        processorMainframe.process(new DeleteUserInput(userId));
        return redirect("/user");
    }


}