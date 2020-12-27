/*
 * Copyright 2020 Karl Dahlgren
 *
 * Licensed under the Apache License, System 2.0 (the "License");
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
import com.castlemock.core.basis.model.system.domain.SystemInformation;
import com.castlemock.core.basis.model.system.domain.SystemInformationTestBuilder;
import com.castlemock.core.basis.service.system.output.GetSystemInformationOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SystemCoreRestControllerTest {

    private ServiceProcessor serviceProcessor;
    private SystemCoreRestController systemController;

    @BeforeEach
    void setup(){
        this.serviceProcessor = mock(ServiceProcessor.class);
        this.systemController = new SystemCoreRestController(serviceProcessor);
    }

    @Test
    @DisplayName("Get system")
    void testGetSystem(){
        final SystemInformation systemInformation = SystemInformationTestBuilder.builder().build();
        when(serviceProcessor.process(any())).thenReturn(GetSystemInformationOutput.builder()
                .systemInformation(systemInformation)
                .build());
        final ResponseEntity<SystemInformation> responseEntity = this.systemController.getSystemInformation();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(systemInformation, responseEntity.getBody());
    }

}
