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

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public final class CreateRestApplicationOutput implements Output {

    private final RestApplication application;

    private CreateRestApplicationOutput(final RestApplication application) {
        this.application = Objects.requireNonNull(application, "application");
    }

    public RestApplication getApplication() {
        return application;
    }

    public static Builder builder(){
        return new Builder();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreateRestApplicationOutput that = (CreateRestApplicationOutput) o;
        return Objects.equals(application, that.application);
    }

    @Override
    public int hashCode() {
        return Objects.hash(application);
    }

    @Override
    public String toString() {
        return "CreateRestApplicationOutput{" +
                "application=" + application +
                '}';
    }

    public static final class Builder {

        private RestApplication application;

        private Builder() {
        }

        public Builder application(final RestApplication application){
            this.application = application;
            return this;
        }

        public CreateRestApplicationOutput build(){
            return new CreateRestApplicationOutput(this.application);
        }

    }


}
