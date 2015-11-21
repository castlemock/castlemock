package com.fortmocks.web.basis.model.user.service;

import com.fortmocks.core.basis.model.Repository;
import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.basis.model.configuration.domain.ConfigurationType;
import com.fortmocks.core.basis.model.configuration.dto.ConfigurationDto;
import com.fortmocks.core.basis.model.configuration.dto.ConfigurationGroupDto;
import com.fortmocks.core.basis.model.configuration.service.message.input.ReadAllConfigurationGroupsInput;
import com.fortmocks.core.basis.model.configuration.service.message.output.ReadAllConfigurationGroupsOutput;
import com.fortmocks.core.basis.model.user.domain.Role;
import com.fortmocks.core.basis.model.user.domain.Status;
import com.fortmocks.core.basis.model.user.domain.User;
import com.fortmocks.core.basis.model.user.dto.UserDto;
import com.fortmocks.core.basis.model.user.service.message.input.ReadAllUsersInput;
import com.fortmocks.core.basis.model.user.service.message.output.ReadAllUsersOutput;
import com.fortmocks.web.basis.model.configuration.service.ReadAllConfigurationGroupsService;
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
public class ReadAllUsersServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private ReadAllUsersService service;

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
        final ReadAllUsersInput input = Mockito.mock(ReadAllUsersInput.class);
        final ServiceTask<ReadAllUsersInput> serviceTask = new ServiceTask<ReadAllUsersInput>();
        serviceTask.setInput(input);
        final ServiceResult<ReadAllUsersOutput> serviceResult = service.process(serviceTask);
        final ReadAllUsersOutput output = serviceResult.getOutput();

        final List<UserDto> returnedUsers = output.getUsers();
        Assert.assertEquals(returnedUsers.size(), users.size());
        final UserDto returnedUser = returnedUsers.get(0);
        Assert.assertEquals(user.getId(), returnedUser.getId());
        Assert.assertEquals(user.getEmail(), returnedUser.getEmail());
        Assert.assertEquals(user.getRole(), returnedUser.getRole());
        Assert.assertEquals(user.getStatus(), returnedUser.getStatus());
        Assert.assertEquals(user.getUsername(), returnedUser.getUsername());
    }


}
