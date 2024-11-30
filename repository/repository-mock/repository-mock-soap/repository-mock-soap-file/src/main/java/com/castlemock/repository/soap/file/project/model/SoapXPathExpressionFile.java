/*
 * Copyright 2024 Karl Dahlgren
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

package com.castlemock.repository.soap.file.project.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "soapXPathExpression")
@XmlAccessorType(XmlAccessType.NONE)
public class SoapXPathExpressionFile {

    @XmlElement
    private String expression;

    private SoapXPathExpressionFile() {

    }

    private SoapXPathExpressionFile(final Builder builder) {
        this.expression = Objects.requireNonNull(builder.expression, "expression");
    }

    public String getExpression() {
        return expression;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private String expression;

        private Builder() {
        }

        public Builder expression(String expression) {
            this.expression = expression;
            return this;
        }

        public SoapXPathExpressionFile build() {
            return new SoapXPathExpressionFile(this);
        }
    }
}