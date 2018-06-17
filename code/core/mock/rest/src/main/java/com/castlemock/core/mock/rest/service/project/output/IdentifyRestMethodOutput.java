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

package com.castlemock.core.mock.rest.service.project.output;

import com.castlemock.core.basis.model.Output;
import com.castlemock.core.basis.model.validation.NotNull;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;

import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class IdentifyRestMethodOutput implements Output{

    @NotNull
    private final String restProjectId;

    @NotNull
    private final String restApplicationId;

    @NotNull
    private final String restResourceId;

    @NotNull
    private final String restMethodId;

    @NotNull
    private final RestMethod restMethod;

    @NotNull
    private final Map<String, String> pathParameters;

    public IdentifyRestMethodOutput(String restProjectId, String restApplicationId,
                                    String restResourceId, String restMethodId,
                                    RestMethod restMethod, Map<String, String> pathParameters) {
        this.restProjectId = restProjectId;
        this.restApplicationId = restApplicationId;
        this.restResourceId = restResourceId;
        this.restMethodId = restMethodId;
        this.restMethod = restMethod;
        this.pathParameters = pathParameters;
    }

    public RestMethod getRestMethod() {
        return restMethod;
    }

    public String getRestProjectId() {
        return restProjectId;
    }

    public String getRestApplicationId() {
        return restApplicationId;
    }

    public String getRestResourceId() {
        return restResourceId;
    }

    public String getRestMethodId() {
        return restMethodId;
    }

    public Map<String, String> getPathParameters() {
        return pathParameters;
    }

}
