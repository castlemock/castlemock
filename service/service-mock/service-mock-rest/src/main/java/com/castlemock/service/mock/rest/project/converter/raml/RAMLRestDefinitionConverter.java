/*
 * Copyright 2017 Karl Dahlgren
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

package com.castlemock.service.mock.rest.project.converter.raml;

import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.service.mock.rest.project.converter.AbstractRestDefinitionConverter;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link RAMLRestDefinitionConverter} provides RAML related functionality.
 * @since 1.10
 * @author Karl Dahlgren
 */
public class RAMLRestDefinitionConverter extends AbstractRestDefinitionConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RAMLRestDefinitionConverter.class);

    /**
     * The convert method provides the functionality to convert the provided {@link File} into
     * a list of {@link RestApplication}.
     * @param file The file which will be converted to one or more {@link RestApplication}.
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A list of {@link RestApplication} based on the provided file.
     */
    @Override
    public List<RestApplication> convert(final File file, final String projectId, final boolean generateResponse){
        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(file);
        return convert(ramlModelResult, generateResponse);
    }

    /**
     * The convert method provides the functionality to convert the provided {@link File} into
     * a list of {@link RestApplication}.
     * @param location The location of the definition file
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A list of {@link RestApplication} based on the provided file.
     */
    @Override
    public List<RestApplication> convert(final String location, final String projectId, final boolean generateResponse){
        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(location);
        return convert(ramlModelResult, generateResponse);
    }

    /**
     * The convert method provides the functionality to convert the provided {@link File} into
     * a list of {@link RestApplication}.
     * @param ramlModelResult The RAML model result
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A list of {@link RestApplication} based on the provided file.
     * @throws IllegalStateException In case the {@link RamlModelResult} is not parsable.
     */
    private List<RestApplication> convert(final RamlModelResult ramlModelResult, final boolean generateResponse){
        if(!ramlModelResult.getValidationResults().isEmpty()){
            throw new IllegalStateException("Unable to parse the RAML file");
        }

        try {

            String title = null;
            List<RestResource> restResources = new ArrayList<>();
            if (ramlModelResult.getApiV08() != null) {
                org.raml.v2.api.model.v08.api.Api api = ramlModelResult.getApiV08();
                title = api.title();
                new RAML08Parser().getResources(api.resources(), restResources, "", generateResponse);
            } else if (ramlModelResult.getApiV10() != null) {
                org.raml.v2.api.model.v10.api.Api api = ramlModelResult.getApiV10();
                title = api.title().value();
                new RAML10Parser().getResources(api.resources(), restResources, "", generateResponse);
            }

            final RestApplication restApplication = RestApplication.builder()
                    .name(title)
                    .resources(restResources)
                    .build();

            return List.of(restApplication);
        } catch (final Exception exception) {
            LOGGER.error("Unable to convert RAML into a REST application: " + exception.getMessage(), exception);
            throw exception;
        }
    }

}
