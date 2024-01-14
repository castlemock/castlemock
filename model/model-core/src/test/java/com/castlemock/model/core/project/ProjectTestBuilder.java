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

package com.castlemock.model.core.project;

import java.util.Date;

public final class ProjectTestBuilder {

    private ProjectTestBuilder(){
    }

    public static ProjectTestBuilder.TestProject.Builder builder(){
        return ProjectTestBuilder.TestProject.builder()
                .id("SoapProject")
                .name("Project name")
                .description("Project description")
                .created(new Date())
                .updated(new Date());
    }

    public static ProjectTestBuilder.TestProject build() {
        return builder().build();
    }

    public static class TestProject extends Project {

        private TestProject(final ProjectTestBuilder.TestProject.Builder builder) {
            super(builder);
        }

        public static ProjectTestBuilder.TestProject.Builder builder() {
            return new ProjectTestBuilder.TestProject.Builder();
        }

        public static class Builder extends Project.Builder<ProjectTestBuilder.TestProject.Builder> {

            private Builder() {
            }

            public ProjectTestBuilder.TestProject build(){
                return new ProjectTestBuilder.TestProject(this);
            }
        }

    }

    
}
