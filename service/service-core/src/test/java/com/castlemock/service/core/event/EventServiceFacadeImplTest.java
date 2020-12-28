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

package com.castlemock.service.core.event;

import com.castlemock.model.core.TypeIdentifier;
import com.castlemock.model.core.event.Event;
import com.castlemock.model.core.event.EventTestBuilder;
import com.castlemock.model.core.service.event.EventServiceAdapter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.1
 */
@SuppressWarnings({"unchecked", "rawtypes"})
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
        MockitoAnnotations.openMocks(this);

        eventServiceAdapter = Mockito.mock(EventServiceAdapter.class);
        final TypeIdentifier typeIdentifier = Mockito.mock(TypeIdentifier.class);
        Mockito.when(typeIdentifier.getType()).thenReturn(TYPE);
        Mockito.when(typeIdentifier.getTypeUrl()).thenReturn(TYPE_URL);
        Mockito.when(eventServiceAdapter.getTypeIdentifier()).thenReturn(typeIdentifier);

        final Map<String, Object> eventServiceAdapters = new HashMap<String, Object>();
        eventServiceAdapters.put("EventServiceAdapter", eventServiceAdapter);
        Mockito.when(applicationContext.getBeansWithAnnotation(Mockito.any(Class.class))).thenReturn(eventServiceAdapters);

        serviceFacade.initiate();
    }

    @Test
    public void testSave(){
        final Event eventDto = new Event();
        Mockito.when(eventServiceAdapter.convertType(Mockito.any(Event.class))).thenReturn(eventDto);
        Mockito.when(eventServiceAdapter.create(Mockito.any(Event.class))).thenReturn(eventDto);
        serviceFacade.save(TYPE, eventDto);
        Mockito.verify(eventServiceAdapter, Mockito.times(1)).create(Mockito.any(Event.class));
    }

    @Test
    public void testDelete(){
        final Event eventDto = new Event();
        Mockito.when(eventServiceAdapter.delete(Mockito.any())).thenReturn(eventDto);
        serviceFacade.delete(TYPE_URL, "Delete event");
        Mockito.verify(eventServiceAdapter, Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test
    public void testUpdate(){
        final Event eventDto = new Event();
        Mockito.when(eventServiceAdapter.convertType(Mockito.any(Event.class))).thenReturn(eventDto);
        Mockito.when(eventServiceAdapter.update(Mockito.any(), Mockito.any(Event.class))).thenReturn(eventDto);
        serviceFacade.update(TYPE_URL, "Id", eventDto);
        Mockito.verify(eventServiceAdapter, Mockito.times(1)).update(Mockito.anyString(), Mockito.any(Event.class));
    }

    @Test
    public void testFindAll(){
        final Event oldEvent = EventTestBuilder.builder().build();
        oldEvent.setStartDate(new GregorianCalendar(2016, 1, 1).getTime());
        final Event newEvent = EventTestBuilder.builder().build();
        newEvent.setStartDate(new GregorianCalendar(2016, 1, 2).getTime());
        final List<Event> eventDtos = new ArrayList<Event>();
        eventDtos.add(oldEvent);
        eventDtos.add(newEvent);
        Mockito.when(eventServiceAdapter.readAll()).thenReturn(eventDtos);

        final List<Event> returnedEventDtos = serviceFacade.findAll();
        Assert.assertEquals(2, returnedEventDtos.size());

        for(Event returnedEventDto : returnedEventDtos){
            Assert.assertEquals(TYPE, returnedEventDto.getTypeIdentifier().getType());
        }

        Assert.assertEquals(returnedEventDtos.get(0).getId(), newEvent.getId());
        Assert.assertEquals(returnedEventDtos.get(1).getId(), oldEvent.getId());
    }

    @Test
    public void testFindOne(){
        final Event eventDto = new Event();
        Mockito.when(eventServiceAdapter.read(Mockito.anyString())).thenReturn(eventDto);
        final Event returnedEventDto = serviceFacade.findOne(TYPE_URL, "Id");
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

    @Test
    public void testClearAll(){
        serviceFacade.clearAll();
        Mockito.verify(eventServiceAdapter, Mockito.times(1)).clearAll();

    }

}
