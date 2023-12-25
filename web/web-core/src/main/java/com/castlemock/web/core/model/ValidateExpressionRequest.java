/*
 * Copyright 2021 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.web.core.model;

import com.castlemock.model.mock.soap.domain.SoapExpressionType;

import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.55
 */
public class ValidateExpressionRequest {

    private SoapExpressionType expressionType;
    private String requestBody;
    private String responseBody;

    private ValidateExpressionRequest() {

    }

    private ValidateExpressionRequest(final Builder builder) {
        this.expressionType = Objects.requireNonNull(builder.expressionType);
        this.responseBody = Objects.requireNonNull(builder.responseBody);
        this.requestBody = Objects.requireNonNull(builder.requestBody);
    }


    public SoapExpressionType getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(SoapExpressionType expressionType) {
        this.expressionType = expressionType;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidateExpressionRequest that = (ValidateExpressionRequest) o;
        return Objects.equals(expressionType, that.expressionType) &&  Objects.equals(requestBody, that.requestBody) && Objects.equals(responseBody, that.responseBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestBody, responseBody);
    }

    @Override
    public String toString() {
        return "ValidateExpressionRequest{" +
                "expressionType='" + expressionType + '\'' +
                ". requestBody='" + requestBody + '\'' +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }

    public static final class Builder {

        private SoapExpressionType expressionType;
        private String requestBody;
        private String responseBody;

        private Builder() {
        }

        public Builder expressionType(final SoapExpressionType expressionType) {
            this.expressionType = expressionType;
            return this;
        }

        public Builder requestBody(final String requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public Builder responseBody(final String responseBody) {
            this.responseBody = responseBody;
            return this;
        }

        public ValidateExpressionRequest build() {
            return new ValidateExpressionRequest(this);
        }
    }

}
