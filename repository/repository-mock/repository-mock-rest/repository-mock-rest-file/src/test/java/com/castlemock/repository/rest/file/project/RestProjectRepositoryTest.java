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

package com.castlemock.repository.rest.file.project;


import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.model.mock.rest.domain.RestProjectTestBuilder;
import com.castlemock.repository.core.file.FileRepositorySupport;
import com.castlemock.repository.rest.file.project.model.RestProjectFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.4
 */
public class RestProjectRepositoryTest {

    @Mock
    private FileRepositorySupport fileRepositorySupport;
    @InjectMocks
    private RestProjectFileRepository repository;
    private static final String DIRECTORY = "/directory";
    private static final String EXTENSION = ".extension";

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(repository, "restProjectFileDirectory", DIRECTORY);
        ReflectionTestUtils.setField(repository, "restProjectFileExtension", EXTENSION);
    }

    @Test
    public void testInitialize(){
        List<RestProject> restProjects = new ArrayList<>();
        RestProject restProject = RestProjectTestBuilder.builder().build();
        restProjects.add(restProject);
        Mockito.when(fileRepositorySupport.load(RestProject.class, DIRECTORY, EXTENSION)).thenReturn(restProjects);
        repository.initialize();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).load(RestProjectFile.class, DIRECTORY, EXTENSION);
    }

    @Test
    public void testFindOne(){
        final RestProject restProject = save();
        final RestProject returnedRestEvent = repository.findOne(restProject.getId()).orElse(null);
        Assert.assertNotNull(returnedRestEvent);
        Assert.assertEquals(returnedRestEvent.getId(), restProject.getId());
        Assert.assertEquals(returnedRestEvent.getDescription(), restProject.getDescription());
        Assert.assertEquals(returnedRestEvent.getName(), restProject.getName());
    }

    @Test
    public void testFindAll(){
        final RestProject restProject = save();
        final List<RestProject> restProjects = repository.findAll();
        Assert.assertEquals(restProjects.size(), 1);
        Assert.assertEquals(restProjects.getFirst().getId(), restProject.getId());
        Assert.assertEquals(restProjects.getFirst().getDescription(), restProject.getDescription());
        Assert.assertEquals(restProjects.getFirst().getName(), restProject.getName());
    }

    @Test
    public void testSave(){
        final RestProject restProject = save();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(Mockito.any(RestProjectFile.class), Mockito.anyString());
    }

    @Test
    public void testDelete(){
        final RestProject restProject = save();
        repository.delete(restProject.getId());
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).delete(DIRECTORY + File.separator + restProject.getId() + EXTENSION);
    }

    @Test
    public void testCount(){
        final RestProject restProject = save();
        final Integer count = repository.count();
        Assert.assertEquals(Integer.valueOf(1), count);
    }

    private RestProject save(){
        RestProject restProject = RestProjectTestBuilder.builder().build();
        repository.save(restProject);
        return restProject;
    }

}
