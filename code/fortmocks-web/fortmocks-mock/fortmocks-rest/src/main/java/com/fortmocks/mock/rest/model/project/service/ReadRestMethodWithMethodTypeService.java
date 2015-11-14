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

package com.fortmocks.mock.rest.model.project.service;

import com.fortmocks.core.model.Service;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.RestMethod;
import com.fortmocks.mock.rest.model.project.domain.RestResource;
import com.fortmocks.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.mock.rest.model.project.service.message.input.ReadRestMethodWithMethodTypeInput;
import com.fortmocks.mock.rest.model.project.service.message.output.ReadRestMethodWithMethodTypeOutput;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class ReadRestMethodWithMethodTypeService extends AbstractRestProjectProcessor implements Service<ReadRestMethodWithMethodTypeInput, ReadRestMethodWithMethodTypeOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the service
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<ReadRestMethodWithMethodTypeOutput> process(final Task<ReadRestMethodWithMethodTypeInput> task) {
        final ReadRestMethodWithMethodTypeInput input = task.getInput();
        final String[] restResourceUriParts = input.getRestResourceUri().split(SLASH);
        final RestResource restResource = findRestResourceType(input.getRestProjectId(), input.getRestApplicationId(), restResourceUriParts);
        RestMethodDto restMethodDto = null;
        if(restResource != null){
            for(RestMethod restMethod : restResource.getRestMethods()){
                if(input.getRestMethodType().equals(restMethod.getRestMethodType())) {
                    restMethodDto = mapper.map(restMethod, RestMethodDto.class);
                }
            }
        }

        return createResult(new ReadRestMethodWithMethodTypeOutput(restMethodDto));
    }
}
