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


package com.castlemock.web.mock.rest.converter;


import com.castlemock.core.mock.rest.model.project.dto.RestApplicationDto;

import java.io.File;
import java.util.List;

/**
 * @author Karl Dahlgren
 * @since 1.10
 */
public interface RestDefinitionConverter {

    /**
     * The convert method provides the functionality to convert the provided {@link File} into
     * a list of {@link RestApplicationDto}.
     * @param file The file which will be converted to one or more {@link RestApplicationDto}.
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A list of {@link RestApplicationDto} based on the provided file.
     */
    List<RestApplicationDto> convert(File file, boolean generateResponse);

}
