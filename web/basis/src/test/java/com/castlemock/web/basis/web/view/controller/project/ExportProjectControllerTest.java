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

package com.castlemock.web.basis.web.view.controller.project;

import com.castlemock.core.basis.service.project.ProjectServiceFacade;
import com.castlemock.web.basis.config.TestApplication;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.basis.web.AbstractControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class ExportProjectControllerTest extends AbstractControllerTest {


    @InjectMocks
    private ExportProjectController exportProjectController;

    @Mock
    private ProjectServiceFacade projectServiceFacade;

    @Override
    protected AbstractController getController() {
        return exportProjectController;
    }

    private static final String TEXT_XML_UTF_8 = "text/xml;charset=utf-8";

    @Test
    public void testProjectExport() throws Exception {
        Mockito.when(projectServiceFacade.exportProject(Mockito.anyString(), Mockito.anyString())).thenReturn("Project information");
        final String url = "/web/soap/project/0/export";
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(url);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, TEXT_XML_UTF_8));
    }

}
