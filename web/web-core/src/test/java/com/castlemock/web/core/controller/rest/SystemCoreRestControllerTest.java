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

package com.castlemock.web.core.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.system.SystemInformation;
import com.castlemock.model.core.system.SystemInformationTestBuilder;
import com.castlemock.service.core.system.output.GetSystemInformationOutput;
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
