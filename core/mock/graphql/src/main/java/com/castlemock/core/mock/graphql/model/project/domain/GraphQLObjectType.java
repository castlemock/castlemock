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


package com.castlemock.core.mock.graphql.model.project.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@XmlRootElement
public class GraphQLObjectType extends GraphQLType {

    private List<GraphQLAttribute> attributes = new CopyOnWriteArrayList<GraphQLAttribute>();

    @XmlElementWrapper(name = "attributes")
    @XmlElement(name = "attribute")
    public List<GraphQLAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<GraphQLAttribute> attributes) {
        this.attributes = attributes;
    }
}
