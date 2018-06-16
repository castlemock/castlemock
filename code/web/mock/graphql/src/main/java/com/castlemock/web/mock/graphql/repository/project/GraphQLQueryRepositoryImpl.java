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

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.basis.model.SearchValidator;
import com.castlemock.core.mock.graphql.model.project.domain.GraphQLQuery;
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
public class GraphQLQueryRepositoryImpl extends AbstractGraphQLOperationFileRepository<GraphQLQueryRepositoryImpl.GraphQLQueryFile, GraphQLQuery> implements GraphQLQueryRepository {

    @Autowired
    private MessageSource messageSource;
    @Value(value = "${graphql.query.file.directory}")
    private String fileDirectory;
    @Value(value = "${graphql.query.file.extension}")
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
    protected void checkType(GraphQLQueryFile type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<GraphQLQuery> search(SearchQuery query) {
        final List<GraphQLQuery> result = new LinkedList<GraphQLQuery>();
        for(GraphQLQueryFile graphQLQueryFile : collection.values()){
            if(SearchValidator.validate(graphQLQueryFile.getName(), query.getQuery())){
                GraphQLQuery graphQLQuery = mapper.map(graphQLQueryFile, GraphQLQuery.class);
                result.add(graphQLQuery);
            }
        }
        return result;
    }

    @Override
    public List<GraphQLQuery> findWithApplicationId(final String projectId) {
        final List<GraphQLQuery> queries = new ArrayList<>();
        for(GraphQLQueryFile query : this.collection.values()){
            if(query.getApplicationId().equals(projectId)){
                GraphQLQuery application = this.mapper.map(query, GraphQLQuery.class);
                queries.add(application);
            }
        }
        return queries;
    }

    @Override
    public void deleteWithApplicationId(final String applicationId) {
        Iterator<GraphQLQueryFile> iterator = this.collection.values().iterator();
        while (iterator.hasNext()){
            GraphQLQueryFile query = iterator.next();
            if(query.getApplicationId().equals(applicationId)){
                delete(query.getId());
            }
        }
    }


    /**
     * @author Karl Dahlgren
     * @since 1.19
     */
    @XmlRootElement(name = "graphQLQuery")
    protected static class GraphQLQueryFile extends AbstractGraphQLOperationFileRepository.GraphQLOperationFile {

    }

}
