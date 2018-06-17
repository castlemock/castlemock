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

package com.castlemock.web.mock.rest.service.project.adapter;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.TypeIdentifier;
import com.castlemock.core.basis.model.project.domain.Project;
import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.service.project.input.*;
import com.castlemock.core.mock.rest.service.project.output.*;
import com.castlemock.web.mock.rest.model.RestTypeIdentifier;
import com.castlemock.web.mock.rest.model.project.RestProjectGenerator;
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
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreate(){
        final RestProject project = RestProjectGenerator.generateRestProject();
        final CreateRestProjectOutput output = new CreateRestProjectOutput(project);

        Mockito.when(serviceProcessor.process(Mockito.any(CreateRestProjectInput.class))).thenReturn(output);

        final RestProject returnedProject = adapter.create(project);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(CreateRestProjectInput.class));
    }

    @Test
    public void testDelete(){
        final RestProject project = RestProjectGenerator.generateRestProject();
        final DeleteRestProjectOutput output = new DeleteRestProjectOutput(project);

        Mockito.when(serviceProcessor.process(Mockito.any(DeleteRestProjectInput.class))).thenReturn(output);

        final RestProject returnedProject = adapter.delete(project.getId());

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(DeleteRestProjectInput.class));
    }

    @Test
    public void testUpdate(){
        final RestProject project = RestProjectGenerator.generateRestProject();
        final UpdateRestProjectOutput output = new UpdateRestProjectOutput(project);

        Mockito.when(serviceProcessor.process(Mockito.any(UpdateRestProjectInput.class))).thenReturn(output);

        final RestProject returnedProject = adapter.update(project.getId(), project);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(UpdateRestProjectInput.class));
    }

    @Test
    public void testReadAll(){
        final RestProject project = RestProjectGenerator.generateRestProject();
        final List<RestProject> projects = Arrays.asList(project);
        final ReadAllRestProjectsOutput output = new ReadAllRestProjectsOutput(projects);

        Mockito.when(serviceProcessor.process(Mockito.any(ReadAllRestProjectsInput.class))).thenReturn(output);

        final List<RestProject> returnedProjects = adapter.readAll();

        Assert.assertEquals(projects, returnedProjects);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ReadAllRestProjectsInput.class));
    }

    @Test
    public void testRead(){
        final RestProject project = RestProjectGenerator.generateRestProject();
        final ReadRestProjectOutput output = new ReadRestProjectOutput(project);

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
        final RestProject project = RestProjectGenerator.generateRestProject();
        final String exportedProject = "Exported project";
        final ExportRestProjectOutput output = new ExportRestProjectOutput(exportedProject);

        Mockito.when(serviceProcessor.process(Mockito.any(ExportRestProjectInput.class))).thenReturn(output);

        final String returnedExportedProject = adapter.exportProject(project.getId());

        Assert.assertEquals(exportedProject, returnedExportedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ExportRestProjectInput.class));
    }

    @Test
    public void testImportProject(){
        final RestProject project = RestProjectGenerator.generateRestProject();
        final String importedProject = "Imported project";
        final ImportRestProjectOutput output = new ImportRestProjectOutput(project);

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
        final SearchRestProjectOutput output = new SearchRestProjectOutput(searchResults);

        Mockito.when(serviceProcessor.process(Mockito.any(SearchRestProjectInput.class))).thenReturn(output);

        final List<SearchResult> returnedSearchResults = adapter.search(new SearchQuery());
        Assert.assertEquals(returnedSearchResults.size(), searchResults.size());
        final SearchResult returnedSearchResult = returnedSearchResults.get(0);
        Assert.assertEquals(returnedSearchResult.getLink(), searchResult.getLink());
        Assert.assertEquals(returnedSearchResult.getTitle(), searchResult.getTitle());
        Assert.assertEquals(returnedSearchResult.getDescription(), searchResult.getDescription());
    }
}
