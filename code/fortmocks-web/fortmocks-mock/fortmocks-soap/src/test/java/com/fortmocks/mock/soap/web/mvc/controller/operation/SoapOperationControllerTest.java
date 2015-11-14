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

package com.fortmocks.mock.soap.web.mvc.controller.operation;

import com.fortmocks.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.mock.soap.model.event.service.message.input.ReadSoapEventsByOperationIdInput;
import com.fortmocks.mock.soap.model.event.service.message.output.ReadSoapEventsByOperationIdOutput;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.config.TestApplication;
import com.fortmocks.mock.soap.model.project.dto.SoapOperationDtoGenerator;
import com.fortmocks.mock.soap.model.project.dto.SoapPortDtoGenerator;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDtoGenerator;
import com.fortmocks.mock.soap.model.project.service.message.input.ReadSoapOperationInput;
import com.fortmocks.mock.soap.model.project.service.message.input.ReadSoapPortInput;
import com.fortmocks.mock.soap.model.project.service.message.input.ReadSoapProjectInput;
import com.fortmocks.mock.soap.model.project.service.message.output.ReadSoapOperationOutput;
import com.fortmocks.mock.soap.model.project.service.message.output.ReadSoapPortOutput;
import com.fortmocks.mock.soap.model.project.service.message.output.ReadSoapProjectOutput;
import com.fortmocks.mock.soap.web.mvc.controller.AbstractSoapControllerTest;
import com.fortmocks.web.core.service.ServiceProcessor;
import com.fortmocks.web.core.web.mvc.controller.AbstractController;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class SoapOperationControllerTest extends AbstractSoapControllerTest {

    private static final String SERVICE_URL = "/web/soap/";
    private static final String PAGE = "partial/mock/soap/operation/soapOperation.jsp";
    private static final String SLASH = "/";
    private static final String HTTP = "http://";
    private static final String MOCK = "mock";
    private static final String COLON = ":";
    private static final String SOAP = "soap";
    private static final Integer DEFAULT_PORT = 80;

    @InjectMocks
    private SoapOperationController serviceController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return serviceController;
    }

    @Test
    @Ignore
    public void testGetServiceValid() throws Exception {
        final SoapProjectDto soapProjectDto = SoapProjectDtoGenerator.generateSoapProjectDto();
        final SoapPortDto soapPortDto = SoapPortDtoGenerator.generateSoapPortDto();
        final SoapOperationDto soapOperationDto = SoapOperationDtoGenerator.generateSoapOperationDto();
        when(serviceProcessor.process(any(ReadSoapOperationInput.class))).thenReturn(new ReadSoapOperationOutput(soapOperationDto));
        when(serviceProcessor.process(any(ReadSoapProjectInput.class))).thenReturn(new ReadSoapProjectOutput(soapProjectDto));
        when(serviceProcessor.process(any(ReadSoapEventsByOperationIdInput.class))).thenReturn(new ReadSoapEventsByOperationIdOutput(new ArrayList<SoapEventDto>()));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + soapProjectDto.getId() + SLASH + PORT + SLASH + soapPortDto.getId() + SLASH + OPERATION + SLASH + soapOperationDto.getId() + SLASH);
        ResultActions result = mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(8))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT_ID, soapProjectDto.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PORT_ID, soapPortDto.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_OPERATION, soapOperationDto));
        SoapOperationDto SoapOperationDtoResponse = (SoapOperationDto) result.andReturn().getModelAndView().getModel().get(SOAP_OPERATION);
        String hostAddress = serviceController.getHostAddress();
        Assert.assertEquals(HTTP + hostAddress + COLON + DEFAULT_PORT + CONTEXT + SLASH + MOCK + SLASH + SOAP + SLASH + PROJECT + SLASH + soapProjectDto.getId() + SLASH + soapOperationDto.getUri(), soapOperationDto.getInvokeAddress());
    }

}
