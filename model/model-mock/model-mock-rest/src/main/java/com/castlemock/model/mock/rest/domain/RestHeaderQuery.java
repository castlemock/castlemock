/*
 * Copyright 2019 Karl Dahlgren
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

package com.castlemock.model.mock.rest.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = RestHeaderQuery.Builder.class)
public class RestHeaderQuery {

    @XmlElement
    private final String header;

    @XmlElement
    private final String query;

    @XmlElement
    private final boolean matchCase;

    @XmlElement
    private final boolean matchAny;

    @XmlElement
    private final boolean matchRegex;


    private RestHeaderQuery(final Builder builder){
        this.header = Objects.requireNonNull(builder.header, "header");
        this.query = Objects.requireNonNull(builder.query, "query");
        this.matchCase = Objects.requireNonNull(builder.matchCase, "matchCase");
        this.matchAny = Objects.requireNonNull(builder.matchAny, "matchAny");
        this.matchRegex = Objects.requireNonNull(builder.matchRegex, "matchRegex");
    }

    public String getHeader() {
        return header;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestHeaderQuery that = (RestHeaderQuery) o;
        return matchCase == that.matchCase &&
                matchAny == that.matchAny &&
                matchRegex == that.matchRegex &&
                Objects.equals(header, that.header) &&
                Objects.equals(query, that.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, query, matchCase, matchAny, matchRegex);
    }

    @Override
    public String toString() {
        return "RestHeaderQuery{" +
                "header='" + header + '\'' +
                ", query='" + query + '\'' +
                ", matchCase=" + matchCase +
                ", matchAny=" + matchAny +
                ", matchRegex=" + matchRegex +
                '}';
    }

    public Builder toBuilder() {
        return builder()
                .header(header)
                .query(query)
                .matchCase(matchCase)
                .matchAny(matchAny)
                .matchRegex(matchRegex);
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String header;
        private String query;
        private Boolean matchCase;
        private Boolean matchAny;
        private Boolean matchRegex;

        private Builder() {
        }

        public Builder header(final String header) {
            this.header = header;
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

        public RestHeaderQuery build() {
            return new RestHeaderQuery(this);
        }
    }
}
