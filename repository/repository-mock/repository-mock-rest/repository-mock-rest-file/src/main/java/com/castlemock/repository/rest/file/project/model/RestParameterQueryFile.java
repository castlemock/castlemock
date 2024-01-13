package com.castlemock.repository.rest.file.project.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "restParameterQuery")
@XmlAccessorType(XmlAccessType.NONE)
public class RestParameterQueryFile {

    @XmlElement
    private String parameter;
    @XmlElement
    private String query;
    @XmlElement
    private boolean matchCase;
    @XmlElement
    private boolean matchAny;
    @XmlElement
    private boolean matchRegex;
    @XmlElement
    private boolean urlEncoded;

    private RestParameterQueryFile() {

    }

    private RestParameterQueryFile(final Builder builder) {
        this.parameter = Objects.requireNonNull(builder.parameter, "parameter");
        this.query = Objects.requireNonNull(builder.query, "query");
        this.matchCase = Objects.requireNonNull(builder.matchCase, "matchCase");
        this.matchAny = Objects.requireNonNull(builder.matchAny, "matchAny");
        this.matchRegex = Objects.requireNonNull(builder.matchRegex, "matchRegex");
        this.urlEncoded = Objects.requireNonNull(builder.urlEncoded, "urlEncoded");
    }

    public String getParameter() {
        return parameter;
    }

    public String getQuery() {
        return query;
    }

    public boolean getMatchCase() {
        return matchCase;
    }

    public boolean getMatchAny() {
        return matchAny;
    }

    public boolean getMatchRegex() {
        return matchRegex;
    }

    public boolean getUrlEncoded() {
        return urlEncoded;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String parameter;
        private String query;
        private Boolean matchCase;
        private Boolean matchAny;
        private Boolean matchRegex;
        private Boolean urlEncoded;

        private Builder() {
        }

        public Builder parameter(String parameter) {
            this.parameter = parameter;
            return this;
        }

        public Builder query(String query) {
            this.query = query;
            return this;
        }

        public Builder matchCase(Boolean matchCase) {
            this.matchCase = matchCase;
            return this;
        }

        public Builder matchAny(Boolean matchAny) {
            this.matchAny = matchAny;
            return this;
        }

        public Builder matchRegex(Boolean matchRegex) {
            this.matchRegex = matchRegex;
            return this;
        }

        public Builder urlEncoded(Boolean urlEncoded) {
            this.urlEncoded = urlEncoded;
            return this;
        }

        public RestParameterQueryFile build() {
            return new RestParameterQueryFile(this);
        }
    }
}
