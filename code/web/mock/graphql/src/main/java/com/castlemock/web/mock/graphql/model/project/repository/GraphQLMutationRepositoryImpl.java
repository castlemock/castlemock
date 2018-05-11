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

package com.castlemock.web.mock.graphql.model.project.repository;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLMutation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Repository
public class GraphQLMutationRepositoryImpl extends AbstractGraphQLOperationFileRepository<GraphQLMutationRepositoryImpl.GraphQLMutationFile, GraphQLMutation> implements GraphQLMutationRepository {

    @Autowired
    private MessageSource messageSource;
    @Value(value = "${graphql.mutation.file.directory}")
    private String fileDirectory;
    @Value(value = "${graphql.mutation.file.extension}")
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
    protected void checkType(GraphQLMutationFile type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLMutation> search(SearchQuery query) {
        final List<GraphQLMutation> result = new LinkedList<GraphQLMutation>();
        for(GraphQLMutationFile graphQLMutationFile : collection.values()){
            if(SearchValidator.validate(graphQLMutationFile.getName(), query.getQuery())){
                GraphQLMutation graphQLMutation = mapper.map(graphQLMutationFile, GraphQLMutation.class);
                result.add(graphQLMutation);
            }
        }
        return result;
    }

    @Override
    public List<GraphQLMutation> findWithApplicationId(final String projectId) {
        final List<GraphQLMutation> mutations = new ArrayList<>();
        for(GraphQLMutationFile mutation : this.collection.values()){
            if(mutation.getApplicationId().equals(projectId)){
                GraphQLMutation application = this.mapper.map(mutation, GraphQLMutation.class);
                mutations.add(application);
            }
        }
        return mutations;
    }

    @Override
    public void deleteWithApplicationId(final String applicationId) {
        Iterator<GraphQLMutationFile> iterator = this.collection.values().iterator();
        while (iterator.hasNext()){
            GraphQLMutationFile mutation = iterator.next();
            if(mutation.getApplicationId().equals(applicationId)){
                delete(mutation.getId());
            }
        }
    }

    /**
     * @author Karl Dahlgren
     * @since 1.19
     */
    @XmlRootElement(name = "graphQLMutation")
    protected static class GraphQLMutationFile extends AbstractGraphQLOperationFileRepository.GraphQLOperationFile {


    }

}
