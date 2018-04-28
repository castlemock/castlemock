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

package com.castlemock.core.mock.soap.legacy.model.project.v1.domain;

import com.castlemock.core.basis.model.project.domain.Project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement(name = "soapProject")
public class SoapProjectV1 extends Project {

    private List<SoapPortV1> ports = new CopyOnWriteArrayList<SoapPortV1>();
    private List<SoapResourceV1> resources = new CopyOnWriteArrayList<SoapResourceV1>();

    @XmlElementWrapper(name = "ports")
    @XmlElement(name = "port")
    public List<SoapPortV1> getPorts() {
        return ports;
    }

    public void setPorts(List<SoapPortV1> ports) {
        this.ports = ports;
    }

    @XmlElementWrapper(name = "resources")
    @XmlElement(name = "resource")
    public List<SoapResourceV1> getResources() {
        return resources;
    }

    public void setResources(List<SoapResourceV1> resources) {
        this.resources = resources;
    }


}
