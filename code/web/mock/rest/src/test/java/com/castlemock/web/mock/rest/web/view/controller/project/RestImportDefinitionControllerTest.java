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

package com.castlemock.web.mock.rest.web.view.controller.project;

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.service.project.input.ReadRestProjectInput;
import com.castlemock.core.mock.rest.service.project.output.ReadRestProjectOutput;
import com.castlemock.web.basis.manager.FileManager;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.mock.rest.config.TestApplication;
import com.castlemock.web.mock.rest.model.project.RestProjectGenerator;
import com.castlemock.web.mock.rest.web.view.command.project.RestDefinitionFileUploadForm;
import com.castlemock.web.mock.rest.web.view.controller.AbstractRestControllerTest;
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

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class RestImportDefinitionControllerTest extends AbstractRestControllerTest {

    private static final String PAGE = "partial/mock/rest/project/restImportDefinition.jsp";
    private static final String IMPORT = "import";
    private static final String TYPE_PARAMETER = "type";
    private static final String DEFINITION_TYPE = "definitionType";
    private static final String DEFINITION_DISPLAY_NAME = "definitionDisplayName";
    private static final String FILE = "file";
    private static final String LING = "link";
    private static final String WADL = "WADL";
    private static final String SWAGGER = "SWAGGER";
    private static final String RAML = "RAML";


    @InjectMocks
    private RestImportDefinitionController restImportDefinitionController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Mock
    private FileManager fileManager;

    @Override
    protected AbstractController getController() {
        return restImportDefinitionController;
    }

    @Test
    public void testImportWADLGet() throws Exception {
        final RestProject restProject = RestProjectGenerator.generateRestProject();
        when(serviceProcessor.process(any(ReadRestProjectInput.class))).thenReturn(new ReadRestProjectOutput(restProject));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + SLASH + PROJECT + SLASH + restProject.getId() + SLASH + IMPORT).param(TYPE_PARAMETER, WADL);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(DEFINITION_TYPE, WADL))
                .andExpect(MockMvcResultMatchers.model().attribute(DEFINITION_DISPLAY_NAME, WADL))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, restProject.getId()));
    }

    @Test
    public void testImportSwaggerGet() throws Exception {
        final RestProject restProject = RestProjectGenerator.generateRestProject();
        when(serviceProcessor.process(any(ReadRestProjectInput.class))).thenReturn(new ReadRestProjectOutput(restProject));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + SLASH + PROJECT + SLASH + restProject.getId() + SLASH + IMPORT).param(TYPE_PARAMETER, SWAGGER);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(DEFINITION_TYPE, SWAGGER))
                .andExpect(MockMvcResultMatchers.model().attribute(DEFINITION_DISPLAY_NAME, "Swagger"))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, restProject.getId()));
    }

    @Test
    public void testImportRAMLGet() throws Exception {
        final RestProject restProject = RestProjectGenerator.generateRestProject();
        when(serviceProcessor.process(any(ReadRestProjectInput.class))).thenReturn(new ReadRestProjectOutput(restProject));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + SLASH + PROJECT + SLASH + restProject.getId() + SLASH + IMPORT).param(TYPE_PARAMETER, RAML);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4 + GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(DEFINITION_TYPE, RAML))
                .andExpect(MockMvcResultMatchers.model().attribute(DEFINITION_DISPLAY_NAME, RAML))
                .andExpect(MockMvcResultMatchers.model().attribute(REST_PROJECT_ID, restProject.getId()));
    }

    @Test
    public void testImportPostFile() throws Exception {
        final RestProject restProject = RestProjectGenerator.generateRestProject();
        final List<File> files = new ArrayList<File>();
        final RestDefinitionFileUploadForm uploadForm = new RestDefinitionFileUploadForm();
        final List<MultipartFile> uploadedFiles = new ArrayList<>();
        uploadForm.setFiles(uploadedFiles);

        when(serviceProcessor.process(any(ReadRestProjectInput.class))).thenReturn(new ReadRestProjectOutput(restProject));
        when(fileManager.uploadFiles(anyListOf(MultipartFile.class))).thenReturn(files);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + SLASH + PROJECT + SLASH + restProject.getId() + SLASH + IMPORT).param(TYPE_PARAMETER, FILE).requestAttr("uploadForm", uploadForm);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
        Mockito.verify(fileManager, times(1)).uploadFiles(anyListOf(MultipartFile.class));
    }

    @Test
    public void testImportPostLink() throws Exception {
        final RestProject restProject = RestProjectGenerator.generateRestProject();
        final List<File> files = new ArrayList<File>();
        final RestDefinitionFileUploadForm uploadForm = new RestDefinitionFileUploadForm();
        final List<MultipartFile> uploadedFiles = new ArrayList<>();
        uploadForm.setFiles(uploadedFiles);

        when(serviceProcessor.process(any(ReadRestProjectInput.class))).thenReturn(new ReadRestProjectOutput(restProject));
        when(fileManager.uploadFiles(anyString())).thenReturn(files);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.post(SERVICE_URL + SLASH + PROJECT + SLASH + restProject.getId() + SLASH + IMPORT).param(TYPE_PARAMETER, LING).requestAttr("uploadForm", uploadForm);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1));
        Mockito.verify(fileManager, times(0)).uploadFiles(anyString());
    }



}
