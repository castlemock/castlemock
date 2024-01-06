package com.castlemock.repository.soap.file.project.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "soapXPathExpression")
public class SoapXPathExpressionFile {

    private String expression;

    private SoapXPathExpressionFile() {

    }

    private SoapXPathExpressionFile(final Builder builder) {
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

        public SoapXPathExpressionFile build() {
            return new SoapXPathExpressionFile(this);
        }
    }
}