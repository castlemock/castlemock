package com.fortmocks.web.mock.soap.model.project.service;

import com.fortmocks.core.basis.model.Repository;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.core.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.core.mock.soap.model.project.domain.SoapPort;
import com.fortmocks.core.mock.soap.model.project.domain.SoapProject;
import com.fortmocks.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.core.mock.soap.model.project.service.message.input.CreateRecordedSoapMockResponseInput;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateRecordedSoapMockResponseServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private CreateRecordedSoapMockResponseService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final SoapProject soapProject = new SoapProject();
        final SoapPort soapPort = new SoapPort();
        final SoapOperation soapOperation = new SoapOperation();
        soapOperation.setId(new String());

        soapProject.setPorts(new ArrayList<SoapPort>());
        soapPort.setOperations(new ArrayList<SoapOperation>());
        soapOperation.setMockResponses(new ArrayList<SoapMockResponse>());

        soapProject.getPorts().add(soapPort);
        soapPort.getOperations().add(soapOperation);

        List<SoapProject> soapProjects = new ArrayList<SoapProject>();
        soapProjects.add(soapProject);

        Mockito.when(repository.findAll()).thenReturn(soapProjects);

    }

    @Test
    public void testProcess(){
        final String SoapMethodId = new String();
        final SoapMockResponseDto SoapMockResponseDto = Mockito.mock(SoapMockResponseDto.class);
        final CreateRecordedSoapMockResponseInput input = new CreateRecordedSoapMockResponseInput(SoapMethodId, SoapMockResponseDto);
        final ServiceTask<CreateRecordedSoapMockResponseInput> serviceTask = new ServiceTask<>(input);
        service.process(serviceTask);
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(SoapProject.class));
    }
}
