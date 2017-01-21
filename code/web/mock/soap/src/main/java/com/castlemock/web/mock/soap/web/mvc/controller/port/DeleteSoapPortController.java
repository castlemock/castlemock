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

package com.castlemock.web.mock.soap.web.mvc.controller.port;

import com.castlemock.core.mock.soap.model.project.service.message.input.DeleteSoapPortInput;
import com.castlemock.core.mock.soap.model.project.service.message.input.DeleteSoapPortsInput;
import com.castlemock.core.mock.soap.model.project.service.message.input.ReadSoapPortInput;
import com.castlemock.core.mock.soap.model.project.service.message.output.ReadSoapPortOutput;
import com.castlemock.web.mock.soap.web.mvc.command.port.DeleteSoapPortsCommand;
import com.castlemock.web.mock.soap.web.mvc.controller.AbstractSoapViewController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The DeleteSoapPortController class provides functionality to delete a specific port
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@Scope("request")
@RequestMapping("/web/soap/project")
@ConditionalOnExpression("${server.mode.demo} == false")
public class DeleteSoapPortController extends AbstractSoapViewController {

    private static final String PAGE = "mock/soap/port/deleteSoapPort";

    /**
     * Returns the port that should be deleted and display it on a confirmation page
     * @param soapProjectId The id of the project which the port belongs to
     * @param soapPortId The id of the port that should be returned
     * @return Returns an port that matches the incoming parameters (soapProjectId and soapPortId)
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}/delete", method = RequestMethod.GET)
    public ModelAndView showConfirmationPage(@PathVariable final String soapProjectId, @PathVariable final String soapPortId) {
        final ReadSoapPortOutput output = serviceProcessor.process(new ReadSoapPortInput(soapProjectId, soapPortId));
        ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(SOAP_PROJECT_ID,soapProjectId);
        model.addObject(SOAP_PORT, output.getSoapPort());
        return model;
    }

    /**
     * Deletes the port that matches the incoming parameters (soapProjectId and soapPortId). The port should
     * displayed on the confirmation page before it is removed
     * @param soapProjectId The id of the project which the port belongs to
     * @param soapPortId The id of the port that should be deleted
     * @return Redirect the user to the project page that matches the project id
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/{soapPortId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView confirm(@PathVariable final String soapProjectId, @PathVariable final String soapPortId) {
        serviceProcessor.process(new DeleteSoapPortInput(soapProjectId, soapPortId));
        return redirect("/soap/project/" + soapProjectId);
    }

    /**
     * The method is responsible for deleting multiple port at once
     * @param soapProjectId The id of the project which the ports belongs to
     * @param deleteSoapPortsCommand The command object that contains a list of the ports that should be deleted.
     * @return Redirect the user to the project page that matches the project id
     */
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{soapProjectId}/port/delete/confirm", method = RequestMethod.POST)
    public ModelAndView confirmDeletationOfMultpleProjects(@PathVariable final String soapProjectId, @ModelAttribute final DeleteSoapPortsCommand deleteSoapPortsCommand) {
        serviceProcessor.process(new DeleteSoapPortsInput(soapProjectId, deleteSoapPortsCommand.getSoapPorts()));
        return redirect("/soap/project/" + soapProjectId);
    }

}