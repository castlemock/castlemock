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


import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapOperationInput;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapPortInput;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapOperationsStatusInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapOperationOutput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapPortOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.soap.config.TestApplication;
import com.castlemock.web.mock.soap.model.project.SoapOperationGenerator;
import com.castlemock.web.mock.soap.model.project.SoapPortGenerator;
import com.castlemock.web.mock.soap.model.project.SoapProjectGenerator;
import com.castlemock.web.mock.soap.web.view.command.operation.SoapOperationModifierCommand;
import com.castlemock.web.mock.soap.web.view.controller.AbstractSoapControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
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
public class SoapPortControllerTest extends AbstractSoapControllerTest {

    private static final String PAGE = "partial/mock/soap/port/soapPort.jsp";
    private static final String SOAP_OPERATIONS = "soapOperations";
    private static final String UPDATE_SOAP_OPERATIONS_ENDPOINT_COMMAND = "command";
    private static final String UPDATE_SOAP_OPERATIONS_ENDPOINT_PAGE = "partial/mock/soap/operation/updateSoapOperationsEndpoint.jsp";

    @InjectMocks
    private SoapPortController soapPortController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return soapPortController;
    }

    @Test
    public void getSoapPort() throws Exception {
        final SoapProject soapProject = SoapProjectGenerator.generateSoapProject();
        final SoapPort soapPort = SoapPortGenerator.generateSoapPort();
        final SoapOperation soapOperation = SoapOperationGenerator.generateSoapOperation();
        final List<SoapOperation> operations = new ArrayList<SoapOperation>();
        operations.add(soapOperation);
        soapPort.setOperations(operations);
        when(serviceProcessor.process(any(ReadSoapPortInput.class))).thenReturn(ReadSoapPortOutput.builder()
                .port(soapPort)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + soapProject.getId() + SLASH + PORT + SLASH + soapPort.getId() + SLASH);
        ResultActions result = mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PORT, soapPort));
        SoapPort soapPortResponse = (SoapPort) result.andReturn().getModelAndView().getModel().get(SOAP_PORT);
    }


    @Test
    public void testServiceFunctionalityUpdate() throws Exception {
        final String projectId = "projectId";
        final String portId = "portId";
        final String[] soapOperationIds = {"Operation1", "Operation2"};

        final SoapOperationModifierCommand command = new SoapOperationModifierCommand();
        command.setSoapOperationIds(soapOperationIds);
        command.setSoapOperationStatus("MOCKED");

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH + PORT + SLASH + portId)
                        .param("action", "update").flashAttr("command", command);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/web/soap/project/" + projectId + "/port/" + portId));


        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.isA(UpdateSoapOperationsStatusInput.class));
    }

    @Test
    public void testServiceFunctionalityUpdateEndpoint() throws Exception {
        final String projectId = "projectId";
        final String portId = "portId";
        final String[] soapOperationIds = {"Operation1", "Operation2"};


        final SoapOperation soapOperation1 = new SoapOperation();
        soapOperation1.setId("SoapOperation1");

        final SoapOperation soapOperation2 = new SoapOperation();
        soapOperation2.setId("SoapOperation2");

        Mockito.when(serviceProcessor.process(Mockito.any(ReadSoapOperationInput.class)))
                .thenReturn(ReadSoapOperationOutput.builder().operation(soapOperation1).build())
                .thenReturn(ReadSoapOperationOutput.builder().operation(soapOperation2).build());


        final List<SoapOperation> operations = Arrays.asList(soapOperation1, soapOperation2);


        final SoapOperationModifierCommand command = new SoapOperationModifierCommand();
        command.setSoapOperationIds(soapOperationIds);
        command.setSoapOperationStatus("ENABLED");

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH + PORT + SLASH + portId)
                        .param("action", "update-endpoint").flashAttr("command", command);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, UPDATE_SOAP_OPERATIONS_ENDPOINT_PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT_ID, projectId))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PORT_ID, portId))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_OPERATIONS, operations))
                .andExpect(MockMvcResultMatchers.model().attributeExists(UPDATE_SOAP_OPERATIONS_ENDPOINT_COMMAND));

        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.isA(ReadSoapOperationInput.class));


    }

}
