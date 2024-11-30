/*
 * Copyright 2020 Karl Dahlgren
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

package com.castlemock.web.mock.soap.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@JsonDeserialize(builder = LinkWsdlRequest.Builder.class)
public class LinkWsdlRequest {

    private final String url;
    private final Boolean generateResponse;
    private final Boolean includeImports;

    private LinkWsdlRequest(final Builder builder) {
        this.url = Objects.requireNonNull(builder.url, "url");
        this.generateResponse = Objects.requireNonNull(builder.generateResponse, "generateResponse");
        this.includeImports = Objects.requireNonNull(builder.includeImports, "includeImports");
    }

    public String getUrl() {
        return url;
    }

    public Boolean getGenerateResponse() {
        return generateResponse;
    }

    public Boolean getIncludeImports() {
        return includeImports;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final LinkWsdlRequest that = (LinkWsdlRequest) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(generateResponse, that.generateResponse) &&
                Objects.equals(includeImports, that.includeImports);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, generateResponse, includeImports);
    }

    @Override
    public String toString() {
        return "LinkWsdlRequest{" +
                "url='" + url + '\'' +
                ", generateResponse=" + generateResponse +
                ", includeImports=" + includeImports +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String url;
        private Boolean generateResponse;
        private Boolean includeImports;

        private Builder() {
        }

        public Builder url(final String url) {
            this.url = url;
            return this;
        }

        public Builder generateResponse(final Boolean generateResponse) {
            this.generateResponse = generateResponse;
            return this;
        }

        public Builder includeImports(final Boolean includeImports) {
            this.includeImports = includeImports;
            return this;
        }

        public LinkWsdlRequest build() {
            return new LinkWsdlRequest(this);
        }
    }
}
