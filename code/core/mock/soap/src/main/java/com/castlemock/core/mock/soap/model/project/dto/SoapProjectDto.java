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

package com.castlemock.core.mock.soap.model.project.dto;

import com.castlemock.core.basis.model.project.dto.ProjectDto;
import com.castlemock.core.mock.soap.model.project.domain.SoapOperationStatus;
import org.dozer.Mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The DTO class for the Project class
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapProjectDto extends ProjectDto {

    @Mapping("ports")
    private List<SoapPortDto> ports = new CopyOnWriteArrayList<SoapPortDto>();
    private Map<SoapOperationStatus, Integer> statusCount = new HashMap<SoapOperationStatus, Integer>();

    /**
     * The default SOAP project constructor
     */
    public SoapProjectDto() {
    }

    /**
     * The constructor will create a new SOAP project DTO based on the provided projectDto
     * @param projectDto The new SOAP project DTO will be based on the provided project DTO and contain
     *                   the same information as the provided project DTO
     */
    public SoapProjectDto(final ProjectDto projectDto){
        super(projectDto);
    }

    /**
     * Returns all the SOAP ports
     * @return The SOAP ports for the SOAP project
     */
    public List<SoapPortDto> getPorts() {
        return ports;
    }

    /**
     * Set new value to the variable ports
     * @param ports The new value to ports
     */
    public void setPorts(List<SoapPortDto> ports) {
        this.ports = ports;
    }

    /**
     * The status count is used in the GUI to information the user on the SOAP operation status distribution.
     * @return The status counts.
     */
    public Map<SoapOperationStatus, Integer> getStatusCount() {
        return statusCount;
    }

    /**
     * Sets a new value to the statusCount variable
     * @param statusCount The new value to statusCount
     */
    public void setStatusCount(Map<SoapOperationStatus, Integer> statusCount) {
        this.statusCount = statusCount;
    }

}
