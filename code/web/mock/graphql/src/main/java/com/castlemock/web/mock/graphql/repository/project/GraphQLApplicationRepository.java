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

package com.castlemock.web.mock.graphql.repository.project;

import com.castlemock.web.basis.repository.Repository;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLApplication;

import java.util.List;

public interface GraphQLApplicationRepository extends Repository<GraphQLApplication, String> {

    List<GraphQLApplication> findWithProjectId(String projectId);

    void deleteWithProjectId(String projectId);

    /**
     * Retrieve the {@link com.castlemock.core.mock.graphql.model.project.domain.GraphQLProject} id
     * for the {@link GraphQLApplication} with the provided id.
     * @param applicationId The id of the {@link GraphQLApplication}.
     * @return The id of the project.
     * @since 1.20
     */
    String getProjectId(String applicationId);

}
