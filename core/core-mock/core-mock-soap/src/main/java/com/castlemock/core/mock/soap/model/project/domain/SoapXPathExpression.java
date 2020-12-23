/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.core.mock.soap.model.project.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class SoapXPathExpression {

    private String expression;

    public SoapXPathExpression(){

    }

    private SoapXPathExpression(final Builder builder){
        this.expression = Objects.requireNonNull(builder.expression);
    }

    @XmlElement
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Builder toBuilder(){
        return builder()
                .expression(expression);
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

        public SoapXPathExpression build() {
            return new SoapXPathExpression(this);
        }
    }
}
