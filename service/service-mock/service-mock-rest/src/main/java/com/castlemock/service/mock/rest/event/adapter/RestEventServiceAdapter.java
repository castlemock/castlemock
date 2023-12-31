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

package com.castlemock.service.mock.rest.event.adapter;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.service.event.EventServiceAdapter;
import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.service.mock.rest.event.input.ClearAllRestEventInput;
import com.castlemock.service.mock.rest.event.input.ReadAllRestEventInput;
import com.castlemock.service.mock.rest.event.output.ReadAllRestEventOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The REST event service adapter provides the functionality to translate incoming
 * requests and transform them into correct service input messages
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class RestEventServiceAdapter implements EventServiceAdapter<RestEvent> {

    @Autowired
    private ServiceProcessor serviceProcessor;

    /**
     * The method is responsible for retrieving all instances from all the various service types.
     * @return A list containing all the instance independent from type
     */
    @Override
    public List<RestEvent> readAll() {
        final ReadAllRestEventOutput output = serviceProcessor.process(ReadAllRestEventInput.builder().build());
        return new ArrayList<>(output.getRestEvents());
    }

    @Override
    public String getType() {
        return "rest";
    }

    /**
     * The method will clear and remove all previous events.
     * @since 1.7
     */
    @Override
    public void clearAll() {
        serviceProcessor.process(ClearAllRestEventInput.builder().build());
    }

}
