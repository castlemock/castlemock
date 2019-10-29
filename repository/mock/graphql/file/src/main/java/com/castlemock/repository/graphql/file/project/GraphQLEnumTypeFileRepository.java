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

package com.castlemock.repository.graphql.file.project;

import com.castlemock.core.basis.model.Saveable;
import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLEnumType;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.graphql.project.GraphQLEnumTypeRepository;
import org.dozer.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
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
@Profile(Profiles.FILE)
public class GraphQLEnumTypeFileRepository extends AbstractGraphQLTypeFileRepository<GraphQLEnumTypeFileRepository.GraphQLEnumTypeFile, GraphQLEnumType> implements GraphQLEnumTypeRepository {

    @Autowired
    private MessageSource messageSource;
    @Value(value = "${graphql.enumtype.file.directory}")
    private String fileDirectory;
    @Value(value = "${graphql.enumtype.file.extension}")
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
    protected void checkType(GraphQLEnumTypeFile type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLEnumType> search(SearchQuery query) {
        final List<GraphQLEnumType> result = new LinkedList<GraphQLEnumType>();
        for(GraphQLEnumTypeFile graphQLEnumTypeFile : collection.values()){
            if(SearchValidator.validate(graphQLEnumTypeFile.getName(), query.getQuery())){
                GraphQLEnumType graphQLEnumType = mapper.map(graphQLEnumTypeFile, GraphQLEnumType.class);
                result.add(graphQLEnumType);
            }
        }
        return result;
    }

    @Override
    public List<GraphQLEnumType> findWithApplicationId(final String projectId) {
        final List<GraphQLEnumType> enumTypes = new ArrayList<>();
        for(GraphQLEnumTypeFile enumType : this.collection.values()){
            if(enumType.getApplicationId().equals(projectId)){
                GraphQLEnumType application = this.mapper.map(enumType, GraphQLEnumType.class);
                enumTypes.add(application);
            }
        }
        return enumTypes;
    }

    @Override
    public void deleteWithApplicationId(final String applicationId) {
        Iterator<GraphQLEnumTypeFile> iterator = this.collection.values().iterator();
        while (iterator.hasNext()){
            GraphQLEnumTypeFile enumType = iterator.next();
            if(enumType.getApplicationId().equals(applicationId)){
                delete(enumType.getId());
            }
        }
    }

    @XmlRootElement(name = "graphQLEnumType")
    protected static class GraphQLEnumTypeFile extends AbstractGraphQLTypeFileRepository.GraphQLTypeFile {

        @Mapping("definitions")
        private List<GraphQLEnumValueDefinitionFile> definitions
                = new CopyOnWriteArrayList<GraphQLEnumValueDefinitionFile>();


        @XmlElementWrapper(name = "definitions")
        @XmlElement(name = "definition")
        public List<GraphQLEnumValueDefinitionFile> getDefinitions() {
            return definitions;
        }

        public void setDefinitions(List<GraphQLEnumValueDefinitionFile> definitions) {
            this.definitions = definitions;
        }
    }

    @XmlRootElement(name = "graphQLEnumValueDefinition")
    protected static class GraphQLEnumValueDefinitionFile implements Saveable<String> {

        @Mapping("id")
        private String id;
        @Mapping("name")
        private String name;

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


    }

}
