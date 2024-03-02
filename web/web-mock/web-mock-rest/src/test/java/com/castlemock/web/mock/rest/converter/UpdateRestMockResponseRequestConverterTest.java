package com.castlemock.web.mock.rest.converter;

import com.castlemock.service.mock.rest.project.input.UpdateRestMockResponseInput;
import com.castlemock.web.mock.rest.model.UpdateRestMockResponseRequest;
import com.castlemock.web.mock.rest.model.UpdateRestMockResponseRequestTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class UpdateRestMockResponseRequestConverterTest {

    @Test
    @DisplayName("To UpdateRestMockResponseInput")
    void testToUpdateRestMockResponseInput() {
        final String projectId = UUID.randomUUID().toString();
        final String applicationId = UUID.randomUUID().toString();
        final String resourceId = UUID.randomUUID().toString();
        final String methodId = UUID.randomUUID().toString();
        final String mockResponseId = UUID.randomUUID().toString();
        final UpdateRestMockResponseRequest request = UpdateRestMockResponseRequestTestBuilder.build();
        final UpdateRestMockResponseInput input = UpdateRestMockResponseRequestConverter.toUpdateRestMockResponseInput(request,
                projectId, applicationId, resourceId, methodId, mockResponseId);

        Assertions.assertNotNull(input);
        Assertions.assertEquals(projectId, input.getProjectId());
        Assertions.assertEquals(applicationId, input.getApplicationId());
        Assertions.assertEquals(resourceId, input.getResourceId());
        Assertions.assertEquals(methodId, input.getMethodId());
        Assertions.assertEquals(request.getName(), input.getName());
        Assertions.assertEquals(request.getBody(), input.getBody());
        Assertions.assertEquals(request.getStatus(), input.getStatus());
        Assertions.assertEquals(request.getHttpHeaders(), input.getHttpHeaders());
        Assertions.assertEquals(request.getHeaderQueries(), input.getHeaderQueries());
        Assertions.assertEquals(request.getContentEncodings(), input.getContentEncodings());
        Assertions.assertEquals(request.getParameterQueries(), input.getParameterQueries());
        Assertions.assertEquals(request.getUsingExpressions(), input.getUsingExpressions());
        Assertions.assertEquals(request.getJsonPathExpressions(), input.getJsonPathExpressions());
        Assertions.assertEquals(request.getHttpStatusCode(), input.getHttpStatusCode());
        Assertions.assertEquals(request.getXpathExpressions(), input.getXpathExpressions());

    }

}
