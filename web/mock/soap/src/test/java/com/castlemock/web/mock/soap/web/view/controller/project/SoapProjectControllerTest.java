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

package com.castlemock.web.mock.soap.web.view.controller.project;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapPortTestBuilder;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.domain.SoapProjectTestBuilder;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapPortInput;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapPortsStatusInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapPortOutput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapProjectOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.soap.config.TestApplication;
import com.castlemock.web.mock.soap.web.view.command.port.SoapPortModifierCommand;
import com.castlemock.web.mock.soap.web.view.controller.AbstractSoapControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
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
public class SoapProjectControllerTest extends AbstractSoapControllerTest {

    private static final String PAGE = "partial/mock/soap/project/soapProject.jsp";
    private static final String DELETE_SOAP_PORTS_PAGE = "partial/mock/soap/port/deleteSoapPorts.jsp";
    private static final String UPDATE_SOAP_PORTS_ENDPOINT_PAGE = "partial/mock/soap/port/updateSoapPortsEndpoint.jsp";
    private static final String DELETE_SOAP_PORTS_COMMAND = "command";
    private static final String UPDATE_SOAP_PORTS_ENDPOINT_COMMAND = "command";
    private static final String SOAP_PORTS = "soapPorts";

    @InjectMocks
    private SoapProjectController soapProjectController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return soapProjectController;
    }

    @Test
    public void getProject() throws Exception {
        final SoapProject soapProject = SoapProjectTestBuilder.builder().build();
        final SoapPort soapPort = SoapPortTestBuilder.builder().build();
        final List<SoapPort> soapPorts = new ArrayList<SoapPort>();
        soapPorts.add(soapPort);
        soapProject.setPorts(soapPorts);
        when(serviceProcessor.process(any(Input.class))).thenReturn(ReadSoapProjectOutput.builder()
                .project(soapProject)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + soapProject.getId() + SLASH);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT, soapProject));

    }

    @Test
    public void getProjectUploadError() throws Exception {
        final SoapProject soapProject = SoapProjectTestBuilder.builder().build();
        final SoapPort soapPort = SoapPortTestBuilder.builder().build();
        final List<SoapPort> soapPorts = new ArrayList<SoapPort>();
        soapPorts.add(soapPort);
        soapProject.setPorts(soapPorts);
        when(serviceProcessor.process(any(Input.class))).thenReturn(ReadSoapProjectOutput.builder()
                .project(soapProject)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + soapProject.getId() + SLASH)
                .param("upload", "error");
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute("upload", "error"))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT, soapProject));

    }

    @Test
    public void getProjectUploadSuccess() throws Exception {
        final SoapProject soapProject = SoapProjectTestBuilder.builder().build();
        final SoapPort soapPort = SoapPortTestBuilder.builder().build();
        final List<SoapPort> soapPorts = new ArrayList<SoapPort>();
        soapPorts.add(soapPort);
        soapProject.setPorts(soapPorts);
        when(serviceProcessor.process(any(Input.class))).thenReturn(ReadSoapProjectOutput.builder()
                .project(soapProject)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + soapProject.getId() + SLASH)
                .param("upload", "success");
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute("upload", "success"))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT, soapProject));

    }

    @Test
    public void projectFunctionalityUpdate() throws Exception {
        final String projectId = "projectId";
        final String[] soapPortIds = {"soapPort1", "soapPort2"};

        final SoapPortModifierCommand command = new SoapPortModifierCommand();
        command.setSoapPortIds(soapPortIds);
        command.setSoapPortStatus("MOCKED");

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH)
                        .param("action", "update").flashAttr("command", command);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/web/soap/project/" + projectId));

        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.any(UpdateSoapPortsStatusInput.class));

    }

    @Test
    public void projectFunctionalityDelete() throws Exception {
        final String projectId = "projectId";
        final String[] soapPortIds = {"soapPort1", "soapPort2"};

        final SoapPort soapPort1 = new SoapPort();
        soapPort1.setName("soapPort1");

        final SoapPort soapPort2 = new SoapPort();
        soapPort1.setName("soapPort2");

        final List<SoapPort> soapPorts = Arrays.asList(soapPort1, soapPort2);

        Mockito.when(serviceProcessor.process(Mockito.any(ReadSoapPortInput.class)))
                .thenReturn(ReadSoapPortOutput.builder()
                        .port(soapPort1)
                        .build())
                .thenReturn(ReadSoapPortOutput.builder()
                        .port(soapPort2)
                        .build());


        final SoapPortModifierCommand command = new SoapPortModifierCommand();
        command.setSoapPortIds(soapPortIds);

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH)
                        .param("action", "delete").flashAttr("command", command);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, DELETE_SOAP_PORTS_PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT_ID, projectId))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PORTS, soapPorts))
                .andExpect(MockMvcResultMatchers.model().attributeExists(DELETE_SOAP_PORTS_COMMAND));


        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.any(ReadSoapPortInput.class));

    }

    @Test
    public void projectFunctionalityUpdateEndpoint() throws Exception {
        final String projectId = "projectId";
        final String[] soapPortIds = {"soapPort1", "soapPort2"};

        final SoapPort soapPort1 = new SoapPort();
        soapPort1.setName("soapPort1");

        final SoapPort soapPort2 = new SoapPort();
        soapPort1.setName("soapPort2");

        final List<SoapPort> soapPorts = Arrays.asList(soapPort1, soapPort2);

        Mockito.when(serviceProcessor.process(Mockito.any(ReadSoapPortInput.class)))
                .thenReturn(ReadSoapPortOutput.builder()
                        .port(soapPort1)
                        .build())
                .thenReturn(ReadSoapPortOutput.builder()
                        .port(soapPort2)
                        .build());


        final SoapPortModifierCommand command = new SoapPortModifierCommand();
        command.setSoapPortIds(soapPortIds);

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH)
                        .param("action", "update-endpoint").flashAttr("command", command);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, UPDATE_SOAP_PORTS_ENDPOINT_PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT_ID, projectId))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PORTS, soapPorts))
                .andExpect(MockMvcResultMatchers.model().attributeExists(UPDATE_SOAP_PORTS_ENDPOINT_COMMAND));


        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.any(ReadSoapPortInput.class));

    }

}
