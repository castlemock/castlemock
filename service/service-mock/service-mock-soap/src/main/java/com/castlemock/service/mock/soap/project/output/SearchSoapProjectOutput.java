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

package com.castlemock.service.mock.soap.project.output;

import com.castlemock.model.core.Output;
import com.castlemock.model.core.SearchResult;
import com.castlemock.model.core.validation.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class SearchSoapProjectOutput implements Output {

    @NotNull
    private final List<SearchResult> searchResults;

    private SearchSoapProjectOutput(final Builder builder) {
        this.searchResults = Objects.requireNonNull(builder.searchResults);
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private List<SearchResult> searchResults;

        private Builder(){

        }

        public Builder searchResults(final List<SearchResult> searchResults){
            this.searchResults = searchResults;
            return this;
        }

        public SearchSoapProjectOutput build(){
            return new SearchSoapProjectOutput(this);
        }
    }
}
