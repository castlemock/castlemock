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

package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.Service;
import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapResource;
import com.castlemock.model.mock.soap.domain.SoapResourceType;
import com.castlemock.service.mock.soap.project.input.ImportSoapResourceInput;
import com.castlemock.service.mock.soap.project.output.ImportSoapResourceOutput;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
@org.springframework.stereotype.Service
public class ImportSoapResourceService extends AbstractSoapProjectService implements Service<ImportSoapResourceInput, ImportSoapResourceOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<ImportSoapResourceOutput> process(final ServiceTask<ImportSoapResourceInput> serviceTask) {
        final ImportSoapResourceInput input = serviceTask.getInput();
        final Optional<String> projectId = input.getProjectId();
        final SoapResource soapResource = input.getResource();
        final String raw = input.getRaw();

        if(soapResource.getName() == null){
            final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
            final Date now = new Date();
            final String soapResourceName = soapResource.getType().name() + "-" + formatter.format(now);
            soapResource.setName(soapResourceName);
        }

        SoapResource result = null;
        if(projectId.isPresent()){
            soapResource.setProjectId(projectId.get());
            result = this.resourceRepository.saveSoapResource(soapResource, raw);
        } else {
            result = this.resourceRepository.saveSoapResource(soapResource, raw);
        }


        return createServiceResult(ImportSoapResourceOutput.builder()
                .resource(result)
                .build());
    }
}
