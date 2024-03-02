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

package com.castlemock.web.mock.soap.utility;

import com.castlemock.model.core.utility.serializer.ExportContainerSerializer;
import com.castlemock.model.mock.soap.SoapExportContainer;
import com.castlemock.model.mock.soap.SoapExportContainerTestBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SoapExportContainerSerializerTest {

    @Test
    public void testSerialize() {
        final SoapExportContainer exportContainer = SoapExportContainerTestBuilder.build();
        final String data = ExportContainerSerializer.serialize(exportContainer);
        final SoapExportContainer imported = ExportContainerSerializer.deserialize(data, SoapExportContainer.class);

        assertEquals(exportContainer, imported);
    }

}
