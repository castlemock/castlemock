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

package com.fortmocks.web.mock.soap.web.mvc.controller.port;


import com.fortmocks.core.mock.soap.model.project.domain.SoapOperationStatus;
import com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.config.TestApplication;
import com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDtoGenerator;
import com.fortmocks.core.mock.soap.model.project.dto.SoapPortDtoGenerator;
import com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDtoGenerator;
import com.fortmocks.core.mock.soap.model.project.service.message.input.GetSoapOperationStatusCountInput;
import com.fortmocks.core.mock.soap.model.project.service.message.input.ReadSoapPortInput;
import com.fortmocks.core.mock.soap.model.project.service.message.output.GetSoapOperationStatusCountOutput;
import com.fortmocks.core.mock.soap.model.project.service.message.output.ReadSoapPortOutput;
import com.fortmocks.web.mock.soap.web.mvc.controller.AbstractSoapControllerTest;
import com.fortmocks.web.basis.service.ServiceProcessor;
import com.fortmocks.web.basis.web.mvc.controller.AbstractController;
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
import java.util.HashMap;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class SoapPortControllerTest extends AbstractSoapControllerTest {


    private static final String SERVICE_URL = "/web/soap/";
    private static final String PAGE = "partial/mock/soap/port/soapPort.jsp";


    @InjectMocks
    private SoapPortController soapPortController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return soapPortController;
    }

    @Test
    @Ignore
    public void testGetServiceValid() throws Exception {
        final SoapProjectDto soapProjectDto = SoapProjectDtoGenerator.generateSoapProjectDto();
        final SoapPortDto soapPortDto = SoapPortDtoGenerator.generateSoapPortDto();
        final SoapOperationDto soapOperationDto = SoapOperationDtoGenerator.generateSoapOperationDto();
        final List<SoapOperationDto> operationDtos = new ArrayList<SoapOperationDto>();
        operationDtos.add(soapOperationDto);
        soapPortDto.setSoapOperations(operationDtos);
        when(serviceProcessor.process(any(ReadSoapPortInput.class))).thenReturn(new ReadSoapPortOutput(soapPortDto));
        when(serviceProcessor.process(any(GetSoapOperationStatusCountInput.class))).thenReturn(new GetSoapOperationStatusCountOutput(new HashMap<SoapOperationStatus, Integer>()));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + soapProjectDto.getId() + SLASH + PORT + SLASH + soapPortDto.getId() + SLASH);
        ResultActions result = mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(7))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PORT, soapPortDto));
        SoapPortDto soapPortDtoResponse = (SoapPortDto) result.andReturn().getModelAndView().getModel().get(SOAP_PORT);
    }


}
