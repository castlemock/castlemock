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


package com.castlemock.web.mock.soap.model.project.repository;

import com.castlemock.core.basis.model.SearchQuery;
import com.castlemock.core.basis.model.SearchResult;
import com.castlemock.core.mock.soap.model.project.domain.SoapPort;
import com.castlemock.core.mock.soap.model.project.dto.SoapPortDto;
import com.castlemock.web.basis.model.RepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class SoapPortRepositoryImpl extends RepositoryImpl<SoapPort, SoapPortDto, String> implements SoapPortRepository {

    @Value(value = "${soap.port.file.directory}")
    private String fileDirectory;
    @Value(value = "${soap.port.file.extension}")
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
    protected void checkType(SoapPort type) {

    }

    /**
     * The method provides the functionality to search in the repository with a {@link SearchQuery}
     *
     * @param query The search query
     * @return A <code>list</code> of {@link SearchResult} that matches the provided {@link SearchQuery}
     */
    @Override
    public List<SearchResult> search(SearchQuery query) {
        return null;
    }

    @Override
    public void deleteWithProjectId(String projectId) {
        Iterator<SoapPort> iterator = this.collection.values().iterator();
        while (iterator.hasNext()){
            SoapPort port = iterator.next();
            if(port.getProjectId().equals(projectId)){
                delete(port.getId());
            }
        }
    }

    @Override
    public List<SoapPortDto> findWithProjectId(String projectId) {
        final List<SoapPortDto> ports = new ArrayList<>();
        for(SoapPort port : this.collection.values()){
            if(port.getProjectId().equals(projectId)){
                SoapPortDto portDto = this.mapper.map(port, SoapPortDto.class);
                ports.add(portDto);
            }
        }
        return ports;
    }

    /**
     * The method finds a {@link SoapPortDto} with the provided name
     * @param soapPortName The name of the {@link SoapPort}
     * @return A {@link SoapPort} that matches the provided search criteria.
     */
    @Override
    public SoapPortDto findWithName(final String projectId, final String soapPortName) {
        for(SoapPort soapPort : collection.values()){
            if(soapPort.getProjectId().equals(projectId) &&
                    soapPort.getName().equals(soapPortName)){
                return mapper.map(soapPort, SoapPortDto.class);
            }
        }
        return null;
    }
}
