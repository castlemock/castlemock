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

package com.castlemock.web.mock.graphql.service.project.adapter;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.core.basis.model.TypeIdentifier;
import com.castlemock.core.basis.model.project.domain.Project;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLProject;
import com.castlemock.core.mock.graphql.service.project.input.*;
import com.castlemock.core.mock.graphql.service.project.output.*;
import com.castlemock.web.mock.graphql.model.GraphQLTypeIdentifier;
import com.castlemock.web.mock.graphql.model.project.GraphQLProjectGenerator;
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
public class GraphQLProjectServiceAdapterTest {

    @Mock
    private ServiceProcessor serviceProcessor;

    @InjectMocks
    private GraphQLProjectServiceAdapter adapter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreate(){
        final GraphQLProject project = GraphQLProjectGenerator.generateGraphQLProject();
        final CreateGraphQLProjectOutput output = new CreateGraphQLProjectOutput(project);

        Mockito.when(serviceProcessor.process(Mockito.any(CreateGraphQLProjectInput.class))).thenReturn(output);

        final GraphQLProject returnedProject = adapter.create(project);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(CreateGraphQLProjectInput.class));
    }

    @Test
    public void testDelete(){
        final GraphQLProject project = GraphQLProjectGenerator.generateGraphQLProject();
        final DeleteGraphQLProjectOutput output = new DeleteGraphQLProjectOutput(project);

        Mockito.when(serviceProcessor.process(Mockito.any(DeleteGraphQLProjectInput.class))).thenReturn(output);

        final GraphQLProject returnedProject = adapter.delete(project.getId());

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(DeleteGraphQLProjectInput.class));
    }

    @Test
    public void testUpdate(){
        final GraphQLProject project = GraphQLProjectGenerator.generateGraphQLProject();
        final UpdateGraphQLProjectOutput output = new UpdateGraphQLProjectOutput(project);

        Mockito.when(serviceProcessor.process(Mockito.any(UpdateGraphQLProjectInput.class))).thenReturn(output);

        final GraphQLProject returnedProject = adapter.update(project.getId(), project);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(UpdateGraphQLProjectInput.class));
    }

    @Test
    public void testReadAll(){
        final GraphQLProject project = GraphQLProjectGenerator.generateGraphQLProject();
        final List<GraphQLProject> projects = Arrays.asList(project);
        final ReadAllGraphQLProjectsOutput output = new ReadAllGraphQLProjectsOutput(projects);

        Mockito.when(serviceProcessor.process(Mockito.any(ReadAllGraphQLProjectsInput.class))).thenReturn(output);

        final List<GraphQLProject> returnedProjects = adapter.readAll();

        Assert.assertEquals(projects, returnedProjects);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ReadAllGraphQLProjectsInput.class));
    }

    @Test
    public void testRead(){
        final GraphQLProject project = GraphQLProjectGenerator.generateGraphQLProject();
        final ReadGraphQLProjectOutput output = new ReadGraphQLProjectOutput(project);

        Mockito.when(serviceProcessor.process(Mockito.any(ReadGraphQLProjectInput.class))).thenReturn(output);

        final GraphQLProject returnedProject = adapter.read(project.getId());

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ReadGraphQLProjectInput.class));
    }

    @Test
    public void testGetTypeIdentifier(){
        final TypeIdentifier typeIdentifier = adapter.getTypeIdentifier();

        Assert.assertTrue(typeIdentifier instanceof GraphQLTypeIdentifier);
    }

    @Test
    public void testExportProject(){
        final GraphQLProject project = GraphQLProjectGenerator.generateGraphQLProject();
        final String exportedProject = "Exported project";
        final ExportGraphQLProjectOutput output = new ExportGraphQLProjectOutput(exportedProject);

        Mockito.when(serviceProcessor.process(Mockito.any(ExportGraphQLProjectInput.class))).thenReturn(output);

        final String returnedExportedProject = adapter.exportProject(project.getId());

        Assert.assertEquals(exportedProject, returnedExportedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ExportGraphQLProjectInput.class));
    }

    @Test
    public void testImportProject(){
        final GraphQLProject project = GraphQLProjectGenerator.generateGraphQLProject();
        final String importedProject = "Imported project";
        final ImportGraphQLProjectOutput output = new ImportGraphQLProjectOutput(project);

        Mockito.when(serviceProcessor.process(Mockito.any(ImportGraphQLProjectInput.class))).thenReturn(output);

        final Project returnedProject = adapter.importProject(importedProject);

        Assert.assertEquals(project, returnedProject);
        Mockito.verify(serviceProcessor, Mockito.times(1)).process(Mockito.any(ImportGraphQLProjectInput.class));
    }
    
    @Test
    public void testSearch(){
        final List<SearchResult> searchResults = new ArrayList<>();
        final SearchResult searchResult = new SearchResult();
        searchResult.setLink("Link");
        searchResult.setTitle("Title");
        searchResult.setDescription("Description");
        searchResults.add(searchResult);
        final SearchGraphQLProjectOutput output = new SearchGraphQLProjectOutput(searchResults);

        Mockito.when(serviceProcessor.process(Mockito.any(SearchGraphQLProjectInput.class))).thenReturn(output);

        final List<SearchResult> returnedSearchResults = adapter.search(new SearchQuery());
        Assert.assertEquals(returnedSearchResults.size(), searchResults.size());
        final SearchResult returnedSearchResult = returnedSearchResults.get(0);
        Assert.assertEquals(returnedSearchResult.getLink(), searchResult.getLink());
        Assert.assertEquals(returnedSearchResult.getTitle(), searchResult.getTitle());
        Assert.assertEquals(returnedSearchResult.getDescription(), searchResult.getDescription());
    }
}
