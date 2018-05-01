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

package com.castlemock.web.mock.graphql.model.event.service;


import com.castlemock.core.mock.graphql.model.event.domain.GraphQLEvent;
import com.castlemock.web.basis.model.event.service.AbstractEventService;
import com.castlemock.web.mock.graphql.model.event.repository.GraphQLEventRepository;

/**
 * The GraphQL event service is responsible for all the functionality related to the GraphQL events.
 * @author Karl Dahlgren
 * @since 1.9
 */
public class AbstractGraphQLEventService extends AbstractEventService<GraphQLEvent, GraphQLEventRepository> {

}

