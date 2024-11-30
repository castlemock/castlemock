/*
 * Copyright 2024 Karl Dahlgren
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

package com.castlemock.repository.rest.file.project.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
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
