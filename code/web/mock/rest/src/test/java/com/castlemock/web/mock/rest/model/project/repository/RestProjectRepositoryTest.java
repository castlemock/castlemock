package com.castlemock.web.mock.rest.model.project.repository;


import com.castlemock.core.mock.rest.model.project.domain.RestProject;
import com.castlemock.core.mock.rest.model.project.dto.RestProjectDto;
import com.castlemock.web.basis.support.FileRepositorySupport;
import com.castlemock.web.mock.rest.model.project.RestProjectDtoGenerator;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
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
    @Spy
    private DozerBeanMapper mapper;
    @InjectMocks
    private RestProjectRepositoryImpl repository;
    private static final String DIRECTORY = "/directory";
    private static final String EXTENSION = ".extension";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(repository, "restProjectFileDirectory", DIRECTORY);
        ReflectionTestUtils.setField(repository, "restProjectFileExtension", EXTENSION);
    }

    @Test
    public void testInitialize(){
        List<RestProject> restProjects = new ArrayList<RestProject>();
        RestProject restProject = RestProjectDtoGenerator.generateFullRestProject();
        restProjects.add(restProject);
        Mockito.when(fileRepositorySupport.load(RestProject.class, DIRECTORY, EXTENSION)).thenReturn(restProjects);
        repository.initialize();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).load(RestProject.class, DIRECTORY, EXTENSION);
    }

    @Test
    public void testFindOne(){
        final RestProjectDto restProject = save();
        final RestProjectDto returnedRestEvent = repository.findOne(restProject.getId());
        Assert.assertEquals(returnedRestEvent.getId(), restProject.getId());
        Assert.assertEquals(returnedRestEvent.getDescription(), restProject.getDescription());
        Assert.assertEquals(returnedRestEvent.getName(), restProject.getName());
    }

    @Test
    public void testFindAll(){
        final RestProjectDto restProject = save();
        final List<RestProjectDto> restProjects = repository.findAll();
        Assert.assertEquals(restProjects.size(), 1);
        Assert.assertEquals(restProjects.get(0).getId(), restProject.getId());
        Assert.assertEquals(restProjects.get(0).getDescription(), restProject.getDescription());
        Assert.assertEquals(restProjects.get(0).getName(), restProject.getName());
    }

    @Test
    public void testSave(){
        final RestProjectDto restProject = save();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(Mockito.any(RestProject.class), Mockito.anyString());
    }

    @Test
    public void testDelete(){
        final RestProjectDto restProject = save();
        repository.delete(restProject.getId());
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).delete(DIRECTORY + File.separator + restProject.getId() + EXTENSION);
    }

    @Test
    public void testCount(){
        final RestProjectDto restProject = save();
        final Integer count = repository.count();
        Assert.assertEquals(new Integer(1), count);
    }

    private RestProjectDto save(){
        RestProjectDto restProject = RestProjectDtoGenerator.generateFullRestProjectDto();
        repository.save(restProject);
        return restProject;
    }

}
