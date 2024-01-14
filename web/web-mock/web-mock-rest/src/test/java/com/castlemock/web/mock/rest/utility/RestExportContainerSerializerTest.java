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

package com.castlemock.web.mock.rest.utility;

import com.castlemock.model.core.utility.serializer.ExportContainerSerializer;
import com.castlemock.model.mock.rest.RestExportContainer;
import com.castlemock.model.mock.rest.RestExportContainerTestBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RestExportContainerSerializerTest {

    @Test
    public void testSerialize() {
        final RestExportContainer exportContainer = RestExportContainerTestBuilder.build();
        final String data = ExportContainerSerializer.serialize(exportContainer);
        final RestExportContainer imported = ExportContainerSerializer.deserialize(data, RestExportContainer.class);

        assertEquals(exportContainer, imported);
    }

}
