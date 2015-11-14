/*
 * Copyright 2015 Karl Dahlgren
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

package com.fortmocks.mock.rest.model.event.repository;

import com.fortmocks.mock.rest.model.event.domain.RestEvent;
import com.fortmocks.web.core.model.RepositoryImpl;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * The class is an implementation of the REST event repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving rest events from the file system. Each rest event is stored as
 * a separate file. The class also contains the directory and the filename extension for the rest event.
 * @author Karl Dahlgren
 * @since 1.0
 * @see RestEventRepositoryImpl
 * @see RepositoryImpl
 * @see RestEvent
 */
@Repository
public class RestEventRepositoryImpl extends RepositoryImpl<RestEvent, Long> implements RestEventRepository {

    @Value(value = "${rest.event.file.directory}")
    private String restEventFileDirectory;
    @Value(value = "${rest.event.file.extension}")
    private String restEventFileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from.
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return restEventFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return restEventFileExtension;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     * @param restEvent The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     * @see RestEvent
     */
    @Override
    protected void checkType(final RestEvent restEvent) {
        Preconditions.checkNotNull(restEvent, "Event cannot be null");
        Preconditions.checkNotNull(restEvent.getId(), "Event id cannot be null");
        Preconditions.checkNotNull(restEvent.getEndDate(), "Event end date cannot be null");
        Preconditions.checkNotNull(restEvent.getStartDate(), "Event start date cannot be null");
    }

}
