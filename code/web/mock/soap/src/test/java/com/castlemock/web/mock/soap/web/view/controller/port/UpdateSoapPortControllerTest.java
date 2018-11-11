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

package com.castlemock.web.mock.soap.web.view.controller.port;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapPortOutput;
import com.castlemock.core.mock.soap.service.project.output.UpdateSoapPortOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.soap.config.TestApplication;
import com.castlemock.web.mock.soap.model.project.SoapPortGenerator;
import com.castlemock.web.mock.soap.model.project.SoapProjectGenerator;
import com.castlemock.web.mock.soap.web.view.command.port.UpdateSoapPortsEndpointCommand;
import com.castlemock.web.mock.soap.web.view.controller.AbstractSoapControllerTest;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class UpdateSoapPortControllerTest extends AbstractSoapControllerTest {

    private static final String PAGE = "partial/mock/soap/port/updateSoapPort.jsp";
    private static final String UPDATE = "update";
    private static final String CONFIRM = "confirm";

    @InjectMocks
    private UpdateSoapPortController updateSoapPortController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return updateSoapPortController;
    }

    @Test
    public void testUpdatePortWithValidId() throws Exception {
        final SoapProject soapProject = SoapProjectGenerator.generateSoapProject();
        final SoapPort soapPort = SoapPortGenerator.generateSoapPort();

        when(serviceProcessor.process(any(Input.class))).thenReturn(ReadSoapPortOutput.builder()
                .port(soapPort)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT +
                SLASH + soapProject.getId() + SLASH + PORT + SLASH + soapPort.getId() + SLASH + UPDATE);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PORT, soapPort));
    }


    @Test
    public void testUpdateConfirmPortWithValidId() throws Exception {
        final SoapProject soapProject = SoapProjectGenerator.generateSoapProject();
        final SoapPort soapPort = SoapPortGenerator.generateSoapPort();
        when(serviceProcessor.process(any(Input.class))).thenReturn(UpdateSoapPortOutput.builder()
                .port(soapPort)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + PROJECT +
                SLASH + soapProject.getId() + SLASH + PORT + SLASH + soapPort.getId() + SLASH + UPDATE, soapPort);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }

    @Test
    public void testUpdatePortEndpoint() throws Exception {
        final UpdateSoapPortsEndpointCommand command = new UpdateSoapPortsEndpointCommand();
        final SoapProject soapProject = SoapProjectGenerator.generateSoapProject();
        final SoapPort soapPort = SoapPortGenerator.generateSoapPort();
        final List<SoapPort> soapPorts = new ArrayList<SoapPort>();
        soapPorts.add(soapPort);
        command.setSoapPorts(soapPorts);
        command.setForwardedEndpoint("/new/endpoint");

        when(serviceProcessor.process(any(Input.class))).thenReturn(UpdateSoapPortOutput.builder()
                .port(soapPort)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + PROJECT +
                SLASH + soapProject.getId() + SLASH + PORT +  SLASH + UPDATE + SLASH + CONFIRM)
                .flashAttr("command", command);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }

}
