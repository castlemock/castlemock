/*
 * Copyright 2016 Karl Dahlgren
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

package com.fortmocks.web.basis.model.event.service;

import com.fortmocks.core.basis.model.TypeIdentifier;
import com.fortmocks.core.basis.model.event.dto.EventDto;
import com.fortmocks.core.basis.model.event.service.EventServiceAdapter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.1
 */
public class EventServiceFacadeImplTest {

    private static final String TYPE = "Type";
    private static final String TYPE_URL = "TypeUrl";

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private EventServiceFacadeImpl serviceFacade;

    private EventServiceAdapter eventServiceAdapter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        eventServiceAdapter = Mockito.mock(EventServiceAdapter.class);
        final TypeIdentifier typeIdentifier = Mockito.mock(TypeIdentifier.class);
        Mockito.when(typeIdentifier.getType()).thenReturn(TYPE);
        Mockito.when(typeIdentifier.getTypeUrl()).thenReturn(TYPE_URL);
        Mockito.when(eventServiceAdapter.getTypeIdentifier()).thenReturn(typeIdentifier);

        final Map<String, EventServiceAdapter> eventServiceAdapters = new HashMap<String, EventServiceAdapter>();
        eventServiceAdapters.put("EventServiceAdapter", eventServiceAdapter);
        Mockito.when(applicationContext.getBeansWithAnnotation(Mockito.any(Class.class))).thenReturn(eventServiceAdapters);

        serviceFacade.initiate();
    }


    @Test
    public void testSave(){
        final EventDto eventDto = new EventDto();
        Mockito.when(eventServiceAdapter.convertType(Mockito.any(EventDto.class))).thenReturn(eventDto);
        serviceFacade.save(TYPE, eventDto);
        Mockito.verify(eventServiceAdapter, Mockito.times(1)).create(Mockito.any(EventDto.class));
    }

    @Test
    public void testDelete(){
        serviceFacade.delete(TYPE_URL, "Delete event");
        Mockito.verify(eventServiceAdapter, Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test
    public void testUpdate(){
        final EventDto eventDto = new EventDto();
        Mockito.when(eventServiceAdapter.convertType(Mockito.any(EventDto.class))).thenReturn(eventDto);
        serviceFacade.update(TYPE_URL, "Id", eventDto);
        Mockito.verify(eventServiceAdapter, Mockito.times(1)).update(Mockito.anyString(), Mockito.any(EventDto.class));
    }

    @Test
    public void testFindAll(){
        final EventDto eventDto = new EventDto();
        final List<EventDto> eventDtos = new ArrayList<EventDto>();
        eventDtos.add(eventDto);
        Mockito.when(eventServiceAdapter.readAll()).thenReturn(eventDtos);

        final List<EventDto> returnedEventDtos = serviceFacade.findAll();
        Assert.assertEquals(1, returnedEventDtos.size());

        for(EventDto returnedEventDto : returnedEventDtos){
            Assert.assertEquals(TYPE, returnedEventDto.getTypeIdentifier().getType());
        }
    }

    @Test
    public void testFindOne(){
        final EventDto eventDto = new EventDto();
        Mockito.when(eventServiceAdapter.read(Mockito.anyString())).thenReturn(eventDto);
        final EventDto returnedEventDto = serviceFacade.findOne(TYPE_URL, "Id");
        Assert.assertEquals(eventDto, returnedEventDto);
        Assert.assertEquals(TYPE, eventDto.getTypeIdentifier().getType());
    }

    @Test
    public void tesGetTypes(){
        List<String> types = serviceFacade.getTypes();
        Assert.assertEquals(1, types.size());
        Assert.assertEquals(TYPE.toUpperCase(), types.get(0));
    }

    @Test
    public void testGetTypeUrl(){
        final String typeUrl = serviceFacade.getTypeUrl(TYPE);
        Assert.assertEquals(TYPE_URL, typeUrl);
    }

}
