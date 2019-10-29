/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.mock.rest.utility.compare;

import com.castlemock.core.basis.utility.compare.AlphanumComparator;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;

import java.util.Comparator;

public class RestMockResponseNameComparator implements Comparator<RestMockResponse> {

    private final static AlphanumComparator ALPHANUM_COMPARATOR = new AlphanumComparator();

    @Override
    public int compare(RestMockResponse o1, RestMockResponse o2) {
        return ALPHANUM_COMPARATOR.compare(o1.getName(), o2.getName());
    }
}
