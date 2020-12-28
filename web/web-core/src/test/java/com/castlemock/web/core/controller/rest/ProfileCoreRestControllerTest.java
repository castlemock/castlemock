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
import com.castlemock.web.core.model.UpdateProfileRequestTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class ProfileCoreRestControllerTest {

    private ServiceProcessor serviceProcessor;
    private ProfileCoreRestController profileCoreRestController;

    @BeforeEach
    void setup(){
        this.serviceProcessor = mock(ServiceProcessor.class);
        this.profileCoreRestController = new ProfileCoreRestController(serviceProcessor);
    }

    @Test
    @DisplayName("Get profile - Unauthorized")
    void testGetProfile(){
        final ResponseEntity<User> responseEntity = profileCoreRestController.getProfile();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Update profile - Unauthorized")
    void testUpdateProfile(){
        final ResponseEntity<User> responseEntity = profileCoreRestController.updateProfile(UpdateProfileRequestTestBuilder.builder()
                .build());
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

}
