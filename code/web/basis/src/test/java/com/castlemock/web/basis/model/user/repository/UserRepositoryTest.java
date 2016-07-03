package com.castlemock.web.basis.model.user.repository;


import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.core.basis.model.user.dto.UserDto;
import com.castlemock.web.basis.model.user.dto.UserDtoGenerator;
import com.castlemock.web.basis.support.FileRepositorySupport;
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
public class UserRepositoryTest {

    @Mock
    private FileRepositorySupport fileRepositorySupport;
    @Spy
    private DozerBeanMapper mapper;
    @InjectMocks
    private UserRepositoryImpl repository;

    private static final String DIRECTORY = "/directory";
    private static final String EXTENSION = ".extension";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(repository, "userFileDirectory", DIRECTORY);
        ReflectionTestUtils.setField(repository, "userFileExtension", EXTENSION);
    }

    @Test
    public void testInitialize(){
        List<User> users = new ArrayList<User>();
        User user = UserDtoGenerator.generateUser();
        users.add(user);
        Mockito.when(fileRepositorySupport.load(User.class, DIRECTORY, EXTENSION)).thenReturn(users);
        repository.initialize();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).load(User.class, DIRECTORY, EXTENSION);
    }

    @Test
    public void testFindOne(){
        final UserDto user = save();
        final UserDto returnedUser = repository.findOne(user.getId());
        Assert.assertEquals(user.getId(), returnedUser.getId());
        Assert.assertEquals(user.getEmail(), returnedUser.getEmail());
        Assert.assertEquals(user.getPassword(), returnedUser.getPassword());
        Assert.assertEquals(user.getRole(), returnedUser.getRole());
        Assert.assertEquals(user.getStatus(), returnedUser.getStatus());
        Assert.assertEquals(user.getUsername(), returnedUser.getUsername());
    }

    @Test
    public void testFindAll(){
        final UserDto user = save();
        final List<UserDto> users = repository.findAll();
        Assert.assertEquals(users.size(), 1);
        Assert.assertEquals(users.get(0), user);
    }

    @Test
    public void testSave(){
        final UserDto user = save();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(Mockito.any(User.class), Mockito.anyString());
    }

    @Test
    public void testDelete(){
        final UserDto user = save();
        repository.delete(user.getId());
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).delete(DIRECTORY + File.separator + user.getId() + EXTENSION);
    }

    @Test
    public void testCount(){
        final UserDto user = save();
        final Integer count = repository.count();
        Assert.assertEquals(new Integer(1), count);
    }

    private UserDto save(){
        final UserDto user = UserDtoGenerator.generateUserDto();
        repository.save(user);
        return user;
    }

}
