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

package com.castlemock.web.mock.soap.web.view.controller.resource;

import com.castlemock.core.mock.soap.model.project.domain.SoapResource;
import com.castlemock.core.mock.soap.service.project.input.LoadSoapResourceInput;
import com.castlemock.core.mock.soap.service.project.input.ReadSoapResourceInput;
import com.castlemock.core.mock.soap.service.project.output.LoadSoapResourceOutput;
import com.castlemock.core.mock.soap.service.project.output.ReadSoapResourceOutput;
import com.castlemock.web.mock.soap.web.view.controller.AbstractSoapViewController;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The SoapResourceController provides functionality to retrieve a specific resource for a project
 * @author Karl Dahlgren
 * @since 1.16
 */
@Controller
@Scope("request")
@RequestMapping("/web/soap/project")
public class SoapResourceController extends AbstractSoapViewController {

    private static final String PAGE = "mock/soap/resource/soapResource";



    /**
     * Returns a resource that matches the incoming parameters (soapProjectId and soapResourceId)
     * @param soapProjectId The id of the project which the port belongs to
     * @param soapResourceId The id of the resource that should be returned
     * @return Returns a resource that matches the incoming parameters (projectId and soapResourceId)
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/resource/{soapResourceId}", method = RequestMethod.GET)
    public ModelAndView getSoapResource(@PathVariable final String soapProjectId, @PathVariable final String soapResourceId) {
        final ReadSoapResourceOutput readSoapResourceOutput = serviceProcessor.process(new ReadSoapResourceInput(soapProjectId, soapResourceId));
        final LoadSoapResourceOutput loadSoapResourceOutput = serviceProcessor.process(new LoadSoapResourceInput(soapProjectId, soapResourceId));
        final SoapResource soapResource = readSoapResourceOutput.getSoapResource();
        final String resource = loadSoapResourceOutput.getResource();

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(SOAP_PROJECT_ID,soapProjectId);
        model.addObject(SOAP_RESOURCE, soapResource);
        model.addObject(SOAP_RESOURCE_DATA, resource);
        return model;
    }
}