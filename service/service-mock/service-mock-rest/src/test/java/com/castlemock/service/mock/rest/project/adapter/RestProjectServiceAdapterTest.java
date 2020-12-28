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

package com.castlemock.service.mock.rest.project.adapter;

import com.castlemock.model.core.model.SearchQuery;
import com.castlemock.model.core.model.SearchResult;
import com.castlemock.model.core.model.ServiceProcessor;
import com.castlemock.model.core.model.TypeIdentifier;
import com.castlemock.model.core.model.project.domain.Project;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.model.mock.rest.domain.RestProjectTestBuilder;
import com.castlemock.service.mock.rest.project.input.CreateRestProjectInput;
import com.castlemock.service.mock.rest.project.input.DeleteRestProjectInput;
import com.castlemock.service.mock.rest.project.input.ExportRestProjectInput;
import com.castlemock.service.mock.rest.project.input.ImportRestProjectInput;
import com.castlemock.service.mock.rest.project.input.ReadAllRestProjectsInput;
import com.castlemock.service.mock.rest.project.input.ReadRestProjectInput;
import com.castlemock.service.mock.rest.project.input.SearchRestProjectInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestProjectInput;
import com.castlemock.service.mock.rest.project.output.CreateRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.DeleteRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.ExportRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.ImportRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.ReadAllRestProjectsOutput;
import com.castlemock.service.mock.rest.project.output.ReadRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.SearchRestProjectOutput;
import com.castlemock.service.mock.rest.project.output.UpdateRestProjectOutput;
import com.castlemock.service.mock.rest.RestTypeIdentifier;
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
public class RestProjectServiceAdapterTest {

    @Mock
    private ServiceProcessor serviceProcessor;

    @InjectMocks
    private RestProjectServiceAdapter adapter;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreate(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final CreateRestProjectOutput output = CreateRestProjectOutput.builder().savedRestApplication(project).build();

        Mockito.when(serviceProcessor.process(Mockito.any(CreateRestProjectInput.class))).thenReturn(output);

        final RestProject returnedProject = adapter.create(project);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(CreateRestProjectInput.class));
    }

    @Test
    public void testDelete(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final DeleteRestProjectOutput output = DeleteRestProjectOutput.builder().project(project).build();

        Mockito.when(serviceProcessor.process(Mockito.any(DeleteRestProjectInput.class))).thenReturn(output);

        final RestProject returnedProject = adapter.delete(project.getId());

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(DeleteRestProjectInput.class));
    }

    @Test
    public void testUpdate(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final UpdateRestProjectOutput output = UpdateRestProjectOutput.builder().updatedRestProject(project).build();

        Mockito.when(serviceProcessor.process(Mockito.any(UpdateRestProjectInput.class))).thenReturn(output);

        final RestProject returnedProject = adapter.update(project.getId(), project);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(UpdateRestProjectInput.class));
    }

    @Test
    public void testReadAll(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final List<RestProject> projects = Arrays.asList(project);
        final ReadAllRestProjectsOutput output = ReadAllRestProjectsOutput.builder().restProjects(projects).build();

        Mockito.when(serviceProcessor.process(Mockito.any(ReadAllRestProjectsInput.class))).thenReturn(output);

        final List<RestProject> returnedProjects = adapter.readAll();

        Assert.assertEquals(projects, returnedProjects);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ReadAllRestProjectsInput.class));
    }

    @Test
    public void testRead(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final ReadRestProjectOutput output = ReadRestProjectOutput.builder().restProject(project).build();

        Mockito.when(serviceProcessor.process(Mockito.any(ReadRestProjectInput.class))).thenReturn(output);

        final RestProject returnedProject = adapter.read(project.getId());

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ReadRestProjectInput.class));
    }

    @Test
    public void testGetTypeIdentifier(){
        final TypeIdentifier typeIdentifier = adapter.getTypeIdentifier();

        Assert.assertTrue(typeIdentifier instanceof RestTypeIdentifier);
    }

    @Test
    public void testExportProject(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final String exportedProject = "Exported project";
        final ExportRestProjectOutput output = ExportRestProjectOutput.builder().exportedProject(exportedProject).build();

        Mockito.when(serviceProcessor.process(Mockito.any(ExportRestProjectInput.class))).thenReturn(output);

        final String returnedExportedProject = adapter.exportProject(project.getId());

        Assert.assertEquals(exportedProject, returnedExportedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ExportRestProjectInput.class));
    }

    @Test
    public void testImportProject(){
        final RestProject project = RestProjectTestBuilder.builder().build();
        final String importedProject = "Imported project";
        final ImportRestProjectOutput output = ImportRestProjectOutput.builder().project(project).build();

        Mockito.when(serviceProcessor.process(Mockito.any(ImportRestProjectInput.class))).thenReturn(output);

        final Project returnedProject = adapter.importProject(importedProject);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ImportRestProjectInput.class));
    }
    
    @Test
    public void testSearch(){
        final List<SearchResult> searchResults = new ArrayList<>();
        final SearchResult searchResult = new SearchResult();
        searchResult.setLink("Link");
        searchResult.setTitle("Title");
        searchResult.setDescription("Description");
        searchResults.add(searchResult);
        final SearchRestProjectOutput output = SearchRestProjectOutput.builder().searchResults(searchResults).build();

        Mockito.when(serviceProcessor.process(Mockito.any(SearchRestProjectInput.class))).thenReturn(output);

        final List<SearchResult> returnedSearchResults = adapter.search(new SearchQuery());
        Assert.assertEquals(returnedSearchResults.size(), searchResults.size());
        final SearchResult returnedSearchResult = returnedSearchResults.get(0);
        Assert.assertEquals(returnedSearchResult.getLink(), searchResult.getLink());
        Assert.assertEquals(returnedSearchResult.getTitle(), searchResult.getTitle());
        Assert.assertEquals(returnedSearchResult.getDescription(), searchResult.getDescription());
    }
}
