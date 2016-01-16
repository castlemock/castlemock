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

package com.fortmocks.web.mock.soap.model.project.repository;

import com.fortmocks.core.mock.soap.model.project.domain.SoapMockResponse;
import com.fortmocks.core.mock.soap.model.project.domain.SoapOperation;
import com.fortmocks.core.mock.soap.model.project.domain.SoapPort;
import com.fortmocks.core.mock.soap.model.project.domain.SoapProject;
import com.fortmocks.web.basis.model.RepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * The class is an implementation of the file repository and provides the functionality to interact with the file system.
 * The repository is responsible for loading and saving soap project from the file system. Each soap project is stored as
 * a separate file. The class also contains the directory and the filename extension for the soap project.
 * @author Karl Dahlgren
 * @since 1.0
 * @see SoapProjectRepository
 * @see RepositoryImpl
 * @see SoapProject
 */
@Repository
public class SoapProjectRepositoryImpl extends RepositoryImpl<SoapProject, String> implements SoapProjectRepository {

    @Value(value = "${soap.project.file.directory}")
    private String soapProjectFileDirectory;
    @Value(value = "${soap.project.file.extension}")
    private String soapProjectFileExtension;

    /**
     * The method returns the directory for the specific file repository. The directory will be used to indicate
     * where files should be saved and loaded from.
     * @return The file directory where the files for the specific file repository could be saved and loaded from.
     */
    @Override
    protected String getFileDirectory() {
        return soapProjectFileDirectory;
    }

    /**
     * The method returns the postfix for the file that the file repository is responsible for managing.
     * @return The file extension for the file type that the repository is responsible for managing .
     */
    @Override
    protected String getFileExtension() {
        return soapProjectFileExtension;
    }

    /**
     * The method is responsible for controller that the type that is about the be saved to the file system is valid.
     * The method should check if the type contains all the necessary values and that the values are valid. This method
     * will always be called before a type is about to be saved. The main reason for why this is vital and done before
     * saving is to make sure that the type can be correctly saved to the file system, but also loaded from the
     * file system upon application startup. The method will throw an exception in case of the type not being acceptable.
     * @param soapProject The instance of the type that will be checked and controlled before it is allowed to be saved on
     *             the file system.
     * @see #save
     * @see SoapProject
     */
    @Override
    protected void checkType(SoapProject soapProject) {

    }


    /**
     * The save method provides the functionality to save an instance to the file system.
     * @param type The type that will be saved to the file system.
     * @return The type that was saved to the file system. The main reason for it is being returned is because
     *         there could be modifications of the object during the save process. For example, if the type does not
     *         have an identifier, then the method will generate a new identifier for the type.
     */
    @Override
    public SoapProject save(final SoapProject type) {
        for(SoapPort soapPort : type.getPorts()){
            if(soapPort.getId() == null){
                String soapPortId = generateId();
                soapPort.setId(soapPortId);
            }
            for(SoapOperation soapOperation : soapPort.getOperations()){
                if(soapOperation.getId() == null){
                    String soapOperationId = generateId();
                    soapOperation.setId(soapOperationId);
                }
                for(SoapMockResponse soapMockResponse : soapOperation.getMockResponses()){
                    if(soapMockResponse.getId() == null){
                        String soapMockResponseId = generateId();
                        soapMockResponse.setId(soapMockResponseId);
                    }
                }
            }
        }
        return super.save(type);
    }

}
