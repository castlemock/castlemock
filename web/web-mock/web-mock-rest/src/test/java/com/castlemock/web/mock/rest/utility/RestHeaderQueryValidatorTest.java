package com.castlemock.web.mock.rest.utility;

import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.mock.rest.domain.RestHeaderQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

class RestHeaderQueryValidatorTest {

    @Test
    @DisplayName("Validate - Query match")
    void testValidateMatchQuery() {
        final RestHeaderQuery headerQuery = RestHeaderQuery.builder()
                .header("Content-Encoding")
                .query("application/json")
                .matchRegex(false)
                .matchCase(true)
                .matchAny(false)
                .build();
        final HttpHeader httpHeader = HttpHeader.builder()
                .name("Content-Encoding")
                .value("application/json")
                .build();

        Assertions.assertTrue(RestHeaderQueryValidator.validate(List.of(headerQuery), Set.of(httpHeader)));
    }

    @Test
    @DisplayName("Validate - Query no match - Case sensitive")
    void testValidateNoMatchCaseSensitive() {
        final RestHeaderQuery headerQuery = RestHeaderQuery.builder()
                .header("Content-Encoding")
                .query("application/JSON")
                .matchRegex(false)
                .matchCase(true)
                .matchAny(false)
                .build();
        final HttpHeader httpHeader = HttpHeader.builder()
                .name("Content-Encoding")
                .value("application/json")
                .build();

        Assertions.assertFalse(RestHeaderQueryValidator.validate(List.of(headerQuery), Set.of(httpHeader)));
    }

    @Test
    @DisplayName("Validate - Query Match - No Case sensitive")
    void testValidateMatchNoCaseSensitive() {
        final RestHeaderQuery headerQuery = RestHeaderQuery.builder()
                .header("Content-Encoding")
                .query("application/JSON")
                .matchRegex(false)
                .matchCase(false)
                .matchAny(false)
                .build();
        final HttpHeader httpHeader = HttpHeader.builder()
                .name("Content-Encoding")
                .value("application/json")
                .build();

        Assertions.assertTrue(RestHeaderQueryValidator.validate(List.of(headerQuery), Set.of(httpHeader)));
    }

    @Test
    @DisplayName("Validate - Query Match - Any")
    void testValidateMatchAny() {
        final RestHeaderQuery headerQuery = RestHeaderQuery.builder()
                .header("Content-Encoding")
                .query("application/json")
                .matchRegex(false)
                .matchCase(false)
                .matchAny(true)
                .build();
        final HttpHeader httpHeader = HttpHeader.builder()
                .name("Content-Encoding")
                .value("application/xml")
                .build();

        Assertions.assertTrue(RestHeaderQueryValidator.validate(List.of(headerQuery), Set.of(httpHeader)));
    }

    @Test
    @DisplayName("Validate - Query no match - Regex")
    void testValidateNoMatchRegex() {
        final RestHeaderQuery headerQuery = RestHeaderQuery.builder()
                .header("Content-Encoding")
                .query("application/json")
                .matchRegex(true)
                .matchCase(false)
                .matchAny(false)
                .build();
        final HttpHeader httpHeader = HttpHeader.builder()
                .name("Content-Encoding")
                .value("application/xml")
                .build();

        Assertions.assertFalse(RestHeaderQueryValidator.validate(List.of(headerQuery), Set.of(httpHeader)));
    }

    @Test
    @DisplayName("Validate - Query match - Regex")
    void testValidateMatchRegex() {
        final RestHeaderQuery headerQuery = RestHeaderQuery.builder()
                .header("Content-Encoding")
                .query("application/(.*)")
                .matchRegex(true)
                .matchCase(false)
                .matchAny(false)
                .build();
        final HttpHeader httpHeader = HttpHeader.builder()
                .name("Content-Encoding")
                .value("APPLICATION/json")
                .build();

        Assertions.assertTrue(RestHeaderQueryValidator.validate(List.of(headerQuery), Set.of(httpHeader)));
    }

    @Test
    @DisplayName("Validate - Query no match - Regex and case sensitive")
    void testValidateMatchRegexCaseSensitive() {
        final RestHeaderQuery headerQuery = RestHeaderQuery.builder()
                .header("Content-Encoding")
                .query("application/(.*)")
                .matchRegex(true)
                .matchCase(true)
                .matchAny(false)
                .build();
        final HttpHeader httpHeader = HttpHeader.builder()
                .name("Content-Encoding")
                .value("APPLICATION/json")
                .build();

        Assertions.assertFalse(RestHeaderQueryValidator.validate(List.of(headerQuery), Set.of(httpHeader)));
    }

}
