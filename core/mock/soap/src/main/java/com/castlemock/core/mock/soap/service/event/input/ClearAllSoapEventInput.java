/*
 * Copyright 2016 Karl Dahlgren
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

package com.castlemock.core.mock.soap.service.event.input;

import com.castlemock.core.basis.model.Input;

/**
 * @author Karl Dahlgren
 * @since 1.7
 */
public final class ClearAllSoapEventInput implements Input {

    private ClearAllSoapEventInput(){

    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        public ClearAllSoapEventInput build(){
            return new ClearAllSoapEventInput();
        }

    }


}
