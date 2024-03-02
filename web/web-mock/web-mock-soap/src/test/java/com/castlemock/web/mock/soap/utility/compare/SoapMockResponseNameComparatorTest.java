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

package com.castlemock.web.mock.soap.utility.compare;

import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class SoapMockResponseNameComparatorTest {

    @Test
    @DisplayName("Compare")
    void testCompare() {
        final SoapMockResponse response1 = SoapMockResponseTestBuilder.builder()
                .name("BBB")
                .build();
        final SoapMockResponse response2 = SoapMockResponseTestBuilder.builder()
                .name("CCC")
                .build();
        final SoapMockResponse response3 = SoapMockResponseTestBuilder.builder()
                .name("AAA")
                .build();

        final List<SoapMockResponse> responses = new ArrayList<>(List.of(response1, response2, response3));
        responses.sort(new SoapMockResponseNameComparator());

        Assertions.assertEquals(3, responses.size());
        Assertions.assertEquals(response3, responses.get(0));
        Assertions.assertEquals(response1, responses.get(1));
        Assertions.assertEquals(response2, responses.get(2));
    }

}
