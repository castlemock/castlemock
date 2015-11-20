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

package com.fortmocks.web.basis.web.mvc.controller.event;

import com.fortmocks.core.model.event.dto.EventDto;
import com.fortmocks.web.basis.config.TestApplication;
import com.fortmocks.web.basis.model.event.dto.EventDtoGenerator;
import com.fortmocks.web.basis.model.event.service.EventServiceFacadeImpl;
import com.fortmocks.web.basis.web.mvc.controller.AbstractController;
import com.fortmocks.web.basis.web.mvc.controller.AbstractControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class EventOverviewControllerTest extends AbstractControllerTest {

    private static final String SERVICE_URL = "/web/event";
    private static final String PAGE = "partial/basis/event/eventOverview.jsp";
    private static final String EVENTS = "events";
    private static final Integer EVENT_COUNT = 5;

    @InjectMocks
    private EventOverviewController eventOverviewController;

    @Override
    protected AbstractController getController() {
        return eventOverviewController;
    }
    @Mock
    private EventServiceFacadeImpl eventServiceComponent;

    @Test
    public void testGetUserWithValidId() throws Exception {
        List<EventDto> eventDtos = new ArrayList<EventDto>();
        for(int index = 0; index < EVENT_COUNT; index++){
            eventDtos.add(EventDtoGenerator.generateEventDto());
        }
        when(eventServiceComponent.findAll()).thenReturn(eventDtos);
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL);
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(EVENTS, eventDtos));
    }

}
