/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.mock.soap.web.resource.controller;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.soap.service.project.output.LoadSoapResourceOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.soap.config.TestApplication;
import com.castlemock.web.mock.soap.web.AbstractControllerTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class ResourceControllerTest extends AbstractControllerTest {

    @InjectMocks
    private ResourceController resourceController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return resourceController;
    }

    @Test
    public void getResource() throws Exception {
        final String wsdl = "Loaded resource";
        when(serviceProcessor.process(any(Input.class))).thenReturn(LoadSoapResourceOutput.builder()
                .resource(wsdl)
                .build());
        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.get("/resource/soap/project/ProjectId1/resource/wsdl.wsdl");

        final MvcResult result = mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final String content = result.getResponse().getContentAsString();
        Assert.assertEquals(wsdl, content);
    }

}
