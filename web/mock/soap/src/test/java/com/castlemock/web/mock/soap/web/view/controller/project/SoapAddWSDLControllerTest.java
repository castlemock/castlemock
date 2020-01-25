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

package com.castlemock.web.mock.soap.web.view.controller.project;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.domain.SoapProjectTestBuilder;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapProjectInput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapProjectOutput;
import com.castlemock.web.basis.manager.FileManager;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.soap.config.TestApplication;
import com.castlemock.web.mock.soap.web.view.command.project.WSDLFileUploadForm;
import com.castlemock.web.mock.soap.web.view.controller.AbstractSoapControllerTest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class SoapAddWSDLControllerTest extends AbstractSoapControllerTest {

    private static final String PAGE = "partial/mock/soap/project/soapAddWSDL.jsp";
    private static final String ADD = "add";
    private static final String WSDL = "wsdl";
    private static final int MAX_SOAP_PORT_COUNT = 5;

    @InjectMocks
    private SoapAddWSDLController  soapAddWSDLController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Mock
    private FileManager fileManager;

    @Override
    protected AbstractController getController() {
        return soapAddWSDLController;
    }

    @Test
    public void testAddWSDLGet() throws Exception {
        final SoapProject soapProject = SoapProjectTestBuilder.builder().build();
        when(serviceProcessor.process(any(ReadSoapProjectInput.class))).thenReturn(ReadSoapProjectOutput.builder()
                .project(soapProject)
                .build());
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + SLASH +
                PROJECT + SLASH + soapProject.getId() + SLASH + ADD + SLASH + WSDL);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT_ID, soapProject.getId()));
    }

    @Test
    @Ignore
    public void testAddWSDLPostFile() throws Exception {
        final SoapProject soapProject = SoapProjectTestBuilder.builder().build();
        final List<File> files = new ArrayList<>();
        final WSDLFileUploadForm uploadForm = new WSDLFileUploadForm();
        final List<MultipartFile> uploadedFiles = new ArrayList<>();
        uploadForm.setFiles(uploadedFiles);

        when(serviceProcessor.process(any(ReadSoapProjectInput.class))).thenReturn(ReadSoapProjectOutput.builder()
                .project(soapProject)
                .build());
        when(fileManager.uploadFiles(anyListOf(MultipartFile.class))).thenReturn(files);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + SLASH +
                PROJECT + SLASH + soapProject.getId() + SLASH + ADD + SLASH + WSDL)
                .param("type", "file").requestAttr("uploadForm", uploadForm);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
        Mockito.verify(fileManager, times(1)).uploadFiles(anyListOf(MultipartFile.class));
    }

    @Test
    public void testAddWSDLPostLink() throws Exception {
        final SoapProject soapProject = SoapProjectTestBuilder.builder().build();
        final List<File> files = new ArrayList<>();
        final WSDLFileUploadForm uploadForm = new WSDLFileUploadForm();
        final List<MultipartFile> uploadedFiles = new ArrayList<>();
        uploadForm.setFiles(uploadedFiles);


        when(serviceProcessor.process(any(ReadSoapProjectInput.class))).thenReturn(ReadSoapProjectOutput.builder()
                .project(soapProject)
                .build());
        when(fileManager.uploadFiles(anyString())).thenReturn(files);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + SLASH +
                PROJECT + SLASH + soapProject.getId() + SLASH + ADD + SLASH + WSDL)
                .param("type", "link").requestAttr("uploadForm", uploadForm);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
        Mockito.verify(fileManager, times(0)).uploadFiles(anyString());
    }



}
