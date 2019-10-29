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

package com.castlemock.core.mock.graphql.service.project.output;

import com.castlemock.core.basis.model.Output;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
public final class ExportGraphQLProjectOutput implements Output{

    private final String exportedProject;

    public ExportGraphQLProjectOutput(String exportedProject) {
        this.exportedProject = exportedProject;
    }

    public String getExportedProject() {
        return exportedProject;
    }

}
