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
