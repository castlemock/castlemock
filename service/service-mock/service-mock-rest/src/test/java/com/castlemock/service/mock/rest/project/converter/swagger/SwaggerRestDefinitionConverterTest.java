package com.castlemock.service.mock.rest.project.converter.swagger;

import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.model.mock.rest.domain.RestResponseStrategy;
import org.junit.Assert;
import org.junit.Test;

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

        Assert.assertNotNull(restApplications);
        Assert.assertEquals(1, restApplications.size());

        final RestApplication restApplication = restApplications.getFirst();

        Assert.assertEquals("Castle Mock Swagger", restApplication.getName());
        Assert.assertEquals(2, restApplication.getResources().size());


        // /mock
        final RestResource mockResource = restApplication.getResources().stream()
                .filter(resource -> resource.getName().equals("/mock"))
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(mockResource);
        Assert.assertEquals("/mock", mockResource.getName());
        Assert.assertEquals("/mock", mockResource.getUri());

        // /mock (GET) - getAllMockServices

        RestMethod getAllMockServicesMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("getAllMockServices"))
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(getAllMockServicesMethod);
        Assert.assertEquals("getAllMockServices", getAllMockServicesMethod.getName());
        Assert.assertEquals("http://castlemock.com/v1", getAllMockServicesMethod.getForwardedEndpoint().orElse(null));
        Assert.assertEquals(HttpMethod.GET, getAllMockServicesMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, getAllMockServicesMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, getAllMockServicesMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), getAllMockServicesMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(Long.valueOf(0L), getAllMockServicesMethod.getNetworkDelay().orElse(0L));
        Assert.assertFalse(getAllMockServicesMethod.getSimulateNetworkDelay().orElse(false));

        if(generatedResponse){
            Assert.assertEquals(4, getAllMockServicesMethod.getMockResponses().size());

            // JSON
            RestMockResponse response200Xml = getAllMockServicesMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation (application/xml)"))
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(response200Xml);
            Assert.assertEquals("successful operation (application/xml)", response200Xml.getName());
            Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<Mock><name>${RANDOM_STRING()}</name><id>${RANDOM_LONG()}</id><createdBy>${RANDOM_STRING()}</createdBy>" +
                    "<mockStatus>${RANDOM_INTEGER()}</mockStatus></Mock>", response200Xml.getBody().orElse(null));

            Assert.assertEquals(Integer.valueOf(200), response200Xml.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, response200Xml.getStatus());
            Assert.assertTrue(response200Xml.getUsingExpressions().orElse(false));
            Assert.assertTrue(response200Xml.getContentEncodings().isEmpty());
            Assert.assertEquals(1, response200Xml.getHttpHeaders().size());
            Assert.assertEquals("Content-Type", response200Xml.getHttpHeaders().getFirst().getName());
            Assert.assertEquals("application/xml", response200Xml.getHttpHeaders().getFirst().getValue());

            // XML
            RestMockResponse response200Json = getAllMockServicesMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation (application/json)"))
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(response200Json);
            Assert.assertEquals("successful operation (application/json)", response200Json.getName());
            Assert.assertEquals("{\"name\":\"${RANDOM_STRING()}\",\"id\":\"${RANDOM_LONG()}\"," +
                    "\"createdBy\":\"${RANDOM_STRING()}\",\"mockStatus\":\"${RANDOM_INTEGER()}\"}",
                    response200Json.getBody().orElse(null));

            Assert.assertEquals(Integer.valueOf(200), response200Json.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, response200Json.getStatus());
            Assert.assertTrue(response200Json.getUsingExpressions().orElse(false));
            Assert.assertTrue(response200Json.getContentEncodings().isEmpty());
            Assert.assertEquals(1, response200Json.getHttpHeaders().size());
            Assert.assertEquals("Content-Type", response200Json.getHttpHeaders().getFirst().getName());
            Assert.assertEquals("application/json", response200Json.getHttpHeaders().getFirst().getValue());


            // Invalid mock id supplied
            RestMockResponse invalidMockResponse = getAllMockServicesMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid mock id supplied"))
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(invalidMockResponse);
            Assert.assertEquals("Invalid mock id supplied", invalidMockResponse.getName());

            Assert.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // Mock not found
            RestMockResponse notFoundResponse = getAllMockServicesMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Mock not found"))
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(notFoundResponse);
            Assert.assertEquals("Mock not found", notFoundResponse.getName());

            Assert.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assert.assertTrue(notFoundResponse.getUsingExpressions().orElse(false));
            Assert.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assert.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assert.assertEquals(0, getAllMockServicesMethod.getMockResponses().size());
        }


        // /mock (POST) - createMock

        RestMethod createMockMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("createMock"))
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(createMockMethod);
        Assert.assertEquals("createMock", createMockMethod.getName());
        Assert.assertEquals("http://castlemock.com/v1", createMockMethod.getForwardedEndpoint().orElse(null));
        Assert.assertEquals(HttpMethod.POST, createMockMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, createMockMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, createMockMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), createMockMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(Long.valueOf(0L), createMockMethod.getNetworkDelay().orElse(0L));
        Assert.assertFalse(createMockMethod.getSimulateNetworkDelay().orElse(false));

        if(generatedResponse){
            Assert.assertEquals(1, createMockMethod.getMockResponses().size());

            // Invalid mock id supplied
            RestMockResponse invalidMockResponse = getAllMockServicesMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid mock id supplied"))
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(invalidMockResponse);
            Assert.assertEquals("Invalid mock id supplied", invalidMockResponse.getName());

            Assert.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

        } else {
            Assert.assertEquals(0, createMockMethod.getMockResponses().size());
        }





        // /mock (HEAD) - headMock

        RestMethod headMockMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("headMock"))
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(headMockMethod);
        Assert.assertEquals("headMock", headMockMethod.getName());
        Assert.assertEquals("http://castlemock.com/v1", headMockMethod.getForwardedEndpoint().orElse(null));
        Assert.assertEquals(HttpMethod.HEAD, headMockMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, headMockMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, headMockMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), headMockMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(Long.valueOf(0L), headMockMethod.getNetworkDelay().orElse(null));
        Assert.assertFalse(headMockMethod.getSimulateNetworkDelay().orElse(false));

        // /mock (OPTIONS) - headerMock

        RestMethod optionsMockMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("optionsMock"))
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(optionsMockMethod);
        Assert.assertEquals("optionsMock", optionsMockMethod.getName());
        Assert.assertEquals("http://castlemock.com/v1", optionsMockMethod.getForwardedEndpoint().orElse(null));
        Assert.assertEquals(HttpMethod.OPTIONS, optionsMockMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, optionsMockMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, optionsMockMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), optionsMockMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(Long.valueOf(0L), optionsMockMethod.getNetworkDelay().orElse(null));
        Assert.assertFalse(optionsMockMethod.getSimulateNetworkDelay().orElse(false));


        // /mock/{mockId}
        final RestResource mockWithParameterResource = restApplication.getResources().stream()
                .filter(resource -> resource.getName().equals("/mock/{mockId}"))
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(mockWithParameterResource);
        Assert.assertEquals("/mock/{mockId}", mockWithParameterResource.getName());
        Assert.assertEquals("/mock/{mockId}", mockWithParameterResource.getUri());

        // /mock/{mockId} (GET) - getMockById

        RestMethod getMockByIdMethod = mockWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("Get mock by mock id"))
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(getMockByIdMethod);
        Assert.assertEquals("Get mock by mock id", getMockByIdMethod.getName());
        Assert.assertEquals("http://castlemock.com/v1", getMockByIdMethod.getForwardedEndpoint().orElse(null));
        Assert.assertEquals(HttpMethod.GET, getMockByIdMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, getMockByIdMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, getMockByIdMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), getMockByIdMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(Long.valueOf(0L), getMockByIdMethod.getNetworkDelay().orElse(null));
        Assert.assertFalse(getMockByIdMethod.getSimulateNetworkDelay().orElse(false));

        if(generatedResponse){
            Assert.assertEquals(4, getMockByIdMethod.getMockResponses().size());

            // JSON
            RestMockResponse response200Xml = getMockByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation (application/xml)"))
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(response200Xml);
            Assert.assertEquals("successful operation (application/xml)", response200Xml.getName());
            Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<Mock><name>${RANDOM_STRING()}" +
                    "</name><id>${RANDOM_LONG()}</id><createdBy>${RANDOM_STRING()}" +
                    "</createdBy><mockStatus>${RANDOM_INTEGER()}</mockStatus></Mock>", response200Xml.getBody().orElse(null));

            Assert.assertEquals(Integer.valueOf(200), response200Xml.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, response200Xml.getStatus());
            Assert.assertTrue(response200Xml.getUsingExpressions().orElse(false));
            Assert.assertTrue(response200Xml.getContentEncodings().isEmpty());
            Assert.assertEquals(1, response200Xml.getHttpHeaders().size());
            Assert.assertEquals("Content-Type", response200Xml.getHttpHeaders().getFirst().getName());
            Assert.assertEquals("application/xml", response200Xml.getHttpHeaders().getFirst().getValue());

            // XML
            RestMockResponse response200Json = getMockByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation (application/json)"))
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(response200Json);
            Assert.assertEquals("successful operation (application/json)", response200Json.getName());
            Assert.assertEquals("{\"name\":\"${RANDOM_STRING()}\",\"id\":\"${RANDOM_LONG()}\",\"createdBy\":\"" +
                            "${RANDOM_STRING()}\",\"mockStatus\":\"${RANDOM_INTEGER()}\"}",
                    response200Json.getBody().orElse(null));

            Assert.assertEquals(Integer.valueOf(200), response200Json.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, response200Json.getStatus());
            Assert.assertTrue(response200Json.getUsingExpressions().orElse(false));
            Assert.assertTrue(response200Json.getContentEncodings().isEmpty());
            Assert.assertEquals(1, response200Json.getHttpHeaders().size());
            Assert.assertEquals("Content-Type", response200Json.getHttpHeaders().getFirst().getName());
            Assert.assertEquals("application/json", response200Json.getHttpHeaders().getFirst().getValue());


            // Invalid mock id supplied
            RestMockResponse invalidMockResponse = getMockByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid mock id supplied"))
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(invalidMockResponse);
            Assert.assertEquals("Invalid mock id supplied", invalidMockResponse.getName());

            Assert.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // Mock not found
            RestMockResponse notFoundResponse = getMockByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Mock not found"))
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(notFoundResponse);
            Assert.assertEquals("Mock not found", notFoundResponse.getName());

            Assert.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assert.assertTrue(notFoundResponse.getUsingExpressions().orElse(false));
            Assert.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assert.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assert.assertEquals(0, getMockByIdMethod.getMockResponses().size());
        }


        // /mock/{mockId} (PUT) - getMockById

        RestMethod updateMockMethod = mockWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("updateMock"))
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(updateMockMethod);
        Assert.assertEquals("updateMock", updateMockMethod.getName());
        Assert.assertEquals("http://castlemock.com/v1", updateMockMethod.getForwardedEndpoint().orElse(null));
        Assert.assertEquals(HttpMethod.PUT, updateMockMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, updateMockMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, updateMockMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), updateMockMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(Long.valueOf(0L), updateMockMethod.getNetworkDelay().orElse(null));
        Assert.assertFalse(updateMockMethod.getSimulateNetworkDelay().orElse(false));

        if(generatedResponse){
            Assert.assertEquals(2, updateMockMethod.getMockResponses().size());

            // Invalid mock id supplied
            RestMockResponse invalidMockResponse = updateMockMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid mock supplied"))
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(invalidMockResponse);
            Assert.assertEquals("Invalid mock supplied", invalidMockResponse.getName());

            Assert.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // Mock not found
            RestMockResponse notFoundResponse = updateMockMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Mock not found"))
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(notFoundResponse);
            Assert.assertEquals("Mock not found", notFoundResponse.getName());

            Assert.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assert.assertTrue(notFoundResponse.getUsingExpressions().orElse(false));
            Assert.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assert.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assert.assertEquals(0, updateMockMethod.getMockResponses().size());
        }

        // /mock/{mockId} (DELETE) - getMockById

        RestMethod deleteMockMethod = mockWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("deleteMock"))
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(deleteMockMethod);
        Assert.assertEquals("deleteMock", deleteMockMethod.getName());
        Assert.assertEquals("http://castlemock.com/v1", deleteMockMethod.getForwardedEndpoint().orElse(null));
        Assert.assertEquals(HttpMethod.DELETE, deleteMockMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, deleteMockMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, deleteMockMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), deleteMockMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(Long.valueOf(0L), deleteMockMethod.getNetworkDelay().orElse(null));
        Assert.assertFalse(deleteMockMethod.getSimulateNetworkDelay().orElse(false));

        if(generatedResponse){
            Assert.assertEquals(1, deleteMockMethod.getMockResponses().size());

            // Mock not found
            RestMockResponse notFoundResponse = deleteMockMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Mock not found"))
                    .findFirst()
                    .orElse(null);

            Assert.assertNotNull(notFoundResponse);
            Assert.assertEquals("Mock not found", notFoundResponse.getName());

            Assert.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assert.assertTrue(notFoundResponse.getUsingExpressions().orElse(false));
            Assert.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assert.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assert.assertEquals(0, deleteMockMethod.getMockResponses().size());
        }
    }
}
