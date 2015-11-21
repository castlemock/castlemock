package com.fortmocks.web.basis.model.user.service;

import com.fortmocks.core.basis.model.Repository;
import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.basis.model.user.domain.Role;
import com.fortmocks.core.basis.model.user.domain.Status;
import com.fortmocks.core.basis.model.user.domain.User;
import com.fortmocks.core.basis.model.user.dto.UserDto;
import com.fortmocks.core.basis.model.user.service.message.input.CreateUserInput;
import com.fortmocks.core.basis.model.user.service.message.input.ReadUserInput;
import com.fortmocks.core.basis.model.user.service.message.output.CreateUserOutput;
import com.fortmocks.core.basis.model.user.service.message.output.ReadUserOutput;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.mockito.verification.VerificationMode;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class CreateUserServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private CreateUserService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        UserDto user = new UserDto();
        user.setUsername("Username");
        user.setPassword("Password");
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.ADMIN);
        user.setEmail("email@email.com");

        User createdUser = new User();
        createdUser.setId(1L);
        createdUser.setPassword("Password");
        createdUser.setUsername("Username");
        createdUser.setStatus(Status.ACTIVE);
        createdUser.setRole(Role.ADMIN);
        createdUser.setEmail("email@email.com");

        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(createdUser);
        final CreateUserInput input = new CreateUserInput(user);
        final ServiceTask<CreateUserInput> serviceTask = new ServiceTask<CreateUserInput>();
        serviceTask.setInput(input);
        final ServiceResult<CreateUserOutput> serviceResult = service.process(serviceTask);
        final CreateUserOutput output = serviceResult.getOutput();

        final UserDto returnedUser = output.getSavedUser();
        Assert.assertNotNull(returnedUser);
        Assert.assertEquals(returnedUser.getId(), createdUser.getId());
        Assert.assertEquals(user.getPassword(), returnedUser.getPassword());
        Assert.assertEquals(user.getEmail(), returnedUser.getEmail());
        Assert.assertEquals(user.getRole(), returnedUser.getRole());
        Assert.assertEquals(user.getStatus(), returnedUser.getStatus());
        Assert.assertEquals(user.getUsername(), returnedUser.getUsername());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(User.class));
    }


}
