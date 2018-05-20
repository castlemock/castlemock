/*
 * Copyright 2015 Karl Dahlgren
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

package com.castlemock.web.basis.web.mvc.controller.event;

import com.castlemock.web.basis.config.TestApplication;
import com.castlemock.web.basis.model.event.service.EventServiceFacadeImpl;
import com.castlemock.web.basis.web.AbstractController;
import com.castlemock.web.basis.web.mvc.controller.AbstractControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class ClearEventControllerTest extends AbstractControllerTest {

    private static final String SERVICE_URL_PAGE = "/web/event/clear";
    private static final String SERVICE_URL_CONFIRM = "/web/event/clear/confirm";
    private static final String PAGE = "partial/basis/event/clearEvents.jsp";

    @InjectMocks
    private ClearEventController clearEventController;

    @Override
    protected AbstractController getController() {
        return clearEventController;
    }
    @Mock
    private EventServiceFacadeImpl eventServiceComponent;

    @Test
    public void testGetClearAllConfirmationPage() throws Exception {
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL_PAGE);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(GLOBAL_VIEW_MODEL_COUNT))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE));
    }

    @Test
    public void testClearAll() throws Exception {
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL_CONFIRM);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/web/event"));


        Mockito.verify(eventServiceComponent, Mockito.times(1)).clearAll();
    }

}
