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

package com.castlemock.web.mock.graphql.repository.project;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLAttributeType;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLProject;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLRequestArgument;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLRequestField;
import com.castlemock.web.basis.repository.project.AbstractProjectFileRepository;
import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class GraphQLProjectRepositoryImpl extends AbstractProjectFileRepository<GraphQLProjectRepositoryImpl.GraphQLProjectFile, GraphQLProject> implements GraphQLProjectRepository {

    @Autowired
    private MessageSource messageSource;
    @Value(value = "${graphql.project.file.directory}")
    private String graphQLProjectFileDirectory;
    @Value(value = "${graphql.project.file.extension}")
    private String graphQLProjectFileExtension;

    private static final String SLASH = "/";
    private static final String GRAPHQL = "graphql";
    private static final String PROJECT = "project";
    private static final String COMMA = ", ";
    private static final String GRAPHQL_TYPE = "GraphQL";
    private static final String QUERY = "query";
    private static final String MUTATION = "mutation";
    private static final String SUBSCRIPTION = "subscription";
    private static final String OBJECT_TYPE = "object";
    private static final String ENUM_TYPE = "enum";


    private static final Logger LOGGER = Logger.getLogger(GraphQLProjectRepositoryImpl.class);

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from. The method is abstract and every subclass is responsible for
     * overriding the method and provided the directory for their corresponding file type.
     *
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return graphQLProjectFileDirectory;
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
        return graphQLProjectFileExtension;
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
    protected void checkType(GraphQLProjectFile type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLProject> search(SearchQuery query) {
        final List<GraphQLProject> result = new LinkedList<GraphQLProject>();
        for(GraphQLProjectFile graphQLProjectFile : collection.values()){
            if(SearchValidator.validate(graphQLProjectFile.getName(), query.getQuery())){
                GraphQLProject graphQLProject = mapper.map(graphQLProjectFile, GraphQLProject.class);
                result.add(graphQLProject);
            }
        }
        return result;
    }

    /**
     * The save method provides the functionality to save an instance to the file system.
     * @param project The type that will be saved to the file system.
     * @return The type that was saved to the file system. The main reason for it is being returned is because
     *         there could be modifications of the object during the save process. For example, if the type does not
     *         have an identifier, then the method will generate a new identifier for the type.
     */
    @Override
    public GraphQLProject save(final GraphQLProjectFile project) {
        return super.save(project);
    }


    /**
     * Finds a project by a given name
     *
     * @param name The name of the project that should be retrieved
     * @return Returns a project with the provided name
     * @see GraphQLProject
     */
    @Override
    public GraphQLProject findGraphQLProjectWithName(String name) {
        Preconditions.checkNotNull(name, "Project name cannot be null");
        Preconditions.checkArgument(!name.isEmpty(), "Project name cannot be empty");
        for(GraphQLProjectFile graphQLProject : collection.values()){
            if(graphQLProject.getName().equalsIgnoreCase(name)) {
                return mapper.map(graphQLProject, GraphQLProject.class);
            }
        }
        return null;
    }

    @XmlRootElement(name = "graphQLProject")
    protected static class GraphQLProjectFile extends AbstractProjectFileRepository.ProjectFile {

    }

    /**
     * @author Karl Dahlgren
     * @since 1.19
     */
    @XmlRootElement(name = "graphQLRequestQuery")
    protected static class GraphQLRequestQueryFile implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("operationName")
        private String operationName;
        @Mapping("fields")
        private List<GraphQLRequestField> fields = new CopyOnWriteArrayList<GraphQLRequestField>();
        @Mapping("arguments")
        private List<GraphQLRequestArgument> arguments = new CopyOnWriteArrayList<GraphQLRequestArgument>();

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
        public String getOperationName() {
            return operationName;
        }

        public void setOperationName(String operationName) {
            this.operationName = operationName;
        }

        @XmlElementWrapper(name = "fields")
        @XmlElement(name = "field")
        public List<GraphQLRequestField> getFields() {
            return fields;
        }

        public void setFields(List<GraphQLRequestField> fields) {
            this.fields = fields;
        }

        @XmlElementWrapper(name = "arguments")
        @XmlElement(name = "argument")
        public List<GraphQLRequestArgument> getArguments() {
            return arguments;
        }

        public void setArguments(List<GraphQLRequestArgument> arguments) {
            this.arguments = arguments;
        }
    }

    @XmlRootElement(name = "graphQLAttribute")
    protected static class GraphQLAttributeFile implements Saveable<String> {

        private String id;
        private String name;
        private String description;
        private String typeId;
        private String typeName;
        private Boolean nullable;
        private Boolean listable;
        private String objectTypeId;
        private String value;
        private GraphQLAttributeType attributeType;
        private List<GraphQLArgumentFile> arguments = new CopyOnWriteArrayList<GraphQLArgumentFile>();

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
        public List<GraphQLArgumentFile> getArguments() {
            return arguments;
        }

        public void setArguments(List<GraphQLArgumentFile> arguments) {
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

    @XmlRootElement(name = "graphQLRequestArgument")
    protected static class GraphQLRequestArgumentFile {

        @Mapping("name")
        private String name;
        @Mapping("arguments")
        private List<GraphQLRequestArgumentFile> arguments = new CopyOnWriteArrayList<GraphQLRequestArgumentFile>();

        @XmlElement
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElementWrapper(name = "arguments")
        @XmlElement(name = "argument")
        public List<GraphQLRequestArgumentFile> getArguments() {
            return arguments;
        }

        public void setArguments(List<GraphQLRequestArgumentFile> arguments) {
            this.arguments = arguments;
        }
    }

    @XmlRootElement(name = "graphQLRequestField")
    public class GraphQLRequestFieldFile {

        @Mapping("name")
        private String name;
        @Mapping("fields")
        private List<GraphQLRequestFieldFile> fields = new CopyOnWriteArrayList<GraphQLRequestFieldFile>();

        @XmlElement
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElementWrapper(name = "fields")
        @XmlElement(name = "field")
        public List<GraphQLRequestFieldFile> getFields() {
            return fields;
        }

        public void setFields(List<GraphQLRequestFieldFile> fields) {
            this.fields = fields;
        }
    }


    @XmlRootElement(name = "graphQLArgument")
    protected static class GraphQLArgumentFile implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;
        @Mapping("description")
        private String description;
        @Mapping("typeName")
        private String typeName;
        @Mapping("typeId")
        private String typeId;
        @Mapping("defaultValue")
        private Object defaultValue;
        @Mapping("nullable")
        private Boolean nullable;
        @Mapping("listable")
        private Boolean listable;
        @Mapping("attributeType")
        private GraphQLAttributeType attributeType;

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
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @XmlElement
        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        @XmlElement
        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        @XmlElement
        public Object getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(Object defaultValue) {
            this.defaultValue = defaultValue;
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

    }


}
