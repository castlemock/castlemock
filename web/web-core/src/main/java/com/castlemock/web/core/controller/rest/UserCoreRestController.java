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

package com.castlemock.web.core.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.user.User;
import com.castlemock.service.core.user.input.*;
import com.castlemock.service.core.user.output.CreateUserOutput;
import com.castlemock.service.core.user.output.ReadAllUsersOutput;
import com.castlemock.service.core.user.output.ReadUserOutput;
import com.castlemock.service.core.user.output.UpdateUserOutput;
import com.castlemock.web.core.model.user.CreateUserRequest;
import com.castlemock.web.core.model.user.UpdateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/rest/core")
@Tag(name="Core - User", description="REST Operations for Castle Mock Core")
@ConditionalOnExpression("${server.mode.demo} == false")
public class UserCoreRestController extends AbstractRestController {

    public UserCoreRestController(final ServiceProcessor serviceProcessor){
        super(serviceProcessor);
    }

    @Operation(summary =  "Create user",
            description = "Create user. Required authorization: Admin.")
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<User> createUser(@RequestBody final CreateUserRequest request) {
        final CreateUserOutput output = serviceProcessor.process(CreateUserInput.builder()
                .email(request.getEmail())
                .role(request.getRole())
                .status(request.getStatus())
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(request.getPassword())
                .build());
        final User createdUser = output.getSavedUser()
                        .toBuilder()
                        .password(EMPTY)
                        .build();
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary =  "Update user",
            description = "Update user. Required authorization: Admin.")
    @RequestMapping(method = RequestMethod.PUT, value = "/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<User> updateUser(@PathVariable("userId") final String userId,
                    @RequestBody final UpdateUserRequest request) {
        final UpdateUserOutput output = serviceProcessor.process(UpdateUserInput.builder()
                .id(userId)
                .email(request.getEmail().orElse(null))
                .role(request.getRole())
                .status(request.getStatus())
                .fullName(request.getFullName().orElse(null))
                .username(request.getUsername())
                .password(request.getPassword().orElse(null))
                .build());
        return output.getUpdatedUser()
                .map(user -> user.toBuilder()
                        .password(EMPTY)
                        .build())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary =  "Get all users",
            description = "Get all users. Required authorization: Admin.")
    @RequestMapping(method = RequestMethod.GET, value = "/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<List<User>> getUsers() {
        final ReadAllUsersOutput output = serviceProcessor.process(new ReadAllUsersInput());
        final List<User> users = output.getUsers()
                .stream()
                .map(user -> user.toBuilder()
                    .password(EMPTY)
                    .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @Operation(summary =  "Get user",
            description = "Get user. Required authorization: Admin.")
    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<User> getUser(@PathVariable("userId") final String userId) {
        final ReadUserOutput output = serviceProcessor.process(ReadUserInput.builder()
                .userId(userId)
                .build());
        return output.getUser()
                .map(user -> user.toBuilder()
                        .password(EMPTY)
                        .build())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary =  "Delete user", description = "Delete user. Required authorization: Admin.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody
    void deleteUser(@PathVariable("userId") final String userId) {
        this.serviceProcessor.process(DeleteUserInput.builder()
                .userId(userId)
                .build());
    }

}
