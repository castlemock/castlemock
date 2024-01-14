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

package com.castlemock.repository.rest.file.project.model;

import com.castlemock.repository.core.file.project.model.ProjectFile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "restProject")
@XmlAccessorType(XmlAccessType.NONE)
public class RestProjectFile extends ProjectFile {

    private RestProjectFile() {

    }

    private RestProjectFile(final Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends ProjectFile.Builder<Builder> {

        public RestProjectFile build() {
            return new RestProjectFile(this);
        }

    }

}