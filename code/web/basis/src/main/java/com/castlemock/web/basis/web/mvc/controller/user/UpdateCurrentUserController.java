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

import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.core.basis.model.user.service.message.input.ReadUserByUsernameInput;
import com.castlemock.core.basis.model.user.service.message.input.UpdateCurrentUserInput;
import com.castlemock.core.basis.model.user.service.message.output.ReadUserByUsernameOutput;
import com.castlemock.core.basis.model.user.service.message.output.UpdateCurrentUserOutput;
import com.castlemock.web.basis.model.user.service.UserDetailSecurityService;
import com.castlemock.web.basis.web.mvc.command.user.UpdateCurrentUserCommand;
import com.castlemock.web.basis.web.mvc.controller.AbstractViewController;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping("/web/me/update")
@ConditionalOnExpression("${server.mode.demo} == false")
public class UpdateCurrentUserController extends AbstractViewController {

    private static final String PAGE = "basis/user/updateCurrentUser";

    @Autowired
    private UserDetailSecurityService userDetailSecurityService;

    /**
     * The method returns a view that displays the editable information about the current user
     * @return A view that allows the user to modify their own information
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        final String loggedInUsername = getLoggedInUsername();
        final ReadUserByUsernameOutput readUserByUsernameOutput = serviceProcessor.process(new ReadUserByUsernameInput(loggedInUsername));
        final User userDto = readUserByUsernameOutput.getUser();
        final ModelAndView model = createPartialModelAndView(PAGE);
        final UpdateCurrentUserCommand updateCurrentUserCommand = new UpdateCurrentUserCommand();
        updateCurrentUserCommand.setUser(userDto);
        model.addObject(COMMAND, updateCurrentUserCommand);
        return model;
    }

    /**
     * The method update provides functionality to update the current logged in user
     * @param updateCurrentUserCommand The updated values for the user that will be updated
     * @return Redirects the user back to the current user page
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute final UpdateCurrentUserCommand updateCurrentUserCommand) {
        final String loggedInUser = getLoggedInUsername();
        final User updatedUser = updateCurrentUserCommand.getUser();
        final String userPassword = updatedUser.getPassword();
        if(userPassword != null){
            Preconditions.checkArgument(userPassword.equals(updateCurrentUserCommand.getVerifiedPassword()), "The password and the verified password does not match");
        }
        final UpdateCurrentUserOutput updateCurrentUserOutput = serviceProcessor.process(new UpdateCurrentUserInput(updatedUser));
        final User savedUser = updateCurrentUserOutput.getUpdatedUser();

        // Updates the logged in username if the updated user received a new username
        if(!savedUser.getUsername().equals(loggedInUser)){
            userDetailSecurityService.updateCurrentLoggedInUser(savedUser.getUsername());
        }
        return redirect("/me");
    }


}