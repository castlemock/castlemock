/*
 * Copyright 2024 Karl Dahlgren
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

package com.castlemock.web.mock.soap.stategy;

import com.castlemock.model.mock.soap.domain.SoapOperation;
import com.castlemock.model.mock.soap.domain.SoapRequest;
import com.castlemock.web.mock.soap.model.SoapException;
import jakarta.servlet.http.HttpServletRequest;

public final class DisabledSoapStrategy implements SoapStrategy {


    public DisabledSoapStrategy() {
    }

    @Override
    public SoapStrategyResult process(final SoapRequest request, final String projectId,
                                final String portId, final SoapOperation operation,
                                final HttpServletRequest httpServletRequest) {
        throw new SoapException("The requested soap operation, " + operation.getName() + ", is disabled");
    }

}
