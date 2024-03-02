package com.castlemock.service.mock.rest.project.converter.openapi;

import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.model.mock.rest.domain.RestResponseStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class OpenApiRestDefinitionConverterTest {

    final OpenApiRestDefinitionConverter converter = new OpenApiRestDefinitionConverter();

    @Test
    public void shouldConvertToRestApplications_givenFile() throws URISyntaxException {
        final URL url = OpenApiRestDefinitionConverter.class.getResource("petstore.json");
        final File file = new File(Objects.requireNonNull(url).toURI());
        final List<RestApplication> restApplications = converter.convert(file, "1", false);
        this.verifyResult(restApplications, false);
    }

    @Test
    public void shouldConvertToRestApplications_givenFile_WithGenerateResponseTrue() throws URISyntaxException {
        final URL url = OpenApiRestDefinitionConverter.class.getResource("petstore.json");
        final File file = new File(Objects.requireNonNull(url).toURI());
        final List<RestApplication> restApplications = converter.convert(file, "1", true);
        this.verifyResult(restApplications, true);
    }

    @Test
    public void shouldThrowError_givenOpenAPINull() throws URISyntaxException {
        final URL url = OpenApiRestDefinitionConverter.class.getResource("petstore_empty.json");
        final File file = new File(Objects.requireNonNull(url).toURI());
        IllegalArgumentException actualException = Assertions.assertThrows(IllegalArgumentException.class, () -> converter.convert(file, "1", false));
        String expectedExceptionMessage = "Unable to parse the OpenApi content.";
        Assertions.assertEquals(actualException.getMessage(), expectedExceptionMessage);
    }

    @Test
    public void shouldConvertToRestApplications_givenMalformedFile() throws URISyntaxException {
        final URL url = OpenApiRestDefinitionConverter.class.getResource("petstore_malformed_openapi.json");
        final File file = new File(Objects.requireNonNull(url).toURI());
        final List<RestApplication> restApplications = converter.convert(file, "1", true);
        this.verifyResultForMalformedFile(restApplications, true);
    }

    private void verifyResult(final List<RestApplication> restApplications,
                              final boolean generatedResponse) {

        Assertions.assertNotNull(restApplications);
        Assertions.assertEquals(1, restApplications.size());

        final RestApplication restApplication = restApplications.getFirst();

        Assertions.assertEquals("Swagger Petstore - OpenAPI 3.0", restApplication.getName());
        Assertions.assertEquals(2, restApplication.getResources().size());


        // /pet
        final RestResource mockResource = restApplication.getResources().stream()
                .filter(resource -> resource.getName().equals("/pet"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(mockResource);
        Assertions.assertEquals("/pet", mockResource.getName());
        Assertions.assertEquals("/pet", mockResource.getUri());

        // /pet (PUT) - updatePet

        RestMethod updatePetRestMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("updatePet"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(updatePetRestMethod);
        Assertions.assertEquals("updatePet", updatePetRestMethod.getName());
        Assertions.assertEquals("/api/v3", updatePetRestMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertEquals(HttpMethod.PUT, updatePetRestMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, updatePetRestMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.SEQUENCE, updatePetRestMethod.getResponseStrategy());
        Assertions.assertEquals(Integer.valueOf(0), updatePetRestMethod.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(Long.valueOf(0L), updatePetRestMethod.getNetworkDelay().orElse(null));
        Assertions.assertFalse(updatePetRestMethod.getSimulateNetworkDelay().orElse(false));

        if (generatedResponse) {
            Assertions.assertEquals(4, updatePetRestMethod.getMockResponses().size());

            // 200
            RestMockResponse response = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Successful operation"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(response);
            Assertions.assertEquals("Successful operation", response.getName());

            Assertions.assertEquals(Integer.valueOf(200), response.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.ENABLED, response.getStatus());
            Assertions.assertTrue(response.getUsingExpressions().orElse(false));
            Assertions.assertTrue(response.getContentEncodings().isEmpty());


            // 400
            RestMockResponse invalidMockResponse = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid ID supplied"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(invalidMockResponse);
            Assertions.assertEquals("Invalid ID supplied", invalidMockResponse.getName());
            Assertions.assertNull(invalidMockResponse.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assertions.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // 404
            RestMockResponse notFoundResponse = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Pet not found"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(notFoundResponse);
            Assertions.assertEquals("Pet not found", notFoundResponse.getName());
            Assertions.assertNull(notFoundResponse.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assertions.assertTrue(notFoundResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, notFoundResponse.getHttpHeaders().size());

            // 405
            RestMockResponse validationExceptionResponse = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Validation exception"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(validationExceptionResponse);
            Assertions.assertEquals("Validation exception", validationExceptionResponse.getName());
            Assertions.assertNull(validationExceptionResponse.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(405), validationExceptionResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, validationExceptionResponse.getStatus());
            Assertions.assertTrue(validationExceptionResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(validationExceptionResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, validationExceptionResponse.getHttpHeaders().size());
        } else {
            Assertions.assertEquals(0, updatePetRestMethod.getMockResponses().size());
        }


        // /pet (POST) - addPet

        RestMethod addPetRestMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("addPet"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(addPetRestMethod);
        Assertions.assertEquals("addPet", addPetRestMethod.getName());
        Assertions.assertEquals("/api/v3", addPetRestMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertEquals(HttpMethod.POST, addPetRestMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, addPetRestMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.SEQUENCE, addPetRestMethod.getResponseStrategy());
        Assertions.assertEquals(Integer.valueOf(0), addPetRestMethod.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(Long.valueOf(0L), addPetRestMethod.getNetworkDelay().orElse(null));
        Assertions.assertFalse(addPetRestMethod.getSimulateNetworkDelay().orElse(false));

        if (generatedResponse) {
            Assertions.assertEquals(2, addPetRestMethod.getMockResponses().size());

            // 200
            RestMockResponse response = addPetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Successful operation"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(response);
            Assertions.assertEquals("Successful operation", response.getName());

            Assertions.assertEquals(Integer.valueOf(200), response.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.ENABLED, response.getStatus());
            Assertions.assertTrue(response.getUsingExpressions().orElse(false));
            Assertions.assertTrue(response.getContentEncodings().isEmpty());

            // 405
            RestMockResponse invalidMockResponse = addPetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid input"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(invalidMockResponse);
            Assertions.assertEquals("Invalid input", invalidMockResponse.getName());
            Assertions.assertNull(invalidMockResponse.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(405), invalidMockResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assertions.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

        } else {
            Assertions.assertEquals(0, addPetRestMethod.getMockResponses().size());
        }

        // /pet/{petId}
        final RestResource petWithParameterResource = restApplication.getResources().stream()
                .filter(resource -> resource.getName().equals("/pet/{petId}"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(petWithParameterResource);
        Assertions.assertEquals("/pet/{petId}", petWithParameterResource.getName());
        Assertions.assertEquals("/pet/{petId}", petWithParameterResource.getUri());
        Assertions.assertNull(petWithParameterResource.getInvokeAddress().orElse(null));

        // /mock/{mockId} (GET) - getMockById

        RestMethod getPetByIdMethod = petWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("getPetById"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(getPetByIdMethod);
        Assertions.assertEquals("getPetById", getPetByIdMethod.getName());
        Assertions.assertEquals("/api/v3", getPetByIdMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertEquals(HttpMethod.GET, getPetByIdMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, getPetByIdMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.SEQUENCE, getPetByIdMethod.getResponseStrategy());
        Assertions.assertEquals(Integer.valueOf(0), getPetByIdMethod.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(Long.valueOf(0L), getPetByIdMethod.getNetworkDelay().orElse(null));
        Assertions.assertFalse(getPetByIdMethod.getSimulateNetworkDelay().orElse(false));
        Assertions.assertNull(getPetByIdMethod.getDefaultBody().orElse(null));

        if (generatedResponse) {
            Assertions.assertEquals(3, getPetByIdMethod.getMockResponses().size());

            // 200
            RestMockResponse response = getPetByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(response);
            Assertions.assertEquals("successful operation", response.getName());
            Assertions.assertEquals(Integer.valueOf(200), response.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.ENABLED, response.getStatus());
            Assertions.assertTrue(response.getUsingExpressions().orElse(false));
            Assertions.assertTrue(response.getContentEncodings().isEmpty());

            // 400
            RestMockResponse invalidMockResponse = getPetByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid ID supplied"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(invalidMockResponse);
            Assertions.assertEquals("Invalid ID supplied", invalidMockResponse.getName());
            Assertions.assertNull(invalidMockResponse.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assertions.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // 404
            RestMockResponse notFoundResponse = getPetByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Pet not found"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(notFoundResponse);
            Assertions.assertEquals("Pet not found", notFoundResponse.getName());
            Assertions.assertNull(notFoundResponse.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assertions.assertTrue(notFoundResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assertions.assertEquals(0, getPetByIdMethod.getMockResponses().size());
        }


        // /pet/{petId} (DELETE) - deletePet

        RestMethod deletePetMethod = petWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("deletePet"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(deletePetMethod);
        Assertions.assertEquals("deletePet", deletePetMethod.getName());
        Assertions.assertEquals("/api/v3", deletePetMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertEquals(HttpMethod.DELETE, deletePetMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, deletePetMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.SEQUENCE, deletePetMethod.getResponseStrategy());
        Assertions.assertEquals(Integer.valueOf(0), deletePetMethod.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(Long.valueOf(0L), deletePetMethod.getNetworkDelay().orElse(null));
        Assertions.assertFalse(deletePetMethod.getSimulateNetworkDelay().orElse(false));
        Assertions.assertNull(deletePetMethod.getDefaultBody().orElse(null));

        if (generatedResponse) {
            Assertions.assertEquals(1, deletePetMethod.getMockResponses().size());

            // 400
            RestMockResponse invalidMockResponse = deletePetMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid pet value"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(invalidMockResponse);
            Assertions.assertEquals("Invalid pet value", invalidMockResponse.getName());
            Assertions.assertNull(invalidMockResponse.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assertions.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, invalidMockResponse.getHttpHeaders().size());
        } else {
            Assertions.assertEquals(0, deletePetMethod.getMockResponses().size());
        }
    }

    private void verifyResultForMalformedFile(final List<RestApplication> restApplications,
                              final boolean generatedResponse) {

        Assertions.assertNotNull(restApplications);
        Assertions.assertEquals(1, restApplications.size());

        final RestApplication restApplication = restApplications.getFirst();

        Assertions.assertEquals("/api/v3", restApplication.getName());
        Assertions.assertEquals(2, restApplication.getResources().size());


        // /pet
        final RestResource mockResource = restApplication.getResources().stream()
                .filter(resource -> resource.getName().equals("/pet"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(mockResource);
        Assertions.assertEquals("/pet", mockResource.getName());
        Assertions.assertEquals("/pet", mockResource.getUri());
        Assertions.assertNull(mockResource.getInvokeAddress().orElse(null));

        // /pet (PUT) - updatePet

        RestMethod updatePetRestMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("Update an existing pet"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(updatePetRestMethod);
        Assertions.assertEquals("Update an existing pet", updatePetRestMethod.getName());
        Assertions.assertEquals("/api/v3", updatePetRestMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertEquals(HttpMethod.PUT, updatePetRestMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, updatePetRestMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.SEQUENCE, updatePetRestMethod.getResponseStrategy());
        Assertions.assertEquals(Integer.valueOf(0), updatePetRestMethod.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(Long.valueOf(0L), updatePetRestMethod.getNetworkDelay().orElse(null));
        Assertions.assertFalse(updatePetRestMethod.getSimulateNetworkDelay().orElse(false));

        if (generatedResponse) {
            Assertions.assertEquals(4, updatePetRestMethod.getMockResponses().size());

            // 200
            RestMockResponse response = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Successful operation"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(response);
            Assertions.assertEquals("Successful operation", response.getName());

            Assertions.assertEquals(Integer.valueOf(200), response.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.ENABLED, response.getStatus());
            Assertions.assertTrue(response.getUsingExpressions().orElse(false));
            Assertions.assertTrue(response.getContentEncodings().isEmpty());


            // 400
            RestMockResponse invalidMockResponse = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid ID supplied"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(invalidMockResponse);
            Assertions.assertEquals("Invalid ID supplied", invalidMockResponse.getName());
            Assertions.assertNull(invalidMockResponse.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assertions.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

            // 404
            RestMockResponse notFoundResponse = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Pet not found"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(notFoundResponse);
            Assertions.assertEquals("Pet not found", notFoundResponse.getName());
            Assertions.assertNull(notFoundResponse.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(404), notFoundResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, notFoundResponse.getStatus());
            Assertions.assertTrue(notFoundResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, notFoundResponse.getHttpHeaders().size());

            // 405
            RestMockResponse validationExceptionResponse = updatePetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Validation exception"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(validationExceptionResponse);
            Assertions.assertEquals("Validation exception", validationExceptionResponse.getName());
            Assertions.assertNull(validationExceptionResponse.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(405), validationExceptionResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, validationExceptionResponse.getStatus());
            Assertions.assertTrue(validationExceptionResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(validationExceptionResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, validationExceptionResponse.getHttpHeaders().size());
        } else {
            Assertions.assertEquals(0, updatePetRestMethod.getMockResponses().size());
        }


        // /pet (POST) - addPet

        RestMethod addPetRestMethod = mockResource.getMethods().stream()
                .filter(method -> method.getName().equals("addPet"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(addPetRestMethod);
        Assertions.assertEquals("addPet", addPetRestMethod.getName());
        Assertions.assertEquals("/api/v3", addPetRestMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertEquals(HttpMethod.POST, addPetRestMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, addPetRestMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.SEQUENCE, addPetRestMethod.getResponseStrategy());
        Assertions.assertEquals(Integer.valueOf(0), addPetRestMethod.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(Long.valueOf(0L), addPetRestMethod.getNetworkDelay().orElse(null));
        Assertions.assertFalse(addPetRestMethod.getSimulateNetworkDelay().orElse(false));
        Assertions.assertNull(addPetRestMethod.getDefaultBody().orElse(null));

        if (generatedResponse) {
            Assertions.assertEquals(2, addPetRestMethod.getMockResponses().size());

            // 200
            RestMockResponse response = addPetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Successful operation"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(response);
            Assertions.assertEquals("Successful operation", response.getName());

            Assertions.assertEquals(Integer.valueOf(200), response.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.ENABLED, response.getStatus());
            Assertions.assertTrue(response.getUsingExpressions().orElse(false));
            Assertions.assertTrue(response.getContentEncodings().isEmpty());

            // 405
            RestMockResponse invalidMockResponse = addPetRestMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid input"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(invalidMockResponse);
            Assertions.assertEquals("Invalid input", invalidMockResponse.getName());
            Assertions.assertNull(invalidMockResponse.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(405), invalidMockResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assertions.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, invalidMockResponse.getHttpHeaders().size());

        } else {
            Assertions.assertEquals(0, addPetRestMethod.getMockResponses().size());
        }

        // /pet/{petId}
        final RestResource petWithParameterResource = restApplication.getResources().stream()
                .filter(resource -> resource.getName().equals("/pet/{petId}"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(petWithParameterResource);
        Assertions.assertEquals("/pet/{petId}", petWithParameterResource.getName());
        Assertions.assertEquals("/pet/{petId}", petWithParameterResource.getUri());
        Assertions.assertNull(petWithParameterResource.getInvokeAddress().orElse(null));

        // /mock/{mockId} (GET) - getMockById

        RestMethod getPetByIdMethod = petWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("getPetById"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(getPetByIdMethod);
        Assertions.assertEquals("getPetById", getPetByIdMethod.getName());
        Assertions.assertEquals("/api/v3", getPetByIdMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertEquals(HttpMethod.GET, getPetByIdMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, getPetByIdMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.SEQUENCE, getPetByIdMethod.getResponseStrategy());
        Assertions.assertEquals(Integer.valueOf(0), getPetByIdMethod.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(Long.valueOf(0L), getPetByIdMethod.getNetworkDelay().orElse(null));
        Assertions.assertFalse(getPetByIdMethod.getSimulateNetworkDelay().orElse(false));
        Assertions.assertNull(getPetByIdMethod.getDefaultBody().orElse(null));

        if (generatedResponse) {
            Assertions.assertEquals(3, getPetByIdMethod.getMockResponses().size());

            // 200
            RestMockResponse response = getPetByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("successful operation"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(response);
            Assertions.assertEquals("successful operation", response.getName());

            Assertions.assertEquals(Integer.valueOf(200), response.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.ENABLED, response.getStatus());
            Assertions.assertTrue(response.getUsingExpressions().orElse(false));
            Assertions.assertTrue(response.getContentEncodings().isEmpty());


            // 400
            RestMockResponse invalidMockResponse = getPetByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Invalid ID supplied"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(invalidMockResponse);
            Assertions.assertEquals("Invalid ID supplied", invalidMockResponse.getName());
            Assertions.assertNull(invalidMockResponse.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(400), invalidMockResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.DISABLED, invalidMockResponse.getStatus());
            Assertions.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());

            // 404
            RestMockResponse notFoundResponse = getPetByIdMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Pet not found"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(notFoundResponse);
            Assertions.assertEquals("Pet not found", notFoundResponse.getName());
            Assertions.assertNull(notFoundResponse.getBody().orElse(null));

            Assertions.assertEquals(Integer.valueOf(200), notFoundResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.ENABLED, notFoundResponse.getStatus());
            Assertions.assertTrue(notFoundResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(notFoundResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, notFoundResponse.getHttpHeaders().size());
        } else {
            Assertions.assertEquals(0, getPetByIdMethod.getMockResponses().size());
        }


        // /pet/{petId} (DELETE) - deletePet

        RestMethod deletePetMethod = petWithParameterResource.getMethods().stream()
                .filter(method -> method.getName().equals("deletePet"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(deletePetMethod);
        Assertions.assertEquals("deletePet", deletePetMethod.getName());
        Assertions.assertEquals("/api/v3", deletePetMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertEquals(HttpMethod.DELETE, deletePetMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, deletePetMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.SEQUENCE, deletePetMethod.getResponseStrategy());
        Assertions.assertEquals(Integer.valueOf(0), deletePetMethod.getCurrentResponseSequenceIndex());
        Assertions.assertEquals(Long.valueOf(0L), deletePetMethod.getNetworkDelay().orElse(null));
        Assertions.assertFalse(deletePetMethod.getSimulateNetworkDelay().orElse(false));
        Assertions.assertNull(deletePetMethod.getDefaultBody().orElse(null));

        if (generatedResponse) {
            Assertions.assertEquals(1, deletePetMethod.getMockResponses().size());

            // 400
            RestMockResponse invalidMockResponse = deletePetMethod.getMockResponses().stream()
                    .filter(method -> method.getName().equals("Auto-generated mocked response"))
                    .findFirst()
                    .orElse(null);

            Assertions.assertNotNull(invalidMockResponse);
            Assertions.assertEquals("Auto-generated mocked response", invalidMockResponse.getName());

            Assertions.assertEquals(Integer.valueOf(200), invalidMockResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.ENABLED, invalidMockResponse.getStatus());
            Assertions.assertTrue(invalidMockResponse.getUsingExpressions().orElse(false));
            Assertions.assertTrue(invalidMockResponse.getContentEncodings().isEmpty());
            Assertions.assertEquals(0, invalidMockResponse.getHttpHeaders().size());
        } else {
            Assertions.assertEquals(0, deletePetMethod.getMockResponses().size());
        }
    }
}