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

package com.castlemock.web.mock.soap.service.project.adapter;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.TypeIdentifier;
import com.castlemock.core.basis.model.project.domain.Project;
import com.castlemock.core.mock.soap.model.project.domain.SoapProject;
import com.castlemock.core.mock.soap.model.project.domain.SoapProjectTestBuilder;
import com.castlemock.core.mock.soap.service.project.input.CreateSoapProjectInput;
import com.castlemock.core.mock.soap.service.project.input.DeleteSoapProjectInput;
import com.castlemock.core.mock.soap.service.project.input.ExportSoapProjectInput;
import com.castlemock.core.mock.soap.service.project.input.ImportSoapProjectInput;
import com.castlemock.core.mock.soap.service.project.input.ReadAllSoapProjectsInput;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapProjectInput;
import com.castlemock.core.mock.soap.service.project.input.SearchSoapProjectInput;
import com.castlemock.core.mock.soap.service.project.input.UpdateSoapProjectInput;
import com.castlemock.core.mock.soap.service.project.output.CreateSoapProjectOutput;
import com.castlemock.core.mock.soap.service.project.output.DeleteSoapProjectOutput;
import com.castlemock.core.mock.soap.service.project.output.ExportSoapProjectOutput;
import com.castlemock.core.mock.soap.service.project.output.ImportSoapProjectOutput;
import com.castlemock.core.mock.soap.service.project.output.ReadAllSoapProjectsOutput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapProjectOutput;
import com.castlemock.core.mock.soap.service.project.output.SearchSoapProjectOutput;
import com.castlemock.core.mock.soap.service.project.output.UpdateSoapProjectOutput;
import com.castlemock.web.mock.soap.model.SoapTypeIdentifier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapProjectServiceAdapterTest {

    @Mock
    private ServiceProcessor serviceProcessor;

    @InjectMocks
    private SoapProjectServiceAdapter adapter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final CreateSoapProjectOutput output = CreateSoapProjectOutput.builder()
                .project(project)
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any(CreateSoapProjectInput.class))).thenReturn(output);

        final SoapProject returnedProject = adapter.create(project);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(CreateSoapProjectInput.class));
    }

    @Test
    public void testDelete(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final DeleteSoapProjectOutput output = DeleteSoapProjectOutput.builder()
                .project(project)
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any(DeleteSoapProjectInput.class))).thenReturn(output);

        final SoapProject returnedProject = adapter.delete(project.getId());

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(DeleteSoapProjectInput.class));
    }

    @Test
    public void testUpdate(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final UpdateSoapProjectOutput output = UpdateSoapProjectOutput.builder()
                .project(project)
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any(UpdateSoapProjectInput.class))).thenReturn(output);

        final SoapProject returnedProject = adapter.update(project.getId(), project);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(UpdateSoapProjectInput.class));
    }

    @Test
    public void testReadAll(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final List<SoapProject> projects = Arrays.asList(project);
        final ReadAllSoapProjectsOutput output = ReadAllSoapProjectsOutput.builder()
                .projects(projects)
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any(ReadAllSoapProjectsInput.class))).thenReturn(output);

        final List<SoapProject> returnedProjects = adapter.readAll();

        Assert.assertEquals(projects, returnedProjects);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ReadAllSoapProjectsInput.class));
    }

    @Test
    public void testRead(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final ReadSoapProjectOutput output = ReadSoapProjectOutput.builder()
                .project(project)
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any(ReadSoapProjectInput.class))).thenReturn(output);

        final SoapProject returnedProject = adapter.read(project.getId());

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ReadSoapProjectInput.class));
    }

    @Test
    public void testGetTypeIdentifier(){
        final TypeIdentifier typeIdentifier = adapter.getTypeIdentifier();

        Assert.assertTrue(typeIdentifier instanceof SoapTypeIdentifier);
    }

    @Test
    public void testExportProject(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final String exportedProject = "Exported project";
        final ExportSoapProjectOutput output = ExportSoapProjectOutput.builder()
                .project(exportedProject)
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any(ExportSoapProjectInput.class))).thenReturn(output);

        final String returnedExportedProject = adapter.exportProject(project.getId());

        Assert.assertEquals(exportedProject, returnedExportedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ExportSoapProjectInput.class));
    }

    @Test
    public void testImportProject(){
        final SoapProject project = SoapProjectTestBuilder.builder().build();
        final String importedProject = "Imported project";
        final ImportSoapProjectOutput output = ImportSoapProjectOutput.builder()
                .project(project)
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any(ImportSoapProjectInput.class))).thenReturn(output);

        final Project returnedProject = adapter.importProject(importedProject);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ImportSoapProjectInput.class));
    }

    @Test
    public void testSearch(){
        final List<SearchResult> searchResults = new ArrayList<>();
        final SearchResult searchResult = new SearchResult();
        searchResult.setLink("Link");
        searchResult.setTitle("Title");
        searchResult.setDescription("Description");
        searchResults.add(searchResult);
        final SearchSoapProjectOutput output = SearchSoapProjectOutput.builder()
                .searchResults(searchResults)
                .build();

        Mockito.when(serviceProcessor.process(Mockito.any(SearchSoapProjectInput.class))).thenReturn(output);

        final List<SearchResult> returnedSearchResults = adapter.search(new SearchQuery());
        Assert.assertEquals(returnedSearchResults.size(), searchResults.size());
        final SearchResult returnedSearchResult = returnedSearchResults.get(0);
        Assert.assertEquals(returnedSearchResult.getLink(), searchResult.getLink());
        Assert.assertEquals(returnedSearchResult.getTitle(), searchResult.getTitle());
        Assert.assertEquals(returnedSearchResult.getDescription(), searchResult.getDescription());
    }
}
