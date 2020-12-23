/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.web.mock.rest.web.rest.controller;

import com.castlemock.core.mock.rest.model.event.domain.RestEvent;
import com.castlemock.core.mock.rest.service.event.input.ReadRestEventInput;
import com.castlemock.core.mock.rest.service.event.output.ReadRestEventOutput;
import com.castlemock.web.basis.web.rest.controller.AbstractRestController;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/rest/rest")
@Api(value="REST - Event", tags = {"REST - Event"})
public class RestEventRestController extends AbstractRestController {

    @ApiOperation(value = "Get REST event", response = RestEvent.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved REST event")})
    @RequestMapping(method = RequestMethod.GET, value = "/event/{eventId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<RestEvent> getRestEvent(
            @ApiParam(name = "eventId", value = "The id of the event")
            @PathVariable(value = "eventId") final String eventId) {
        final ReadRestEventOutput output = super.serviceProcessor.process(ReadRestEventInput.builder()
                .restEventId(eventId)
                .build());

        return ResponseEntity.ok(output.getRestEvent());
    }

}
