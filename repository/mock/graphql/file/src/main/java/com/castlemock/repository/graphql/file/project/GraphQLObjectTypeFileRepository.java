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

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLObjectType;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.graphql.project.GraphQLObjectTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Repository
@Profile(Profiles.FILE)
public class GraphQLObjectTypeFileRepository extends AbstractGraphQLTypeFileRepository<GraphQLObjectTypeFileRepository.GraphQLObjectTypeFile, GraphQLObjectType> implements GraphQLObjectTypeRepository {

    @Autowired
    private MessageSource messageSource;
    @Value(value = "${graphql.object.file.directory}")
    private String fileDirectory;
    @Value(value = "${graphql.object.file.extension}")
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
    protected void checkType(GraphQLObjectTypeFile type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLObjectType> search(SearchQuery query) {
        final List<GraphQLObjectType> result = new LinkedList<GraphQLObjectType>();
        for(GraphQLObjectTypeFile graphQLObjectTypeFile : collection.values()){
            if(SearchValidator.validate(graphQLObjectTypeFile.getName(), query.getQuery())){
                GraphQLObjectType graphQLObjectType = mapper.map(graphQLObjectTypeFile, GraphQLObjectType.class);
                result.add(graphQLObjectType);
            }
        }
        return result;
    }

    @Override
    public List<GraphQLObjectType> findWithApplicationId(final String projectId) {
        final List<GraphQLObjectType> objectTypes = new ArrayList<>();
        for(GraphQLObjectTypeFile objectType : this.collection.values()){
            if(objectType.getApplicationId().equals(projectId)){
                GraphQLObjectType application = this.mapper.map(objectType, GraphQLObjectType.class);
                objectTypes.add(application);
            }
        }
        return objectTypes;
    }

    @Override
    public void deleteWithApplicationId(final String applicationId) {
        Iterator<GraphQLObjectTypeFile> iterator = this.collection.values().iterator();
        while (iterator.hasNext()){
            GraphQLObjectTypeFile objectType = iterator.next();
            if(objectType.getApplicationId().equals(applicationId)){
                delete(objectType.getId());
            }
        }
    }

    @XmlRootElement(name = "graphQLObjectType")
    protected static class GraphQLObjectTypeFile extends AbstractGraphQLTypeFileRepository.GraphQLTypeFile {

    }

}
