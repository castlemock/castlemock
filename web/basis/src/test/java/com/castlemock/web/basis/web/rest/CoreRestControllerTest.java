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

package com.castlemock.web.basis.web.rest;

import com.castlemock.core.basis.model.project.domain.Project;
import com.castlemock.web.basis.config.TestApplication;
import com.castlemock.web.basis.service.project.ProjectServiceFacadeImpl;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.basis.web.AbstractControllerTest;
import com.castlemock.web.basis.web.rest.controller.CoreRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class CoreRestControllerTest extends AbstractControllerTest {

    @InjectMocks
    private CoreRestController coreRestController;

    @Mock
    private ProjectServiceFacadeImpl projectServiceComponent;

    private static final String BASE_SERVICE_URL = "/api/rest/core";

    @Test
    public void getSoapProjectTest() throws Exception {
        final Project project = Mockito.mock(Project.class);
        final String serviceUrl = BASE_SERVICE_URL + "/project/soap/1";

        when(projectServiceComponent.findOne(Mockito.anyString(), Mockito.anyString())).thenReturn(project);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(serviceUrl);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(projectServiceComponent, Mockito.times(1)).findOne("soap", "1");
    }

    @Test
    public void getRestProjectTest() throws Exception {
        final Project project = Mockito.mock(Project.class);
        final String serviceUrl = BASE_SERVICE_URL + "/project/rest/2";

        when(projectServiceComponent.findOne(Mockito.anyString(), Mockito.anyString())).thenReturn(project);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(serviceUrl);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(projectServiceComponent, Mockito.times(1)).findOne("rest", "2");
    }

    @Test
    public void deleteSoapProjectTest() throws Exception {
        final Project project = Mockito.mock(Project.class);
        final String serviceUrl = BASE_SERVICE_URL + "/project/soap/1";

        when(projectServiceComponent.delete(Mockito.anyString(), Mockito.anyString())).thenReturn(project);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.delete(serviceUrl);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(projectServiceComponent, Mockito.times(1)).delete("soap", "1");
    }

    @Test
    public void deleteRestProjectTest() throws Exception {
        final Project project = Mockito.mock(Project.class);
        final String serviceUrl = BASE_SERVICE_URL + "/project/rest/2";

        when(projectServiceComponent.delete(Mockito.anyString(), Mockito.anyString())).thenReturn(project);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.delete(serviceUrl);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(projectServiceComponent, Mockito.times(1)).delete("rest", "2");
    }

    @Test
    public void exportRestProjectTest() throws Exception {
        final String exported = "This is an exported project";
        final String serviceUrl = BASE_SERVICE_URL + "/project/rest/2/export";

        when(projectServiceComponent.exportProject(Mockito.anyString(), Mockito.anyString())).thenReturn(exported);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(serviceUrl);
        final MvcResult result = mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final String content = result.getResponse().getContentAsString();

        assertEquals(exported, content);
        Mockito.verify(projectServiceComponent, Mockito.times(1)).exportProject("rest", "2");
    }



    @Override
    protected AbstractController getController() {
        return coreRestController;
    }
}
