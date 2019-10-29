package com.castlemock.web.mock.rest.converter.swagger;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.rest.model.project.domain.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class SwaggerRestDefinitionConverterTest {


    @Test
    public void testConvert() throws URISyntaxException {
        final SwaggerRestDefinitionConverter converter = new SwaggerRestDefinitionConverter();
        final URL url = SwaggerRestDefinitionConverter.class.getResource("full.json");
        final File file = new File(url.toURI());
        final List<RestApplication> restApplications = converter.convert(file, false);
        this.verifyResult(restApplications, false);
    }

    @Test
    public void testConvertGenerateResponse() throws URISyntaxException {
        final SwaggerRestDefinitionConverter converter = new SwaggerRestDefinitionConverter();
        final URL url = SwaggerRestDefinitionConverter.class.getResource("full.json");
        final File file = new File(url.toURI());
        final List<RestApplication> restApplications = converter.convert(file, true);
        this.verifyResult(restApplications, true);
    }



    private void verifyResult(final List<RestApplication> restApplications,
                              final boolean generatedResponse){

        Assert.assertNotNull(restApplications);
        Assert.assertEquals(1, restApplications.size());

        final RestApplication restApplication = restApplications.get(0);

        Assert.assertEquals("Castle Mock Swagger", restApplication.getName());
        Assert.assertNull(restApplication.getId());
        Assert.assertNull(restApplication.getProjectId());
        Assert.assertEquals(2, restApplication.getResources().size());


        // /mock
        final RestResource mockResource = restApplication.getResources().stream()
                .filter(resource -> resource.getName().equals("/mock"))
                .findFirst()
                .get();

        Assert.assertNotNull(mockResource);
        Assert.assertEquals("/mock", mockResource.getName());
        Assert.assertEquals("/mock", mockResource.getUri());
        Assert.assertNull(mockResource.getId());
        Assert.assertNull(mockResource.getApplicationId());
        Assert.assertNull(mockResource.getInvokeAddress());

        // /mock (GET) - getAllMockServices

        RestMethod getAllMockServicesMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("getAllMockServices"))
                .findFirst()
                .get();

        Assert.assertNotNull(getAllMockServicesMethod);
        Assert.assertEquals("getAllMockServices", getAllMockServicesMethod.getName());
        Assert.assertEquals("http://castlemock.com/v1", getAllMockServicesMethod.getForwardedEndpoint());
        Assert.assertEquals(HttpMethod.GET, getAllMockServicesMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, getAllMockServicesMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, getAllMockServicesMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), getAllMockServicesMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(0L, getAllMockServicesMethod.getNetworkDelay());
        Assert.assertFalse(getAllMockServicesMethod.getSimulateNetworkDelay());
        Assert.assertNull(getAllMockServicesMethod.getDefaultBody());
        Assert.assertNull(getAllMockServicesMethod.getId());
        Assert.assertNull(getAllMockServicesMethod.getResourceId());

        if(generatedResponse){
            Assert.assertEquals(4, getAllMockServicesMethod.getMockResponses().size());

            // JSON
            RestMockResponse response200Xml = getAllMockServicesMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation (application/xml)"))
                    .findFirst()
                    .get();

            Assert.assertEquals("successful operation (application/xml)", response200Xml.getName());
            Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<Mock><name>${RANDOM_STRING()}</name><id>${RANDOM_LONG()}</id><createdBy>${RANDOM_STRING()}</createdBy>" +
                    "<mockStatus>${RANDOM_INTEGER()}</mockStatus></Mock>", response200Xml.getBody());

            Assert.assertEquals(Integer.valueOf(200), response200Xml.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, response200Xml.getStatus());
            Assert.assertTrue(response200Xml.isUsingExpressions());
            Assert.assertTrue(response200Xml.getContentEncodings().isEmpty());
            Assert.assertNull(response200Xml.getId());
            Assert.assertNull(response200Xml.getMethodId());
            Assert.assertEquals(1, response200Xml.getHttpHeaders().size());
            Assert.assertEquals("Content-Type", response200Xml.getHttpHeaders().get(0).getName());
            Assert.assertEquals("application/xml", response200Xml.getHttpHeaders().get(0).getValue());

            // XML
            RestMockResponse response200Json = getAllMockServicesMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation (application/json)"))
                    .findFirst()
                    .get();

            Assert.assertEquals("successful operation (application/json)", response200Json.getName());
            Assert.assertEquals("{\"name\":\"${RANDOM_STRING()}\",\"id\":\"${RANDOM_LONG()}\"," +
                    "\"createdBy\":\"${RANDOM_STRING()}\",\"mockStatus\":\"${RANDOM_INTEGER()}\"}",
                    response200Json.getBody());

            Assert.assertEquals(Integer.valueOf(200), response200Json.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, response200Json.getStatus());
            Assert.assertTrue(response200Json.isUsingExpressions());
            Assert.assertTrue(response200Json.getContentEncodings().isEmpty());
            Assert.assertNull(response200Json.getId());
            Assert.assertNull(response200Json.getMethodId());
            Assert.assertEquals(1, response200Json.getHttpHeaders().size());
            Assert.assertEquals("Content-Type", response200Json.getHttpHeaders().get(0).getName());
            Assert.assertEquals("application/json", response200Json.getHttpHeaders().get(0).getValue());


            // Invalid mock id supplied
            RestMockResponse invalidMockResponse = getAllMockServicesMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid mock id supplied"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Invalid mock id supplied", invalidMockResponse.getName());
            Assert.assertNull(invalidMockResponse.getBody());

            Assert.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.isUsingExpressions());
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertNull(invalidMockResponse.getId());
            Assert.assertNull(invalidMockResponse.getMethodId());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // Mock not found
            RestMockResponse notFoundResponse = getAllMockServicesMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Mock not found"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Mock not found", notFoundResponse.getName());
            Assert.assertNull(notFoundResponse.getBody());

            Assert.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assert.assertTrue(notFoundResponse.isUsingExpressions());
            Assert.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assert.assertNull(notFoundResponse.getId());
            Assert.assertNull(notFoundResponse.getMethodId());
            Assert.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assert.assertEquals(0, getAllMockServicesMethod.getMockResponses().size());
        }


        // /mock (POST) - createMock

        RestMethod createMockMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("createMock"))
                .findFirst()
                .get();

        Assert.assertNotNull(createMockMethod);
        Assert.assertEquals("createMock", createMockMethod.getName());
        Assert.assertEquals("http://castlemock.com/v1", createMockMethod.getForwardedEndpoint());
        Assert.assertEquals(HttpMethod.POST, createMockMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, createMockMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, createMockMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), createMockMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(0L, createMockMethod.getNetworkDelay());
        Assert.assertFalse(createMockMethod.getSimulateNetworkDelay());
        Assert.assertNull(createMockMethod.getDefaultBody());
        Assert.assertNull(createMockMethod.getId());
        Assert.assertNull(createMockMethod.getResourceId());

        if(generatedResponse){
            Assert.assertEquals(1, createMockMethod.getMockResponses().size());

            // Invalid mock id supplied
            RestMockResponse invalidMockResponse = getAllMockServicesMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid mock id supplied"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Invalid mock id supplied", invalidMockResponse.getName());
            Assert.assertNull(invalidMockResponse.getBody());

            Assert.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.isUsingExpressions());
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertNull(invalidMockResponse.getId());
            Assert.assertNull(invalidMockResponse.getMethodId());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

        } else {
            Assert.assertEquals(0, createMockMethod.getMockResponses().size());
        }





        // /mock (HEAD) - headMock

        RestMethod headMockMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("headMock"))
                .findFirst()
                .get();

        Assert.assertNotNull(headMockMethod);
        Assert.assertEquals("headMock", headMockMethod.getName());
        Assert.assertEquals("http://castlemock.com/v1", headMockMethod.getForwardedEndpoint());
        Assert.assertEquals(HttpMethod.HEAD, headMockMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, headMockMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, headMockMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), headMockMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(0L, headMockMethod.getNetworkDelay());
        Assert.assertFalse(headMockMethod.getSimulateNetworkDelay());
        Assert.assertNull(headMockMethod.getDefaultBody());
        Assert.assertNull(headMockMethod.getId());
        Assert.assertNull(headMockMethod.getResourceId());

        // /mock (OPTIONS) - headerMock

        RestMethod optionsMockMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("optionsMock"))
                .findFirst()
                .get();

        Assert.assertNotNull(optionsMockMethod);
        Assert.assertEquals("optionsMock", optionsMockMethod.getName());
        Assert.assertEquals("http://castlemock.com/v1", optionsMockMethod.getForwardedEndpoint());
        Assert.assertEquals(HttpMethod.OPTIONS, optionsMockMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, optionsMockMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, optionsMockMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), optionsMockMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(0L, optionsMockMethod.getNetworkDelay());
        Assert.assertFalse(optionsMockMethod.getSimulateNetworkDelay());
        Assert.assertNull(optionsMockMethod.getDefaultBody());
        Assert.assertNull(optionsMockMethod.getId());
        Assert.assertNull(optionsMockMethod.getResourceId());



        // /mock/{mockId}
        final RestResource mockWithParameterResource = restApplication.getResources().stream()
                .filter(resource -> resource.getName().equals("/mock/{mockId}"))
                .findFirst()
                .get();

        Assert.assertNotNull(mockWithParameterResource);
        Assert.assertEquals("/mock/{mockId}", mockWithParameterResource.getName());
        Assert.assertEquals("/mock/{mockId}", mockWithParameterResource.getUri());
        Assert.assertNull(mockWithParameterResource.getId());
        Assert.assertNull(mockWithParameterResource.getApplicationId());
        Assert.assertNull(mockWithParameterResource.getInvokeAddress());

        // /mock/{mockId} (GET) - getMockById

        RestMethod getMockByIdMethod = mockWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("Get mock by mock id"))
                .findFirst()
                .get();

        Assert.assertNotNull(getMockByIdMethod);
        Assert.assertEquals("Get mock by mock id", getMockByIdMethod.getName());
        Assert.assertEquals("http://castlemock.com/v1", getMockByIdMethod.getForwardedEndpoint());
        Assert.assertEquals(HttpMethod.GET, getMockByIdMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, getMockByIdMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, getMockByIdMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), getMockByIdMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(0L, getMockByIdMethod.getNetworkDelay());
        Assert.assertFalse(getMockByIdMethod.getSimulateNetworkDelay());
        Assert.assertNull(getMockByIdMethod.getDefaultBody());
        Assert.assertNull(getMockByIdMethod.getId());
        Assert.assertNull(getMockByIdMethod.getResourceId());

        if(generatedResponse){
            Assert.assertEquals(4, getMockByIdMethod.getMockResponses().size());

            // JSON
            RestMockResponse response200Xml = getMockByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation (application/xml)"))
                    .findFirst()
                    .get();

            Assert.assertEquals("successful operation (application/xml)", response200Xml.getName());
            Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                    "<Mock><name>${RANDOM_STRING()}" +
                    "</name><id>${RANDOM_LONG()}</id><createdBy>${RANDOM_STRING()}" +
                    "</createdBy><mockStatus>${RANDOM_INTEGER()}</mockStatus></Mock>", response200Xml.getBody());

            Assert.assertEquals(Integer.valueOf(200), response200Xml.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, response200Xml.getStatus());
            Assert.assertTrue(response200Xml.isUsingExpressions());
            Assert.assertTrue(response200Xml.getContentEncodings().isEmpty());
            Assert.assertNull(response200Xml.getId());
            Assert.assertNull(response200Xml.getMethodId());
            Assert.assertEquals(1, response200Xml.getHttpHeaders().size());
            Assert.assertEquals("Content-Type", response200Xml.getHttpHeaders().get(0).getName());
            Assert.assertEquals("application/xml", response200Xml.getHttpHeaders().get(0).getValue());

            // XML
            RestMockResponse response200Json = getMockByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation (application/json)"))
                    .findFirst()
                    .get();

            Assert.assertEquals("successful operation (application/json)", response200Json.getName());
            Assert.assertEquals("{\"name\":\"${RANDOM_STRING()}\",\"id\":\"${RANDOM_LONG()}\",\"createdBy\":\"" +
                            "${RANDOM_STRING()}\",\"mockStatus\":\"${RANDOM_INTEGER()}\"}",
                    response200Json.getBody());

            Assert.assertEquals(Integer.valueOf(200), response200Json.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, response200Json.getStatus());
            Assert.assertTrue(response200Json.isUsingExpressions());
            Assert.assertTrue(response200Json.getContentEncodings().isEmpty());
            Assert.assertNull(response200Json.getId());
            Assert.assertNull(response200Json.getMethodId());
            Assert.assertEquals(1, response200Json.getHttpHeaders().size());
            Assert.assertEquals("Content-Type", response200Json.getHttpHeaders().get(0).getName());
            Assert.assertEquals("application/json", response200Json.getHttpHeaders().get(0).getValue());


            // Invalid mock id supplied
            RestMockResponse invalidMockResponse = getMockByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid mock id supplied"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Invalid mock id supplied", invalidMockResponse.getName());
            Assert.assertNull(invalidMockResponse.getBody());

            Assert.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.isUsingExpressions());
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertNull(invalidMockResponse.getId());
            Assert.assertNull(invalidMockResponse.getMethodId());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // Mock not found
            RestMockResponse notFoundResponse = getMockByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Mock not found"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Mock not found", notFoundResponse.getName());
            Assert.assertNull(notFoundResponse.getBody());

            Assert.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assert.assertTrue(notFoundResponse.isUsingExpressions());
            Assert.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assert.assertNull(notFoundResponse.getId());
            Assert.assertNull(notFoundResponse.getMethodId());
            Assert.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assert.assertEquals(0, getMockByIdMethod.getMockResponses().size());
        }


        // /mock/{mockId} (PUT) - getMockById

        RestMethod updateMockMethod = mockWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("updateMock"))
                .findFirst()
                .get();

        Assert.assertNotNull(updateMockMethod);
        Assert.assertEquals("updateMock", updateMockMethod.getName());
        Assert.assertEquals("http://castlemock.com/v1", updateMockMethod.getForwardedEndpoint());
        Assert.assertEquals(HttpMethod.PUT, updateMockMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, updateMockMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, updateMockMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), updateMockMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(0L, updateMockMethod.getNetworkDelay());
        Assert.assertFalse(updateMockMethod.getSimulateNetworkDelay());
        Assert.assertNull(updateMockMethod.getDefaultBody());
        Assert.assertNull(updateMockMethod.getId());
        Assert.assertNull(updateMockMethod.getResourceId());

        if(generatedResponse){
            Assert.assertEquals(2, updateMockMethod.getMockResponses().size());

            // Invalid mock id supplied
            RestMockResponse invalidMockResponse = updateMockMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid mock supplied"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Invalid mock supplied", invalidMockResponse.getName());
            Assert.assertNull(invalidMockResponse.getBody());

            Assert.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.isUsingExpressions());
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertNull(invalidMockResponse.getId());
            Assert.assertNull(invalidMockResponse.getMethodId());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // Mock not found
            RestMockResponse notFoundResponse = updateMockMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Mock not found"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Mock not found", notFoundResponse.getName());
            Assert.assertNull(notFoundResponse.getBody());

            Assert.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assert.assertTrue(notFoundResponse.isUsingExpressions());
            Assert.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assert.assertNull(notFoundResponse.getId());
            Assert.assertNull(notFoundResponse.getMethodId());
            Assert.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assert.assertEquals(0, updateMockMethod.getMockResponses().size());
        }

        // /mock/{mockId} (DELETE) - getMockById

        RestMethod deleteMockMethod = mockWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("deleteMock"))
                .findFirst()
                .get();

        Assert.assertNotNull(deleteMockMethod);
        Assert.assertEquals("deleteMock", deleteMockMethod.getName());
        Assert.assertEquals("http://castlemock.com/v1", deleteMockMethod.getForwardedEndpoint());
        Assert.assertEquals(HttpMethod.DELETE, deleteMockMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, deleteMockMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, deleteMockMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), deleteMockMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(0L, deleteMockMethod.getNetworkDelay());
        Assert.assertFalse(deleteMockMethod.getSimulateNetworkDelay());
        Assert.assertNull(deleteMockMethod.getDefaultBody());
        Assert.assertNull(deleteMockMethod.getId());
        Assert.assertNull(deleteMockMethod.getResourceId());


        if(generatedResponse){
            Assert.assertEquals(1, deleteMockMethod.getMockResponses().size());

            // Mock not found
            RestMockResponse notFoundResponse = deleteMockMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Mock not found"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Mock not found", notFoundResponse.getName());
            Assert.assertNull(notFoundResponse.getBody());

            Assert.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assert.assertTrue(notFoundResponse.isUsingExpressions());
            Assert.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assert.assertNull(notFoundResponse.getId());
            Assert.assertNull(notFoundResponse.getMethodId());
            Assert.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assert.assertEquals(0, deleteMockMethod.getMockResponses().size());
        }
    }
}
