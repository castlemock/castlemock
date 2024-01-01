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

package com.castlemock.web.mock.rest.model;

import com.castlemock.model.core.http.HttpMethod;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
@XmlRootElement
public class CreateRestMethodRequest {

    private String name;
    private HttpMethod httpMethod;

    private CreateRestMethodRequest(){

    }

    private CreateRestMethodRequest(final Builder builder){
        this.name = Objects.requireNonNull(builder.name, "name");
        this.httpMethod = Objects.requireNonNull(builder.httpMethod, "httpMethod");
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreateRestMethodRequest that = (CreateRestMethodRequest) o;
        return Objects.equals(name, that.name) && httpMethod == that.httpMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, httpMethod);
    }

    @Override
    public String toString() {
        return "CreateRestMethodRequest{" +
                "name='" + name + '\'' +
                ", httpMethod=" + httpMethod +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String name;
        private HttpMethod httpMethod;

        private Builder() {
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder httpMethod(final HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public CreateRestMethodRequest build() {
            return new CreateRestMethodRequest(this);
        }
    }
}
