package com.castlemock.model.mock.rest.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class RestParameterQueryMatch {

    private RestParameterQuery query;
    private String match;

    public RestParameterQueryMatch(){

    }

    private RestParameterQueryMatch(final Builder builder){
        this.query = Objects.requireNonNull(builder.query);
        this.match = Objects.requireNonNull(builder.match);
    }

    @XmlElement
    public RestParameterQuery getQuery() {
        return query;
    }

    public void setQuery(RestParameterQuery query) {
        this.query = query;
    }

    @XmlElement
    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestParameterQueryMatch that = (RestParameterQueryMatch) o;
        return Objects.equals(query, that.query) &&
                Objects.equals(match, that.match);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, match);
    }

    @Override
    public String toString() {
        return "RestParameterQueryMatch{" +
                "query=" + query +
                ", match='" + match + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private RestParameterQuery query;
        private String match;

        private Builder() {
        }

        public Builder query(final RestParameterQuery query) {
            this.query = query;
            return this;
        }

        public Builder match(final String match) {
            this.match = match;
            return this;
        }

        public RestParameterQueryMatch build() {
            return new RestParameterQueryMatch(this);
        }
    }
}
