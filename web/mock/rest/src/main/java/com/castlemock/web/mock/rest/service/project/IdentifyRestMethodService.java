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

package com.castlemock.web.mock.rest.service.project;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.web.basis.utility.UrlUtility;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.core.mock.rest.service.project.input.IdentifyRestMethodInput;
import com.castlemock.core.mock.rest.service.project.output.IdentifyRestMethodOutput;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@org.springframework.stereotype.Service
public class IdentifyRestMethodService extends AbstractRestProjectService implements Service<IdentifyRestMethodInput, IdentifyRestMethodOutput> {

    protected static final String SLASH = "/";

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<IdentifyRestMethodOutput> process(final ServiceTask<IdentifyRestMethodInput> serviceTask) {
        final IdentifyRestMethodInput input = serviceTask.getInput();
        final Map<String, RestResource> resources =
                this.resourceRepository.findWithApplicationId(input.getRestApplicationId())
                        .stream()
                        .filter(resource -> UrlUtility.isPatternMatch(resource.getUri(), input.getRestResourceUri()))
                        .collect(toMap(RestResource::getId, Function.identity()));

        final RestMethod method = resources
                .values()
                .stream()
                .map(RestResource::getId)
                .map(this.methodRepository::findWithResourceId)
                .flatMap(Collection::stream)
                .filter(m -> input.getHttpMethod().equals(m.getHttpMethod()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unable to identify REST method: " +
                        input.getRestResourceUri() + " (" + input.getHttpMethod() + ")"));

        final RestResource resource = Optional.ofNullable(resources.get(method.getResourceId()))
                .orElseThrow(() -> new IllegalArgumentException("Unable to get REST resource: " + method.getResourceId()));

        final Map<String, String> pathParameters = new HashMap<>();
        pathParameters.putAll(UrlUtility.getPathParameters(resource.getUri(), input.getRestResourceUri()));
        pathParameters.putAll(UrlUtility.getQueryStringParameters(resource.getUri(), input.getHttpParameters()));

        final List<RestMockResponse> mockResponses = this.mockResponseRepository.findWithMethodId(method.getId());
        method.setMockResponses(mockResponses);

        return createServiceResult(IdentifyRestMethodOutput.builder()
                        .restProjectId(input.getRestProjectId())
                        .restApplicationId(input.getRestApplicationId())
                        .restResourceId(resource.getId())
                        .restMethodId(method.getId())
                        .restMethod(method)
                        .pathParameters(pathParameters)
                        .build());
    }

}
