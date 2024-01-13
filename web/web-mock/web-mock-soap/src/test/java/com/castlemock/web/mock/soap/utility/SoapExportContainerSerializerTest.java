package com.castlemock.web.mock.soap.utility;

import com.castlemock.model.core.utility.serializer.ExportContainerSerializer;
import com.castlemock.model.mock.soap.SoapExportContainer;
import com.castlemock.model.mock.soap.SoapExportContainerTestBuilder;
import org.junit.Test;

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
