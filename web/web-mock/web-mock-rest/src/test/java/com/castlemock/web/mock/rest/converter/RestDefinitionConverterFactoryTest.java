package com.castlemock.web.mock.rest.converter;

import com.castlemock.model.mock.rest.RestDefinitionType;
import com.castlemock.service.core.manager.FileManager;
import com.castlemock.service.mock.rest.project.converter.RestDefinitionConverter;
import com.castlemock.service.mock.rest.project.converter.RestDefinitionConverterFactory;
import com.castlemock.service.mock.rest.project.converter.raml.RAMLRestDefinitionConverter;
import com.castlemock.service.mock.rest.project.converter.swagger.SwaggerRestDefinitionConverter;
import com.castlemock.service.mock.rest.project.converter.wadl.WADLRestDefinitionConverter;
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
