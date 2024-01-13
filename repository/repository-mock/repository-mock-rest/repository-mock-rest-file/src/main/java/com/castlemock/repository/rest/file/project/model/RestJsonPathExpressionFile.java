package com.castlemock.repository.rest.file.project.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "restJsonPathExpression")
@XmlAccessorType(XmlAccessType.NONE)
public class RestJsonPathExpressionFile {

    @XmlElement
    private String expression;

    private RestJsonPathExpressionFile() {

    }

    private RestJsonPathExpressionFile(final Builder builder) {
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

        public RestJsonPathExpressionFile build() {
            return new RestJsonPathExpressionFile(this);
        }
    }
}