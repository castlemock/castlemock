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

package com.castlemock.web.mock.soap.web.resource.controller;

import com.castlemock.core.mock.soap.service.project.input.LoadSoapResourceInput;
import com.castlemock.core.mock.soap.service.project.output.LoadSoapResourceOutput;
import com.castlemock.web.basis.web.rest.controller.AbstractRestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/resource/soap/project")
public class ResourceController extends AbstractRestController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/{projectId}/resource/{resourceId}")
    public String getResource(@PathVariable final String projectId,
                              @PathVariable final String resourceId) {
        final LoadSoapResourceOutput output =
                this.serviceProcessor.process(new LoadSoapResourceInput(projectId, resourceId));
        return output.getResource();
    }

}
