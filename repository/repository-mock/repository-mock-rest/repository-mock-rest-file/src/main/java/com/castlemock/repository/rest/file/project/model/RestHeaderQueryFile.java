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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "restHeaderQuery")
@XmlAccessorType(XmlAccessType.NONE)
public class RestHeaderQueryFile {

    @XmlElement
    private String header;
    @XmlElement
    private String query;
    @XmlElement
    private boolean matchCase;
    @XmlElement
    private boolean matchAny;
    @XmlElement
    private boolean matchRegex;

    private RestHeaderQueryFile() {

    }

    private RestHeaderQueryFile(final Builder builder) {
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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String header;
        private String query;
        private Boolean matchCase;
        private Boolean matchAny;
        private Boolean matchRegex;

        private Builder() {
        }

        public Builder header(String header) {
            this.header = header;
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

        public RestHeaderQueryFile build() {
            return new RestHeaderQueryFile(this);
        }
    }
}