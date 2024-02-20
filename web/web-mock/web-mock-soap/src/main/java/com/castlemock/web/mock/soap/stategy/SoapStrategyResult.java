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

package com.castlemock.web.mock.soap.stategy;

import com.castlemock.model.core.Input;
import com.castlemock.model.mock.soap.domain.SoapResponse;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class SoapStrategyResult {

    private final SoapResponse response;
    private final List<Input> postServiceRequests;

    private SoapStrategyResult(final Builder builder) {
        this.response = builder.response;
        this.postServiceRequests = builder.postServiceRequests;
    }

    public Optional<SoapResponse> getResponse() {
        return Optional.ofNullable(response);
    }

    public List<Input> getPostServiceRequests() {
        return Optional.ofNullable(postServiceRequests)
                .map(List::copyOf)
                .orElseGet(List::of);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SoapStrategyResult that = (SoapStrategyResult) o;
        return Objects.equals(response, that.response) && Objects.equals(postServiceRequests, that.postServiceRequests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(response, postServiceRequests);
    }

    @Override
    public String toString() {
        return "SoapStrategyResult{" +
                "response=" + response +
                ", postServiceRequests=" + postServiceRequests +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private SoapResponse response;
        private List<Input> postServiceRequests;

        private Builder() {
        }

        public Builder response(final SoapResponse response) {
            this.response = response;
            return this;
        }

        public Builder postServiceRequests(final List<Input> postServiceRequests) {
            this.postServiceRequests = postServiceRequests;
            return this;
        }

        public SoapStrategyResult build() {
            return new SoapStrategyResult(this);
        }
    }
}
