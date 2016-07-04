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

package com.castlemock.core.mock.rest.model.project.dto;

import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import org.dozer.Mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestApplicationDto {

    @Mapping("id")
    private String id;

    @Mapping("name")
    private String name;

    @Mapping("resources")
    private List<RestResourceDto> resources = new CopyOnWriteArrayList<RestResourceDto>();

    private Map<RestMethodStatus, Integer> statusCount = new HashMap<RestMethodStatus, Integer>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RestResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<RestResourceDto> resources) {
        this.resources = resources;
    }

    public Map<RestMethodStatus, Integer> getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(Map<RestMethodStatus, Integer> statusCount) {
        this.statusCount = statusCount;
    }
}
