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

package com.castlemock.web.mock.rest.web.rest.controller.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.52
 */
@XmlRootElement
public class UpdateRestResourceRequest {

    private String name;
    private String uri;

    public UpdateRestResourceRequest(){

    }

    private UpdateRestResourceRequest(final Builder builder){
        this.name = Objects.requireNonNull(builder.name);
        this.uri = Objects.requireNonNull(builder.uri);
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @XmlElement
    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateRestResourceRequest that = (UpdateRestResourceRequest) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, uri);
    }

    @Override
    public String toString() {
        return "UpdateRestResourceRequest{" +
                "name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String name;
        private String uri;

        private Builder() {
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder uri(final String uri) {
            this.uri = uri;
            return this;
        }

        public UpdateRestResourceRequest build() {
            return new UpdateRestResourceRequest(this);
        }
    }
}
