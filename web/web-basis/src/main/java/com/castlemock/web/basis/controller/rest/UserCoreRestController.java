/*
 * Copyright 2019 Karl Dahlgren
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

package com.castlemock.web.basis.controller.rest;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.core.basis.service.user.input.*;
import com.castlemock.core.basis.service.user.output.CreateUserOutput;
import com.castlemock.core.basis.service.user.output.ReadAllUsersOutput;
import com.castlemock.core.basis.service.user.output.ReadUserOutput;
import com.castlemock.core.basis.service.user.output.UpdateUserOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/rest/core")
@Api(value="Core", description="REST Operations for Castle Mock Core", tags = {"Core"})
@ConditionalOnExpression("${server.mode.demo} == false")
public class UserCoreRestController extends AbstractRestController {

    public UserCoreRestController(final ServiceProcessor serviceProcessor){
        super(serviceProcessor);
    }

    @ApiOperation(value = "Create user",response = User.class,
            notes = "Create user. Required authorization: Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created user")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<User> createUser(@RequestBody final User user) {
        final CreateUserOutput output = serviceProcessor.process(CreateUserInput.builder()
                .user(user)
                .build());
        final User createdUser = output.getSavedUser();
        createdUser.setPassword(EMPTY);
        return ResponseEntity.ok(createdUser);
    }

    @ApiOperation(value = "Update user",response = User.class,
            notes = "Update user. Required authorization: Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated user")
    })
    @RequestMapping(method = RequestMethod.PUT, value = "/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<User> updateUser(@PathVariable("userId") final String userId,
                    @RequestBody final User user) {
        final UpdateUserOutput output = serviceProcessor.process(UpdateUserInput.builder()
                .user(user)
                .userId(userId)
                .build());
        final User updatedUser = output.getUpdatedUser();
        updatedUser.setPassword(EMPTY);
        return ResponseEntity.ok(updatedUser);
    }

    @ApiOperation(value = "Get all users",response = User.class,
            notes = "Get all users. Required authorization: Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all users")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<List<User>> getUsers() {
        final ReadAllUsersOutput output = serviceProcessor.process(new ReadAllUsersInput());
        final List<User> users = output.getUsers();
        users.forEach(user -> user.setPassword(EMPTY));
        return ResponseEntity.ok(users);
    }

    @ApiOperation(value = "Get user",response = User.class,
            notes = "Get user. Required authorization: Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<User> getUser(@PathVariable("userId") final String userId) {
        final ReadUserOutput output = serviceProcessor.process(ReadUserInput.builder()
                .userId(userId)
                .build());
        final User user = output.getUser();
        user.setPassword(EMPTY);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Delete user", notes = "Delete user. Required authorization: Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted user")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    void deleteUser(@PathVariable("userId") final String userId) {
        serviceProcessor.process(DeleteUserInput.builder()
                .userId(userId)
                .build());
    }

}
