/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.mock.soap.web.mvc.controller.resource;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.soap.model.project.dto.SoapResourceDto;
import com.castlemock.core.mock.soap.model.project.service.message.input.LoadSoapResourceInput;
import com.castlemock.core.mock.soap.model.project.service.message.input.ReadSoapResourceInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.LoadSoapResourceOutput;
import com.castlemock.core.mock.soap.model.project.service.message.output.ReadSoapResourceOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.soap.config.TestApplication;
import com.castlemock.web.mock.soap.web.mvc.controller.AbstractSoapControllerTest;
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

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class SoapResourceControllerTest extends AbstractSoapControllerTest {

    private static final String SOAP_PROJECT_ID = "soapProjectId";
    private static final String SOAP_RESOURCE = "soapResource";
    private static final String SOAP_RESOURCE_DATA = "soapResourceData";
    private static final String PAGE = "partial/mock/soap/resource/soapResource.jsp";

    @InjectMocks
    private SoapResourceController soapResourceController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return soapResourceController;
    }

    @Test
    public void testGetServiceValid() throws Exception {
        final String projectId = "ProjectId";
        final String resourceId = "ResourceId";
        final SoapResourceDto soapResourceDto = new SoapResourceDto();
        soapResourceDto.setName("SOAP resource name");
        final ReadSoapResourceOutput readSoapResourceOutput = new ReadSoapResourceOutput(soapResourceDto);

        final String resource = "Resource";
        final LoadSoapResourceOutput loadSoapResourceOutput = new LoadSoapResourceOutput(resource);


        when(serviceProcessor.process(isA(ReadSoapResourceInput.class))).thenReturn(readSoapResourceOutput);
        when(serviceProcessor.process(isA(LoadSoapResourceInput.class))).thenReturn(loadSoapResourceOutput);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + projectId + SLASH + RESOURCE + SLASH + resourceId);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT_ID, projectId))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_RESOURCE, soapResourceDto))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_RESOURCE_DATA, resource));

    }


}
