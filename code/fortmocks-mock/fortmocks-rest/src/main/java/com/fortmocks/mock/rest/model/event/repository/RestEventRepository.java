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

package com.fortmocks.mock.rest.model.event.repository;


import com.fortmocks.core.model.Repository;
import com.fortmocks.mock.rest.model.event.RestEvent;

/**
 * The rest event repository provides the functionality to interact with the file system.
 * The repository is responsible for loading and rest events to the file system. Each
 * rest event is stored as a separate file.
 * @author Karl Dahlgren
 * @since 1.0
 * @see RestEvent
 * @see Repository
 */
public interface RestEventRepository extends Repository<RestEvent, Long> {
}
