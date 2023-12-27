package com.castlemock.service.mock.rest.project.converter.openapi;

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

public class OpenApiRestDefinitionConverterTest {

    final OpenApiRestDefinitionConverter converter = new OpenApiRestDefinitionConverter();

    @Test
    public void shouldConvertToRestApplications_givenFile() throws URISyntaxException {
        final URL url = OpenApiRestDefinitionConverter.class.getResource("petstore.json");
        final File file = new File(url.toURI());
        final List<RestApplication> restApplications = converter.convert(file, false);
        this.verifyResult(restApplications, false);
    }

    @Test
    public void shouldConvertToRestApplications_givenFile_WithGenerateResponseTrue() throws URISyntaxException {
        final URL url = OpenApiRestDefinitionConverter.class.getResource("petstore.json");
        final File file = new File(url.toURI());
        final List<RestApplication> restApplications = converter.convert(file, true);
        this.verifyResult(restApplications, true);
    }

    @Test
    public void shouldThrowError_givenOpenAPINull() throws URISyntaxException {
        final URL url = OpenApiRestDefinitionConverter.class.getResource("petstore_empty.json");
        final File file = new File(url.toURI());
        IllegalArgumentException actualException = Assert.assertThrows(IllegalArgumentException.class, () -> converter.convert(file, false));
        String expectedExceptionMessage = "Unable to parse the OpenApi content.";
        Assert.assertEquals(actualException.getMessage(), expectedExceptionMessage);
    }

    @Test
    public void shouldConvertToRestApplications_givenMalformedFile() throws URISyntaxException {
        final URL url = OpenApiRestDefinitionConverter.class.getResource("petstore_malformed_openapi.json");
        final File file = new File(url.toURI());
        final List<RestApplication> restApplications = converter.convert(file, true);
        this.verifyResultForMalformedFile(restApplications, true);
    }

    private void verifyResult(final List<RestApplication> restApplications,
                              final boolean generatedResponse) {

        Assert.assertNotNull(restApplications);
        Assert.assertEquals(1, restApplications.size());

        final RestApplication restApplication = restApplications.get(0);

        Assert.assertEquals("Swagger Petstore - OpenAPI 3.0", restApplication.getName());
        Assert.assertNull(restApplication.getId());
        Assert.assertNull(restApplication.getProjectId());
        Assert.assertEquals(2, restApplication.getResources().size());


        // /pet
        final RestResource mockResource = restApplication.getResources().stream()
                .filter(resource -> resource.getName().equals("/pet"))
                .findFirst()
                .get();

        Assert.assertNotNull(mockResource);
        Assert.assertEquals("/pet", mockResource.getName());
        Assert.assertEquals("/pet", mockResource.getUri());
        Assert.assertNull(mockResource.getId());
        Assert.assertNull(mockResource.getApplicationId());
        Assert.assertNull(mockResource.getInvokeAddress());

        // /pet (PUT) - updatePet

        RestMethod updatePetRestMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("updatePet"))
                .findFirst()
                .get();

        Assert.assertNotNull(updatePetRestMethod);
        Assert.assertEquals("updatePet", updatePetRestMethod.getName());
        Assert.assertEquals("/api/v3", updatePetRestMethod.getForwardedEndpoint());
        Assert.assertEquals(HttpMethod.PUT, updatePetRestMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, updatePetRestMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, updatePetRestMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), updatePetRestMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(0L, updatePetRestMethod.getNetworkDelay());
        Assert.assertFalse(updatePetRestMethod.getSimulateNetworkDelay());
        Assert.assertNull(updatePetRestMethod.getDefaultBody());
        Assert.assertNull(updatePetRestMethod.getId());
        Assert.assertNull(updatePetRestMethod.getResourceId());

        if (generatedResponse) {
            Assert.assertEquals(4, updatePetRestMethod.getMockResponses().size());

            // 200
            RestMockResponse response = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Successful operation"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Successful operation", response.getName());

            Assert.assertEquals(Integer.valueOf(200), response.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, response.getStatus());
            Assert.assertTrue(response.isUsingExpressions());
            Assert.assertTrue(response.getContentEncodings().isEmpty());
            Assert.assertNull(response.getId());
            Assert.assertNull(response.getMethodId());


            // 400
            RestMockResponse invalidMockResponse = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid ID supplied"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Invalid ID supplied", invalidMockResponse.getName());
            Assert.assertNull(invalidMockResponse.getBody());

            Assert.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.isUsingExpressions());
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertNull(invalidMockResponse.getId());
            Assert.assertNull(invalidMockResponse.getMethodId());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // 404
            RestMockResponse notFoundResponse = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Pet not found"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Pet not found", notFoundResponse.getName());
            Assert.assertNull(notFoundResponse.getBody());

            Assert.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assert.assertTrue(notFoundResponse.isUsingExpressions());
            Assert.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assert.assertNull(notFoundResponse.getId());
            Assert.assertNull(notFoundResponse.getMethodId());
            Assert.assertEquals(0, notFoundResponse.getHttpHeaders().size());

            // 405
            RestMockResponse validationExceptionResponse = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Validation exception"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Validation exception", validationExceptionResponse.getName());
            Assert.assertNull(validationExceptionResponse.getBody());

            Assert.assertEquals(Integer.valueOf(405), validationExceptionResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, validationExceptionResponse.getStatus());
            Assert.assertTrue(validationExceptionResponse.isUsingExpressions());
            Assert.assertTrue(validationExceptionResponse.getContentEncodings().isEmpty());
            Assert.assertNull(validationExceptionResponse.getId());
            Assert.assertNull(validationExceptionResponse.getMethodId());
            Assert.assertEquals(0, validationExceptionResponse.getHttpHeaders().size());
        } else {
            Assert.assertEquals(0, updatePetRestMethod.getMockResponses().size());
        }


        // /pet (POST) - addPet

        RestMethod addPetRestMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("addPet"))
                .findFirst()
                .get();

        Assert.assertNotNull(addPetRestMethod);
        Assert.assertEquals("addPet", addPetRestMethod.getName());
        Assert.assertEquals("/api/v3", addPetRestMethod.getForwardedEndpoint());
        Assert.assertEquals(HttpMethod.POST, addPetRestMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, addPetRestMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, addPetRestMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), addPetRestMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(0L, addPetRestMethod.getNetworkDelay());
        Assert.assertFalse(addPetRestMethod.getSimulateNetworkDelay());
        Assert.assertNull(addPetRestMethod.getDefaultBody());
        Assert.assertNull(addPetRestMethod.getId());
        Assert.assertNull(addPetRestMethod.getResourceId());

        if (generatedResponse) {
            Assert.assertEquals(2, addPetRestMethod.getMockResponses().size());

            // 200
            RestMockResponse response = addPetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Successful operation"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Successful operation", response.getName());

            Assert.assertEquals(Integer.valueOf(200), response.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, response.getStatus());
            Assert.assertTrue(response.isUsingExpressions());
            Assert.assertTrue(response.getContentEncodings().isEmpty());
            Assert.assertNull(response.getId());
            Assert.assertNull(response.getMethodId());

            // 405
            RestMockResponse invalidMockResponse = addPetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid input"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Invalid input", invalidMockResponse.getName());
            Assert.assertNull(invalidMockResponse.getBody());

            Assert.assertEquals(Integer.valueOf(405), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.isUsingExpressions());
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertNull(invalidMockResponse.getId());
            Assert.assertNull(invalidMockResponse.getMethodId());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

        } else {
            Assert.assertEquals(0, addPetRestMethod.getMockResponses().size());
        }

        // /pet/{petId}
        final RestResource petWithParameterResource = restApplication.getResources().stream()
                .filter(resource -> resource.getName().equals("/pet/{petId}"))
                .findFirst()
                .get();

        Assert.assertNotNull(petWithParameterResource);
        Assert.assertEquals("/pet/{petId}", petWithParameterResource.getName());
        Assert.assertEquals("/pet/{petId}", petWithParameterResource.getUri());
        Assert.assertNull(petWithParameterResource.getId());
        Assert.assertNull(petWithParameterResource.getApplicationId());
        Assert.assertNull(petWithParameterResource.getInvokeAddress());

        // /mock/{mockId} (GET) - getMockById

        RestMethod getPetByIdMethod = petWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("getPetById"))
                .findFirst()
                .get();

        Assert.assertNotNull(getPetByIdMethod);
        Assert.assertEquals("getPetById", getPetByIdMethod.getName());
        Assert.assertEquals("/api/v3", getPetByIdMethod.getForwardedEndpoint());
        Assert.assertEquals(HttpMethod.GET, getPetByIdMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, getPetByIdMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, getPetByIdMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), getPetByIdMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(0L, getPetByIdMethod.getNetworkDelay());
        Assert.assertFalse(getPetByIdMethod.getSimulateNetworkDelay());
        Assert.assertNull(getPetByIdMethod.getDefaultBody());
        Assert.assertNull(getPetByIdMethod.getId());
        Assert.assertNull(getPetByIdMethod.getResourceId());

        if (generatedResponse) {
            Assert.assertEquals(3, getPetByIdMethod.getMockResponses().size());

            // 200
            RestMockResponse response = getPetByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation"))
                    .findFirst()
                    .get();

            Assert.assertEquals("successful operation", response.getName());

            Assert.assertEquals(Integer.valueOf(200), response.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, response.getStatus());
            Assert.assertTrue(response.isUsingExpressions());
            Assert.assertTrue(response.getContentEncodings().isEmpty());
            Assert.assertNull(response.getId());
            Assert.assertNull(response.getMethodId());


            // 400
            RestMockResponse invalidMockResponse = getPetByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid ID supplied"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Invalid ID supplied", invalidMockResponse.getName());
            Assert.assertNull(invalidMockResponse.getBody());

            Assert.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.isUsingExpressions());
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertNull(invalidMockResponse.getId());
            Assert.assertNull(invalidMockResponse.getMethodId());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // 404
            RestMockResponse notFoundResponse = getPetByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Pet not found"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Pet not found", notFoundResponse.getName());
            Assert.assertNull(notFoundResponse.getBody());

            Assert.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assert.assertTrue(notFoundResponse.isUsingExpressions());
            Assert.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assert.assertNull(notFoundResponse.getId());
            Assert.assertNull(notFoundResponse.getMethodId());
            Assert.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assert.assertEquals(0, getPetByIdMethod.getMockResponses().size());
        }


        // /pet/{petId} (DELETE) - deletePet

        RestMethod deletePetMethod = petWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("deletePet"))
                .findFirst()
                .get();

        Assert.assertNotNull(deletePetMethod);
        Assert.assertEquals("deletePet", deletePetMethod.getName());
        Assert.assertEquals("/api/v3", deletePetMethod.getForwardedEndpoint());
        Assert.assertEquals(HttpMethod.DELETE, deletePetMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, deletePetMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, deletePetMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), deletePetMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(0L, deletePetMethod.getNetworkDelay());
        Assert.assertFalse(deletePetMethod.getSimulateNetworkDelay());
        Assert.assertNull(deletePetMethod.getDefaultBody());
        Assert.assertNull(deletePetMethod.getId());
        Assert.assertNull(deletePetMethod.getResourceId());

        if (generatedResponse) {
            Assert.assertEquals(1, deletePetMethod.getMockResponses().size());

            // 400
            RestMockResponse invalidMockResponse = deletePetMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid pet value"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Invalid pet value", invalidMockResponse.getName());
            Assert.assertNull(invalidMockResponse.getBody());

            Assert.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.isUsingExpressions());
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertNull(invalidMockResponse.getId());
            Assert.assertNull(invalidMockResponse.getMethodId());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());
        } else {
            Assert.assertEquals(0, deletePetMethod.getMockResponses().size());
        }
    }

    private void verifyResultForMalformedFile(final List<RestApplication> restApplications,
                              final boolean generatedResponse) {

        Assert.assertNotNull(restApplications);
        Assert.assertEquals(1, restApplications.size());

        final RestApplication restApplication = restApplications.get(0);

        Assert.assertEquals("/api/v3", restApplication.getName());
        Assert.assertNull(restApplication.getId());
        Assert.assertNull(restApplication.getProjectId());
        Assert.assertEquals(2, restApplication.getResources().size());


        // /pet
        final RestResource mockResource = restApplication.getResources().stream()
                .filter(resource -> resource.getName().equals("/pet"))
                .findFirst()
                .get();

        Assert.assertNotNull(mockResource);
        Assert.assertEquals("/pet", mockResource.getName());
        Assert.assertEquals("/pet", mockResource.getUri());
        Assert.assertNull(mockResource.getId());
        Assert.assertNull(mockResource.getApplicationId());
        Assert.assertNull(mockResource.getInvokeAddress());

        // /pet (PUT) - updatePet

        RestMethod updatePetRestMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("Update an existing pet"))
                .findFirst()
                .get();

        Assert.assertNotNull(updatePetRestMethod);
        Assert.assertEquals("Update an existing pet", updatePetRestMethod.getName());
        Assert.assertEquals("/api/v3", updatePetRestMethod.getForwardedEndpoint());
        Assert.assertEquals(HttpMethod.PUT, updatePetRestMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, updatePetRestMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, updatePetRestMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), updatePetRestMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(0L, updatePetRestMethod.getNetworkDelay());
        Assert.assertFalse(updatePetRestMethod.getSimulateNetworkDelay());
        Assert.assertNull(updatePetRestMethod.getDefaultBody());
        Assert.assertNull(updatePetRestMethod.getId());
        Assert.assertNull(updatePetRestMethod.getResourceId());

        if (generatedResponse) {
            Assert.assertEquals(4, updatePetRestMethod.getMockResponses().size());

            // 200
            RestMockResponse response = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Successful operation"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Successful operation", response.getName());

            Assert.assertEquals(Integer.valueOf(200), response.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, response.getStatus());
            Assert.assertTrue(response.isUsingExpressions());
            Assert.assertTrue(response.getContentEncodings().isEmpty());
            Assert.assertNull(response.getId());
            Assert.assertNull(response.getMethodId());


            // 400
            RestMockResponse invalidMockResponse = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid ID supplied"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Invalid ID supplied", invalidMockResponse.getName());
            Assert.assertNull(invalidMockResponse.getBody());

            Assert.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.isUsingExpressions());
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertNull(invalidMockResponse.getId());
            Assert.assertNull(invalidMockResponse.getMethodId());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // 404
            RestMockResponse notFoundResponse = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Pet not found"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Pet not found", notFoundResponse.getName());
            Assert.assertNull(notFoundResponse.getBody());

            Assert.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assert.assertTrue(notFoundResponse.isUsingExpressions());
            Assert.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assert.assertNull(notFoundResponse.getId());
            Assert.assertNull(notFoundResponse.getMethodId());
            Assert.assertEquals(0, notFoundResponse.getHttpHeaders().size());

            // 405
            RestMockResponse validationExceptionResponse = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Validation exception"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Validation exception", validationExceptionResponse.getName());
            Assert.assertNull(validationExceptionResponse.getBody());

            Assert.assertEquals(Integer.valueOf(405), validationExceptionResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, validationExceptionResponse.getStatus());
            Assert.assertTrue(validationExceptionResponse.isUsingExpressions());
            Assert.assertTrue(validationExceptionResponse.getContentEncodings().isEmpty());
            Assert.assertNull(validationExceptionResponse.getId());
            Assert.assertNull(validationExceptionResponse.getMethodId());
            Assert.assertEquals(0, validationExceptionResponse.getHttpHeaders().size());
        } else {
            Assert.assertEquals(0, updatePetRestMethod.getMockResponses().size());
        }


        // /pet (POST) - addPet

        RestMethod addPetRestMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("addPet"))
                .findFirst()
                .get();

        Assert.assertNotNull(addPetRestMethod);
        Assert.assertEquals("addPet", addPetRestMethod.getName());
        Assert.assertEquals("/api/v3", addPetRestMethod.getForwardedEndpoint());
        Assert.assertEquals(HttpMethod.POST, addPetRestMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, addPetRestMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, addPetRestMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), addPetRestMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(0L, addPetRestMethod.getNetworkDelay());
        Assert.assertFalse(addPetRestMethod.getSimulateNetworkDelay());
        Assert.assertNull(addPetRestMethod.getDefaultBody());
        Assert.assertNull(addPetRestMethod.getId());
        Assert.assertNull(addPetRestMethod.getResourceId());

        if (generatedResponse) {
            Assert.assertEquals(2, addPetRestMethod.getMockResponses().size());

            // 200
            RestMockResponse response = addPetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Successful operation"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Successful operation", response.getName());

            Assert.assertEquals(Integer.valueOf(200), response.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, response.getStatus());
            Assert.assertTrue(response.isUsingExpressions());
            Assert.assertTrue(response.getContentEncodings().isEmpty());
            Assert.assertNull(response.getId());
            Assert.assertNull(response.getMethodId());

            // 405
            RestMockResponse invalidMockResponse = addPetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid input"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Invalid input", invalidMockResponse.getName());
            Assert.assertNull(invalidMockResponse.getBody());

            Assert.assertEquals(Integer.valueOf(405), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.isUsingExpressions());
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertNull(invalidMockResponse.getId());
            Assert.assertNull(invalidMockResponse.getMethodId());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

        } else {
            Assert.assertEquals(0, addPetRestMethod.getMockResponses().size());
        }

        // /pet/{petId}
        final RestResource petWithParameterResource = restApplication.getResources().stream()
                .filter(resource -> resource.getName().equals("/pet/{petId}"))
                .findFirst()
                .get();

        Assert.assertNotNull(petWithParameterResource);
        Assert.assertEquals("/pet/{petId}", petWithParameterResource.getName());
        Assert.assertEquals("/pet/{petId}", petWithParameterResource.getUri());
        Assert.assertNull(petWithParameterResource.getId());
        Assert.assertNull(petWithParameterResource.getApplicationId());
        Assert.assertNull(petWithParameterResource.getInvokeAddress());

        // /mock/{mockId} (GET) - getMockById

        RestMethod getPetByIdMethod = petWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("getPetById"))
                .findFirst()
                .get();

        Assert.assertNotNull(getPetByIdMethod);
        Assert.assertEquals("getPetById", getPetByIdMethod.getName());
        Assert.assertEquals("/api/v3", getPetByIdMethod.getForwardedEndpoint());
        Assert.assertEquals(HttpMethod.GET, getPetByIdMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, getPetByIdMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, getPetByIdMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), getPetByIdMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(0L, getPetByIdMethod.getNetworkDelay());
        Assert.assertFalse(getPetByIdMethod.getSimulateNetworkDelay());
        Assert.assertNull(getPetByIdMethod.getDefaultBody());
        Assert.assertNull(getPetByIdMethod.getId());
        Assert.assertNull(getPetByIdMethod.getResourceId());

        if (generatedResponse) {
            Assert.assertEquals(3, getPetByIdMethod.getMockResponses().size());

            // 200
            RestMockResponse response = getPetByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation"))
                    .findFirst()
                    .get();

            Assert.assertEquals("successful operation", response.getName());

            Assert.assertEquals(Integer.valueOf(200), response.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, response.getStatus());
            Assert.assertTrue(response.isUsingExpressions());
            Assert.assertTrue(response.getContentEncodings().isEmpty());
            Assert.assertNull(response.getId());
            Assert.assertNull(response.getMethodId());


            // 400
            RestMockResponse invalidMockResponse = getPetByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid ID supplied"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Invalid ID supplied", invalidMockResponse.getName());
            Assert.assertNull(invalidMockResponse.getBody());

            Assert.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.isUsingExpressions());
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertNull(invalidMockResponse.getId());
            Assert.assertNull(invalidMockResponse.getMethodId());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // 404
            RestMockResponse notFoundResponse = getPetByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Pet not found"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Pet not found", notFoundResponse.getName());
            Assert.assertNull(notFoundResponse.getBody());

            Assert.assertEquals(Integer.valueOf(200), notFoundResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, notFoundResponse.getStatus());
            Assert.assertTrue(notFoundResponse.isUsingExpressions());
            Assert.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assert.assertNull(notFoundResponse.getId());
            Assert.assertNull(notFoundResponse.getMethodId());
            Assert.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assert.assertEquals(0, getPetByIdMethod.getMockResponses().size());
        }


        // /pet/{petId} (DELETE) - deletePet

        RestMethod deletePetMethod = petWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("deletePet"))
                .findFirst()
                .get();

        Assert.assertNotNull(deletePetMethod);
        Assert.assertEquals("deletePet", deletePetMethod.getName());
        Assert.assertEquals("/api/v3", deletePetMethod.getForwardedEndpoint());
        Assert.assertEquals(HttpMethod.DELETE, deletePetMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, deletePetMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.SEQUENCE, deletePetMethod.getResponseStrategy());
        Assert.assertEquals(Integer.valueOf(0), deletePetMethod.getCurrentResponseSequenceIndex());
        Assert.assertEquals(0L, deletePetMethod.getNetworkDelay());
        Assert.assertFalse(deletePetMethod.getSimulateNetworkDelay());
        Assert.assertNull(deletePetMethod.getDefaultBody());
        Assert.assertNull(deletePetMethod.getId());
        Assert.assertNull(deletePetMethod.getResourceId());

        if (generatedResponse) {
            Assert.assertEquals(1, deletePetMethod.getMockResponses().size());

            // 400
            RestMockResponse invalidMockResponse = deletePetMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Auto-generated mocked response"))
                    .findFirst()
                    .get();

            Assert.assertEquals("Auto-generated mocked response", invalidMockResponse.getName());
            Assert.assertNull(invalidMockResponse.getBody());

            Assert.assertEquals(Integer.valueOf(200), invalidMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, invalidMockResponse.getStatus());
            Assert.assertTrue(invalidMockResponse.isUsingExpressions());
            Assert.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assert.assertNull(invalidMockResponse.getId());
            Assert.assertNull(invalidMockResponse.getMethodId());
            Assert.assertEquals(0, invalidMockResponse.getHttpHeaders().size());
        } else {
            Assert.assertEquals(0, deletePetMethod.getMockResponses().size());
        }
    }
}