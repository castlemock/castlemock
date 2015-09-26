/*
 * Copyright 2015 Karl Dahlgren
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

package com.fortmocks.war.base.web.mvc.controller;


import org.junit.Before;
import org.junit.Ignore;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class) @SpringApplicationConfiguration(classes = Application.class) @WebAppConfiguration
//@PrepareForTest( { LoginControllerTest.class })
@Ignore
public class LoginControllerTest {

    private static final String PAGE = "login";
    private static final String REDIRECT = "redirect:/web";
    private static final String IS_LOGGED_IN_METHOD = "isLoggedIn";
    private static final String CONTEXT_VALUE = "fortmocks";
    private static final String CONTEXT = "context";
    private static final String INVALID_CREDENTAILS_KEY_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";
    private static final String ERROR_INPUT = "Invalid credentials";
    private static final String LOCKED_INPUT = "User locked";
    private static final String LOGOUT_INPUT = "Logging out";
    private static final String ERROR = "error";
    private static final String MSG = "msg";

    private static final String SERVICE_URL = "/login";

    @InjectMocks
    private LoginController loginController;

    private MockMvc mockMvc;

    @Before
    public void initiateTest() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }


     /*
    @Test
    public void testLoginNotLoggedIn() throws Exception {
        MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0));
    }

    @Test
    public void testLoginLoggedIn() throws Exception {
        MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0));
    }

    @Test
    public void testLoginInvalidCredentials() throws Exception {
        MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL).sessionAttr(INVALID_CREDENTAILS_KEY_EXCEPTION, new BadCredentialsException(ERROR_INPUT));
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0));
    }

    @Test
    public void testLoginLockedException() throws Exception {
        MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL).sessionAttr(INVALID_CREDENTAILS_KEY_EXCEPTION, new LockedException(LOCKED_INPUT));
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0));
    }
    */
     /*
    @Test
    public void testLoginUnknowError() throws Exception {

        PowerMockito.doReturn(true).when(loginController, method(LoginController.class, "isLoggedIn"));
        MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0));
    }
    */

     /*
    @Test
    public void testLogout() throws Exception {
        PowerMockito.verifyPrivate(loginController, Mockito.times(0)).invoke(IS_LOGGED_IN_METHOD);
        MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL).param(LOGOUT_INPUT)
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0));
    }
    */





}
