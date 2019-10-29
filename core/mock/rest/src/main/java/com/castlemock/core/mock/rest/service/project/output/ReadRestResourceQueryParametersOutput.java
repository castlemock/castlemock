/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.core.mock.rest.service.project.output;

import com.castlemock.core.basis.model.Output;
import com.castlemock.core.mock.rest.model.project.domain.RestParameterQuery;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

public final class ReadRestResourceQueryParametersOutput implements Output {

    private final Set<RestParameterQuery> queries;

    private ReadRestResourceQueryParametersOutput(final Builder builder){
        this.queries = ImmutableSet.copyOf(builder.queries);
    }

    public Set<RestParameterQuery> getQueries() {
        return queries;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private Set<RestParameterQuery> queries;

        public Builder queries(final Set<RestParameterQuery> queries){
            this.queries = queries;
            return this;
        }

        public ReadRestResourceQueryParametersOutput build(){
            return new ReadRestResourceQueryParametersOutput(this);
        }

    }
}
