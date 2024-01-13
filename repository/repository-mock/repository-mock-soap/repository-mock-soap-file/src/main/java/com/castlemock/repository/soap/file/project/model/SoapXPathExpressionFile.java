package com.castlemock.repository.soap.file.project.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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