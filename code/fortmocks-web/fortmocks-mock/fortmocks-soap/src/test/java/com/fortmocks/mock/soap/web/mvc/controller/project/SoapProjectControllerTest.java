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

package com.fortmocks.mock.soap.web.mvc.controller.project;

import com.fortmocks.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.config.TestApplication;
import com.fortmocks.mock.soap.model.project.dto.SoapPortDtoGenerator;
import com.fortmocks.mock.soap.model.project.dto.SoapProjectDtoGenerator;
import com.fortmocks.mock.soap.model.project.service.SoapProjectService;
import com.fortmocks.mock.soap.web.mvc.controller.AbstractSoapControllerTest;
import com.fortmocks.web.core.web.mvc.controller.AbstractController;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class SoapProjectControllerTest extends AbstractSoapControllerTest {


    private static final String SERVICE_URL = "/web/soap/";
    private static final String PAGE = "partial/mock/soap/project/soapProject.jsp";

    @InjectMocks
    private SoapProjectController soapProjectController;

    @Mock
    private SoapProjectService soapProjectService;

    @Override
    protected AbstractController getController() {
        return soapProjectController;
    }

    @Test
    public void testGetServiceValid() throws Exception {
        final SoapProjectDto soapProjectDto = SoapProjectDtoGenerator.generateSoapProjectDto();
        final SoapPortDto soapPortDto = SoapPortDtoGenerator.generateSoapPortDto();
        final List<SoapPortDto> soapPortDtos = new ArrayList<SoapPortDto>();
        soapPortDtos.add(soapPortDto);
        soapProjectDto.setSoapPorts(soapPortDtos);
        when(soapProjectService.findOne(anyLong())).thenReturn(soapProjectDto);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + soapProjectDto.getId() + SLASH);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(6))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT, soapProjectDto));

    }


}
