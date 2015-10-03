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
package com.fortmocks.war.mock.soap.web.mvc.controller.operation;

import com.fortmocks.core.mock.soap.model.project.SoapOperationStatus;
import com.fortmocks.core.mock.soap.model.project.SoapResponseStrategy;
import com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.war.base.config.Application;
import com.fortmocks.war.base.web.mvc.controller.AbstractController;
import com.fortmocks.war.mock.soap.model.project.dto.SoapOperationDtoGenerator;
import com.fortmocks.war.mock.soap.model.project.dto.SoapPortDtoGenerator;
import com.fortmocks.war.mock.soap.model.project.dto.SoapProjectDtoGenerator;
import com.fortmocks.core.mock.soap.model.project.service.SoapProjectService;
import com.fortmocks.war.mock.soap.web.mvc.controller.AbstractSoapControllerTest;
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

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UpdateSoapOperationControllerTest extends AbstractSoapControllerTest {

    private static final String SERVICE_URL = "/web/soap/";
    private static final String PAGE = "partial/mock/soap/operation/updateSoapOperation.jsp";
    private static final String SLASH = "/";
    private static final String UPDATE = "update";
    private static final String SOAP_OPERATION_STATUSES = "soapOperationStatuses";
    private static final String COMMAND = "command";

    @InjectMocks
    private UpdateSoapOperationController updateSoapOperationController;

    @Mock
    private SoapProjectService soapProjectService;

    @Override
    protected AbstractController getController() {
        return updateSoapOperationController;
    }

    @Test
    public void testGetUserWithValidId() throws Exception {
        final SoapProjectDto soapProjectDto = SoapProjectDtoGenerator.generateSoapProjectDto();
        final SoapPortDto soapPortDto = SoapPortDtoGenerator.generateSoapPortDto();
        final SoapOperationDto soapOperationDto = SoapOperationDtoGenerator.generateSoapOperationDto();
        when(soapProjectService.findSoapOperation(anyLong(), anyLong(), anyLong())).thenReturn(soapOperationDto);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + soapProjectDto.getId() + SLASH + PORT + SLASH + soapPortDto.getId() + SLASH + OPERATION + SLASH + soapOperationDto.getId() + SLASH + UPDATE + SLASH);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(9))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT_ID, soapProjectDto.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PORT_ID, soapPortDto.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_OPERATION_ID, soapOperationDto.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(COMMAND, soapOperationDto))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_OPERATION_STATUSES, SoapOperationStatus.values()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_MOCK_RESPONSE_STRATEGIES, SoapResponseStrategy.values()));
    }

    @Test
    public void testPostUserWithValidId() throws Exception {
        final SoapProjectDto soapProjectDto = SoapProjectDtoGenerator.generateSoapProjectDto();
        final SoapPortDto soapPortDto = SoapPortDtoGenerator.generateSoapPortDto();
        final SoapOperationDto soapOperationDto = SoapOperationDtoGenerator.generateSoapOperationDto();
        when(soapProjectService.findSoapOperation(anyLong(), anyLong(), anyLong())).thenReturn(soapOperationDto);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + PROJECT + SLASH + soapProjectDto.getId() + SLASH + PORT + SLASH + soapPortDto.getId() + SLASH + OPERATION + SLASH + soapOperationDto.getId() + SLASH + UPDATE + SLASH);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }

}
