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

package com.castlemock.web.mock.rest.config;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.service.core.manager.UrlManager;
import com.castlemock.service.mock.rest.project.input.ImportRestProjectInput;
import com.google.common.base.Strings;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RestDemoModeInitializer {

    @Value("${server.mode.demo.project.rest.url:}")
    protected String projectUrl;
    @Autowired
    protected UrlManager urlManager;
    @Autowired
    private ServiceProcessor serviceProcessor;

    private static final Logger LOGGER = LoggerFactory.getLogger(RestDemoModeInitializer.class);

    @PostConstruct
    private void setup(){
        if(!Strings.isNullOrEmpty(projectUrl)){
            try {
                final Optional<String> project = this.urlManager.readFromUrl(projectUrl);

                project.ifPresent(raw -> serviceProcessor.process(ImportRestProjectInput.builder()
                        .projectRaw(raw)
                        .build()));
            } catch (Exception e){
                LOGGER.warn("Unable to load the demo project", e);
            }

        }
    }

}
