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

package com.fortmocks.mock.rest.model.project.processor;

import com.fortmocks.core.model.Processor;
import com.fortmocks.core.model.Result;
import com.fortmocks.core.model.Task;
import com.fortmocks.mock.rest.model.project.domain.*;
import com.fortmocks.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.mock.rest.model.project.processor.message.input.CreateRestMethodInput;
import com.fortmocks.mock.rest.model.project.processor.message.output.CreateRestMethodOutput;
import org.springframework.stereotype.Service;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class CreateRestMethodProcessor extends AbstractRestProjectProcessor implements Processor<CreateRestMethodInput, CreateRestMethodOutput> {

    /**
     * The process message is responsible for processing an incoming task and generate
     * a response based on the incoming task input
     * @param task The task that will be processed by the processor
     * @return A result based on the processed incoming task
     * @see Task
     * @see Result
     */
    @Override
    public Result<CreateRestMethodOutput> process(final Task<CreateRestMethodInput> task) {
        final CreateRestMethodInput input = task.getInput();
        final RestMethodDto restMethod = input.getRestMethod();
        if(restMethod.getRestMethodStatus() == null){
            restMethod.setRestMethodStatus(RestMethodStatus.MOCKED);
        }
        if(restMethod.getRestResponseStrategy() == null){
            restMethod.setRestResponseStrategy(RestResponseStrategy.RANDOM);
        }

        final RestResource restResource = findRestResourceType(input.getRestProjectId(), input.getRestApplicationId(), input.getRestResourceId());
        final Long restMethodId = getNextRestMethodId();
        restMethod.setId(restMethodId);
        final RestMethod createdRestMethod = mapper.map(restMethod, RestMethod.class);
        restResource.getRestMethods().add(createdRestMethod);
        save(input.getRestProjectId());
        return createResult(new CreateRestMethodOutput(mapper.map(createdRestMethod, RestMethodDto.class)));
    }
}
