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


package com.castlemock.core.basis.model.project.domain;

import com.castlemock.core.basis.model.TypeIdentifier;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.*;

public class ProjectTest {

    @Test
    public void getId() {
        final Project project = new Project();
        project.setId("Id");
        Assert.assertEquals("Id", project.getId());
    }

    @Test
    public void setId() {
        final Project project = new Project();
        project.setId("Id");
        Assert.assertEquals("Id", project.getId());
    }

    @Test
    public void getName() {
        final Project project = new Project();
        project.setName("Name");
        Assert.assertEquals("Name", project.getName());
    }

    @Test
    public void setName() {
        final Project project = new Project();
        project.setName("Name");
        Assert.assertEquals("Name", project.getName());
    }

    @Test
    public void getUpdated() {
        final Date date = new Date();
        final Project project = new Project();
        project.setUpdated(date);
        Assert.assertEquals(date, project.getUpdated());
    }

    @Test
    public void setUpdated() {
        final Date date = new Date();
        final Project project = new Project();
        project.setUpdated(date);
        Assert.assertEquals(date, project.getUpdated());
    }

    @Test
    public void getCreated() {
        final Date date = new Date();
        final Project project = new Project();
        project.setCreated(date);
        Assert.assertEquals(date, project.getCreated());
    }

    @Test
    public void setCreated() {
        final Date date = new Date();
        final Project project = new Project();
        project.setCreated(date);
        Assert.assertEquals(date, project.getCreated());
    }

    @Test
    public void getDescription() {
        final Project project = new Project();
        project.setDescription("Description");
        Assert.assertEquals("Description", project.getDescription());
    }

    @Test
    public void setDescription() {
        final Project project = new Project();
        project.setDescription("Description");
        Assert.assertEquals("Description", project.getDescription());
    }

    @Test
    public void getTypeIdentifier() {
        final TypeIdentifier typeIdentifier = Mockito.mock(TypeIdentifier.class);
        final Project project = new Project();
        project.setTypeIdentifier(typeIdentifier);
        Assert.assertEquals(typeIdentifier, project.getTypeIdentifier());
    }

    @Test
    public void setTypeIdentifier() {
        final TypeIdentifier typeIdentifier = Mockito.mock(TypeIdentifier.class);
        final Project project = new Project();
        project.setTypeIdentifier(typeIdentifier);
        Assert.assertEquals(typeIdentifier, project.getTypeIdentifier());
    }
}