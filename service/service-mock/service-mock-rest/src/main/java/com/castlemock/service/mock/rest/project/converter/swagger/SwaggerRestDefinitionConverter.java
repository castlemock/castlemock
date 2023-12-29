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

package com.castlemock.service.mock.rest.project.converter.swagger;

import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.core.utility.IdUtility;
import com.castlemock.model.core.utility.file.FileUtility;
import com.castlemock.model.core.utility.parser.expression.Expression;
import com.castlemock.model.core.utility.parser.expression.ExpressionInput;
import com.castlemock.model.core.utility.parser.expression.ExpressionInputParser;
import com.castlemock.model.core.utility.parser.expression.RandomBooleanExpression;
import com.castlemock.model.core.utility.parser.expression.RandomDateExpression;
import com.castlemock.model.core.utility.parser.expression.RandomDateTimeExpression;
import com.castlemock.model.core.utility.parser.expression.RandomDecimalExpression;
import com.castlemock.model.core.utility.parser.expression.RandomDoubleExpression;
import com.castlemock.model.core.utility.parser.expression.RandomEnumExpression;
import com.castlemock.model.core.utility.parser.expression.RandomFloatExpression;
import com.castlemock.model.core.utility.parser.expression.RandomIntegerExpression;
import com.castlemock.model.core.utility.parser.expression.RandomLongExpression;
import com.castlemock.model.core.utility.parser.expression.RandomPasswordExpression;
import com.castlemock.model.core.utility.parser.expression.RandomStringExpression;
import com.castlemock.model.core.utility.parser.expression.RandomUUIDExpression;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentArray;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgumentString;
import com.castlemock.model.mock.rest.domain.RestApplication;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;
import com.castlemock.model.mock.rest.domain.RestResource;
import com.castlemock.model.mock.rest.domain.RestResponseStrategy;
import com.castlemock.service.mock.rest.project.converter.AbstractRestDefinitionConverter;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import io.swagger.models.ArrayModel;
import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.RefModel;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.models.Xml;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.BooleanProperty;
import io.swagger.models.properties.DateProperty;
import io.swagger.models.properties.DateTimeProperty;
import io.swagger.models.properties.DecimalProperty;
import io.swagger.models.properties.DoubleProperty;
import io.swagger.models.properties.FloatProperty;
import io.swagger.models.properties.IntegerProperty;
import io.swagger.models.properties.LongProperty;
import io.swagger.models.properties.PasswordProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.properties.StringProperty;
import io.swagger.models.properties.UUIDProperty;
import io.swagger.parser.SwaggerParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
    public List<RestApplication> convert(final File file, final String projectId, final boolean generateResponse){
        final String swaggerContent = FileUtility.getFileContent(file);
        final Swagger swagger = new SwaggerParser().parse(swaggerContent);
        final RestApplication restApplication = convertSwagger(swagger, projectId, generateResponse);
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
    public List<RestApplication> convert(final String location, final String projectId, final boolean generateResponse){
        final Swagger swagger = new SwaggerParser().read(location);
        final RestApplication restApplication = convertSwagger(swagger, projectId, generateResponse);
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
    private RestApplication convertSwagger(final Swagger swagger, final String projectId, final boolean generateResponse){

        if(swagger == null){
            throw new IllegalArgumentException("Unable to parse the Swagger content.");
        }

        final String forwardAddress = getForwardAddress(swagger);
        final Map<String, Model> definitions = swagger.getDefinitions();

        final List<RestResource> resources = new ArrayList<>();
        final String applicationId = UUID.randomUUID().toString();
        for(Map.Entry<String, Path> pathEntry : swagger.getPaths().entrySet()){
            final String resourceName = pathEntry.getKey();
            final Path resourcePath = pathEntry.getValue();
            final String resourceId = UUID.randomUUID().toString();

            final List<RestMethod> methods = new ArrayList<>();
            if(resourcePath.getGet() != null){
                Operation operation = resourcePath.getGet();
                RestMethod restMethod = createRestMethod(operation, definitions, HttpMethod.GET, forwardAddress, resourceId, generateResponse);
                methods.add(restMethod);
            }
            if(resourcePath.getPost() != null){
                Operation operation = resourcePath.getPost();
                RestMethod restMethod = createRestMethod(operation, definitions, HttpMethod.POST,
                        forwardAddress, resourceId, generateResponse);
                methods.add(restMethod);
            }
            if(resourcePath.getPut() != null){
                Operation operation = resourcePath.getPut();
                RestMethod restMethod = createRestMethod(operation, definitions, HttpMethod.PUT,
                        forwardAddress, resourceId, generateResponse);
                methods.add(restMethod);
            }
            if(resourcePath.getDelete() != null){
                Operation operation = resourcePath.getDelete();
                RestMethod restMethod = createRestMethod(operation, definitions, HttpMethod.DELETE,
                        forwardAddress, resourceId, generateResponse);
                methods.add(restMethod);
            }
            if(resourcePath.getHead() != null){
                Operation operation = resourcePath.getHead();
                RestMethod restMethod = createRestMethod(operation, definitions, HttpMethod.HEAD,
                        forwardAddress, resourceId, generateResponse);
                methods.add(restMethod);
            }
            if(resourcePath.getOptions() != null){
                Operation operation = resourcePath.getOptions();
                RestMethod restMethod = createRestMethod(operation, definitions, HttpMethod.OPTIONS,
                        forwardAddress, resourceId, generateResponse);
                methods.add(restMethod);
            }

            resources.add(RestResource.builder()
                    .id(resourceId)
                    .applicationId(applicationId)
                    .name(resourceName)
                    .uri(resourceName)
                    .methods(methods)
                    .invokeAddress("")
                    .build());
        }

        return RestApplication.builder()
                .id(applicationId)
                .projectId(projectId)
                .name(this.getApplicationName(swagger))
                .resources(resources)
                .build();
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
                                        final String resourceId,
                                        final boolean generateResponse){

        String methodName;
        if(operation.getOperationId() != null){
            methodName = operation.getOperationId();
        } else if(operation.getSummary() != null){
            methodName = operation.getSummary();
        } else {
            methodName = httpMethod.name();
        }

        final String methodId = UUID.randomUUID().toString();
        List<RestMockResponse> mockResponses = List.of();
        if(generateResponse){
            if(!operation.getResponses().isEmpty()){
                mockResponses = new ArrayList<>(generateResponse(operation.getResponses(), definitions, methodId, operation.getProduces()));
            } else {
                RestMockResponse generatedResponse = generateResponse(methodId);
                mockResponses = List.of(generatedResponse);
            }
        }

        return RestMethod.builder()
                .id(methodId)
                .resourceId(resourceId)
                .name(methodName)
                .httpMethod(httpMethod)
                .status(RestMethodStatus.MOCKED)
                .responseStrategy(RestResponseStrategy.SEQUENCE)
                .forwardedEndpoint(forwardAddress)
                .mockResponses(mockResponses)
                .simulateNetworkDelay(false)
                .networkDelay(0L)
                .automaticForward(false)
                .build();

    }

    /**
     * The method generates a default response.
     * @param responses The Swagger response definitions
     * @return The newly generated {@link RestMockResponse}.
     */
    private Collection<RestMockResponse> generateResponse(final Map<String,Response> responses,
                                                          final Map<String, Model> definitions,
                                                          final String methodId,
                                                          final List<String> produces){
        if(produces == null){
            return Collections.emptyList();
        }

        final List<RestMockResponse> mockResponses = new ArrayList<>();
        for(Map.Entry<String, Response> responseEntry : responses.entrySet()){
            Map<String, String> bodies = new HashMap<String, String>();
            Response response = responseEntry.getValue();
            for(String produce : produces){
                String body = null;
                if(APPLICATION_XML.equalsIgnoreCase(produce)){
                    body = generateXmlBody(response, definitions)
                            .orElse(null);
                } else if(APPLICATION_JSON.equalsIgnoreCase(produce)){
                    body = generateJsonBody(response, definitions);
                }
                if(body != null && !body.isEmpty()){
                    bodies.put(produce, body);
                }
            }

            int httpStatusCode = extractHttpStatusCode(responseEntry.getKey());

            if(bodies.isEmpty()){
                RestMockResponse restMockResponse = generateResponse(httpStatusCode, methodId, response);
                mockResponses.add(restMockResponse);
            } else {
                for(Map.Entry<String, String> bodyEntry : bodies.entrySet()){
                    final String contentType = bodyEntry.getKey();
                    final String body = bodyEntry.getValue();
                    final RestMockResponse restMockResponse = generateResponse(httpStatusCode,methodId, response);
                    restMockResponse.setName(restMockResponse.getName() + " (" + contentType + ")");
                    restMockResponse.setBody(body);

                    final HttpHeader httpHeader = HttpHeader.builder()
                            .name(CONTENT_TYPE)
                            .value(contentType)
                            .build();
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
    private RestMockResponse generateResponse(final int httpStatusCode, final String methodId, final Response response){

        final RestMockResponseStatus status;
        if(httpStatusCode == DEFAULT_RESPONSE_CODE){
            status = RestMockResponseStatus.ENABLED;
        } else {
            status = RestMockResponseStatus.DISABLED;
        }

        final List<HttpHeader> headers = new ArrayList<>();
        if(response.getHeaders() != null){
            for(Map.Entry<String, Property> headerEntry : response.getHeaders().entrySet()){
                final String headerName = headerEntry.getKey();
                final HttpHeader httpHeader = HttpHeader.builder()
                        .name(headerName)
                        .value("")
                        .build();
                // Swagger does not include an example value for the response.
                headers.add(httpHeader);
            }
        }
        return RestMockResponse.builder()
                .id(IdUtility.generateId())
                .methodId(methodId)
                .name(response.getDescription())
                .httpStatusCode(httpStatusCode)
                .usingExpressions(true)
                .status(status)
                .httpHeaders(headers)
                .build();
    }


    /**
     * The method provides the functionality to generate an XML body based on a provided {@link Response}
     * and a list of {@link Model}s that might be required.
     * @param response The Swagger response which the XML body will be based on.
     * @param definitions Definitions of Swagger models
     * @return An XML response body.
     * @since 1.13
     */
    @SuppressWarnings("deprecation")
    private Optional<String> generateXmlBody(final Response response, final Map<String, Model> definitions){
        // TODO Investigate deprecated schema
        final Property schema = response.getSchema();
        if(schema == null){
            return Optional.empty();
        }

        final StringWriter stringWriter = new StringWriter();
        try {
            final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            final Document document = docBuilder.newDocument();
            getXmlElement(null, schema, definitions, document)
                    .ifPresent(document::appendChild);

            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
            final DOMSource source = new DOMSource(document);

            final StreamResult result = new StreamResult(stringWriter);
            transformer.transform(source, result);
            return Optional.of(stringWriter.toString());
        } catch (Exception e){
            LOGGER.error("Unable to generate a XML response body", e);
        }

        return Optional.of(stringWriter.toString());
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
    private Optional<Element> getXmlElement(String name, final Property property, final Map<String, Model> definitions,
                                  final Document document) {
        Element element = null;
        if(name == null){
            name = property.getType();
        }

        if(property instanceof RefProperty refProperty){
            final String simpleRef = refProperty.getSimpleRef();
            final Model model = definitions.get(simpleRef);

            if(model == null){
                LOGGER.warn("Unable to find the following definition in the Swagger file: " + simpleRef);
                return Optional.empty();
            }
            element = getXmlElement(model, definitions, document);
        } else if(property instanceof ArrayProperty arrayProperty){
            final Property item = arrayProperty.getItems();
            final int maxItems = getMaxItems(arrayProperty.getMaxItems());
            element = document.createElement(name);
            for(int index = 0; index < maxItems; index++){
                getXmlElement(name, item, definitions, document)
                        .ifPresent(element::appendChild);
            }
        } else {
            final String expression = getExpressionIdentifier(property)
                    .orElse(null);
            element = document.createElement(name);
            if(expression != null){
                final Text text = document.createTextNode(expression);
                element.appendChild(text);
            }
        }

        return Optional.of(element);
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
                getXmlElement(arrayModel.getType(), item, definitions, document)
                        .ifPresent(element::appendChild);
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
                getXmlElement(property.getKey(), property.getValue(), definitions, document)
                        .ifPresent(element::appendChild);
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
    @SuppressWarnings("deprecation")
    private String generateJsonBody(final Response response, final Map<String, Model> definitions){
        final StringWriter writer = new StringWriter();
        // TODO Investigate the deprecated schema
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
            String expression = getExpressionIdentifier(property)
                    .orElse(null);

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
    private Optional<String> getExpressionIdentifier(final Property property){
        ExpressionInput expressionInput = null;
        if(property instanceof IntegerProperty){
            expressionInput = new ExpressionInput(RandomIntegerExpression.IDENTIFIER);
        } else if(property instanceof LongProperty) {
            expressionInput = new ExpressionInput(RandomLongExpression.IDENTIFIER);
        } else if(property instanceof StringProperty stringProperty){
            final List<String> enumValues = stringProperty.getEnum();

            if(enumValues == null || enumValues.isEmpty()){
                expressionInput = new ExpressionInput(RandomStringExpression.IDENTIFIER);
            } else {
                expressionInput = new ExpressionInput(RandomEnumExpression.IDENTIFIER);
                final ExpressionArgumentArray arrayArgument = new ExpressionArgumentArray();
                for (String enumValue : enumValues) {
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
            return Optional.empty();
        }

        return Optional.of(ExpressionInputParser.convert(expressionInput));
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
