package com.castlemock.web.mock.rest.converter.wadl;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.mock.rest.model.project.domain.*;
import com.castlemock.web.core.manager.FileManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class WADLRestDefinitionConverterTest {

    @Mock
    private FileManager fileManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private static final String AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME = "Auto-generated mocked response";
    private static final int DEFAULT_RESPONSE_CODE = 200;

    @Test
    public void testConvertGetPath(){
        final List<RestApplication> restApplications = loadApplicationsWithPath("get.wadl", false);
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        verifyApplication(restApplications.get(0),
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
        Assert.assertEquals(applicationName, restApplication.getName());
        Assert.assertNull(restApplication.getId());
        Assert.assertNull(restApplication.getProjectId());
        //Assert.assertNull(restApplication.getStatusCount());

        Assert.assertEquals(1, restApplication.getResources().size());
        RestResource restResource = restApplication.getResources().get(0);

        Assert.assertEquals(resourceName, restResource.getName());
        Assert.assertEquals(resourceUri, restResource.getUri());
        Assert.assertNull(restResource.getId());
        Assert.assertNull(restResource.getApplicationId());
        Assert.assertNull(restResource.getInvokeAddress());
        //Assert.assertNull(restResource.getStatusCount());

        Assert.assertEquals(1, restResource.getMethods().size());
        RestMethod restMethod = restResource.getMethods().get(0);
        Assert.assertEquals(methodName, restMethod.getName());
        Assert.assertEquals(httpMethod, restMethod.getHttpMethod());
        Assert.assertEquals(RestMethodStatus.MOCKED, restMethod.getStatus());
        Assert.assertEquals(RestResponseStrategy.RANDOM, restMethod.getResponseStrategy());
        Assert.assertEquals(0, restMethod.getNetworkDelay());
        Assert.assertFalse(restMethod.getSimulateNetworkDelay());
        Assert.assertNull(restMethod.getId());
        Assert.assertNull(restMethod.getResourceId());
        Assert.assertNull(restMethod.getForwardedEndpoint());
        Assert.assertNull(restMethod.getDefaultBody());

        if(generatedResponse){
            Assert.assertEquals(1, restMethod.getMockResponses().size());
            RestMockResponse restMockResponse = restMethod.getMockResponses().get(0);
            Assert.assertEquals(AUTO_GENERATED_MOCK_RESPONSE_DEFAULT_NAME, restMockResponse.getName());
            Assert.assertEquals(Integer.valueOf(DEFAULT_RESPONSE_CODE), restMockResponse.getHttpStatusCode());
            Assert.assertEquals(RestMockResponseStatus.ENABLED, restMockResponse.getStatus());
            Assert.assertTrue(restMockResponse.getContentEncodings().isEmpty());
            Assert.assertTrue(restMockResponse.getHttpHeaders().isEmpty());
            Assert.assertTrue(restMockResponse.isUsingExpressions());

            Assert.assertNull(restMockResponse.getId());
            Assert.assertNull(restMockResponse.getMethodId());
            Assert.assertNull(restMockResponse.getBody());

        }else {
            Assert.assertEquals(0, restMethod.getMockResponses().size());
        }
    }

    private List<RestApplication> loadApplications(final String path,
                                                   final boolean generatedResponse){
        final WADLRestDefinitionConverter converter = new WADLRestDefinitionConverter(this.fileManager);
        final URL url = WADLRestDefinitionConverter.class.getResource(path);
        final File file;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            Assert.fail(e.getMessage());
            throw new RuntimeException(e);
        }

        return converter.convert(file, generatedResponse);
    }

    private List<RestApplication> loadApplicationsWithPath(final String path,
                                                   final boolean generatedResponse){
        final WADLRestDefinitionConverter converter = new WADLRestDefinitionConverter(this.fileManager);
        final URL url = WADLRestDefinitionConverter.class.getResource(path);
        final File file;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            Assert.fail(e.getMessage());
            throw new RuntimeException(e);
        }

        try {
            Mockito.when(fileManager.uploadFiles(Mockito.anyString())).thenReturn(Collections.singletonList(file));
            Mockito.when(fileManager.deleteFile(Mockito.any(File.class))).thenReturn(true);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
            throw new RuntimeException(e);
        }

        return converter.convert(path, generatedResponse);
    }

}
