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
