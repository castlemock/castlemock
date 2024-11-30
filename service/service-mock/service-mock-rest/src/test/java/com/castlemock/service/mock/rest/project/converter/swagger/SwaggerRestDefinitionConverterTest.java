package com.castlemock.service.mock.rest.project.converter.swagger;

import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.rest.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class SwaggerRestDefinitionConverterTest {


    @Test
    public void testConvert() throws URISyntaxException {
        final SwaggerRestDefinitionConverter converter = new SwaggerRestDefinitionConverter();
        final URL url = SwaggerRestDefinitionConverter.class.getResource("full.json");
        final File file = new File(Objects.requireNonNull(url).toURI());
        final List<RestApplication> restApplications = converter.convert(file, "1", false);
        this.verifyResult(restApplications, false);
    }

    @Test
    public void testConvertGenerateResponse() throws URISyntaxException {
        final SwaggerRestDefinitionConverter converter = new SwaggerRestDefinitionConverter();
        final URL url = SwaggerRestDefinitionConverter.class.getResource("full.json");
        final File file = new File(Objects.requireNonNull(url).toURI());
        final List<RestApplication> restApplications = converter.convert(file,"1", true);
        this.verifyResult(restApplications, true);
    }



    private void verifyResult(final List<RestApplication> restApplications,
                              final boolean generatedResponse){

        Assertions.assertNotNull(restApplications);
        Assertions.assertEquals(1, restApplications.size());

        final RestApplication restApplication = restApplications.getFirst();

        Assertions.assertEquals("Castle Mock Swagger", restApplication.getName());
        Assertions.assertEquals(2, restApplication.getResources().size());


        // /mock
        final RestResource mockResource = restApplication.getResources().stream()
                .filter(resource -> resource.getName().equals("/mock"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(mockResource);
        Assertions.assertEquals("/mock", mockResource.getName());
        Assertions.assertEquals("/mock", mockResource.getUri());

        // /mock (GET) - getAllMockServices

        RestMethod getAllMockServicesMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("getAllMockServices"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(getAllMockServicesMethod);
        Assertions.assertEquals("getAllMockServices", getAllMockServicesMethod.getName());
        Assertions.assertEquals("http://castlemock.com/v1", getAllMockServicesMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertEquals(HttpMethod.GET, getAllMockServicesMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, getAllMockServicesMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.SEQUENCE, getAllMockServicesMethod.getResponseStrategy());
        Assertions.assertEquals(Integer.valueOf(0), getAllMockServicesMethod.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(Long.valueOf(0L), getAllMockServicesMethod.getNetworkDelay().orElse(0L));
        Assertions.assertFalse(getAllMockServicesMethod.getSimulateNetworkDelay().orElse(false));

        if(generatedResponse){
            Assertions.assertEquals(4, getAllMockServicesMethod.getMockResponses().size());

            // JSON
            RestMockResponse response200Xml = getAllMockServicesMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation (application/xml)"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(response200Xml);
            Assertions.assertEquals("successful operation (application/xml)", response200Xml.getName());
            Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<Mock><name>${RANDOM_STRING()}</name><id>${RANDOM_LONG()}</id><createdBy>${RANDOM_STRING()}</createdBy>" +
                    "<mockStatus>${RANDOM_INTEGER()}</mockStatus></Mock>", response200Xml.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(200), response200Xml.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.ENABLED, response200Xml.getStatus());
            Assertions.assertTrue(response200Xml.getUsingExpressions().orElse(false));
            Assertions.assertTrue(response200Xml.getContentEncodings().isEmpty());
            Assertions.assertEquals(1, response200Xml.getHttpHeaders().size());
            Assertions.assertEquals("Content-Type", response200Xml.getHttpHeaders().getFirst().getName());
            Assertions.assertEquals("application/xml", response200Xml.getHttpHeaders().getFirst().getValue());

            // XML
            RestMockResponse response200Json = getAllMockServicesMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation (application/json)"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(response200Json);
            Assertions.assertEquals("successful operation (application/json)", response200Json.getName());
            Assertions.assertEquals("{\"name\":\"${RANDOM_STRING()}\",\"id\":\"${RANDOM_LONG()}\"," +
                    "\"createdBy\":\"${RANDOM_STRING()}\",\"mockStatus\":\"${RANDOM_INTEGER()}\"}",
                    response200Json.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(200), response200Json.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.ENABLED, response200Json.getStatus());
            Assertions.assertTrue(response200Json.getUsingExpressions().orElse(false));
            Assertions.assertTrue(response200Json.getContentEncodings().isEmpty());
            Assertions.assertEquals(1, response200Json.getHttpHeaders().size());
            Assertions.assertEquals("Content-Type", response200Json.getHttpHeaders().getFirst().getName());
            Assertions.assertEquals("application/json", response200Json.getHttpHeaders().getFirst().getValue());


            // Invalid mock id supplied
            RestMockResponse invalidMockResponse = getAllMockServicesMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid mock id supplied"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(invalidMockResponse);
            Assertions.assertEquals("Invalid mock id supplied", invalidMockResponse.getName());

            Assertions.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assertions.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // Mock not found
            RestMockResponse notFoundResponse = getAllMockServicesMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Mock not found"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(notFoundResponse);
            Assertions.assertEquals("Mock not found", notFoundResponse.getName());

            Assertions.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assertions.assertTrue(notFoundResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assertions.assertEquals(0, getAllMockServicesMethod.getMockResponses().size());
        }


        // /mock (POST) - createMock

        RestMethod createMockMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("createMock"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(createMockMethod);
        Assertions.assertEquals("createMock", createMockMethod.getName());
        Assertions.assertEquals("http://castlemock.com/v1", createMockMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertEquals(HttpMethod.POST, createMockMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, createMockMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.SEQUENCE, createMockMethod.getResponseStrategy());
        Assertions.assertEquals(Integer.valueOf(0), createMockMethod.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(Long.valueOf(0L), createMockMethod.getNetworkDelay().orElse(0L));
        Assertions.assertFalse(createMockMethod.getSimulateNetworkDelay().orElse(false));

        if(generatedResponse){
            Assertions.assertEquals(1, createMockMethod.getMockResponses().size());

            // Invalid mock id supplied
            RestMockResponse invalidMockResponse = getAllMockServicesMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid mock id supplied"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(invalidMockResponse);
            Assertions.assertEquals("Invalid mock id supplied", invalidMockResponse.getName());

            Assertions.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assertions.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

        } else {
            Assertions.assertEquals(0, createMockMethod.getMockResponses().size());
        }





        // /mock (HEAD) - headMock

        RestMethod headMockMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("headMock"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(headMockMethod);
        Assertions.assertEquals("headMock", headMockMethod.getName());
        Assertions.assertEquals("http://castlemock.com/v1", headMockMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertEquals(HttpMethod.HEAD, headMockMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, headMockMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.SEQUENCE, headMockMethod.getResponseStrategy());
        Assertions.assertEquals(Integer.valueOf(0), headMockMethod.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(Long.valueOf(0L), headMockMethod.getNetworkDelay().orElse(null));
        Assertions.assertFalse(headMockMethod.getSimulateNetworkDelay().orElse(false));

        // /mock (OPTIONS) - headerMock

        RestMethod optionsMockMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("optionsMock"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(optionsMockMethod);
        Assertions.assertEquals("optionsMock", optionsMockMethod.getName());
        Assertions.assertEquals("http://castlemock.com/v1", optionsMockMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertEquals(HttpMethod.OPTIONS, optionsMockMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, optionsMockMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.SEQUENCE, optionsMockMethod.getResponseStrategy());
        Assertions.assertEquals(Integer.valueOf(0), optionsMockMethod.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(Long.valueOf(0L), optionsMockMethod.getNetworkDelay().orElse(null));
        Assertions.assertFalse(optionsMockMethod.getSimulateNetworkDelay().orElse(false));


        // /mock/{mockId}
        final RestResource mockWithParameterResource = restApplication.getResources().stream()
                .filter(resource -> resource.getName().equals("/mock/{mockId}"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(mockWithParameterResource);
        Assertions.assertEquals("/mock/{mockId}", mockWithParameterResource.getName());
        Assertions.assertEquals("/mock/{mockId}", mockWithParameterResource.getUri());

        // /mock/{mockId} (GET) - getMockById

        RestMethod getMockByIdMethod = mockWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("Get mock by mock id"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(getMockByIdMethod);
        Assertions.assertEquals("Get mock by mock id", getMockByIdMethod.getName());
        Assertions.assertEquals("http://castlemock.com/v1", getMockByIdMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertEquals(HttpMethod.GET, getMockByIdMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, getMockByIdMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.SEQUENCE, getMockByIdMethod.getResponseStrategy());
        Assertions.assertEquals(Integer.valueOf(0), getMockByIdMethod.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(Long.valueOf(0L), getMockByIdMethod.getNetworkDelay().orElse(null));
        Assertions.assertFalse(getMockByIdMethod.getSimulateNetworkDelay().orElse(false));

        if(generatedResponse){
            Assertions.assertEquals(4, getMockByIdMethod.getMockResponses().size());

            // JSON
            RestMockResponse response200Xml = getMockByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation (application/xml)"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(response200Xml);
            Assertions.assertEquals("successful operation (application/xml)", response200Xml.getName());
            Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<Mock><name>${RANDOM_STRING()}" +
                    "</name><id>${RANDOM_LONG()}</id><createdBy>${RANDOM_STRING()}" +
                    "</createdBy><mockStatus>${RANDOM_INTEGER()}</mockStatus></Mock>", response200Xml.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(200), response200Xml.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.ENABLED, response200Xml.getStatus());
            Assertions.assertTrue(response200Xml.getUsingExpressions().orElse(false));
            Assertions.assertTrue(response200Xml.getContentEncodings().isEmpty());
            Assertions.assertEquals(1, response200Xml.getHttpHeaders().size());
            Assertions.assertEquals("Content-Type", response200Xml.getHttpHeaders().getFirst().getName());
            Assertions.assertEquals("application/xml", response200Xml.getHttpHeaders().getFirst().getValue());

            // XML
            RestMockResponse response200Json = getMockByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation (application/json)"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(response200Json);
            Assertions.assertEquals("successful operation (application/json)", response200Json.getName());
            Assertions.assertEquals("{\"name\":\"${RANDOM_STRING()}\",\"id\":\"${RANDOM_LONG()}\",\"createdBy\":\"" +
                            "${RANDOM_STRING()}\",\"mockStatus\":\"${RANDOM_INTEGER()}\"}",
                    response200Json.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(200), response200Json.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.ENABLED, response200Json.getStatus());
            Assertions.assertTrue(response200Json.getUsingExpressions().orElse(false));
            Assertions.assertTrue(response200Json.getContentEncodings().isEmpty());
            Assertions.assertEquals(1, response200Json.getHttpHeaders().size());
            Assertions.assertEquals("Content-Type", response200Json.getHttpHeaders().getFirst().getName());
            Assertions.assertEquals("application/json", response200Json.getHttpHeaders().getFirst().getValue());


            // Invalid mock id supplied
            RestMockResponse invalidMockResponse = getMockByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid mock id supplied"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(invalidMockResponse);
            Assertions.assertEquals("Invalid mock id supplied", invalidMockResponse.getName());

            Assertions.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assertions.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // Mock not found
            RestMockResponse notFoundResponse = getMockByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Mock not found"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(notFoundResponse);
            Assertions.assertEquals("Mock not found", notFoundResponse.getName());

            Assertions.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assertions.assertTrue(notFoundResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assertions.assertEquals(0, getMockByIdMethod.getMockResponses().size());
        }


        // /mock/{mockId} (PUT) - getMockById

        RestMethod updateMockMethod = mockWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("updateMock"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(updateMockMethod);
        Assertions.assertEquals("updateMock", updateMockMethod.getName());
        Assertions.assertEquals("http://castlemock.com/v1", updateMockMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertEquals(HttpMethod.PUT, updateMockMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, updateMockMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.SEQUENCE, updateMockMethod.getResponseStrategy());
        Assertions.assertEquals(Integer.valueOf(0), updateMockMethod.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(Long.valueOf(0L), updateMockMethod.getNetworkDelay().orElse(null));
        Assertions.assertFalse(updateMockMethod.getSimulateNetworkDelay().orElse(false));

        if(generatedResponse){
            Assertions.assertEquals(2, updateMockMethod.getMockResponses().size());

            // Invalid mock id supplied
            RestMockResponse invalidMockResponse = updateMockMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid mock supplied"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(invalidMockResponse);
            Assertions.assertEquals("Invalid mock supplied", invalidMockResponse.getName());

            Assertions.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assertions.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // Mock not found
            RestMockResponse notFoundResponse = updateMockMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Mock not found"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(notFoundResponse);
            Assertions.assertEquals("Mock not found", notFoundResponse.getName());

            Assertions.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assertions.assertTrue(notFoundResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assertions.assertEquals(0, updateMockMethod.getMockResponses().size());
        }

        // /mock/{mockId} (DELETE) - getMockById

        RestMethod deleteMockMethod = mockWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("deleteMock"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(deleteMockMethod);
        Assertions.assertEquals("deleteMock", deleteMockMethod.getName());
        Assertions.assertEquals("http://castlemock.com/v1", deleteMockMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertEquals(HttpMethod.DELETE, deleteMockMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, deleteMockMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.SEQUENCE, deleteMockMethod.getResponseStrategy());
        Assertions.assertEquals(Integer.valueOf(0), deleteMockMethod.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(Long.valueOf(0L), deleteMockMethod.getNetworkDelay().orElse(null));
        Assertions.assertFalse(deleteMockMethod.getSimulateNetworkDelay().orElse(false));

        if(generatedResponse){
            Assertions.assertEquals(1, deleteMockMethod.getMockResponses().size());

            // Mock not found
            RestMockResponse notFoundResponse = deleteMockMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Mock not found"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(notFoundResponse);
            Assertions.assertEquals("Mock not found", notFoundResponse.getName());

            Assertions.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assertions.assertTrue(notFoundResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assertions.assertEquals(0, deleteMockMethod.getMockResponses().size());
        }
    }
}
