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
import com.castlemock.model.mock.rest.domain.RestApplication;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class ReadRestApplicationOutput implements Output{

    private final RestApplication applicationId;

    private ReadRestApplicationOutput(final RestApplication applicationId) {
        this.applicationId = applicationId;
    }

    public Optional<RestApplication> getApplicationId() {
        return Optional.ofNullable(applicationId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ReadRestApplicationOutput that = (ReadRestApplicationOutput) o;
        return Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationId);
    }

    @Override
    public String toString() {
        return "ReadRestApplicationOutput{" +
                "applicationId=" + applicationId +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private RestApplication restApplication;

        private Builder() {
        }

        public Builder application(final RestApplication restApplication){
            this.restApplication = restApplication;
            return this;
        }

        public ReadRestApplicationOutput build(){
            return new ReadRestApplicationOutput(this.restApplication);
        }

    }

}
