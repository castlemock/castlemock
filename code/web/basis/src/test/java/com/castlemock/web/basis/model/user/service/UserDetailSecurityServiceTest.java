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

package com.castlemock.web.basis.model.user.service;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.user.dto.UserDto;
import com.castlemock.core.basis.model.user.service.message.input.ReadUserByUsernameInput;
import com.castlemock.core.basis.model.user.service.message.output.ReadUserByUsernameOutput;
import com.castlemock.web.basis.model.user.dto.UserDtoGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsername(){
        final UserDto user = UserDtoGenerator.generateUserDto();
        final ReadUserByUsernameOutput output = new ReadUserByUsernameOutput(user);
        Mockito.when(serviceProcessor.process(Mockito.any(ReadUserByUsernameInput.class))).thenReturn(output);

        final UserDetails userDetails = service.loadUserByUsername("username");
        Assert.assertEquals(user.getUsername(), userDetails.getUsername());
        Assert.assertEquals(user.getPassword(), userDetails.getPassword());
        final Collection<? extends GrantedAuthority> grantedAuthorityList = userDetails.getAuthorities();
        Assert.assertEquals(1, grantedAuthorityList.size());
        for(GrantedAuthority grantedAuthority : grantedAuthorityList){
            Assert.assertEquals(user.getRole().name(), grantedAuthority.getAuthority());
        }
    }
}
