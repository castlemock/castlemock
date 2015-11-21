package com.fortmocks.web.basis.model.user.service;

import com.fortmocks.core.basis.model.Repository;
import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.basis.model.user.domain.Role;
import com.fortmocks.core.basis.model.user.domain.Status;
import com.fortmocks.core.basis.model.user.domain.User;
import com.fortmocks.core.basis.model.user.dto.UserDto;
import com.fortmocks.core.basis.model.user.service.message.input.ReadUserByUsernameInput;
import com.fortmocks.core.basis.model.user.service.message.input.ReadUsersByRoleInput;
import com.fortmocks.core.basis.model.user.service.message.output.ReadUserByUsernameOutput;
import com.fortmocks.core.basis.model.user.service.message.output.ReadUsersByRoleOutput;
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
public class ReadUsersByRoleServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private ReadUsersByRoleService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        List<User> users = new ArrayList<User>();
        User user = new User();
        user.setId(1L);
        user.setUsername("Username");
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.ADMIN);
        user.setEmail("email@email.com");
        users.add(user);


        Mockito.when(repository.findAll()).thenReturn(users);
        final ReadUsersByRoleInput input = new ReadUsersByRoleInput(Role.ADMIN);
        final ServiceTask<ReadUsersByRoleInput> serviceTask = new ServiceTask<ReadUsersByRoleInput>();
        serviceTask.setInput(input);
        final ServiceResult<ReadUsersByRoleOutput> serviceResult = service.process(serviceTask);
        final ReadUsersByRoleOutput output = serviceResult.getOutput();

        final List<UserDto> returnedUsers = output.getUsers();
        Assert.assertNotNull(returnedUsers);
        Assert.assertEquals(users.size(), returnedUsers.size());
        final UserDto returnedUser = returnedUsers.get(0);
        Assert.assertEquals(user.getId(), returnedUser.getId());
        Assert.assertEquals(user.getEmail(), returnedUser.getEmail());
        Assert.assertEquals(user.getRole(), returnedUser.getRole());
        Assert.assertEquals(user.getStatus(), returnedUser.getStatus());
        Assert.assertEquals(user.getUsername(), returnedUser.getUsername());
    }


}
