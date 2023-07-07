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

package com.castlemock.web.core.controller.rest;

import com.castlemock.web.core.model.ContextResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
@Controller
@RequestMapping("/api/rest/core")
@Tag(name="Core - Context")
public class ContextController {

    private final ServletContext servletContext;

    @Autowired
    public ContextController(final ServletContext servletContext){
        this.servletContext = Objects.requireNonNull(servletContext);
    }

    @Operation(summary =  "Get context")
    @RequestMapping(method = RequestMethod.GET, value = "/context")
    public @ResponseBody
    ResponseEntity<ContextResponse> getContext() {
        return ResponseEntity.ok(ContextResponse.builder()
                .context(servletContext.getContextPath())
                .build());
    }

}
