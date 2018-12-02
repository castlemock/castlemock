/*
 * Copyright 2018 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.repository.core.file.user;


import com.castlemock.core.basis.model.user.domain.User;
import com.castlemock.core.basis.model.user.domain.UserDtoGenerator;
import com.castlemock.repository.core.file.FileRepositorySupport;
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
    private UserFileRepository repository;

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
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).load(UserFileRepository.UserFile.class, DIRECTORY, EXTENSION);
    }

    @Test
    public void testFindOne(){
        final User user = save();
        final User returnedUser = repository.findOne(user.getId());
        Assert.assertEquals(user.getId(), returnedUser.getId());
        Assert.assertEquals(user.getEmail(), returnedUser.getEmail());
        Assert.assertEquals(user.getPassword(), returnedUser.getPassword());
        Assert.assertEquals(user.getRole(), returnedUser.getRole());
        Assert.assertEquals(user.getStatus(), returnedUser.getStatus());
        Assert.assertEquals(user.getUsername(), returnedUser.getUsername());
    }

    @Test
    public void testFindAll(){
        final User user = save();
        final List<User> users = repository.findAll();
        Assert.assertEquals(users.size(), 1);
        Assert.assertEquals(users.get(0), user);
    }

    @Test
    public void testSave(){
        final User user = save();
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).save(Mockito.any(User.class), Mockito.anyString());
    }

    @Test
    public void testDelete(){
        final User user = save();
        repository.delete(user.getId());
        Mockito.verify(fileRepositorySupport, Mockito.times(1)).delete(DIRECTORY + File.separator + user.getId() + EXTENSION);
    }

    @Test
    public void testCount(){
        final User user = save();
        final Integer count = repository.count();
        Assert.assertEquals(new Integer(1), count);
    }

    private User save(){
        final User user = UserDtoGenerator.generateUserDto();
        repository.save(user);
        return user;
    }

}
