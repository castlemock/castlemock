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
import com.castlemock.model.core.user.UserTestBuilder;
import com.castlemock.service.core.user.input.ReadUserByUsernameInput;
import com.castlemock.service.core.user.output.ReadUserByUsernameOutput;
import com.castlemock.web.core.config.JWTEncoderDecoder;
import com.castlemock.web.core.model.authentication.AuthenticationRequestTestBuilder;
import com.castlemock.web.core.model.authentication.AuthenticationRequest;
import com.castlemock.web.core.model.authentication.AuthenticationResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
class AuthenticationRestControllerTest {

    private ServiceProcessor serviceProcessor;
    private AuthenticationManager authenticationManager;
    private JWTEncoderDecoder jwtEncoderDecoder;
    private AuthenticationRestController authenticationRestController;

    @BeforeEach
    void setup(){
        this.serviceProcessor = mock(ServiceProcessor.class);
        this.authenticationManager = mock(AuthenticationManager.class);
        this.jwtEncoderDecoder = mock(JWTEncoderDecoder.class);
        this.authenticationRestController = new AuthenticationRestController(serviceProcessor, authenticationManager, jwtEncoderDecoder);

    }

    @Test
    @DisplayName("Login")
    void testLogin(){
        final AuthenticationRequest request = AuthenticationRequestTestBuilder.builder().build();
        final HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        final Authentication authentication = mock(Authentication.class);
        final String token = "token";
        final User user = UserTestBuilder.builder().build();

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(serviceProcessor.process(any())).thenReturn(ReadUserByUsernameOutput.builder()
                .user(user)
                .build());
        when(jwtEncoderDecoder.createToken(any())).thenReturn(token);

        final ResponseEntity<AuthenticationResponse> responseEntity = authenticationRestController.login(request, httpServletResponse);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        final AuthenticationResponse response = responseEntity.getBody();

        assertNotNull(response);
        assertEquals(user.getUsername(), response.getUsername());
        assertEquals(user.getRole(), response.getRole());
        assertEquals(token, response.getToken());

        verify(authentication, times(1)).isAuthenticated();
        verify(authenticationManager, times(1)).authenticate(any());
        verify(serviceProcessor, times(1)).process(any(ReadUserByUsernameInput.class));
        verify(jwtEncoderDecoder, times(1)).createToken(any());
    }

    @Test
    @DisplayName("Logout")
    void testLogout(){
        final HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        final ResponseEntity<Void> responseEntity = authenticationRestController.logout(httpServletResponse);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(httpServletResponse, times(2)).addCookie(any());
    }

}
