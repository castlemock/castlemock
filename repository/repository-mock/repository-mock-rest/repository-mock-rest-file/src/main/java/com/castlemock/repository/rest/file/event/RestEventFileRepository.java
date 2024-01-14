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

package com.castlemock.repository.rest.file.event;

import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.core.file.FileRepository;
import com.castlemock.repository.core.file.event.AbstractEventFileRepository;
import com.castlemock.repository.rest.event.RestEventRepository;
import com.castlemock.repository.rest.file.event.converter.RestEventConverter;
import com.castlemock.repository.rest.file.event.converter.RestEventFileConverter;
import com.castlemock.repository.rest.file.event.model.RestEventFile;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The class is an implementation of the REST event repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving rest events from the file system. Each rest event is stored as
 * a separate file. The class also contains the directory and the filename extension for the rest event.
 * @author Karl Dahlgren
 * @since 1.0
 * @see RestEventFileRepository
 * @see FileRepository
 */
@Repository
@Profile(Profiles.FILE)
public class RestEventFileRepository extends AbstractEventFileRepository<RestEventFile, RestEvent> implements RestEventRepository {

    @Value(value = "${rest.event.file.directory}")
    private String restEventFileDirectory;
    @Value(value = "${rest.event.file.extension}")
    private String restEventFileExtension;

    public RestEventFileRepository() {
        super(RestEventFileConverter::toRestEvent, RestEventConverter::toRestEventFile);
    }

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
     */
    @Override
    protected void checkType(final RestEventFile restEvent) {
        Preconditions.checkNotNull(restEvent, "Event cannot be null");
        Preconditions.checkNotNull(restEvent.getId(), "Event id cannot be null");
        Preconditions.checkNotNull(restEvent.getEndDate(), "Event end date cannot be null");
        Preconditions.checkNotNull(restEvent.getStartDate(), "Event start date cannot be null");
    }

    /**
     * The service finds the oldest event
     * @return The oldest event
     */
    @Override
    public RestEvent getOldestEvent() {
        RestEventFile oldestEvent = null;
        for(RestEventFile event : collection.values()){
            if(oldestEvent == null){
                oldestEvent = event;
            } else if(event.getStartDate().before(oldestEvent.getStartDate())){
                oldestEvent = event;
            }
        }

        return Optional.ofNullable(oldestEvent)
                .map(RestEventFileConverter::toRestEvent)
                .orElse(null);
    }

    /**
     * Find events by REST method ID
     * @param restMethodId The id of the REST method
     * @return A list of {@link RestEvent} that matches the provided <code>restMethodId</code>
     */
    @Override
    public List<RestEvent> findEventsByMethodId(final String restMethodId) {
        return this.collection.values()
                .stream()
                .filter(event -> event.getMethodId().equals(restMethodId))
                .map(RestEventFileConverter::toRestEvent)
                .toList();
    }

    /**
     * The method finds and deletes the oldest event.
     * @return The event that was deleted.
     * @since 1.5
     */
    @Override
    public synchronized RestEvent deleteOldestEvent(){
        final RestEvent event = getOldestEvent();
        this.delete(event.getId());
        return event;
    }

    /**
     * The method clears and deletes all logs.
     * @since 1.7
     */
    @Override
    public void clearAll() {
        this.collection.values()
                .stream()
                .map(RestEventFile::getId)
                .toList()
                .forEach(this::delete);
    }

}
