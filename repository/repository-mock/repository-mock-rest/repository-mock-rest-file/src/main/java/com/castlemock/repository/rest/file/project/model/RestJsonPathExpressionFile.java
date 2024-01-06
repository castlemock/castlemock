package com.castlemock.repository.rest.file.project.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "restJsonPathExpression")
public class RestJsonPathExpressionFile {

    private String expression;

    private RestJsonPathExpressionFile() {

    }

    private RestJsonPathExpressionFile(final Builder builder) {
        this.expression = Objects.requireNonNull(builder.expression, "expression");
    }

    @XmlElement
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
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