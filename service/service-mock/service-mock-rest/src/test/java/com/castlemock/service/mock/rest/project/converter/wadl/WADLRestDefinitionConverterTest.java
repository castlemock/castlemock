package com.castlemock.service.mock.rest.project.converter.wadl;

import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.mock.rest.domain.*;
import com.castlemock.service.core.manager.FileManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class WADLRestDefinitionConverterTest {

    @Mock
    private FileManager fileManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private static final String AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME = "Auto-generated mocked response";
    private static final int DEFAULT_RESPONSE_CODE = 200;

    @Test
    public void testConvertGetPath(){
        final List<RestApplication> restApplications = loadApplicationsWithPath("get.wadl", false);
        verifyApplication(restApplications.getFirst(),
                "get",
                "getUser",
                "/UserService/V1/getUser",
                "getUser",
                HttpMethod.GET,
                false);
    }

    @Test
    public void testConvertGet(){
        final List<RestApplication> restApplications = loadApplications("get.wadl", false);
        verifyApplication(restApplications.getFirst(),
                "get",
                "getUser",
                "/UserService/V1/getUser",
                "getUser",
                HttpMethod.GET,
                false);
    }

    @Test
    public void testConvertPost(){
        final List<RestApplication> restApplications = loadApplications("post.wadl", false);
        verifyApplication(restApplications.getFirst(),
                "post",
                "createUser",
                "/UserService/V1/createUser",
                "createUser",
                HttpMethod.POST,
                false);
    }

    @Test
    public void testConvertPut(){
        final List<RestApplication> restApplications = loadApplications("put.wadl", false);
        verifyApplication(restApplications.getFirst(),
                "put",
                "updateUser",
                "/UserService/V1/updateUser",
                "updateUser",
                HttpMethod.PUT,
                false);
    }

    @Test
    public void testConvertDelete(){
        final List<RestApplication> restApplications = loadApplications("delete.wadl", false);
        verifyApplication(restApplications.getFirst(),
                "delete",
                "deleteUser",
                "/UserService/V1/deleteUser",
                "deleteUser",
                HttpMethod.DELETE,
                false);
    }

    @Test
    public void testConvertHead(){
        final List<RestApplication> restApplications = loadApplications("head.wadl", false);
        verifyApplication(restApplications.getFirst(),
                "head",
                "headUser",
                "/UserService/V1/headUser",
                "headUser",
                HttpMethod.HEAD,
                false);
    }

    @Test
    public void testConvertOptions(){
        final List<RestApplication> restApplications = loadApplications("options.wadl", false);
        verifyApplication(restApplications.getFirst(),
                "options",
                "optionsUser",
                "/UserService/V1/optionsUser",
                "optionsUser",
                HttpMethod.OPTIONS,
                false);
    }

    @Test
    public void testConvertPatch(){
        final List<RestApplication> restApplications = loadApplications("patch.wadl", false);
        verifyApplication(restApplications.getFirst(),
                "patch",
                "patchUser",
                "/UserService/V1/patchUser",
                "patchUser",
                HttpMethod.PATCH,
                false);
    }


    @Test
    public void testConvertTrace(){
        final List<RestApplication> restApplications = loadApplications("trace.wadl", false);
        verifyApplication(restApplications.getFirst(),
                "trace",
                "traceUser",
                "/UserService/V1/traceUser",
                "traceUser",
                HttpMethod.TRACE,
                false);
    }

    @Test
    public void testConvertGetGenerateResult(){
        final List<RestApplication> restApplications = loadApplications("get.wadl", true);
        verifyApplication(restApplications.getFirst(),
                "get",
                "getUser",
                "/UserService/V1/getUser",
                "getUser",
                HttpMethod.GET,
                true);
    }

    @Test
    public void testConvertPostGenerateResult(){
        final List<RestApplication> restApplications = loadApplications("post.wadl", true);
        verifyApplication(restApplications.getFirst(),
                "post",
                "createUser",
                "/UserService/V1/createUser",
                "createUser",
                HttpMethod.POST,
                true);
    }

    @Test
    public void testConvertPutGenerateResult(){
        final List<RestApplication> restApplications = loadApplications("put.wadl", true);
        verifyApplication(restApplications.getFirst(),
                "put",
                "updateUser",
                "/UserService/V1/updateUser",
                "updateUser",
                HttpMethod.PUT,
                true);
    }

    @Test
    public void testConvertDeleteGenerateResult(){
        final List<RestApplication> restApplications = loadApplications("delete.wadl", true);
        verifyApplication(restApplications.getFirst(),
                "delete",
                "deleteUser",
                "/UserService/V1/deleteUser",
                "deleteUser",
                HttpMethod.DELETE,
                true);
    }

    @Test
    public void testConvertHeadGenerateResult(){
        final List<RestApplication> restApplications = loadApplications("head.wadl", true);
        verifyApplication(restApplications.getFirst(),
                "head",
                "headUser",
                "/UserService/V1/headUser",
                "headUser",
                HttpMethod.HEAD,
                true);
    }

    @Test
    public void testConvertOptionsGenerateResult(){
        final List<RestApplication> restApplications = loadApplications("options.wadl", true);
        verifyApplication(restApplications.getFirst(),
                "options",
                "optionsUser",
                "/UserService/V1/optionsUser",
                "optionsUser",
                HttpMethod.OPTIONS,
                true);
    }

    @Test
    public void testConvertPatchGenerateResult(){
        final List<RestApplication> restApplications = loadApplications("patch.wadl", true);
        verifyApplication(restApplications.getFirst(),
                "patch",
                "patchUser",
                "/UserService/V1/patchUser",
                "patchUser",
                HttpMethod.PATCH,
                true);
    }


    @Test
    public void testConvertTraceGenerateResult(){
        final List<RestApplication> restApplications = loadApplications("trace.wadl", true);
        verifyApplication(restApplications.getFirst(),
                "trace",
                "traceUser",
                "/UserService/V1/traceUser",
                "traceUser",
                HttpMethod.TRACE,
                true);
    }

    private void verifyApplication(final RestApplication restApplication,
                                   final String applicationName,
                                   final String resourceName,
                                   final String resourceUri,
                                   final String methodName,
                                   final HttpMethod httpMethod,
                                   final boolean generatedResponse){
        Assertions.assertEquals(applicationName, restApplication.getName());
        //Assertions.assertNull(restApplication.getStatusCount());

        Assertions.assertEquals(1, restApplication.getResources().size());
        RestResource restResource = restApplication.getResources().getFirst();

        Assertions.assertEquals(resourceName, restResource.getName());
        Assertions.assertEquals(resourceUri, restResource.getUri());
        Assertions.assertNull(restResource.getInvokeAddress().orElse(null));
        //Assertions.assertNull(restResource.getStatusCount());

        Assertions.assertEquals(1, restResource.getMethods().size());
        RestMethod restMethod = restResource.getMethods().getFirst();
        Assertions.assertEquals(methodName, restMethod.getName());
        Assertions.assertEquals(httpMethod, restMethod.getHttpMethod());
        Assertions.assertEquals(RestMethodStatus.MOCKED, restMethod.getStatus());
        Assertions.assertEquals(RestResponseStrategy.RANDOM, restMethod.getResponseStrategy());
        Assertions.assertFalse(restMethod.getSimulateNetworkDelay().orElse(false));
        Assertions.assertNull(restMethod.getForwardedEndpoint().orElse(null));
        Assertions.assertNull(restMethod.getDefaultBody().orElse(null));
        Assertions.assertNull(restMethod.getNetworkDelay().orElse(null));

        if(generatedResponse){
            Assertions.assertEquals(1, restMethod.getMockResponses().size());
            RestMockResponse restMockResponse = restMethod.getMockResponses().getFirst();
            Assertions.assertEquals(AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME, restMockResponse.getName());
            Assertions.assertEquals(Integer.valueOf(DEFAULT_RESPONSE_CODE), restMockResponse.getHttpStatusCode());
            Assertions.assertEquals(RestMockResponseStatus.ENABLED, restMockResponse.getStatus());
            Assertions.assertTrue(restMockResponse.getContentEncodings().isEmpty());
            Assertions.assertTrue(restMockResponse.getHttpHeaders().isEmpty());
            Assertions.assertTrue(restMockResponse.getUsingExpressions().orElse(false));

            Assertions.assertNull(restMockResponse.getBody().orElse(null));

        }else {
            Assertions.assertEquals(0, restMethod.getMockResponses().size());
        }
    }

    private List<RestApplication> loadApplications(final String path,
                                                   final boolean generatedResponse){
        final WADLRestDefinitionConverter converter = new WADLRestDefinitionConverter(this.fileManager);
        final URL url = WADLRestDefinitionConverter.class.getResource(path);
        final File file;
        try {
            file = new File(Objects.requireNonNull(url).toURI());
        } catch (URISyntaxException e) {
            Assertions.fail(e.getMessage());
            throw new RuntimeException(e);
        }

        return converter.convert(file, "1", generatedResponse);
    }

    private List<RestApplication> loadApplicationsWithPath(final String path,
                                                   final boolean generatedResponse){
        final WADLRestDefinitionConverter converter = new WADLRestDefinitionConverter(this.fileManager);
        final URL url = WADLRestDefinitionConverter.class.getResource(path);
        final File file;
        try {
            file = new File(Objects.requireNonNull(url).toURI());
        } catch (URISyntaxException e) {
            Assertions.fail(e.getMessage());
            throw new RuntimeException(e);
        }

        try {
            Mockito.when(fileManager.uploadFiles(Mockito.anyString())).thenReturn(Collections.singletonList(file));
            Mockito.when(fileManager.deleteFile(Mockito.any(File.class))).thenReturn(true);
        } catch (IOException | URISyntaxException e) {
            Assertions.fail(e.getMessage());
            throw new RuntimeException(e);
        }

        return converter.convert(path, "1", generatedResponse);
    }

}
