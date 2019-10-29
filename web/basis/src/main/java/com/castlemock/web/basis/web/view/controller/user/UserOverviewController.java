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

package com.castlemock.web.basis.web.view.controller.user;

import com.castlemock.core.basis.model.user.domain.Role;
import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.core.basis.service.user.input.ReadAllUsersInput;
import com.castlemock.core.basis.service.user.output.ReadAllUsersOutput;
import com.castlemock.web.basis.web.view.controller.AbstractViewController;
import com.castlemock.web.basis.web.view.controller.MenuItem;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * The UserOverviewController provides functionality to retrieve all the
 * users registered in the database.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/user")
public class UserOverviewController extends AbstractViewController {

    private static final String PAGE = "basis/user/userOverview";


    /**
     * The method retrieves all the users in the database and creates a view that
     * displays all retrieved users
     * @return A view that displays all the users registered in the database.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView defaultPage() {
        final ReadAllUsersOutput readAllUsersOutput = serviceProcessor.process(new ReadAllUsersInput());
        final List<User> users = readAllUsersOutput.getUsers();
        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(USERS, users);
        model.addObject(ROLES, Role.values());
        model.addObject(COMMAND, new User());
        model.addObject(SELECTED_MENU, MenuItem.USER);
        return model;
    }

}