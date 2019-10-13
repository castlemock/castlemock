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

package com.castlemock.web.mock.rest.web.view.command.application;

/**
 * The RestApplicationModifierCommand is used when the user want to set the same status
 * to multiple REST applications
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestApplicationModifierCommand {

    private String restMethodStatus;
    private String[] restApplicationIds;

    public String getRestMethodStatus() {
        return restMethodStatus;
    }

    public void setRestMethodStatus(String restMethodStatus) {
        this.restMethodStatus = restMethodStatus;
    }

    public String[] getRestApplicationIds() {
        return restApplicationIds;
    }

    public void setRestApplicationIds(String[] restApplicationIds) {
        this.restApplicationIds = restApplicationIds;
    }
}
