/*
 * Copyright 2015 Karl Dahlgren
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

package com.castlemock.service.mock.rest.project.output;

import com.castlemock.model.core.Output;
import com.castlemock.model.mock.rest.domain.RestMethod;

import java.util.Objects;
import java.util.Optional;


/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class ReadRestMethodOutput implements Output{

    private final RestMethod method;

    private ReadRestMethodOutput(final RestMethod method) {
        this.method = method;
    }

    public Optional<RestMethod> getMethod() {
        return Optional.ofNullable(method);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ReadRestMethodOutput that = (ReadRestMethodOutput) o;
        return Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method);
    }

    @Override
    public String toString() {
        return "ReadRestMethodOutput{" +
                "method=" + method +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private RestMethod method;

        public Builder method(final RestMethod method){
            this.method = method;
            return this;
        }

        public ReadRestMethodOutput build(){
            return new ReadRestMethodOutput(this.method);
        }

    }
}
