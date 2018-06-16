/*
 * Copyright 2016 Karl Dahlgren
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

package com.castlemock.web.basis.model.project.service;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.TypeIdentifier;
import com.castlemock.core.basis.model.project.domain.Project;
import com.castlemock.core.basis.service.project.ProjectServiceAdapter;
import com.castlemock.web.basis.service.project.ProjectServiceFacadeImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.1
 */
public class ProjectServiceFacadeImplTest {

    private static final String TYPE = "Type";
    private static final String TYPE_URL = "TypeUrl";

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private ProjectServiceFacadeImpl serviceFacade;

    private ProjectServiceAdapter projectServiceAdapter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        projectServiceAdapter = Mockito.mock(ProjectServiceAdapter.class);
        final TypeIdentifier typeIdentifier = Mockito.mock(TypeIdentifier.class);
        Mockito.when(typeIdentifier.getType()).thenReturn(TYPE);
        Mockito.when(typeIdentifier.getTypeUrl()).thenReturn(TYPE_URL);
        Mockito.when(projectServiceAdapter.getTypeIdentifier()).thenReturn(typeIdentifier);

        final Map<String, Object> projectServiceAdapters = new HashMap<String, Object>();
        projectServiceAdapters.put("ProjectServiceAdapter", projectServiceAdapter);
        Mockito.when(applicationContext.getBeansWithAnnotation(Mockito.any(Class.class))).thenReturn(projectServiceAdapters);

        serviceFacade.initiate();
    }

    @Test
    public void testExportProject(){
        Mockito.when(projectServiceAdapter.exportProject(Mockito.anyString())).thenReturn("ExportId");
        final String id = serviceFacade.exportProject(TYPE_URL, "ExportId");
        Assert.assertEquals(id, "ExportId");
    }

    @Test
    public void testImportProject(){
        serviceFacade.importProject(TYPE, "Imported project");
        Mockito.verify(projectServiceAdapter, Mockito.times(1)).importProject(Mockito.anyString());
    }

    @Test
    public void testSearch(){
        final List<SearchResult> result = new ArrayList<SearchResult>();
        final SearchResult firstSearchResult = new SearchResult();
        firstSearchResult.setTitle("First title");
        firstSearchResult.setDescription("First description");
        firstSearchResult.setLink("First link");

        final SearchResult secondSearchResult = new SearchResult();
        secondSearchResult.setTitle("Second title");
        secondSearchResult.setDescription("Second description");
        secondSearchResult.setLink("Second link");

        result.add(firstSearchResult);
        result.add(secondSearchResult);
        Mockito.when(projectServiceAdapter.search(Mockito.any(SearchQuery.class))).thenReturn(result);

        final SearchQuery searchQuery = new SearchQuery();
        searchQuery.setQuery("Search query");

        final List<SearchResult> returnedResult = serviceFacade.search(searchQuery);

        Assert.assertEquals(result.size(), returnedResult.size());

        for(int index = 0; index < result.size(); index++){
            SearchResult searchResult = result.get(index);
            SearchResult returnedSearchResult = returnedResult.get(index);

            Assert.assertEquals(searchResult.getTitle(), returnedSearchResult.getTitle());
            Assert.assertEquals(searchResult.getDescription(), returnedSearchResult.getDescription());
            Assert.assertEquals(searchResult.getLink(), returnedSearchResult.getLink());
        }

    }

    @Test
    public void testSave(){
        final Project projectDto = new Project();
        Mockito.when(projectServiceAdapter.convertType(Mockito.any(Project.class))).thenReturn(projectDto);
        serviceFacade.save(TYPE, projectDto);
        Mockito.verify(projectServiceAdapter, Mockito.times(1)).create(Mockito.any(Project.class));
    }

    @Test
    public void testDelete(){
        serviceFacade.delete(TYPE_URL, "Delete project");
        Mockito.verify(projectServiceAdapter, Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test
    public void testUpdate(){
        final Project projectDto = new Project();
        Mockito.when(projectServiceAdapter.convertType(Mockito.any(Project.class))).thenReturn(projectDto);
        serviceFacade.update(TYPE_URL, "Id", projectDto);
        Mockito.verify(projectServiceAdapter, Mockito.times(1)).update(Mockito.anyString(), Mockito.any(Project.class));
    }

    @Test
    public void testFindAll(){
        final Project projectDto = new Project();
        final List<Project> projectDtos = new ArrayList<Project>();
        projectDtos.add(projectDto);
        Mockito.when(projectServiceAdapter.readAll()).thenReturn(projectDtos);

        final List<Project> returnedProjectDtos = serviceFacade.findAll();
        Assert.assertEquals(1, returnedProjectDtos.size());

        for(Project returnedProjectDto : returnedProjectDtos){
            Assert.assertEquals(TYPE, returnedProjectDto.getTypeIdentifier().getType());
        }
    }

    @Test
    public void testFindOne(){
        final Project projectDto = new Project();
        Mockito.when(projectServiceAdapter.read(Mockito.anyString())).thenReturn(projectDto);
        final Project returnedProjectDto = serviceFacade.findOne(TYPE_URL, "Id");
        Assert.assertEquals(projectDto, returnedProjectDto);
        Assert.assertEquals(TYPE, projectDto.getTypeIdentifier().getType());
    }

    @Test
    public void tesGetTypes(){
        List<String> types = serviceFacade.getTypes();
        Assert.assertEquals(1, types.size());
        Assert.assertEquals(TYPE.toUpperCase(), types.get(0));
    }

    @Test
    public void testGetTypeUrl(){
        final String typeUrl = serviceFacade.getTypeUrl(TYPE);
        Assert.assertEquals(TYPE_URL, typeUrl);
    }

}
