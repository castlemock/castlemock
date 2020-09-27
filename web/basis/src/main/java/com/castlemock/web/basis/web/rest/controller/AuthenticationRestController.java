/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.web.basis.web.rest.controller;


import com.castlemock.core.basis.service.user.input.ReadUserByUsernameInput;
import com.castlemock.core.basis.service.user.output.ReadUserByUsernameOutput;
import com.castlemock.web.basis.config.JWTEncoderDecoder;
import com.castlemock.web.basis.model.authentication.AuthenticationRequest;
import com.castlemock.web.basis.model.authentication.AuthenticationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/rest/core")
@Api(value="Core", description="REST Operations for Castle Mock Core", tags = {"Core"})
public class AuthenticationRestController extends AbstractRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTEncoderDecoder jwtEncoderDecoder;

    /**
     * Authenticate user
     * @return Token upon successfully authenticating the user.
     */
    @ApiOperation(value = "Login",response = AuthenticationResponse.class,
            notes = "Authenticate user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully authenticated")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public @ResponseBody ResponseEntity<AuthenticationResponse> login(@RequestBody final AuthenticationRequest request, final HttpServletResponse httpServletResponse) {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if(authentication.isAuthenticated()){
            final ReadUserByUsernameOutput output = serviceProcessor.process(new ReadUserByUsernameInput(request.getUsername()));
            final Map<String, String> claims = new HashMap<>();
            claims.put("userId", output.getUser().getId());
            final String token = jwtEncoderDecoder.createToken(claims);
            final Cookie cookie = new Cookie("token",token);

            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            httpServletResponse.addCookie(cookie);

            return ResponseEntity.ok(AuthenticationResponse.of(token));

        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
