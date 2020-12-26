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
import com.castlemock.core.basis.service.user.input.ReadUserByUsernameInput;
import com.castlemock.core.basis.service.user.input.UpdateCurrentUserInput;
import com.castlemock.core.basis.service.user.output.ReadUserByUsernameOutput;
import com.castlemock.core.basis.service.user.output.UpdateCurrentUserOutput;
import com.castlemock.web.basis.model.UpdateProfileRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/rest/core")
@Api(value="Core", description="REST Operations for Castle Mock Core", tags = {"Core"})
@ConditionalOnExpression("${server.mode.demo} == false")
public class ProfileCoreRestController extends AbstractRestController {

    public ProfileCoreRestController(final ServiceProcessor serviceProcessor){
        super(serviceProcessor);
    }

    @ApiOperation(value = "Get profile",response = User.class,
            notes = "Get current profile. Required authorization: Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved current profile")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/profile")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<User> getProfile() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalArgumentException("");
        }

        final ReadUserByUsernameOutput output = serviceProcessor.process(ReadUserByUsernameInput.builder()
                .username(authentication.getName())
                .build());
        final User user = output.getUser();

        if(user == null) {
            throw new IllegalArgumentException("");
        }

        user.setPassword(EMPTY);
        return ResponseEntity.ok(user);
    }


    @ApiOperation(value = "Update profile",response = User.class,
            notes = "Get current profile. Required authorization: Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved current profile")
    })
    @RequestMapping(method = RequestMethod.PUT, value = "/profile")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<User> updateProfile(@RequestBody final UpdateProfileRequest request) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalArgumentException("");
        }

        final ReadUserByUsernameOutput readUserByUsernameOutput = serviceProcessor.process(ReadUserByUsernameInput.builder()
                .username(authentication.getName())
                .build());
        final User user = readUserByUsernameOutput.getUser();

        if(user == null) {
            throw new IllegalArgumentException("");
        }

        final UpdateCurrentUserOutput updateCurrentUserOutput = serviceProcessor.process(UpdateCurrentUserInput.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build());

        final User updatedUser = updateCurrentUserOutput.getUpdatedUser();

        updatedUser.setPassword(EMPTY);
        return ResponseEntity.ok(updatedUser);
    }

}
