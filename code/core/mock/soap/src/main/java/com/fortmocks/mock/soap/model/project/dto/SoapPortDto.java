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

package com.fortmocks.mock.soap.model.project.dto;

import com.fortmocks.mock.soap.model.project.domain.SoapOperationStatus;
import org.dozer.Mapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapPortDto {

    @Mapping("id")
    private Long id;

    @Mapping("name")
    private String name;

    @Mapping("soapOperations")
    private List<SoapOperationDto> soapOperations = new LinkedList<SoapOperationDto>();

    private Map<SoapOperationStatus, Integer> statusCount = new HashMap<SoapOperationStatus, Integer>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SoapOperationDto> getSoapOperations() {
        return soapOperations;
    }

    public void setSoapOperations(List<SoapOperationDto> soapOperations) {
        this.soapOperations = soapOperations;
    }

    public Map<SoapOperationStatus, Integer> getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(Map<SoapOperationStatus, Integer> statusCount) {
        this.statusCount = statusCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
