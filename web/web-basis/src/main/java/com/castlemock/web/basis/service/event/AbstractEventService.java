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

package com.castlemock.web.basis.service.event;

import com.castlemock.core.basis.model.event.domain.Event;
import com.castlemock.repository.Repository;
import com.castlemock.web.basis.service.AbstractService;

/**
 * The event service provides the functionality that affects all events
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractEventService<D extends Event, R extends Repository<D, String>> extends AbstractService<D,String, R> {

}
