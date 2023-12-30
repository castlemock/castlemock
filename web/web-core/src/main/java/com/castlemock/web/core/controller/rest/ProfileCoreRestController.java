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
import com.castlemock.service.core.user.input.ReadUserByUsernameInput;
import com.castlemock.service.core.user.input.UpdateCurrentUserInput;
import com.castlemock.service.core.user.output.ReadUserByUsernameOutput;
import com.castlemock.service.core.user.output.UpdateCurrentUserOutput;
import com.castlemock.web.core.model.UpdateProfileRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
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
@Tag(name="Core - Profile", description="REST Operations for Castle Mock Core")
@ConditionalOnExpression("${server.mode.demo} == false")
public class ProfileCoreRestController extends AbstractRestController {

    public ProfileCoreRestController(final ServiceProcessor serviceProcessor){
        super(serviceProcessor);
    }

    @Operation(summary = "Get profile", description = "Get current profile. Required authorization: Admin.")
    @RequestMapping(method = RequestMethod.GET, value = "/profile")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<User> getProfile() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final ReadUserByUsernameOutput output = serviceProcessor.process(ReadUserByUsernameInput.builder()
                .username(authentication.getName())
                .build());
        final User user = output.getUser();

        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(user.toBuilder()
                .password(EMPTY)
                .build());
    }


    @Operation(summary =  "Update profile",
            description = "Get current profile. Required authorization: Admin.")
    @RequestMapping(method = RequestMethod.PUT, value = "/profile")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<User> updateProfile(@RequestBody final UpdateProfileRequest request) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final ReadUserByUsernameOutput readUserByUsernameOutput = serviceProcessor.process(ReadUserByUsernameInput.builder()
                .username(authentication.getName())
                .build());
        final User user = readUserByUsernameOutput.getUser();

        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        final UpdateCurrentUserOutput updateCurrentUserOutput = serviceProcessor.process(UpdateCurrentUserInput.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build());

        final User updatedUser = updateCurrentUserOutput.getUpdatedUser()
                        .toBuilder()
                        .password(EMPTY)
                        .build();

        return ResponseEntity.ok(updatedUser);
    }

}
