package com.castlemock.web.core.controller.rest;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.service.core.expression.input.ValidateExpressionInput;
import com.castlemock.service.core.expression.output.ValidateExpressionOutput;
import com.castlemock.web.core.model.ValidateExpressionRequest;
import com.castlemock.web.core.model.ValidateExpressionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ExpressionRestControllerTest {


    @Test
    @DisplayName("Validate expression")
    void testValidateExpression() {
        final ServiceProcessor serviceProcessor = Mockito.mock(ServiceProcessor.class);
        final ExpressionRestController controller = new ExpressionRestController(serviceProcessor);
        final String requestBody = "Request body";
        final String responseBody = "Response body";
        final String output = "Output";

        Mockito.when(serviceProcessor.process(Mockito.any())).thenReturn(ValidateExpressionOutput.builder()
                .output(output)
                .build());

        final ResponseEntity<ValidateExpressionResponse> response = controller.validateExpression(ValidateExpressionRequest.builder()
                .requestBody(requestBody)
                .responseBody(responseBody)
                .build());

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(response.getBody().getOutput(), output);

        Mockito.verify(serviceProcessor, Mockito.times(1)).process(ValidateExpressionInput.builder()
                .requestBody(requestBody)
                .responseBody(responseBody)
                .build());
        Mockito.verifyNoMoreInteractions(serviceProcessor);
    }

}
