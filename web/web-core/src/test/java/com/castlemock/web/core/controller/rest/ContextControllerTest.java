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
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ContextControllerTest {

    private ServletContext servletContext;
    private ContextController contextController;

    @BeforeEach
    void setup(){
        this.servletContext = mock(ServletContext.class);
        this.contextController = new ContextController(servletContext);
    }

    @Test
    @DisplayName("Get context")
    void testGetContext(){
        final String context = "castlemock";
        when(servletContext.getContextPath()).thenReturn(context);
        final ResponseEntity<ContextResponse> responseEntity = this.contextController.getContext();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(context, responseEntity.getBody().getContext());
        verify(servletContext, times(1)).getContextPath();
    }

}
