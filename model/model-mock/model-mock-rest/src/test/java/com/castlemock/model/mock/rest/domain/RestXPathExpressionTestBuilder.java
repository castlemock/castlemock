package com.castlemock.model.mock.rest.domain;

public final class RestXPathExpressionTestBuilder {

    private RestXPathExpressionTestBuilder() {
    }

    public static RestXPathExpression.Builder builder(){
        return RestXPathExpression
                .builder()
                .expression("//GetWhoISResponse/GetWhoISResult[text()='google.com is a good domain']");
    }

    public static RestXPathExpression build() {
        return builder().build();
    }
}
