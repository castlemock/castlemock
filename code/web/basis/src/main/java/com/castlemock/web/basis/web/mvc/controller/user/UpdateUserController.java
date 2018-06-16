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

package com.castlemock.web.basis.web.mvc.controller.user;

import com.castlemock.core.basis.model.user.domain.Role;
import com.castlemock.core.basis.model.user.domain.Status;
import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.core.basis.service.user.input.ReadUserByUsernameInput;
import com.castlemock.core.basis.service.user.input.ReadUserInput;
import com.castlemock.core.basis.service.user.input.UpdateUserInput;
import com.castlemock.core.basis.service.user.output.ReadUserByUsernameOutput;
import com.castlemock.core.basis.service.user.output.ReadUserOutput;
import com.castlemock.core.basis.service.user.output.UpdateUserOutput;
import com.castlemock.web.basis.service.user.UserDetailSecurityService;
import com.castlemock.web.basis.web.mvc.controller.AbstractViewController;
import com.castlemock.web.basis.web.mvc.controller.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
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
@ConditionalOnExpression("${server.mode.demo} == false")
public class UpdateUserController extends AbstractViewController {

    private static final String PAGE = "basis/user/updateUser";

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
    public ModelAndView defaultPage(@PathVariable final String userId) {
        final ReadUserOutput readUserOutput = serviceProcessor.process(new ReadUserInput(userId));
        final User userDto = readUserOutput.getUser();
        userDto.setPassword(EMPTY);
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(ROLES, Role.values());
        model.addObject(USER_STATUSES, Status.values());
        model.addObject(COMMAND, userDto);
        model.addObject(SELECTED_MENU, MenuItem.USER);
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
    public ModelAndView update(@PathVariable final String userId, @ModelAttribute final User updatedUser) {
        final String loggedInUsername = getLoggedInUsername();
        final ReadUserByUsernameOutput readUserByUsernameOutput = serviceProcessor.process(new ReadUserByUsernameInput(loggedInUsername));
        final User loggedInUser = readUserByUsernameOutput.getUser();
        final UpdateUserOutput updateUserOutput = serviceProcessor.process(new UpdateUserInput(userId, updatedUser));
        final User savedUser = updateUserOutput.getUpdatedUser();

        // Update the current logged in user if the username has been updated
        if(loggedInUser.getId().equals(userId) && !savedUser.getUsername().equals(loggedInUsername)){
            userDetailSecurityService.updateCurrentLoggedInUser(savedUser.getUsername());
        }

        return redirect("/user/" + userId);
    }


}