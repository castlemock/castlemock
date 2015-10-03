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

package com.fortmocks.war.mock.rest.manager;

import com.fortmocks.core.mock.rest.model.project.dto.RestResourceDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * WADLComponent provides functionality to parse a WADL file
 * @author Karl Dahlgren
 * @since 1.0
 */
@Component
public class WADLComponent {

    /**
     * The method provides the functionality to download a WADL file from a specific URL, download it and generate
     * new REST applications.
     * @param wadlUrl The URL from where the WADL file can be downloaded
     * @param generateResponse Boolean value for if a default response should be generated for each new method. The
     *                         value true will cause the method to generate a default response for each new method. No
     *                         default responses will be created if the variable is set to false.
     * @return  The extracted ports from the downloaded WADL file.
     */
    public List<RestResourceDto> createRestResources(final String wadlUrl, final boolean generateResponse) {
        return null;
    }

    /**
     * Parse an WADL file and creates new ports based on the WADL file
     * @param files The list of WADL files
     * @param generateResponse Boolean value for if a default response should be generated for each new method. The
     *                         value true will cause the method to generate a default response for each new method. No
     *                         default responses will be created if the variable is set to false.
     * @return The extracted ports from the WADL file.
     */
    public List<RestResourceDto> createRestResources(final List<MultipartFile> files, final boolean generateResponse){
        return null;
    }

}

