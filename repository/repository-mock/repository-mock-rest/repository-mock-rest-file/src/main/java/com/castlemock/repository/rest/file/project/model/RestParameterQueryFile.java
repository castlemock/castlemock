package com.castlemock.repository.rest.file.project.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "restParameterQuery")
public class RestParameterQueryFile {

    private String parameter;
    private String query;
    private boolean matchCase;
    private boolean matchAny;
    private boolean matchRegex;
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

    @XmlElement
    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @XmlElement
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @XmlElement
    public boolean getMatchCase() {
        return matchCase;
    }

    public void setMatchCase(boolean matchCase) {
        this.matchCase = matchCase;
    }

    @XmlElement
    public boolean getMatchAny() {
        return matchAny;
    }

    public void setMatchAny(boolean matchAny) {
        this.matchAny = matchAny;
    }

    @XmlElement
    public boolean getMatchRegex() {
        return matchRegex;
    }

    public void setMatchRegex(boolean matchRegex) {
        this.matchRegex = matchRegex;
    }

    @XmlElement
    public boolean getUrlEncoded() {
        return urlEncoded;
    }

    public void setUrlEncoded(boolean urlEncoded) {
        this.urlEncoded = urlEncoded;
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
