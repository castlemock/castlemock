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

package com.fortmocks.mock.soap.web.mvc.command.operation;

/**
 * The ServiceModifierCommand is used when the user want to set the same status
 * to multiple services
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapOperationModifierCommand {

    private Long[] soapOperationIds;
    private String soapOperationStatus;

    /**
     * Returns a list of the services that will get the new status
     * @return List of services
     */
    public Long[] getSoapOperationIds() {
        return soapOperationIds;
    }

    /**
     * Sets a new list of operation ids
     * @param soapOperationIds The new value for operation ids
     */
    public void setSoapOperationIds(Long[] soapOperationIds) {
        this.soapOperationIds = soapOperationIds;
    }

    /**
     * Returns the operation status that will be the new status for the provided operation ids
     * @return Returns that operation status
     */
    public String getSoapOperationStatus() {
        return soapOperationStatus;
    }

    /**
     * Set a new value to the operation status
     * @param soapOperationStatus The new operation status value
     */
    public void setSoapOperationStatus(String soapOperationStatus) {
        this.soapOperationStatus = soapOperationStatus;
    }

}
