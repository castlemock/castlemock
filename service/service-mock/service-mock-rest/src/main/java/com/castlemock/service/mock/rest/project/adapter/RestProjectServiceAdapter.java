/*
 * Copyright 2015 Karl Dahlgren
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

package com.castlemock.service.mock.rest.project.adapter;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.service.project.ProjectServiceAdapter;
import com.castlemock.model.mock.rest.domain.RestProject;
import com.castlemock.service.mock.rest.project.input.ReadAllRestProjectsInput;
import com.castlemock.service.mock.rest.project.output.ReadAllRestProjectsOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The REST project service adapter is responsible for providing the basic functionality for all the
 * project services.
 * @author Karl Dahlgren
 * @since 1.0
 * @see RestProject
 */
@Service
public class RestProjectServiceAdapter implements ProjectServiceAdapter<RestProject> {

    @Autowired
    private ServiceProcessor serviceProcessor;


    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    @Override
    public List<RestProject> readAll() {
        final ReadAllRestProjectsOutput output = serviceProcessor.process(ReadAllRestProjectsInput.builder().build());
        return output.getRestProjects();
    }

    @Override
    public String getType() {
        return "rest";
    }

}
