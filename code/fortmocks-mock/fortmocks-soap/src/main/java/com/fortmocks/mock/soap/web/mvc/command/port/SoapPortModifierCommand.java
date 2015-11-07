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

package com.fortmocks.mock.soap.web.mvc.command.port;

/**
 * The SoapPortModifierCommand is used when the user want to set the same status
 * to multiple ports
 * @author Karl Dahlgren
 * @since 1.0
 */
public class SoapPortModifierCommand {

    private Long[] soapPortIds;
    private String soapPortStatus;

    public Long[] getSoapPortIds() {
        return soapPortIds;
    }

    public void setSoapPortIds(Long[] soapPortIds) {
        this.soapPortIds = soapPortIds;
    }

    public String getSoapPortStatus() {
        return soapPortStatus;
    }

    public void setSoapPortStatus(String soapPortStatus) {
        this.soapPortStatus = soapPortStatus;
    }
}
