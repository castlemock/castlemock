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

package com.fortmocks.web.mock.soap.web.mvc.controller.mockresponse;

import com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.config.TestApplication;
import com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDtoGenerator;
import com.fortmocks.core.mock.soap.model.project.dto.SoapPortDtoGenerator;
import com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDtoGenerator;
import com.fortmocks.core.mock.soap.model.project.service.message.input.ReadSoapOperationInput;
import com.fortmocks.core.mock.soap.model.project.service.message.output.ReadSoapOperationOutput;
import com.fortmocks.web.mock.soap.web.mvc.controller.AbstractSoapControllerTest;
import com.fortmocks.core.basis.model.ServiceProcessor;
import com.fortmocks.web.basis.web.mvc.controller.AbstractController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


/**
 * @author: Karl Dahlgren
 * @since: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class CreateSoapMockResponseControllerTest extends AbstractSoapControllerTest {

    private static final String SERVICE_URL = "/web/soap/";
    private static final String PAGE = "partial/mock/soap/mockresponse/createSoapMockResponse.jsp";
    private static final String CREATE_RESPONSE = "create/response";

    @InjectMocks
    private CreateSoapMockResponseController createMockResponseController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return createMockResponseController;
    }

    @Test
    public void testCreateResponse() throws Exception {
        final SoapProjectDto projectDto = SoapProjectDtoGenerator.generateSoapProjectDto();
        final SoapPortDto applicationDto = SoapPortDtoGenerator.generateSoapPortDto();
        final SoapOperationDto soapOperationDto = SoapOperationDtoGenerator.generateSoapOperationDto();
        when(serviceProcessor.process(any(ReadSoapOperationInput.class))).thenReturn(new ReadSoapOperationOutput(soapOperationDto));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + projectDto.getId() + SLASH + PORT + SLASH + applicationDto.getId() + SLASH + OPERATION + SLASH + soapOperationDto.getId() + SLASH + CREATE_RESPONSE + SLASH);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(8))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT_ID, projectDto.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PORT_ID, applicationDto.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_OPERATION, soapOperationDto));
    }

    @Test
    public void testPostCreateResponse() throws Exception {
        final SoapProjectDto projectDto = SoapProjectDtoGenerator.generateSoapProjectDto();
        final SoapPortDto applicationDto = SoapPortDtoGenerator.generateSoapPortDto();
        final SoapOperationDto soapOperationDto = SoapOperationDtoGenerator.generateSoapOperationDto();
        when(serviceProcessor.process(any(ReadSoapOperationInput.class))).thenReturn(new ReadSoapOperationOutput(soapOperationDto));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + projectDto.getId() + SLASH + PORT + SLASH + applicationDto.getId() + SLASH + OPERATION + SLASH + soapOperationDto.getId() + SLASH + CREATE_RESPONSE + SLASH);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }

}
