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

package com.castlemock.web.basis.web.rest.controller;

import com.castlemock.core.basis.model.event.domain.Event;
import com.castlemock.core.basis.service.event.EventServiceFacade;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * The {@link EventCoreRestController} is the REST controller that provides
 * the interface for the core operations.
 * @author Karl Dahlgren
 * @since 1.19
 */
@Controller
@RequestMapping("/api/rest/core")
@Api(value="Core", description="REST Operations for Castle Mock Core", tags = {"Core"})
@ConditionalOnExpression("${server.mode.demo} == false")
public class EventCoreRestController extends AbstractRestController {

    @Autowired
    private EventServiceFacade eventServiceFacade;

    /**
     * The method retrieves all events
     * @return The retrieved event.
     */
    @ApiOperation(value = "Get events",response = Event.class,
            notes = "Get events. Required authorization: Reader, Modifier or Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved event")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/event")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<List<Event>> getEvents() {
        return ResponseEntity.ok(eventServiceFacade.findAll());
    }

    /**
     * The method retrieves a event with a particular ID.
     * @param type The type of the event.
     * @param eventId The event id.
     * @return The retrieved event.
     */
    @ApiOperation(value = "Get event",response = Event.class,
            notes = "Get event. Required authorization: Reader, Modifier or Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved event")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/event/{type}/{eventId}")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Event> getEvent(
            @ApiParam(name = "type", value = "The type of the event", allowableValues = "rest,soap")
            @PathVariable("type") final String type,
            @ApiParam(name = "eventId", value = "The id of the event")
            @PathVariable("eventId") final String eventId) {
        return ResponseEntity.ok(eventServiceFacade.findOne(type, eventId));
    }

    @ApiOperation(value = "Delete all event",response = Event.class,
            notes = "Delete all event. Required authorization: Modifier or Admin.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved event")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = "/event")
    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<Void> deleteAllEvents() {
        eventServiceFacade
                .findAll()
                .forEach(event -> eventServiceFacade.delete(event.getTypeIdentifier().getType(), event.getId()));
        return ResponseEntity.ok().build();
    }

}
