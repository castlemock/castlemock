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

package com.castlemock.model.core.event;

import java.util.Comparator;

/**
 * The comparator provides the functionality to sort events based on their start date
 * @author Karl Dahlgren
 * @since 1.3
 */
public class EventStartDateComparator implements Comparator<Event> {

    @Override
    public int compare(Event o1, Event o2) {
        return o1.getStartDate().compareTo(o2.getStartDate()) * -1;
    }
}
