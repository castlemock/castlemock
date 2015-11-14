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

package com.fortmocks.war.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.context.annotation.Configuration;

/**
 * The class MimeConfig is an EmbeddedServletContainerCustomizer and is responsible for customizing the mime mapping.
 * @author Karl Dahlgren
 * @since 1.0
 */
@Configuration
public class MimeConfig implements EmbeddedServletContainerCustomizer {

    /**
     * The method customize customizes the mime mapping and add it to the provided container
     * @param container The configurable embedded servlet container
     */
    @Override
    public void customize(final ConfigurableEmbeddedServletContainer container) {
        final MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("xsd", "text/xml; charset=utf-8");
        mappings.add("ico", "image/x-icon");
        container.setMimeMappings(mappings);
    }

}
