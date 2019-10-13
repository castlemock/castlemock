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

package com.castlemock.web.mock.soap.web.view.command.port;

import com.castlemock.core.mock.soap.model.project.domain.SoapPort;

import java.util.List;

/**
 * The DeleteApplicationsCommand is a command class and is used to carry information on
 * which applications should be deleted from the database
 * @author Karl Dahlgren
 * @since 1.0
 */
public class DeleteSoapPortsCommand {

    private List<SoapPort> soapPorts;

    /**
     * Returns a list of application that will be deleted from the database
     * @return The list of application that will be deleted
     */
    public List<SoapPort> getSoapPorts() {
        return soapPorts;
    }

    /**
     * Sets a new value for the applications
     * @param soapPorts The new value that will be used for the applications
     */
    public void setSoapPorts(List<SoapPort> soapPorts) {
        this.soapPorts = soapPorts;
    }
}

