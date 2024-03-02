/*
 * Copyright 2016 Karl Dahlgren
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

package com.castlemock.service.core.user;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.user.User;
import com.castlemock.model.core.user.UserTestBuilder;
import com.castlemock.service.core.user.input.ReadUserByUsernameInput;
import com.castlemock.service.core.user.output.ReadUserByUsernameOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author Karl Dahlgren
 * @since 1.1
 */
public class UserDetailSecurityServiceTest {

    @Mock
    private ServiceProcessor serviceProcessor;

    @InjectMocks
    private UserDetailSecurityService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername(){
        final User user = UserTestBuilder.builder().build();
        final ReadUserByUsernameOutput output = ReadUserByUsernameOutput.builder()
                .user(user)
                .build();
        Mockito.when(serviceProcessor.process(Mockito.any(ReadUserByUsernameInput.class))).thenReturn(output);

        final UserDetails userDetails = service.loadUserByUsername("username");
        Assertions.assertEquals(user.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(user.getPassword(), userDetails.getPassword());
        final Collection<? extends GrantedAuthority> grantedAuthorityList = userDetails.getAuthorities();
        Assertions.assertEquals(1, grantedAuthorityList.size());
        for(GrantedAuthority grantedAuthority : grantedAuthorityList){
            Assertions.assertEquals(user.getRole().name(), grantedAuthority.getAuthority());
        }
    }
}
