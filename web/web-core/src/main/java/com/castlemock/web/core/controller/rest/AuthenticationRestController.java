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

package com.castlemock.web.core.controller.rest;


import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.user.User;
import com.castlemock.service.core.user.input.ReadUserByUsernameInput;
import com.castlemock.service.core.user.output.ReadUserByUsernameOutput;
import com.castlemock.web.core.config.JWTEncoderDecoder;
import com.castlemock.web.core.model.authentication.AuthenticationRequest;
import com.castlemock.web.core.model.authentication.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/api/rest/core")
@Tag(name="Core - Authentication", description="REST Operations for Castle Mock Core")
public class AuthenticationRestController extends AbstractRestController {

    private final AuthenticationManager authenticationManager;
    private final JWTEncoderDecoder jwtEncoderDecoder;

    @Autowired
    public AuthenticationRestController(final ServiceProcessor serviceProcessor,
                                        final AuthenticationManager authenticationManager,
                                        final JWTEncoderDecoder jwtEncoderDecoder){
        super(serviceProcessor);
        this.authenticationManager = Objects.requireNonNull(authenticationManager, "authenticationManager");
        this.jwtEncoderDecoder = Objects.requireNonNull(jwtEncoderDecoder, "jwtEncoderDecoder");
    }

    /**
     * Authenticate user
     * @return Token upon successfully authenticating the user.
     */
    @Operation(summary =  "Login", description = "Authenticate user")
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public @ResponseBody ResponseEntity<AuthenticationResponse> login(@RequestBody final AuthenticationRequest request, final HttpServletResponse httpServletResponse) {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if(authentication.isAuthenticated()){
            final ReadUserByUsernameOutput output = serviceProcessor.process(ReadUserByUsernameInput.builder()
                    .username(request.getUsername())
                    .build());

            if (output.getUser().isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            final User user = output.getUser().get();
            final Map<String, String> claims = new HashMap<>();
            claims.put("userId", user.getId());
            final String token = jwtEncoderDecoder.createToken(claims);

            final Cookie tokenCookie = new Cookie("token",token);
            tokenCookie.setMaxAge(7 * 24 * 60 * 60);
            tokenCookie.setHttpOnly(true);
            tokenCookie.setPath("/");
            httpServletResponse.addCookie(tokenCookie);

            return ResponseEntity.ok(AuthenticationResponse.builder()
                    .token(token)
                    .username(user.getUsername())
                    .role(user.getRole())
                    .build());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Logout user
     * @return Token upon successfully logout the user.
     */
    @Operation(summary =  "Logout", description = "Logout user")
    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public @ResponseBody ResponseEntity<Void> logout(final HttpServletResponse httpServletResponse) {
        final Cookie tokenCookie = new Cookie("token",null);
        tokenCookie.setMaxAge(0);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setPath("/");
        httpServletResponse.addCookie(tokenCookie);

        final Cookie sessionCookie = new Cookie("JSESSIONID",null);
        sessionCookie.setMaxAge(0);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setPath("/");
        httpServletResponse.addCookie(sessionCookie);

        return ResponseEntity.ok().build();
    }
}
