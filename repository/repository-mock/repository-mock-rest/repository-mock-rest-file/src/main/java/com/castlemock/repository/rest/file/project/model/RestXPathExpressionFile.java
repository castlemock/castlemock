package com.castlemock.repository.rest.file.project.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "restXPathExpression")
@XmlAccessorType(XmlAccessType.NONE)
public class RestXPathExpressionFile {

    @XmlElement
    private String expression;

    private RestXPathExpressionFile() {

    }

    private RestXPathExpressionFile(final Builder builder) {
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

        public RestXPathExpressionFile build() {
            return new RestXPathExpressionFile(this);
        }
    }
}