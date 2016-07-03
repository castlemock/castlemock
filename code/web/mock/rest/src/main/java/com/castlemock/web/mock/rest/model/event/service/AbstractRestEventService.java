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

package com.castlemock.web.mock.rest.model.event.service;

import com.castlemock.core.mock.rest.model.event.domain.RestEvent;
import com.castlemock.core.mock.rest.model.event.dto.RestEventDto;
import com.castlemock.web.basis.model.event.service.AbstractEventService;
import com.castlemock.web.mock.rest.model.event.repository.RestEventRepository;
import org.springframework.stereotype.Service;

/**
 * The REST event service is responsible for all the functionality related to the REST events.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Service
public class AbstractRestEventService extends AbstractEventService<RestEvent, RestEventDto, RestEventRepository> {


}

