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

package com.castlemock.web.mock.graphql.web.view.command.project;

import com.castlemock.core.mock.graphql.model.GraphQLDefinitionType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The GraphQLDefinitionFileUploadForm is a class that represent a form which contains a
 * multipart file.
 * @author Karl Dahlgren
 * @since 1.19
 */
public class GraphQLDefinitionFileUploadForm {

    private List<MultipartFile> files;
    private String link;
    private GraphQLDefinitionType definitionType;

    /**
     * Returns the multipart files
     * @return The multipart files
     */
    public List<MultipartFile> getFiles() {
        return files;
    }

    /**
     * Set new value to the multipart file list.
     * @param files The new value for the multipart list
     */
    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public GraphQLDefinitionType getDefinitionType() {
        return definitionType;
    }

    public void setDefinitionType(GraphQLDefinitionType definitionType) {
        this.definitionType = definitionType;
    }
}
