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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.55
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = ValidateExpressionResponse.Builder.class)
public class ValidateExpressionResponse {

    private final String output;

    private ValidateExpressionResponse(final Builder builder) {
        this.output = Objects.requireNonNull(builder.output, "output");
    }

    public String getOutput() {
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidateExpressionResponse that = (ValidateExpressionResponse) o;
        return Objects.equals(output, that.output);
    }

    @Override
    public int hashCode() {
        return Objects.hash(output);
    }

    @Override
    public String toString() {
        return "ValidateExpressionResponse{" +
                "output='" + output + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private String output;

        private Builder() {
        }

        public Builder output(final String output) {
            this.output = output;
            return this;
        }

        public ValidateExpressionResponse build() {
            return new ValidateExpressionResponse(this);
        }
    }

}
