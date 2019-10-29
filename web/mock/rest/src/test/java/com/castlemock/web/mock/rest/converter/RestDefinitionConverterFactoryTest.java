package com.castlemock.web.mock.rest.converter;

import com.castlemock.core.mock.rest.model.RestDefinitionType;
import com.castlemock.web.basis.manager.FileManager;
import com.castlemock.web.mock.rest.converter.raml.RAMLRestDefinitionConverter;
import com.castlemock.web.mock.rest.converter.swagger.SwaggerRestDefinitionConverter;
import com.castlemock.web.mock.rest.converter.wadl.WADLRestDefinitionConverter;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

public class RestDefinitionConverterFactoryTest {

    @Mock
    private FileManager fileManager;

    @Test
    public void testGetConverterRAML(){
        final RestDefinitionConverter converter =
                RestDefinitionConverterFactory.getConverter(RestDefinitionType.RAML, fileManager);

        Assert.assertTrue((converter instanceof RAMLRestDefinitionConverter));
    }

    @Test
    public void testGetConverterSwagger(){
        final RestDefinitionConverter converter =
                RestDefinitionConverterFactory.getConverter(RestDefinitionType.SWAGGER, fileManager);

        Assert.assertTrue((converter instanceof SwaggerRestDefinitionConverter));
    }

    @Test
    public void testGetConverterWADL(){
        final RestDefinitionConverter converter =
                RestDefinitionConverterFactory.getConverter(RestDefinitionType.WADL, fileManager);

        Assert.assertTrue((converter instanceof WADLRestDefinitionConverter));
    }

}
