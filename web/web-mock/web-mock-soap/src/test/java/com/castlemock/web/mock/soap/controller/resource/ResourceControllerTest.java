/*
 * Copyright 2024 Karl Dahlgren
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

package com.castlemock.web.mock.soap.controller.resource;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.service.mock.soap.project.input.LoadSoapResourceInput;
import com.castlemock.service.mock.soap.project.output.LoadSoapResourceOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

class ResourceControllerTest {

    @Test
    @DisplayName("Get resource")
    void testGetResource() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final ResourceController controller = new ResourceController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String content = "<resource></resource>";

        Mockito.when(serviceProcessor.process(LoadSoapResourceInput
                .builder()
                .projectId(projectId)
                .resourceId(resourceId)
                .build())).thenReturn(LoadSoapResourceOutput.builder()
                .resource(content)
                .build());

        final ResponseEntity<String> response = controller.getResource(projectId, resourceId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(content, response.getBody());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(LoadSoapResourceInput
                .builder()
                .projectId(projectId)
                .resourceId(resourceId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

    @Test
    @DisplayName("Get resource - Not found")
    void testGetResourceNotFound() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final ResourceController controller = new ResourceController(serviceProcessor);
        final String projectId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();

        Mockito.when(serviceProcessor.process(LoadSoapResourceInput
                .builder()
                .projectId(projectId)
                .resourceId(resourceId)
                .build())).thenReturn(LoadSoapResourceOutput.builder()
                .resource(null)
                .build());

        final ResponseEntity<String> response = controller.getResource(projectId, resourceId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(LoadSoapResourceInput
                .builder()
                .projectId(projectId)
                .resourceId(resourceId)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

}
