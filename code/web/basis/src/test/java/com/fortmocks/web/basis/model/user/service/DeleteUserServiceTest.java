package com.fortmocks.web.basis.model.user.service;

import com.fortmocks.core.basis.model.Repository;
import com.fortmocks.core.basis.model.ServiceResult;
import com.fortmocks.core.basis.model.ServiceTask;
import com.fortmocks.core.basis.model.user.domain.Role;
import com.fortmocks.core.basis.model.user.domain.User;
import com.fortmocks.core.basis.model.user.service.message.input.DeleteUserInput;
import com.fortmocks.core.basis.model.user.service.message.output.DeleteUserOutput;
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
public class DeleteUserServiceTest {

    @Spy
    private DozerBeanMapper mapper;

    @Mock
    private Repository repository;

    @InjectMocks
    private DeleteUserService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcess(){
        User user = new User();
        user.setRole(Role.MODIFIER);

        Mockito.when(repository.findOne(Mockito.anyLong())).thenReturn(user);

        final DeleteUserInput input = new DeleteUserInput(1L);
        final ServiceTask<DeleteUserInput> serviceTask = new ServiceTask<DeleteUserInput>();
        serviceTask.setInput(input);
        final ServiceResult<DeleteUserOutput> serviceResult = service.process(serviceTask);
        serviceResult.getOutput();
        Mockito.verify(repository, Mockito.times(1)).delete(Mockito.anyLong());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessDeleteLastAdmin(){
        User user = new User();
        user.setRole(Role.ADMIN);

        List<User> users = new ArrayList<>();
        users.add(user);

        Mockito.when(repository.findOne(Mockito.anyLong())).thenReturn(user);
        Mockito.when(repository.findAll()).thenReturn(users);

        final DeleteUserInput input = new DeleteUserInput(1L);
        final ServiceTask<DeleteUserInput> serviceTask = new ServiceTask<DeleteUserInput>();
        serviceTask.setInput(input);
        final ServiceResult<DeleteUserOutput> serviceResult = service.process(serviceTask);
        serviceResult.getOutput();
        Mockito.verify(repository, Mockito.times(1)).delete(Mockito.anyLong());
    }


}
