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

package com.castlemock.web.mock.graphql.repository.project.file;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLArgument;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLAttributeType;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLAttribute;
import com.castlemock.web.basis.repository.FileRepository;
import com.castlemock.web.mock.graphql.repository.project.GraphQLAttributeRepository;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class GraphQLAttributeFileRepository extends FileRepository<GraphQLAttributeFileRepository.GraphQLAttributeFile, GraphQLAttribute, String> implements GraphQLAttributeRepository {

    @Autowired
    private MessageSource messageSource;
    @Value(value = "${graphql.attribute.file.directory}")
    private String fileDirectory;
    @Value(value = "${graphql.attribute.file.extension}")
    private String fileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from. The method is abstract and every subclass is responsible for
     * overriding the method and provided the directory for their corresponding file type.
     *
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return fileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * The method is abstract and every subclass is responsible for overriding the method and provided the postfix
     * for their corresponding file type.
     *
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return fileExtension;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     *
     * @param type The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     */
    @Override
    protected void checkType(GraphQLAttributeFile type) {

    }

    /**
     * Updates an instance that matches the provided id.
     *
     * @param id   The id of the instance that will be updated.
     * @param type The updated version that will replace the old one.
     * @return A copy of the replaced value.
     * @since 1.20
     */
    @Override
    public GraphQLAttribute update(final String id, final GraphQLAttribute type) {
        final GraphQLAttribute existing = this.findOne(id);
        existing.setValue(type.getValue());
        this.save(existing);
        return existing;
    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLAttribute> search(SearchQuery query) {
        final List<GraphQLAttribute> result = new LinkedList<GraphQLAttribute>();
        for(GraphQLAttributeFile graphQLAttributeFile : collection.values()){
            if(SearchValidator.validate(graphQLAttributeFile.getName(), query.getQuery())){
                GraphQLAttribute graphQLAttribute = mapper.map(graphQLAttributeFile, GraphQLAttribute.class);
                result.add(graphQLAttribute);
            }
        }
        return result;
    }

    @Override
    public List<GraphQLAttribute> findWithObjectTypeId(final String projectId) {
        final List<GraphQLAttribute> attributes = new ArrayList<>();
        for(GraphQLAttributeFile attribute : this.collection.values()){
            if(attribute.getObjectTypeId().equals(projectId)){
                GraphQLAttribute application = this.mapper.map(attribute, GraphQLAttribute.class);
                attributes.add(application);
            }
        }
        return attributes;
    }

    @Override
    public void deleteWithObjectTypeId(final String objectTypeId) {
        Iterator<GraphQLAttributeFile> iterator = this.collection.values().iterator();
        while (iterator.hasNext()){
            GraphQLAttributeFile attribute = iterator.next();
            if(attribute.getObjectTypeId().equals(objectTypeId)){
                delete(attribute.getId());
            }
        }
    }

    @XmlRootElement(name = "graphQLAttribute")
    protected static class GraphQLAttributeFile implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("description")
        private String description;
        @Mapping("typeId")
        private String typeId;
        @Mapping("typeName")
        private String typeName;
        @Mapping("nullable")
        private Boolean nullable;
        @Mapping("listable")
        private Boolean listable;
        @Mapping("objectTypeId")
        private String objectTypeId;
        @Mapping("value")
        private String value;
        @Mapping("attributeType")
        private GraphQLAttributeType attributeType;
        @Mapping("arguments")
        private List<GraphQLArgument> arguments = new CopyOnWriteArrayList<GraphQLArgument>();

        @Override
        @XmlElement
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        @XmlElement
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @XmlElement
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @XmlElement
        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        @XmlElement
        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        @XmlElement
        public Boolean getNullable() {
            return nullable;
        }

        public void setNullable(Boolean nullable) {
            this.nullable = nullable;
        }

        @XmlElement
        public Boolean getListable() {
            return listable;
        }

        public void setListable(Boolean listable) {
            this.listable = listable;
        }

        @XmlElement
        public GraphQLAttributeType getAttributeType() {
            return attributeType;
        }

        public void setAttributeType(GraphQLAttributeType attributeType) {
            this.attributeType = attributeType;
        }

        @XmlElementWrapper(name = "arguments")
        @XmlElement(name = "argument")
        public List<GraphQLArgument> getArguments() {
            return arguments;
        }

        public void setArguments(List<GraphQLArgument> arguments) {
            this.arguments = arguments;
        }

        @XmlElement
        public String getObjectTypeId() {
            return objectTypeId;
        }

        public void setObjectTypeId(String objectTypeId) {
            this.objectTypeId = objectTypeId;
        }
    }


}
