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

import com.fortmocks.mock.soap.manager.WSDLComponent;
import com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.mock.soap.config.TestApplication;
import com.fortmocks.core.mock.soap.model.project.dto.SoapPortDtoGenerator;
import com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDtoGenerator;
import com.fortmocks.core.mock.soap.model.project.service.message.input.ReadSoapProjectInput;
import com.fortmocks.core.mock.soap.model.project.service.message.output.ReadSoapProjectOutput;
import com.fortmocks.mock.soap.web.mvc.command.project.WSDLFileUploadForm;
import com.fortmocks.mock.soap.web.mvc.controller.AbstractSoapControllerTest;
import com.fortmocks.web.core.service.ServiceProcessor;
import com.fortmocks.web.core.web.mvc.controller.AbstractController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class SoapAddWSDLControllerTest extends AbstractSoapControllerTest {

    private static final String SERVICE_URL = "/web/soap/project/";
    private static final String PAGE = "partial/mock/soap/project/soapAddWSDL.jsp";
    private static final String ADD = "add";
    private static final String WSDL = "wsdl";
    private static final String TYPE = "type";
    private static final String TYPE_FILE = "file";
    private static final int MAX_SOAP_PORT_COUNT = 5;

    @InjectMocks
    private SoapAddWSDLController  soapAddWSDLController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Mock
    private WSDLComponent wsdlComponent;

    @Override
    protected AbstractController getController() {
        return soapAddWSDLController;
    }

    @Test
    public void testAddWSDLGet() throws Exception {
        final SoapProjectDto soapProjectDto = SoapProjectDtoGenerator.generateSoapProjectDto();
        when(serviceProcessor.process(any(ReadSoapProjectInput.class))).thenReturn(new ReadSoapProjectOutput(soapProjectDto));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + soapProjectDto.getId() + SLASH + ADD + SLASH + WSDL);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT_ID, soapProjectDto.getId()));
    }

    @Test
    public void testAddWSDLPostFile() throws Exception {
        final SoapProjectDto soapProjectDto = SoapProjectDtoGenerator.generateSoapProjectDto();
        final List<SoapPortDto> soapPortDtos = new ArrayList<>();
        final WSDLFileUploadForm uploadForm = new WSDLFileUploadForm();
        final List<MultipartFile> uploadedFiles = new ArrayList<>();
        uploadForm.setFiles(uploadedFiles);

        for(int index = 0; index < MAX_SOAP_PORT_COUNT; index++){
            final SoapPortDto soapPortDto = SoapPortDtoGenerator.generateSoapPortDto();
            soapPortDtos.add(soapPortDto);
        }

        when(serviceProcessor.process(any(ReadSoapProjectInput.class))).thenReturn(new ReadSoapProjectOutput(soapProjectDto));
        when(wsdlComponent.createSoapPorts(anyListOf(MultipartFile.class), anyBoolean())).thenReturn(soapPortDtos);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + soapProjectDto.getId() + SLASH + ADD + SLASH + WSDL).param("type", "file").requestAttr("uploadForm", uploadForm);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
        Mockito.verify(wsdlComponent, times(1)).createSoapPorts(anyListOf(MultipartFile.class), anyBoolean());
    }

    @Test
    public void testAddWSDLPostLink() throws Exception {
        final SoapProjectDto soapProjectDto = SoapProjectDtoGenerator.generateSoapProjectDto();
        final List<SoapPortDto> soapPortDtos = new ArrayList<>();
        final WSDLFileUploadForm uploadForm = new WSDLFileUploadForm();
        final List<MultipartFile> uploadedFiles = new ArrayList<>();
        uploadForm.setFiles(uploadedFiles);

        for(int index = 0; index < MAX_SOAP_PORT_COUNT; index++){
            final SoapPortDto soapPortDto = SoapPortDtoGenerator.generateSoapPortDto();
            soapPortDtos.add(soapPortDto);
        }

        when(serviceProcessor.process(any(ReadSoapProjectInput.class))).thenReturn(new ReadSoapProjectOutput(soapProjectDto));
        when(wsdlComponent.createSoapPorts(anyListOf(MultipartFile.class), anyBoolean())).thenReturn(soapPortDtos);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + soapProjectDto.getId() + SLASH + ADD + SLASH + WSDL).param("type", "link").requestAttr("uploadForm", uploadForm);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
        Mockito.verify(wsdlComponent, times(1)).createSoapPorts(anyString(), anyBoolean());
    }



}
