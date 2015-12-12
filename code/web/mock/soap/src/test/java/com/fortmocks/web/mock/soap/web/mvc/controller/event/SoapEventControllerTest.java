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

package com.fortmocks.web.mock.soap.web.mvc.controller.event;

import com.fortmocks.core.basis.model.Input;
import com.fortmocks.core.basis.model.ServiceProcessor;
import com.fortmocks.core.mock.soap.model.event.dto.SoapEventDto;
import com.fortmocks.core.mock.soap.model.event.service.message.input.ReadSoapEventInput;
import com.fortmocks.core.mock.soap.model.event.service.message.output.ReadSoapEventOutput;
import com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.core.mock.soap.model.project.service.message.output.ReadSoapProjectOutput;
import com.fortmocks.web.basis.web.mvc.controller.AbstractController;
import com.fortmocks.web.mock.soap.config.TestApplication;
import com.fortmocks.web.mock.soap.model.event.SoapEventDtoGenerator;
import com.fortmocks.web.mock.soap.model.project.SoapPortDtoGenerator;
import com.fortmocks.web.mock.soap.model.project.SoapProjectDtoGenerator;
import com.fortmocks.web.mock.soap.web.mvc.controller.AbstractSoapControllerTest;
import com.fortmocks.web.mock.soap.web.mvc.controller.project.SoapProjectController;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class SoapEventControllerTest extends AbstractSoapControllerTest {

    private static final String PAGE = "partial/mock/soap/event/soapEvent.jsp";

    @InjectMocks
    private SoapEventController soapEventController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return soapEventController;
    }

    @Test
    public void testGetServiceValid() throws Exception {
        final SoapEventDto soapEventDto = SoapEventDtoGenerator.generateSoapProjectDto();
        when(serviceProcessor.process(any(ReadSoapEventInput.class))).thenReturn(new ReadSoapEventOutput(soapEventDto));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + EVENT + SLASH + soapEventDto.getId());
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(EVENT, soapEventDto));

    }


}
