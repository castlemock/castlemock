package com.castlemock.web.mock.soap.model.event.repository;

import com.castlemock.core.mock.soap.model.event.domain.SoapEvent;
import com.castlemock.core.mock.soap.model.event.dto.SoapEventDto;
import com.castlemock.web.basis.support.FileRepositorySupport;
import com.castlemock.web.mock.soap.model.event.SoapEventDtoGenerator;
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
public class SoapEventRepositoryTest {

    @Mock
    private FileRepositorySupport fileRepositorySupport;
    @Spy
    private DozerBeanMapper mapper;
    @InjectMocks
    private SoapEventRepositoryImpl repository;
    private static final String DIRECTORY = "/directory";
    private static final String EXTENSION = ".extension";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(repository, "soapEventFileDirectory", DIRECTORY);
        ReflectionTestUtils.setField(repository, "soapEventFileExtension", EXTENSION);
    }

    @Test
    public void testInitialize(){
        List<SoapEvent> soapEvents = new ArrayList<SoapEvent>();
        SoapEvent soapEvent = SoapEventDtoGenerator.generateSoapEvent();
        soapEvents.add(soapEvent);
        Mockito.when(fileRepositorySupport.load(SoapEvent.class, DIRECTORY, EXTENSION)).thenReturn(soapEvents);
        repository.initialize();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).load(SoapEvent.class, DIRECTORY, EXTENSION);
    }

    @Test
    public void testFindOne(){
        final SoapEventDto soapEvent = save();
        final SoapEventDto returnedSoapEvent = repository.findOne(soapEvent.getId());
        Assert.assertEquals(returnedSoapEvent.getProjectId(), soapEvent.getProjectId());
        Assert.assertEquals(returnedSoapEvent.getId(), soapEvent.getId());
        Assert.assertEquals(returnedSoapEvent.getResourceName(), soapEvent.getResourceName());
        Assert.assertEquals(returnedSoapEvent.getTypeIdentifier(), soapEvent.getTypeIdentifier());
        Assert.assertEquals(returnedSoapEvent.getOperationId(), soapEvent.getOperationId());
        Assert.assertEquals(returnedSoapEvent.getPortId(), soapEvent.getPortId());
        Assert.assertEquals(returnedSoapEvent.getResourceName(), soapEvent.getResourceName());
        Assert.assertEquals(returnedSoapEvent.getResourceLink(), soapEvent.getResourceLink());
    }

    @Test
    public void testFindAll(){
        final SoapEventDto soapEvent = save();
        final List<SoapEventDto> soapEvents = repository.findAll();
        Assert.assertEquals(soapEvents.size(), 1);
        Assert.assertEquals(soapEvents.get(0).getProjectId(), soapEvent.getProjectId());
        Assert.assertEquals(soapEvents.get(0).getId(), soapEvent.getId());
        Assert.assertEquals(soapEvents.get(0).getResourceName(), soapEvent.getResourceName());
        Assert.assertEquals(soapEvents.get(0).getTypeIdentifier(), soapEvent.getTypeIdentifier());
        Assert.assertEquals(soapEvents.get(0).getOperationId(), soapEvent.getOperationId());
        Assert.assertEquals(soapEvents.get(0).getPortId(), soapEvent.getPortId());
        Assert.assertEquals(soapEvents.get(0).getResourceName(), soapEvent.getResourceName());
        Assert.assertEquals(soapEvents.get(0).getResourceLink(), soapEvent.getResourceLink());
    }

    @Test
    public void testSave(){
        final SoapEventDto soapEvent = save();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(Mockito.any(SoapEvent.class), Mockito.anyString());
    }

    @Test
    public void testDelete(){
        final SoapEventDto soapEvent = save();
        repository.delete(soapEvent.getId());
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).delete(DIRECTORY + File.separator + soapEvent.getId() + EXTENSION);
    }

    @Test
    public void testCount(){
        final SoapEventDto soapEvent = save();
        final Integer count = repository.count();
        Assert.assertEquals(new Integer(1), count);
    }

    private SoapEventDto save(){
        final SoapEventDto soapEvent = SoapEventDtoGenerator.generateSoapEventDto();
        repository.save(soapEvent);
        return soapEvent;
    }

}
