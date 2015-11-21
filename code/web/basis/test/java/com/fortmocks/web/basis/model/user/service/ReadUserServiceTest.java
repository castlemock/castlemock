package com.fortmocks.web.basis.model.user.service;

import com.fortmocks.core.basis.model.Repository;
import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.basis.model.user.domain.Role;
import com.fortmocks.core.basis.model.user.domain.Status;
import com.fortmocks.core.basis.model.user.domain.User;
import com.fortmocks.core.basis.model.user.dto.UserDto;
import com.fortmocks.core.basis.model.user.service.message.input.ReadAllUsersInput;
import com.fortmocks.core.basis.model.user.service.message.input.ReadUserInput;
import com.fortmocks.core.basis.model.user.service.message.output.ReadAllUsersOutput;
import com.fortmocks.core.basis.model.user.service.message.output.ReadUserOutput;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class ReadUserServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private ReadUserService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        User user = new User();
        user.setId(1L);
        user.setUsername("Username");
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.ADMIN);
        user.setEmail("email@email.com");


        Mockito.when(repository.findOne(Mockito.anyLong())).thenReturn(user);
        final ReadUserInput input = Mockito.mock(ReadUserInput.class);
        input.setUserId(1L);
        final ServiceTask<ReadUserInput> serviceTask = new ServiceTask<ReadUserInput>();
        serviceTask.setInput(input);
        final ServiceResult<ReadUserOutput> serviceResult = service.process(serviceTask);
        final ReadUserOutput output = serviceResult.getOutput();

        final UserDto returnedUser = output.getUser();
        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(user.getId(), returnedUser.getId());
        Assert.assertEquals(user.getEmail(), returnedUser.getEmail());
        Assert.assertEquals(user.getRole(), returnedUser.getRole());
        Assert.assertEquals(user.getStatus(), returnedUser.getStatus());
        Assert.assertEquals(user.getUsername(), returnedUser.getUsername());
    }


}
