package com.castlemock.web.mock.rest.model.event.repository;


import com.castlemock.core.mock.rest.model.event.domain.RestEvent;
import com.castlemock.core.mock.rest.model.event.dto.RestEventDto;
import com.castlemock.web.basis.support.FileRepositorySupport;
import com.castlemock.web.mock.rest.model.project.RestEventDtoGenerator;
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
public class RestEventRepositoryTest {

    @Mock
    private FileRepositorySupport fileRepositorySupport;
    @Spy
    private DozerBeanMapper mapper;
    @InjectMocks
    private RestEventRepositoryImpl repository;
    private static final String DIRECTORY = "/directory";
    private static final String EXTENSION = ".extension";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(repository, "restEventFileDirectory", DIRECTORY);
        ReflectionTestUtils.setField(repository, "restEventFileExtension", EXTENSION);
    }

    @Test
    public void testInitialize(){
        List<RestEvent> restEvents = new ArrayList<RestEvent>();
        RestEvent restEvent = RestEventDtoGenerator.generateRestEvent();
        restEvents.add(restEvent);
        Mockito.when(fileRepositorySupport.load(RestEvent.class, DIRECTORY, EXTENSION)).thenReturn(restEvents);
        repository.initialize();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).load(RestEvent.class, DIRECTORY, EXTENSION);
    }

    @Test
    public void testFindOne(){
        final RestEventDto restEvent = save();
        final RestEventDto returnedRestEvent = repository.findOne(restEvent.getId());
        Assert.assertEquals(returnedRestEvent.getResourceId(), restEvent.getResourceId());
        Assert.assertEquals(returnedRestEvent.getApplicationId(), restEvent.getApplicationId());
        Assert.assertEquals(returnedRestEvent.getMethodId(), restEvent.getMethodId());
        Assert.assertEquals(returnedRestEvent.getProjectId(), restEvent.getProjectId());
        Assert.assertEquals(returnedRestEvent.getResourceName(), restEvent.getResourceName());
    }

    @Test
    public void testFindAll(){
        final RestEventDto restEvent = save();
        final List<RestEventDto> restEvents = repository.findAll();
        Assert.assertEquals(restEvents.size(), 1);
        Assert.assertEquals(restEvents.get(0).getResourceId(), restEvent.getResourceId());
        Assert.assertEquals(restEvents.get(0).getApplicationId(), restEvent.getApplicationId());
        Assert.assertEquals(restEvents.get(0).getMethodId(), restEvent.getMethodId());
        Assert.assertEquals(restEvents.get(0).getProjectId(), restEvent.getProjectId());
        Assert.assertEquals(restEvents.get(0).getResourceName(), restEvent.getResourceName());
    }

    @Test
    public void testSave(){
        final RestEventDto restEvent = save();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(Mockito.any(RestEvent.class), Mockito.anyString());
    }

    @Test
    public void testDelete(){
        final RestEventDto restEvent = save();
        repository.delete(restEvent.getId());
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).delete(DIRECTORY + File.separator + restEvent.getId() + EXTENSION);
    }

    @Test
    public void testCount(){
        final RestEventDto restEvent = save();
        final Integer count = repository.count();
        Assert.assertEquals(new Integer(1), count);
    }

    private RestEventDto save(){
        final RestEventDto restEvent = RestEventDtoGenerator.generateRestEventDto();
        repository.save(restEvent);
        return restEvent;
    }

}
