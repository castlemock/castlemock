/*
 * Copyright 2016 Karl Dahlgren
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

package com.castlemock.web.core.service.system;

import com.castlemock.core.basis.Environment;
import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.basis.model.system.domain.SystemInformation;
import com.castlemock.core.basis.service.system.input.GetSystemInformationInput;
import com.castlemock.core.basis.service.system.output.GetSystemInformationOutput;
import com.castlemock.repository.Profiles;
import com.castlemock.web.core.service.configuration.AbstractConfigurationGroupService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;

/**
 * The {@link GetSystemInformationService} is used to retrieve information about the system which
 * the application is deployed on.
 * @author Karl Dahlgren
 * @author Mohammad Hewedy
 * @since 1.7
 */
@org.springframework.stereotype.Service
public class GetSystemInformationService extends AbstractConfigurationGroupService implements Service<GetSystemInformationInput, GetSystemInformationOutput> {

    @Value("${base.file.directory}")
    private String castleMockHomeDirectory;

    @Autowired
    private Environment environment;

    @Autowired
    private org.springframework.core.env.Environment springEnvironment;

    @Autowired
    private ObjectProvider<MongoProperties> mongoPropertiesProvider;

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     *
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<GetSystemInformationOutput> process(final ServiceTask<GetSystemInformationInput> serviceTask) {
        SystemInformation.Builder builder = SystemInformation.builder()
                .javaVersion(System.getProperty("java.version"))
                .javaVendor(System.getProperty("java.vendor"))
                .operatingSystemName(System.getProperty("os.name"))
                .tomcatBuilt(environment.getServerBuilt())
                .tomcatInfo(environment.getServerInfo())
                .tomcatVersion(environment.getServerNumber())
                .availableProcessors(Runtime.getRuntime().availableProcessors())
                .totalMemory(Runtime.getRuntime().totalMemory() / 1000000)
                .maxMemory(Runtime.getRuntime().maxMemory() / 1000000)
                .freeMemory(Runtime.getRuntime().freeMemory() / 1000000)
                .castleMockHomeDirectory(this.castleMockHomeDirectory);


        final org.springframework.core.env.Profiles mongoProfiles =
                org.springframework.core.env.Profiles.of(Profiles.MONGODB);
        final org.springframework.core.env.Profiles fileProfiles =
                org.springframework.core.env.Profiles.of(Profiles.FILE);

        if (springEnvironment.acceptsProfiles(mongoProfiles)) {
            com.castlemock.core.basis.model.system.domain.MongoProperties mongoProperties = mongoPropertiesProvider.stream().map(props ->
                    new com.castlemock.core.basis.model.system.domain.MongoProperties(
                            props.getHost(), props.getPort(), props.determineUri(),
                            props.getMongoClientDatabase(), isMongoUsesUri(props)))
                    .findFirst()
                    .orElse(null);
            builder = builder.mongoProperties(mongoProperties);
        }
        builder = builder.showCastleMockHomeDirectory(springEnvironment.acceptsProfiles(mongoProfiles));
        builder = builder.showMongoProperties(springEnvironment.acceptsProfiles(fileProfiles));
        return createServiceResult(GetSystemInformationOutput.builder()
                .systemInformation(builder.build())
                .build());
    }

    // see org.springframework.boot.autoconfigure.mongo.MongoClientFactory
    private boolean isMongoUsesUri(MongoProperties mongoProperties) {
        if (mongoProperties.getUri() != null) {
            return true;
        }
        return !hasMongoCustomAddress(mongoProperties) && !hasMongoCustomCredentials(mongoProperties);
    }

    private boolean hasMongoCustomAddress(MongoProperties mongoProperties) {
        return mongoProperties.getHost() != null || mongoProperties.getPort() != null;
    }

    private boolean hasMongoCustomCredentials(MongoProperties mongoProperties) {
        return mongoProperties.getUsername() != null
                && mongoProperties.getPassword() != null;
    }
}
