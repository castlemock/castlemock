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

package com.castlemock.web.basis.web.view.controller.system;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.system.service.dto.SystemInformation;
import com.castlemock.core.basis.service.system.input.GetSystemInformationInput;
import com.castlemock.core.basis.service.system.output.GetSystemInformationOutput;
import com.castlemock.web.basis.config.TestApplication;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.basis.web.AbstractControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * @author Karl Dahlgren
 * @since 1.18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class SystemControllerTest extends AbstractControllerTest {

    private static final String PAGE = "partial/basis/system/system.jsp";
    private static final String SERVICE_URL = "/web/system";
    private static final String SYSTEM_INFORMATION = "systemInformation";


    @InjectMocks
    private SystemController systemController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return systemController;
    }

    @Test
    public void getDefaultPage() throws Exception {
        final SystemInformation systemInformation = new SystemInformation();
        systemInformation.setAvailableProcessors(1);
        systemInformation.setCastleMockHomeDirectory("/HOME/CastleMock");
        systemInformation.setFreeMemory(512L);
        systemInformation.setJavaVendor("Oracle");
        systemInformation.setJavaVersion("1.8");
        systemInformation.setMaxMemory(2048L);
        systemInformation.setOperatingSystemName("Linux");
        systemInformation.setTomcatBuilt("Tomcat 8.5");
        systemInformation.setTomcatInfo("Apache Tomcat");
        systemInformation.setTomcatVersion("8.5");
        systemInformation.setTotalMemory(1024L);

        final GetSystemInformationOutput output = new GetSystemInformationOutput(systemInformation);

        when(serviceProcessor.process(any(GetSystemInformationInput.class))).thenReturn(output);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SYSTEM_INFORMATION, systemInformation));
    }



}
