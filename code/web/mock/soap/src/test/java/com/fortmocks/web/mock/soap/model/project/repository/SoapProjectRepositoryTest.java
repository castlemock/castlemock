package com.fortmocks.web.mock.soap.model.project.repository;


import com.fortmocks.core.mock.soap.model.project.domain.SoapProject;
import com.fortmocks.web.basis.support.FileRepositorySupport;
import com.fortmocks.web.mock.soap.model.project.SoapProjectDtoGenerator;
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
public class SoapProjectRepositoryTest {

    @Mock
    private FileRepositorySupport fileRepositorySupport;

    @InjectMocks
    private SoapProjectRepositoryImpl repository;
    private static final String DIRECTORY = "/directory";
    private static final String EXTENSION = ".extension";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(repository, "soapProjectFileDirectory", DIRECTORY);
        ReflectionTestUtils.setField(repository, "soapProjectFileExtension", EXTENSION);
    }

    @Test
    public void testInitialize(){
        List<SoapProject> soapProjects = new ArrayList<SoapProject>();
        SoapProject soapProject = SoapProjectDtoGenerator.generateFullSoapProject();
        soapProjects.add(soapProject);
        Mockito.when(fileRepositorySupport.load(SoapProject.class, DIRECTORY, EXTENSION)).thenReturn(soapProjects);
        repository.initialize();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).load(SoapProject.class, DIRECTORY, EXTENSION);
    }

    @Test
    public void testFindOne(){
        final SoapProject soapProject = save();
        final SoapProject returnedSoapEvent = repository.findOne(soapProject.getId());
        Assert.assertEquals(soapProject, returnedSoapEvent);
    }

    @Test
    public void testFindAll(){
        final SoapProject soapProject = save();
        final List<SoapProject> soapProjects = repository.findAll();
        Assert.assertEquals(soapProjects.size(), 1);
        Assert.assertEquals(soapProjects.get(0), soapProject);
    }

    @Test
    public void testSave(){
        final SoapProject soapProject = save();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(soapProject, DIRECTORY + File.separator + soapProject.getId() + EXTENSION);
    }

    @Test
    public void testDelete(){
        final SoapProject soapProject = save();
        repository.delete(soapProject.getId());
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).delete(DIRECTORY + File.separator + soapProject.getId() + EXTENSION);
    }

    @Test
    public void testCount(){
        final SoapProject soapProject = save();
        final Integer count = repository.count();
        Assert.assertEquals(new Integer(1), count);
    }

    private SoapProject save(){
        SoapProject soapProject = SoapProjectDtoGenerator.generateFullSoapProject();
        repository.save(soapProject);
        return soapProject;
    }

}
