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
package com.castlemock.web.mock.soap.web.view.controller.operation;

import com.castlemock.core.basis.model.Input;
import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.soap.model.project.domain.*;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapOperationsForwardedEndpointInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapOperationOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.soap.config.TestApplication;
import com.castlemock.web.mock.soap.model.project.SoapOperationGenerator;
import com.castlemock.web.mock.soap.model.project.SoapPortGenerator;
import com.castlemock.web.mock.soap.model.project.SoapProjectGenerator;
import com.castlemock.web.mock.soap.web.view.command.operation.UpdateSoapOperationsEndpointCommand;
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
public class UpdateSoapOperationControllerTest extends AbstractSoapControllerTest {

    private static final String PAGE = "partial/mock/soap/operation/updateSoapOperation.jsp";
    private static final String SLASH = "/";
    private static final String UPDATE = "update";
    private static final String SOAP_OPERATION_STATUSES = "soapOperationStatuses";
    private static final String SOAP_OPERATION_IDENTIFY_STRATEGIES = "soapOperationIdentifyStrategies";
    private static final String COMMAND = "command";

    @InjectMocks
    private UpdateSoapOperationController updateSoapOperationController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return updateSoapOperationController;
    }

    @Test
    public void updateSoapOperationGet() throws Exception {
        final SoapProject soapProject = SoapProjectGenerator.generateSoapProject();
        final SoapPort soapPort = SoapPortGenerator.generateSoapPort();
        final SoapOperation soapOperation = SoapOperationGenerator.generateSoapOperation();
        final List<SoapOperationStatus> soapOperationStatuses = Arrays.asList(SoapOperationStatus.values());
        when(serviceProcessor.process(any(Input.class))).thenReturn(new ReadSoapOperationOutput(soapOperation));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + soapProject.getId() + SLASH + PORT + SLASH + soapPort.getId() + SLASH + OPERATION + SLASH + soapOperation.getId() + SLASH + UPDATE + SLASH);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(7 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT_ID, soapProject.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PORT_ID, soapPort.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_OPERATION_ID, soapOperation.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(COMMAND, soapOperation))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_OPERATION_STATUSES, soapOperationStatuses))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_OPERATION_IDENTIFY_STRATEGIES, SoapOperationIdentifyStrategy.values()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_MOCK_RESPONSE_STRATEGIES, SoapResponseStrategy.values()));
    }

    @Test
    public void updateSoapOperationPost() throws Exception {
        final SoapProject soapProject = SoapProjectGenerator.generateSoapProject();
        final SoapPort soapPort = SoapPortGenerator.generateSoapPort();
        final SoapOperation soapOperation = SoapOperationGenerator.generateSoapOperation();
        when(serviceProcessor.process(any(Input.class))).thenReturn(new ReadSoapOperationOutput(soapOperation));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + soapProject.getId() + SLASH + PORT + SLASH + soapPort.getId() + SLASH + OPERATION + SLASH + soapOperation.getId() + SLASH + UPDATE + SLASH);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }

    @Test
    public void testUpdateEndpoint() throws Exception {
        final String projectId = "projectId";
        final String portId = "portId";

        final UpdateSoapOperationsEndpointCommand updateSoapOperationsEndpointCommand = new UpdateSoapOperationsEndpointCommand();
        updateSoapOperationsEndpointCommand.setForwardedEndpoint("http://localhost:8080/web");

        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH + PORT + SLASH + portId + SLASH + OPERATION + SLASH  + "update/confirm")
                .flashAttr("updateSoapOperationsEndpointCommand", updateSoapOperationsEndpointCommand);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/web/soap/project/" + projectId + "/port/" + portId));

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(UpdateSoapOperationsForwardedEndpointInput.class));
    }

}
