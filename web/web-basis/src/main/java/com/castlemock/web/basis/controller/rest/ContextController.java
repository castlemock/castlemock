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

package com.castlemock.web.basis.controller.rest;

import com.castlemock.web.basis.model.ContextResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
@Controller
@RequestMapping("/api/rest/core")
@Api(value="Core", tags = {"Core"})
public class ContextController {

    private final ServletContext servletContext;

    @Autowired
    public ContextController(final ServletContext servletContext){
        this.servletContext = Objects.requireNonNull(servletContext);
    }

    @ApiOperation(value = "Get context")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved context")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/context")
    public @ResponseBody
    ResponseEntity<ContextResponse> getContext() {
        return ResponseEntity.ok(ContextResponse.builder()
                .context(servletContext.getContextPath())
                .build());
    }

}
