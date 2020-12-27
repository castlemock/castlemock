/*
 * Copyright 2019 Karl Dahlgren
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

import com.castlemock.core.basis.model.ServiceProcessor;
import com.castlemock.web.basis.model.VersionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/api/rest/core")
@Api(value="Core", description="REST Operations for Castle Mock Core", tags = {"Core"})
public class VersionCoreRestController extends AbstractRestController {

    @Value("${app.version:Undefined}")
    private String version;

    public VersionCoreRestController(final ServiceProcessor serviceProcessor){
        super(serviceProcessor);
    }

    @ApiOperation(value = "Get system version")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved system version")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/version")
    public @ResponseBody
    ResponseEntity<VersionResponse> getVersion() {
        return ResponseEntity.ok(VersionResponse.builder()
                .version(Optional.ofNullable(version).orElse("Undefined"))
                .build());
    }

}
