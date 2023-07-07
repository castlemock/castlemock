/*
 * Copyright 2021 Karl Dahlgren
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

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.service.core.expression.input.ValidateExpressionInput;
import com.castlemock.service.core.expression.output.ValidateExpressionOutput;
import com.castlemock.web.core.model.ValidateExpressionRequest;
import com.castlemock.web.core.model.ValidateExpressionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Karl Dahlgren
 * @since 1.55
 */
@Controller
@RequestMapping("/api/rest/core")
@Tag(name="Core - Expression", description="REST Operations for Castle Mock Core")
@ConditionalOnExpression("${server.mode.demo} == false")
public class ExpressionRestController extends AbstractRestController {

    public ExpressionRestController(final ServiceProcessor serviceProcessor){
        super(serviceProcessor);
    }

    @Operation(summary =  "Validate expression",
            description = "Validate expression. Required authorization: Reader, Modification, Admin.")
    @RequestMapping(method = RequestMethod.POST, value = "/expression/validate")
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    public @ResponseBody
    ResponseEntity<ValidateExpressionResponse> validateExpression(@RequestBody final ValidateExpressionRequest request) {
        final ValidateExpressionOutput output = serviceProcessor.process(ValidateExpressionInput.builder()
                .requestBody(request.getRequestBody())
                .responseBody(request.getResponseBody())
                .build());
        return ResponseEntity.ok(ValidateExpressionResponse.builder()
                .output(output.getOutput())
                .build());
    }

}
