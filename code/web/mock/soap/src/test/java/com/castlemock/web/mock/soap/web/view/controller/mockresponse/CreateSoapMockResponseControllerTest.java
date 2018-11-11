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

package com.castlemock.web.mock.soap.web.view.controller.mockresponse;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperation;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapOperationInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapOperationOutput;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.soap.config.TestApplication;
import com.castlemock.web.mock.soap.model.project.SoapOperationGenerator;
import com.castlemock.web.mock.soap.model.project.SoapPortGenerator;
import com.castlemock.web.mock.soap.model.project.SoapProjectGenerator;
import com.castlemock.web.mock.soap.web.view.controller.AbstractSoapControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class CreateSoapMockResponseControllerTest extends AbstractSoapControllerTest {

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
        final SoapProject project = SoapProjectGenerator.generateSoapProject();
        final SoapPort application = SoapPortGenerator.generateSoapPort();
        final SoapOperation soapOperation = SoapOperationGenerator.generateSoapOperation();
        when(serviceProcessor.process(any(ReadSoapOperationInput.class))).thenReturn(ReadSoapOperationOutput.builder()
                .operation(soapOperation)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + project.getId() + SLASH + PORT + SLASH + application.getId() + SLASH + OPERATION + SLASH + soapOperation.getId() + SLASH + CREATE_RESPONSE + SLASH);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT_ID, project.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PORT_ID, application.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(DEMO_MODE, false))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_OPERATION, soapOperation));
    }

    @Test
    public void testPostCreateResponse() throws Exception {
        final SoapProject project = SoapProjectGenerator.generateSoapProject();
        final SoapPort application = SoapPortGenerator.generateSoapPort();
        final SoapOperation soapOperation = SoapOperationGenerator.generateSoapOperation();
        when(serviceProcessor.process(any(ReadSoapOperationInput.class))).thenReturn(ReadSoapOperationOutput.builder()
                .operation(soapOperation)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + PROJECT +
                SLASH + project.getId() + SLASH + PORT + SLASH + application.getId() + SLASH + OPERATION +
                SLASH + soapOperation.getId() + SLASH + CREATE_RESPONSE + SLASH);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
    }

}
