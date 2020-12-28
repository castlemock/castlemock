package com.castlemock.model.mock.rest.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class RestParameterQuery {

    private String parameter;
    private String query;
    private boolean matchCase;
    private boolean matchAny;
    private boolean matchRegex;

    public RestParameterQuery(){

    }

    private RestParameterQuery(final Builder builder){
        this.parameter = Objects.requireNonNull(builder.parameter);
        this.query = Objects.requireNonNull(builder.query);
        this.matchCase = Objects.requireNonNull(builder.matchCase);
        this.matchAny = Objects.requireNonNull(builder.matchAny);
        this.matchRegex = Objects.requireNonNull(builder.matchRegex);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestParameterQuery that = (RestParameterQuery) o;
        return matchCase == that.matchCase &&
                matchAny == that.matchAny &&
                matchRegex == that.matchRegex &&
                Objects.equals(parameter, that.parameter) &&
                Objects.equals(query, that.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameter, query, matchCase, matchAny, matchRegex);
    }

    @Override
    public String toString() {
        return "RestParameterQuery{" +
                "parameter='" + parameter + '\'' +
                ", query='" + query + '\'' +
                ", matchCase=" + matchCase +
                ", matchAny=" + matchAny +
                ", matchRegex=" + matchRegex +
                '}';
    }

    public Builder toBuilder() {
        return builder()
                .parameter(parameter)
                .query(query)
                .matchCase(matchCase)
                .matchAny(matchAny)
                .matchRegex(matchRegex);
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

        private Builder() {
        }

        public Builder parameter(final String parameter) {
            this.parameter = parameter;
            return this;
        }

        public Builder query(final String query) {
            this.query = query;
            return this;
        }

        public Builder matchCase(final Boolean matchCase) {
            this.matchCase = matchCase;
            return this;
        }

        public Builder matchAny(final Boolean matchAny) {
            this.matchAny = matchAny;
            return this;
        }

        public Builder matchRegex(final Boolean matchRegex) {
            this.matchRegex = matchRegex;
            return this;
        }

        public RestParameterQuery build() {
            return new RestParameterQuery(this);
        }
    }
}
