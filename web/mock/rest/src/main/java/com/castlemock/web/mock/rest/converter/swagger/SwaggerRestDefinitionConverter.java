/*
 * Copyright 2017 Karl Dahlgren
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

package com.castlemock.web.mock.rest.converter.swagger;

import com.castlemock.core.basis.model.http.domain.HttpMethod;
import com.castlemock.core.basis.model.http.domain.HttpHeader;
import com.castlemock.core.basis.utility.file.FileUtility;
import com.castlemock.core.basis.utility.parser.expression.*;
import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgumentArray;
import com.castlemock.core.basis.utility.parser.expression.argument.ExpressionArgumentString;
import com.castlemock.core.mock.rest.model.project.domain.RestMethodStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponseStatus;
import com.castlemock.core.mock.rest.model.project.domain.RestResponseStrategy;
import com.castlemock.core.mock.rest.model.project.domain.RestApplication;
import com.castlemock.core.mock.rest.model.project.domain.RestMethod;
import com.castlemock.core.mock.rest.model.project.domain.RestMockResponse;
import com.castlemock.core.mock.rest.model.project.domain.RestResource;
import com.castlemock.web.mock.rest.converter.AbstractRestDefinitionConverter;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import io.swagger.models.*;
import io.swagger.models.properties.*;
import io.swagger.parser.SwaggerParser;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * The {@link SwaggerRestDefinitionConverter} provides Swagger related functionality.
 * @since 1.10
 * @author Karl Dahlgren
 */
public class SwaggerRestDefinitionConverter extends AbstractRestDefinitionConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerRestDefinitionConverter.class);
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_XML = "application/xml";
    private static final String APPLICATION_JSON = "application/json";
    private static final int MAX_RESPONSE_ITEMS = 10;

    /**
     * The convert method provides the functionality to convert the provided {@link File} into
     * a list of {@link RestApplication}.
     * @param file The file which will be converted to one or more {@link RestApplication}.
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A list of {@link RestApplication} based on the provided file.
     */
    @Override
    public List<RestApplication> convert(final File file, final boolean generateResponse){
        final String swaggerContent = FileUtility.getFileContent(file);
        final Swagger swagger = new SwaggerParser().parse(swaggerContent);
        final RestApplication restApplication = convertSwagger(swagger, generateResponse);
        return Arrays.asList(restApplication);
    }

    /**
     * The convert method provides the functionality to convert the provided {@link File} into
     * a list of {@link RestApplication}.
     * @param location The location of the definition file
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A list of {@link RestApplication} based on the provided file.
     */
    @Override
    public List<RestApplication> convert(final String location, final boolean generateResponse){
        final Swagger swagger = new SwaggerParser().read(location);
        final RestApplication restApplication = convertSwagger(swagger, generateResponse);
        return Arrays.asList(restApplication);
    }


    /**
     * The method provides the functionality to convert a Swagger String into a {@link RestApplication}.
     * The following HTTP methods will be extracted from the Swagger content:
     * <ul>
     *     <li>GET</li>
     *     <li>PUT</li>
     *     <li>POST</li>
     *     <li>DELETE</li>
     *     <li>HEAD</li>
     *     <li>OPTIONS</li>
     * </ul>
     * @param swagger The Swagger content which will be generated into a {@link RestApplication}.
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A {@link RestApplication} based on the provided Swagger content.
     */
    private RestApplication convertSwagger(final Swagger swagger, final boolean generateResponse){

        if(swagger == null){
            throw new IllegalArgumentException("Unable to parse the Swagger content.");
        }

        final RestApplication restApplication = new RestApplication();
        restApplication.setName(this.getApplicationName(swagger));

        final String forwardAddress = getForwardAddress(swagger);
        final Map<String, Model> definitions = swagger.getDefinitions();

        for(Map.Entry<String, Path> pathEntry : swagger.getPaths().entrySet()){
            final String resourceName = pathEntry.getKey();
            final Path resourcePath = pathEntry.getValue();
            final RestResource restResource = new RestResource();

            restResource.setName(resourceName);
            restResource.setUri(resourceName);

            if(resourcePath.getGet() != null){
                Operation operation = resourcePath.getGet();
                RestMethod restMethod = createRestMethod(operation, definitions, HttpMethod.GET, forwardAddress, generateResponse);
                restResource.getMethods().add(restMethod);
            }
            if(resourcePath.getPost() != null){
                Operation operation = resourcePath.getPost();
                RestMethod restMethod = createRestMethod(operation, definitions, HttpMethod.POST, forwardAddress, generateResponse);
                restResource.getMethods().add(restMethod);
            }
            if(resourcePath.getPut() != null){
                Operation operation = resourcePath.getPut();
                RestMethod restMethod = createRestMethod(operation, definitions, HttpMethod.PUT, forwardAddress, generateResponse);
                restResource.getMethods().add(restMethod);
            }
            if(resourcePath.getDelete() != null){
                Operation operation = resourcePath.getDelete();
                RestMethod restMethod = createRestMethod(operation, definitions, HttpMethod.DELETE, forwardAddress, generateResponse);
                restResource.getMethods().add(restMethod);
            }
            if(resourcePath.getHead() != null){
                Operation operation = resourcePath.getHead();
                RestMethod restMethod = createRestMethod(operation, definitions, HttpMethod.HEAD, forwardAddress, generateResponse);
                restResource.getMethods().add(restMethod);
            }
            if(resourcePath.getOptions() != null){
                Operation operation = resourcePath.getOptions();
                RestMethod restMethod = createRestMethod(operation, definitions, HttpMethod.OPTIONS, forwardAddress, generateResponse);
                restResource.getMethods().add(restMethod);
            }

            restApplication.getResources().add(restResource);
        }

        return restApplication;
    }


    /**
     * The method extracts the forward address from the {@link Swagger} model.
     * @param swagger The {@link Swagger} model contains information about the source address.
     * @return The extracted source address configured in {@link Swagger}.
     */
    private String getForwardAddress(final Swagger swagger){
        String schemas = "http";
        if(swagger.getSchemes() != null && !swagger.getSchemes().isEmpty()){
            schemas = swagger.getSchemes().get(0).toValue();
        }


        return schemas + "://" + swagger.getHost() + swagger.getBasePath();
    }

    /**
     * Create a {@link RestMethod} based on a Swagger {@link Operation} and a {@link HttpMethod}.
     * @param operation The Swagger operation that will be converted to a {@link RestMethod}.
     * @param httpMethod The {@link HttpMethod} of the new {@link RestMethod}.
     * @param forwardAddress The configured forward address. The request for this method will be forwarded to
     *                       this address if the service is configured to be {@link RestMethodStatus#FORWARDED},
     *                       {@link RestMethodStatus#RECORDING} or  {@link RestMethodStatus#RECORD_ONCE}
     * @param generateResponse Will generate a default response if true. No response will be generated if false.
     * @return A {@link RestMethod} based on the provided Swagger {@link Operation} and the {@link HttpMethod}.
     */
    private RestMethod createRestMethod(final Operation operation, final Map<String, Model> definitions,
                                        final HttpMethod httpMethod, final String forwardAddress,
                                        final boolean generateResponse){
        final RestMethod restMethod = new RestMethod();

        String methodName;
        if(operation.getOperationId() != null){
            methodName = operation.getOperationId();
        } else if(operation.getSummary() != null){
            methodName = operation.getSummary();
        } else {
            methodName = httpMethod.name();
        }

        restMethod.setHttpMethod(httpMethod);
        restMethod.setName(methodName);
        restMethod.setStatus(RestMethodStatus.MOCKED);
        restMethod.setResponseStrategy(RestResponseStrategy.SEQUENCE);
        restMethod.setForwardedEndpoint(forwardAddress);

        if(generateResponse){
            if(!operation.getResponses().isEmpty()){
                Collection<RestMockResponse> mockResponses = generateResponse(operation.getResponses(), definitions, operation.getProduces());
                restMethod.getMockResponses().addAll(mockResponses);
            } else {
                RestMockResponse generatedResponse = generateResponse();
                restMethod.getMockResponses().add(generatedResponse);
            }
        }

        return restMethod;
    }

    /**
     * The method generates a default response.
     * @param responses The Swagger response definitions
     * @return The newly generated {@link RestMockResponse}.
     */
    private Collection<RestMockResponse> generateResponse(final Map<String,Response> responses,
                                                          final Map<String, Model> definitions,
                                                          final List<String> produces){
        final List<RestMockResponse> mockResponses = new ArrayList<>();
        for(Map.Entry<String, Response> responseEntry : responses.entrySet()){
            Map<String, String> bodies = new HashMap<String, String>();
            Response response = responseEntry.getValue();
            for(String produce : produces){
                String body = null;
                if(APPLICATION_XML.equalsIgnoreCase(produce)){
                    body = generateXmlBody(response, definitions);
                } else if(APPLICATION_JSON.equalsIgnoreCase(produce)){
                    body = generateJsonBody(response, definitions);
                }
                if(body != null && !body.isEmpty()){
                    bodies.put(produce, body);
                }
            }

            int httpStatusCode = extractHttpStatusCode(responseEntry.getKey());

            if(bodies.isEmpty()){
                RestMockResponse restMockResponse = generateResponse(httpStatusCode, response);
                mockResponses.add(restMockResponse);
            } else {
                for(Map.Entry<String, String> bodyEntry : bodies.entrySet()){
                    String contentType = bodyEntry.getKey();
                    String body = bodyEntry.getValue();
                    RestMockResponse restMockResponse = generateResponse(httpStatusCode, response);
                    restMockResponse.setName(restMockResponse.getName() + " (" + contentType + ")");
                    restMockResponse.setBody(body);

                    HttpHeader httpHeader = new HttpHeader();
                    httpHeader.setName(CONTENT_TYPE);
                    httpHeader.setValue(contentType);
                    restMockResponse.getHttpHeaders().add(httpHeader);
                    mockResponses.add(restMockResponse);
                }
            }
        }
        return mockResponses;
    }

    /**
     * The method generates a mocked response based on the provided {@link Response} and the
     * <code>httpStatusCode</code>.
     * @param httpStatusCode The HTTP status code that the mocked response will have. Please note that
     *                       any mock response with status code different from OK (200), will be
     *                       marked as disabled.
     * @param response The Swagger response that the mocked response will be based on.
     * @return A new {@link RestMockResponse} based on the provided {@link Response}.
     */
    private RestMockResponse generateResponse(final int httpStatusCode, final Response response){
        RestMockResponse restMockResponse = new RestMockResponse();
        restMockResponse.setName(response.getDescription());
        restMockResponse.setHttpStatusCode(httpStatusCode);
        restMockResponse.setUsingExpressions(true);
        if(httpStatusCode == DEFAULT_RESPONSE_CODE){
            restMockResponse.setStatus(RestMockResponseStatus.ENABLED);
        } else {
            restMockResponse.setStatus(RestMockResponseStatus.DISABLED);
        }

        if(response.getHeaders() != null){
            for(Map.Entry<String, Property> headerEntry : response.getHeaders().entrySet()){
                String headerName = headerEntry.getKey();
                HttpHeader httpHeader = new HttpHeader();
                httpHeader.setName(headerName);
                // Swagger does not include an example value for the response.
                restMockResponse.getHttpHeaders().add(httpHeader);
            }
        }
        return restMockResponse;
    }


    /**
     * The method provides the functionality to generate an XML body based on a provided {@link Response}
     * and a list of {@link Model}s that might be required.
     * @param response The Swagger response which the XML body will be based on.
     * @param definitions Definitions of Swagger models
     * @return An XML response body.
     * @since 1.13
     */
    private String generateXmlBody(final Response response, final Map<String, Model> definitions){
        final Property schema = response.getSchema();
        if(schema == null){
            return null;
        }

        final StringWriter stringWriter = new StringWriter();
        try {
            final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            final Document document = docBuilder.newDocument();
            final Element root = getXmlElement(null, schema, definitions, document);
            document.appendChild(root);

            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
            final DOMSource source = new DOMSource(document);

            final StreamResult result = new StreamResult(stringWriter);
            transformer.transform(source, result);
            return stringWriter.toString();
        } catch (Exception e){
            LOGGER.error("Unable to generate a XML response body", e);
        }

        return stringWriter.toString();
    }

    /**
     * The method creates a DOM {@link Element} based on a {@link Property}.
     * @param name The name of the element
     * @param property The property that the element if based on.
     * @param definitions Models which may or may not be required
     * @param document The XML DOM document
     * @return An {@link Element}  based on the provided {@link Property}.
     * @since 1.13
     */
    private Element getXmlElement(String name, final Property property, final Map<String, Model> definitions,
                                  final Document document) {
        Element element = null;
        if(name == null){
            name = property.getType();
        }

        if(property instanceof RefProperty){
            final RefProperty refProperty = (RefProperty) property;
            final String simpleRef = refProperty.getSimpleRef();
            final Model model = definitions.get(simpleRef);

            if(model == null){
                LOGGER.warn("Unable to find the following definition in the Swagger file: " + simpleRef);
                return null;
            }
            element = getXmlElement(model, definitions, document);
        } else if(property instanceof ArrayProperty){
            final ArrayProperty arrayProperty = (ArrayProperty) property;
            final Property item = arrayProperty.getItems();
            final int maxItems = getMaxItems(arrayProperty.getMaxItems());
            element = document.createElement(name);
            for(int index = 0; index < maxItems; index++){
                Element child = getXmlElement(name, item, definitions, document);
                element.appendChild(child);
            }
        } else {
            String expression = getExpressionIdentifier(property);
            element = document.createElement(name);
            if(expression != null){
                Text text = document.createTextNode(expression);
                element.appendChild(text);
            } else {

            }
        }

        return element;
    }

    /**
     * Method used to generate a response body based on a {@link Model} and perhaps related other {@link Model}.
     * @param model The {@link Model} used to generate to the body.
     * @param definitions Other {@link Model} that might be related and required.
     * @param document The XML DOM document.
     * @since 1.13
     * @see {@link #getXmlElement(String, Property, Map, Document)}
     */
    private Element getXmlElement(final Model model, final Map<String, Model> definitions, final Document document) {

        Element element = null;

        if(model instanceof ModelImpl){
            Xml xml = ((ModelImpl) model).getXml();
            if(xml != null) {
                if(xml.getName() != null){
                    element = document.createElement(xml.getName());
                }
            }
        }

        if(element == null){
            // Unclear when this can happen, but this should still be handled
            element = document.createElement("Result");
        }

        if(model instanceof ArrayModel){
            final ArrayModel arrayModel = (ArrayModel) model;
            final Property item = arrayModel.getItems();
            final int maxItems = getMaxItems(arrayModel.getMaxItems());
            for(int index = 0; index < maxItems; index++){
                Element child = getXmlElement(arrayModel.getType(), item, definitions, document);
                element.appendChild(child);
            }
        } else if(model instanceof RefModel){
            final RefModel refModel = (RefModel) model;
            final String simpleRef = refModel.getSimpleRef();
            final Model subModel = definitions.get(simpleRef);
            final Element child = getXmlElement(subModel, definitions, document);
            element.appendChild(child);
        }

        if(model.getProperties() != null){
            for(Map.Entry<String, Property> property : model.getProperties().entrySet()){
                Element subElement = getXmlElement(property.getKey(), property.getValue(), definitions, document);
                element.appendChild(subElement);
            }
        }

        return element;
    }


    /**
     * The method generates a body based on the provided {@link Response} and a map of {@link Model}.
     * @param response The response which the body will be based on.
     * @param definitions The map of definitions that might be required to generate the response.
     * @return A HTTP response body based on the provided {@link Response}.
     * @since 1.13
     * @see {@link #generateJsonBody(String, Property, Map, JsonGenerator)}
     */
    private String generateJsonBody(final Response response, final Map<String, Model> definitions){
        final StringWriter writer = new StringWriter();
        final Property schema = response.getSchema();
        if(schema == null){
            return writer.toString();
        }


        final JsonFactory factory = new JsonFactory();
        JsonGenerator generator = null;
        try {
            generator = factory.createGenerator(writer);
            generateJsonBody(null, schema, definitions, generator);
        } catch (IOException e) {
            LOGGER.error("Unable to generate a response body", e);
        } finally {
            if(generator != null){
                try {
                    generator.close();
                } catch (IOException e) {
                    LOGGER.error("Unable to close the JsonGenerator", e);
                }
            }
        }

        return writer.toString();
    }

    /**
     * The method generates a response body based on a given name, {@link Property} and a map of {@link Model}.
     * @param name The name of the property.
     * @param property The property that will be part of the response.
     * @param definitions The map of definitions will be used when composing the response body.
     * @param generator The {@link JsonGenerator}.
     * @throws IOException
     * @since 1.13
     * @see {@link #generateJsonBody(Response, Map)}
     */
    private void generateJsonBody(final String name, final Property property,
                              final Map<String, Model> definitions, final JsonGenerator generator) throws IOException {

        if(name != null){
            generator.writeFieldName(name);
        }

        if(property instanceof RefProperty){
            final RefProperty refProperty = (RefProperty) property;
            final String simpleRef = refProperty.getSimpleRef();
            final Model model = definitions.get(simpleRef);

            if(model == null){
                LOGGER.warn("Unable to find the following definition in the Swagger file: " + simpleRef);
                return;
            }
            generateJsonBody(model, definitions, generator);
        } else if(property instanceof ArrayProperty){
            final ArrayProperty arrayProperty = (ArrayProperty) property;
            final Property item = arrayProperty.getItems();
            final int maxItems = getMaxItems(arrayProperty.getMaxItems());
            generator.writeStartArray();

            for(int index = 0; index < maxItems; index++){
                generateJsonBody(item.getName(), item, definitions, generator);
            }
            generator.writeEndArray();
        } else {
            String expression = getExpressionIdentifier(property);

            if(expression != null){
                generator.writeObject(expression);
            } else {
                // Unsupported type. Need to write something otherwise
                // we might have a serialization problem.
                generator.writeObject("");
            }
        }
    }

    /**
     * Method used to generate a response body based on a {@link Model} and perhaps related other {@link Model}.
     * @param model The {@link Model} used to generate to the body.
     * @param definitions Other {@link Model} that might be related and required.
     * @param generator generator The {@link JsonGenerator}.
     * @throws IOException
     * @since 1.13
     * @see {@link #generateJsonBody(String, Property, Map, JsonGenerator)}
     */
    private void generateJsonBody(final Model model, final Map<String, Model> definitions, final JsonGenerator generator) throws IOException {
        generator.writeStartObject();
        if(model instanceof ArrayModel){
            final ArrayModel arrayModel = (ArrayModel) model;
            final Property item = arrayModel.getItems();
            final int maxItems = getMaxItems(arrayModel.getMaxItems());
            generator.writeStartArray();
            for(int index = 0; index < maxItems; index++){
                generateJsonBody(item.getName(), item, definitions, generator);
            }
            generator.writeEndArray();
        } else if(model instanceof RefModel){
            final RefModel refModel = (RefModel) model;
            final String simpleRef = refModel.getSimpleRef();
            final Model subModel = definitions.get(simpleRef);
            generateJsonBody(subModel, definitions, generator);
        }

        if(model.getProperties() != null){
            for(Map.Entry<String, Property> property : model.getProperties().entrySet()){
                generateJsonBody(property.getKey(), property.getValue(), definitions, generator);
            }
        }

        generator.writeEndObject();
    }


    /**
     * The method returns an expression identifier for a given {@link Property}.
     * @param property The property that the expression identifier will be based on.
     * @return An expression identifier that matches the provided {@link Property}.
     * If nothing is matched, then <code>null</code> will be returned.
     * @see Expression
     * @see 1.13
     */
    private String getExpressionIdentifier(final Property property){
        ExpressionInput expressionInput = null;
        if(property instanceof IntegerProperty){
            expressionInput = new ExpressionInput(RandomIntegerExpression.IDENTIFIER);
        } else if(property instanceof LongProperty) {
            expressionInput = new ExpressionInput(RandomLongExpression.IDENTIFIER);
        } else if(property instanceof StringProperty){
            final StringProperty stringProperty = (StringProperty) property;
            final List<String> enumValues = stringProperty.getEnum();

            if(enumValues == null || enumValues.isEmpty()){
                expressionInput = new ExpressionInput(RandomStringExpression.IDENTIFIER);
            } else {
                expressionInput = new ExpressionInput(RandomEnumExpression.IDENTIFIER);
                ExpressionArgumentArray arrayArgument = new ExpressionArgumentArray();
                final Iterator<String> enumIterator = enumValues.iterator();
                while(enumIterator.hasNext()){
                    String enumValue = enumIterator.next();
                    ExpressionArgumentString expressionArgumentString = new ExpressionArgumentString(enumValue);
                    arrayArgument.addArgument(expressionArgumentString);
                }

                expressionInput.addArgument(RandomEnumExpression.VALUES_PARAMETER, arrayArgument);
            }
        } else if(property instanceof DoubleProperty){
            expressionInput = new ExpressionInput(RandomDoubleExpression.IDENTIFIER);
        } else if(property instanceof FloatProperty){
            expressionInput = new ExpressionInput(RandomFloatExpression.IDENTIFIER);
        } else if(property instanceof BooleanProperty){
            expressionInput = new ExpressionInput(RandomBooleanExpression.IDENTIFIER);
        } else if(property instanceof UUIDProperty){
            expressionInput = new ExpressionInput(RandomUUIDExpression.IDENTIFIER);
        } else if(property instanceof DecimalProperty){
            expressionInput = new ExpressionInput(RandomDecimalExpression.IDENTIFIER);
        } else if(property instanceof DateProperty){
            expressionInput = new ExpressionInput(RandomDateExpression.IDENTIFIER);
        } else if(property instanceof DateTimeProperty){
            expressionInput = new ExpressionInput(RandomDateTimeExpression.IDENTIFIER);
        } else if(property instanceof PasswordProperty){
            expressionInput = new ExpressionInput(RandomPasswordExpression.IDENTIFIER);
        } else {
            LOGGER.warn("Unsupported property type: " + property.getClass().getSimpleName());
            return null;
        }

        return ExpressionInputParser.convert(expressionInput);
    }


    /**
     * The method will extract the HTTP response status code. The provided response code
     * is a {@link String} and should be parsed to an integer. However, the response code
     * is not always the actual response code. In fact, it can be anything. Therefore,
     * upon {@link NumberFormatException} the default response code will be returned: 200.
     * @param responseCode The response code that will be parsed into an integer.
     * @return The parsed response code. 200 if the parsing failed.
     */
    private int extractHttpStatusCode(final String responseCode){
        try {
            return Integer.parseInt(responseCode);
        } catch (Exception e){
            return DEFAULT_RESPONSE_CODE;
        }
    }

    /**
     * Get the max item count. It is based in the value configured in the Swagger file.
     * If the <code>maxitemCount</code> is null, then the <code>MAX_RESPONSE_ITEMS</code>
     * will be returned.
     *
     * If <code>maxitemCount</code> is not null, then it will take the minimum of <code>maxitemCount</code>
     * and <code>MAX_RESPONSE_ITEMS</code>.
     * @param maxItemCount The max item count configured in the Swagger file.
     * @return The max item count.
     * ßee 1.13
     */
    private int getMaxItems(final Integer maxItemCount){
        if(maxItemCount == null){
            return MAX_RESPONSE_ITEMS;
        }
        return Math.min(MAX_RESPONSE_ITEMS, maxItemCount);
    }

    private String getApplicationName(final Swagger swagger){
        if(swagger.getInfo() != null &&
                swagger.getInfo().getTitle() != null){
            return swagger.getInfo().getTitle();
        } else if(swagger.getHost() != null){
            return swagger.getHost();
        } else if(swagger.getBasePath() != null){
            return swagger.getBasePath();
        }

        throw new IllegalArgumentException("Unable to extract application name " +
                "from the following swagger config: " + swagger);
    }

}
