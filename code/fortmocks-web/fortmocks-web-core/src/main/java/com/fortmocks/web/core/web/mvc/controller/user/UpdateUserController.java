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

import com.fortmocks.core.model.user.Role;
import com.fortmocks.core.model.user.Status;
import com.fortmocks.core.model.user.dto.UserDto;
import com.fortmocks.core.model.user.message.*;
import com.fortmocks.web.core.model.user.service.UserDetailSecurityService;
import com.fortmocks.web.core.web.mvc.controller.AbstractViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The UpdateUserController controller provides functionality to update
 * a specific user with updated attributes
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/user")
public class UpdateUserController extends AbstractViewController {

    private static final String PAGE = "core/user/updateUser";

    @Autowired
    private UserDetailSecurityService userDetailSecurityService;
    /**
     * The method retrieves a user from the database and creates a view to display the
     * retrieved user.
     * @param userId The id of the user that should be retrieved
     * @return A view that displays the user
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{userId}/update", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final Long userId) {
        final FindUserInput findUserInput = new FindUserInput();
        findUserInput.setUserId(userId);
        final FindUserOutput findUserOutput = processorMainframe.process(findUserInput);
        final UserDto userDto = findUserOutput.getUser();
        userDto.setPassword(EMPTY);
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(ROLES, Role.values());
        model.addObject(USER_STATUSES, Status.values());
        model.addObject(COMMAND, userDto);
        return model;
    }

    /**
     * The method update provides functionality to update a specific user.
     * @param userId The id of the user that will be updated
     * @param updatedUser The updated values for the user that will be updated
     * @return Redirects the user back to the modified user page
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{userId}/update", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable final Long userId, @ModelAttribute final UserDto updatedUser) {
        final String loggedInUsername = getLoggedInUsername();
        final FindUserByUsernameInput findUserByUsernameInput = new FindUserByUsernameInput();
        findUserByUsernameInput.setUsername(loggedInUsername);
        final FindUserByUsernameOutput findUserByUsernameOutput = processorMainframe.process(findUserByUsernameInput);
        final UserDto loggedInUser = findUserByUsernameOutput.getUser();

        final UpdateUserInput updateUserInput = new UpdateUserInput();
        updateUserInput.setUserId(userId);
        updateUserInput.setUser(updatedUser);

        final UpdateUserOutput updateUserOutput = processorMainframe.process(updateUserInput);
        final UserDto savedUser = updateUserOutput.getUpdatedUser();

        // Update the current logged in user if the username has been updated
        if(loggedInUser.getId().equals(userId) && !savedUser.getUsername().equals(loggedInUsername)){
            userDetailSecurityService.updateCurrentLoggedInUser(savedUser.getUsername());
        }

        return redirect("/user/" + userId);
    }


}