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

package com.castlemock.web.mock.soap.web.mvc.controller.operation;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.soap.model.event.dto.SoapEventDto;
import com.castlemock.core.mock.soap.model.event.service.message.input.ReadSoapEventsByOperationIdInput;
import com.castlemock.core.mock.soap.model.event.service.message.output.ReadSoapEventsByOperationIdOutput;
import com.castlemock.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapOperationDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapPortDto;
import com.castlemock.core.mock.soap.model.project.dto.SoapProjectDto;
import com.castlemock.core.mock.soap.model.project.service.message.input.ReadSoapMockResponseInput;
import com.castlemock.core.mock.soap.model.project.service.message.input.ReadSoapOperationInput;
import com.castlemock.core.mock.soap.model.project.service.message.input.ReadSoapPortInput;
import com.castlemock.core.mock.soap.model.project.service.message.input.UpdateSoapMockResponseInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.ReadSoapMockResponseOutput;
import com.castlemock.core.mock.soap.model.project.service.message.output.ReadSoapOperationOutput;
import com.castlemock.core.mock.soap.model.project.service.message.output.ReadSoapPortOutput;
import com.castlemock.core.mock.soap.model.project.service.message.output.UpdateSoapMockResponseOutput;
import com.castlemock.web.basis.web.mvc.controller.AbstractController;
import com.castlemock.web.mock.soap.config.TestApplication;
import com.castlemock.web.mock.soap.model.project.SoapOperationDtoGenerator;
import com.castlemock.web.mock.soap.model.project.SoapPortDtoGenerator;
import com.castlemock.web.mock.soap.model.project.SoapProjectDtoGenerator;
import com.castlemock.web.mock.soap.web.mvc.command.mockresponse.SoapMockResponseModifierCommand;
import com.castlemock.web.mock.soap.web.mvc.controller.AbstractSoapControllerTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class SoapOperationControllerTest extends AbstractSoapControllerTest {

    private static final String DELETE_SOAP_MOCK_RESPONSES_COMMAND = "deleteSoapMockResponsesCommand";
    private static final String PAGE = "partial/mock/soap/operation/soapOperation.jsp";
    private static final String DELETE_MOCK_RESPONSES_PAGE = "partial/mock/soap/mockresponse/deleteSoapMockResponses.jsp";
    private static final String SLASH = "/";
    private static final String HTTP = "http://";
    private static final String MOCK = "mock";
    private static final String COLON = ":";
    private static final String SOAP = "soap";
    private static final Integer DEFAULT_PORT = 80;
    protected static final String SOAP_MOCK_RESPONSES = "soapMockResponses";

    @InjectMocks
    private SoapOperationController serviceController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return serviceController;
    }

    @Test
    public void testDefaultPage() throws Exception {
        final SoapProjectDto soapProjectDto = SoapProjectDtoGenerator.generateSoapProjectDto();
        final SoapPortDto soapPortDto = SoapPortDtoGenerator.generateSoapPortDto();
        final SoapOperationDto soapOperationDto = SoapOperationDtoGenerator.generateSoapOperationDto();
        when(serviceProcessor.process(isA(ReadSoapPortInput.class))).thenReturn(new ReadSoapPortOutput(soapPortDto));
        when(serviceProcessor.process(isA(ReadSoapOperationInput.class))).thenReturn(new ReadSoapOperationOutput(soapOperationDto));
        when(serviceProcessor.process(isA(ReadSoapEventsByOperationIdInput.class))).thenReturn(new ReadSoapEventsByOperationIdOutput(new ArrayList<SoapEventDto>()));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + soapProjectDto.getId() + SLASH + PORT + SLASH + soapPortDto.getId() + SLASH + OPERATION + SLASH + soapOperationDto.getId() + SLASH);
        ResultActions result = mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(6 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT_ID, soapProjectDto.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PORT_ID, soapPortDto.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_OPERATION, soapOperationDto));
        SoapOperationDto SoapOperationDtoResponse = (SoapOperationDto) result.andReturn().getModelAndView().getModel().get(SOAP_OPERATION);
        String hostAddress = serviceController.getHostAddress();
        Assert.assertEquals(HTTP + hostAddress + COLON + DEFAULT_PORT + CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT + SLASH + soapProjectDto.getId() + SLASH + soapPortDto.getUri(), soapOperationDto.getInvokeAddress());
    }


    @Test
    public void testServiceFunctionalityUpdate() throws Exception {
        final String projectId = "projectId";
        final String portId = "portId";
        final String operationId = "operationId";
        final String[] soapMockResponseIds = {"MockResponse1", "MockResponse2"};


        final SoapMockResponseDto soapMockResponse1 = new SoapMockResponseDto();
        soapMockResponse1.setId("MockResponseId1");

        final SoapMockResponseDto soapMockResponse2 = new SoapMockResponseDto();
        soapMockResponse2.setId("MockResponseId2");

        Mockito.when(serviceProcessor.process(Mockito.any(ReadSoapMockResponseInput.class)))
                .thenReturn(new ReadSoapMockResponseOutput(soapMockResponse1))
                .thenReturn(new ReadSoapMockResponseOutput(soapMockResponse2));


        final SoapMockResponseModifierCommand soapMockResponseModifierCommand = new SoapMockResponseModifierCommand();
        soapMockResponseModifierCommand.setSoapMockResponseIds(soapMockResponseIds);
        soapMockResponseModifierCommand.setSoapMockResponseStatus("ENABLED");

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH + PORT + SLASH + portId + SLASH + OPERATION + SLASH + operationId)
                        .param("action", "update").flashAttr("soapMockResponseModifierCommand", soapMockResponseModifierCommand);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/web/soap/project/" + projectId + "/port/" + portId + "/operation/" + operationId));

        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.isA(ReadSoapMockResponseInput.class));
        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.isA(UpdateSoapMockResponseInput.class));

    }

    @Test
    public void testServiceFunctionalityDelete() throws Exception {
        final String projectId = "projectId";
        final String portId = "portId";
        final String operationId = "operationId";
        final String[] soapMockResponseIds = {"MockResponse1", "MockResponse2"};


        final SoapMockResponseDto soapMockResponse1 = new SoapMockResponseDto();
        soapMockResponse1.setId("MockResponseId1");

        final SoapMockResponseDto soapMockResponse2 = new SoapMockResponseDto();
        soapMockResponse2.setId("MockResponseId2");

        final List<SoapMockResponseDto> mockResponses = Arrays.asList(soapMockResponse1, soapMockResponse2);

        Mockito.when(serviceProcessor.process(Mockito.any(ReadSoapMockResponseInput.class)))
                .thenReturn(new ReadSoapMockResponseOutput(soapMockResponse1))
                .thenReturn(new ReadSoapMockResponseOutput(soapMockResponse2));


        final SoapMockResponseModifierCommand soapMockResponseModifierCommand = new SoapMockResponseModifierCommand();
        soapMockResponseModifierCommand.setSoapMockResponseIds(soapMockResponseIds);

        final MockHttpServletRequestBuilder message =
                MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectId + SLASH + PORT + SLASH + portId + SLASH + OPERATION + SLASH + operationId)
                        .param("action", "delete").flashAttr("soapMockResponseModifierCommand", soapMockResponseModifierCommand);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(6 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, DELETE_MOCK_RESPONSES_PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT_ID, projectId))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PORT_ID, portId))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_OPERATION_ID, operationId))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_MOCK_RESPONSES, mockResponses))
                .andExpect(MockMvcResultMatchers.model().attributeExists(DELETE_SOAP_MOCK_RESPONSES_COMMAND));

        Mockito.verify(serviceProcessor, Mockito.times(2)).process(Mockito.isA(ReadSoapMockResponseInput.class));

    }


}
