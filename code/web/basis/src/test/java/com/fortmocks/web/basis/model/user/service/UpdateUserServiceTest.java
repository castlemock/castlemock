package com.fortmocks.web.basis.model.user.service;

import com.fortmocks.core.basis.model.Repository;
import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.basis.model.user.domain.Role;
import com.fortmocks.core.basis.model.user.domain.Status;
import com.fortmocks.core.basis.model.user.domain.User;
import com.fortmocks.core.basis.model.user.dto.UserDto;
import com.fortmocks.core.basis.model.user.service.message.input.CreateUserInput;
import com.fortmocks.core.basis.model.user.service.message.input.UpdateUserInput;
import com.fortmocks.core.basis.model.user.service.message.output.CreateUserOutput;
import com.fortmocks.core.basis.model.user.service.message.output.UpdateUserOutput;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class UpdateUserServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private UpdateUserService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        User user = new User();
        user.setId(1L);
        user.setPassword("Password");
        user.setUsername("Username");
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.ADMIN);
        user.setEmail("email@email.com");

        UserDto updatedUser = new UserDto();
        updatedUser.setId(1L);
        updatedUser.setPassword("UpdatedPassword");
        updatedUser.setUsername("UpdatedUsername");
        updatedUser.setStatus(Status.ACTIVE);
        updatedUser.setRole(Role.ADMIN);
        updatedUser.setEmail("email@email.com");

        Mockito.when(repository.findOne(Mockito.anyLong())).thenReturn(user);
        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
        final UpdateUserInput input = new UpdateUserInput(1L, updatedUser);
        final ServiceTask<UpdateUserInput> serviceTask = new ServiceTask<UpdateUserInput>();
        serviceTask.setInput(input);
        final ServiceResult<UpdateUserOutput> serviceResult = service.process(serviceTask);
        final UpdateUserOutput output = serviceResult.getOutput();

        final UserDto returnedUser = output.getUpdatedUser();
        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(updatedUser.getId(), returnedUser.getId());
        Assert.assertEquals(updatedUser.getPassword(), returnedUser.getPassword());
        Assert.assertEquals(updatedUser.getEmail(), returnedUser.getEmail());
        Assert.assertEquals(updatedUser.getRole(), returnedUser.getRole());
        Assert.assertEquals(updatedUser.getStatus(), returnedUser.getStatus());
        Assert.assertEquals(updatedUser.getUsername(), returnedUser.getUsername());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(User.class));
    }


}
