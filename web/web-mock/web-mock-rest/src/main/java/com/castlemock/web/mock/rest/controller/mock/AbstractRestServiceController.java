/*
 * Copyright 2015 Karl Dahlgren
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

package com.castlemock.web.mock.rest.controller.mock;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.http.HttpHeader;
import com.castlemock.model.core.http.HttpMethod;
import com.castlemock.model.core.http.HttpParameter;
import com.castlemock.model.core.utility.IdUtility;
import com.castlemock.model.core.utility.JsonPathUtility;
import com.castlemock.model.core.utility.XPathUtility;
import com.castlemock.model.core.utility.parser.ExternalInputBuilder;
import com.castlemock.model.core.utility.parser.TextParser;
import com.castlemock.model.core.utility.parser.expression.argument.ExpressionArgument;
import com.castlemock.model.mock.rest.domain.RestEvent;
import com.castlemock.model.mock.rest.domain.RestMethod;
import com.castlemock.model.mock.rest.domain.RestMethodStatus;
import com.castlemock.model.mock.rest.domain.RestMockResponse;
import com.castlemock.model.mock.rest.domain.RestMockResponseStatus;
import com.castlemock.model.mock.rest.domain.RestRequest;
import com.castlemock.model.mock.rest.domain.RestResponse;
import com.castlemock.model.mock.rest.domain.RestResponseStrategy;
import com.castlemock.service.mock.rest.event.input.CreateRestEventInput;
import com.castlemock.service.mock.rest.project.input.CreateRestMockResponseInput;
import com.castlemock.service.mock.rest.project.input.IdentifyRestMethodInput;
import com.castlemock.service.mock.rest.project.input.UpdateCurrentRestMockResponseSequenceIndexInput;
import com.castlemock.service.mock.rest.project.input.UpdateRestMethodsStatusInput;
import com.castlemock.service.mock.rest.project.output.IdentifyRestMethodOutput;
import com.castlemock.web.core.controller.AbstractController;
import com.castlemock.web.core.utility.HttpMessageSupport;
import com.castlemock.web.mock.rest.model.RestException;
import com.castlemock.web.mock.rest.utility.RestHeaderQueryValidator;
import com.castlemock.web.mock.rest.utility.RestParameterQueryValidator;
import com.castlemock.web.mock.rest.utility.compare.RestMockResponseNameComparator;
import com.google.common.base.Preconditions;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The abstract REST controller is a base class shared among all the REST service classes.
 *
 * @author Karl Dahlgren
 * @since 1.0
 */
public abstract class AbstractRestServiceController extends AbstractController {

    private static final String REST = "rest";
    private static final String APPLICATION = "application";
    private static final String CONTENT_ENCODING = "Content-Encoding";
    private static final String RECORDED_RESPONSE_NAME = "Recorded response";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRestServiceController.class);

    private static final RestMockResponseNameComparator MOCK_RESPONSE_NAME_COMPARATOR =
            new RestMockResponseNameComparator();

    private final ServletContext servletContext;
    private final RestClient restClient;

    protected AbstractRestServiceController(final ServiceProcessor serviceProcessor,
                                            final ServletContext servletContext,
                                            final RestClient restClient){
        super(serviceProcessor);
        this.servletContext = Objects.requireNonNull(servletContext);
        this.restClient = restClient;
    }

    /**
     * @param projectId           The id of the project which the incoming request and mocked response belongs to
     * @param applicationId       The id of the application which the incoming and mocked response belongs to
     * @param httpMethod          The request method
     * @param httpServletRequest  The incoming request
     * @param httpServletResponse The outgoing response
     * @return Returns the response as an String
     */
    protected ResponseEntity<String> process(final String projectId,
                                             final String applicationId,
                                             final HttpMethod httpMethod,
                                             final HttpServletRequest httpServletRequest,
                                             final HttpServletResponse httpServletResponse) {
        try {
            Preconditions.checkNotNull(projectId, "The project id cannot be null");
            Preconditions.checkNotNull(applicationId, "The application id cannot be null");
            Preconditions.checkNotNull(httpMethod, "The REST method cannot be null");
            Preconditions.checkNotNull(httpServletRequest, "The HTTP Servlet Request cannot be null");
            Preconditions.checkNotNull(httpServletResponse, "The HTTP Servlet Response cannot be null");
            final RestRequest restRequest = prepareRequest(projectId, applicationId, httpMethod, httpServletRequest);

            final IdentifyRestMethodOutput output = serviceProcessor.process(IdentifyRestMethodInput.builder()
                    .restProjectId(projectId)
                    .restApplicationId(applicationId)
                    .restResourceUri(restRequest.getUri())
                    .httpMethod(httpMethod)
                    .httpParameters(restRequest.getHttpParameters())
                    .build());
            final String resourceId = output.getRestResourceId();

            return process(restRequest, projectId, applicationId, resourceId,
                    output.getRestMethod(), output.getPathParameters(),
                    httpServletRequest);
        } catch (Exception exception) {
            LOGGER.error("REST service exception: " + exception.getMessage(), exception);
            throw new RestException(exception);
        }
    }

    /**
     * The method prepares a request
     *
     * @param projectId          The id of the project that the incoming request belongs to
     * @param applicationId      The id of the application that the incoming request belongs to
     * @param httpMethod         The HTTP method that should be mocked, such as GET or POST
     * @param httpServletRequest The incoming request
     * @return A new created project
     */
    private RestRequest prepareRequest(final String projectId,
                                       final String applicationId,
                                       final HttpMethod httpMethod,
                                       final HttpServletRequest httpServletRequest) {

        final String body = HttpMessageSupport.getBody(httpServletRequest);
        final String incomingRequestUri = httpServletRequest.getRequestURI();
        final String restResourceUri = incomingRequestUri.replace(servletContext.getContextPath() + SLASH + MOCK + SLASH + REST +
                SLASH + PROJECT + SLASH + projectId + SLASH + APPLICATION + SLASH + applicationId, EMPTY);
        final List<HttpParameter> httpParameters = HttpMessageSupport.extractParameters(httpServletRequest);
        final List<HttpHeader> httpHeaders = HttpMessageSupport.extractHttpHeaders(httpServletRequest);
        return RestRequest.builder()
                .body(body)
                .uri(restResourceUri)
                .httpMethod(httpMethod)
                .httpHeaders(httpHeaders)
                .httpParameters(httpParameters)
                .contentType(httpServletRequest.getContentType())
                .build();
    }

    /**
     * The process method provides the functionality to process an incoming request. The request will be identified
     * and a corresponding action will be applied for the request. The following actions are support:
     * Forward, record, mock or disable.
     *
     * @param restRequest         The incoming request
     * @param projectId           The id of the project that the incoming request belongs to
     * @param applicationId       The id of the application that the incoming request belongs to
     * @param resourceId          The id of the resource that the incoming request belongs to
     * @param restMethod          The REST method which the incoming request belongs to
     * @return A response in String format
     */
    protected ResponseEntity<String> process(final RestRequest restRequest,
                                             final String projectId,
                                             final String applicationId,
                                             final String resourceId,
                                             final RestMethod restMethod,
                                             final Map<String, Set<String>> pathParameters,
                                             final HttpServletRequest httpServletRequest) {
        Preconditions.checkNotNull(restRequest, "Rest request cannot be null");
        final Date startDate = new Date();
        RestResponse response = null;
        try {
            if (RestMethodStatus.DISABLED.equals(restMethod.getStatus())) {
                throw new RestException("The requested REST method, " + restMethod.getName() + ", is disabled");
            } else if (RestMethodStatus.FORWARDED.equals(restMethod.getStatus())) {
                response = forwardRequest(restRequest, restMethod);
            } else if (RestMethodStatus.RECORDING.equals(restMethod.getStatus())) {
                response = forwardRequestAndRecordResponse(restRequest, projectId, applicationId, resourceId, restMethod);
            } else if (RestMethodStatus.RECORD_ONCE.equals(restMethod.getStatus())) {
                response = forwardRequestAndRecordResponseOnce(restRequest, projectId, applicationId, resourceId, restMethod);
            } else if (RestMethodStatus.ECHO.equals(restMethod.getStatus())) {
                response = echoResponse(restRequest);
            } else { // Status.MOCKED
                response = mockResponse(restRequest, projectId, applicationId, resourceId, restMethod, pathParameters, httpServletRequest);
            }

            final HttpHeaders responseHeaders = new HttpHeaders();

            response.getHttpHeaders()
                    .stream()
                    .filter(httpHeader -> !httpHeader.getName().equalsIgnoreCase(CONTENT_ENCODING))
                    .forEach(httpHeader -> {
                        final List<String> headerValues = new LinkedList<>();
                        headerValues.add(httpHeader.getValue());
                        responseHeaders.put(httpHeader.getName(), headerValues);
                    });

            if (restMethod.getSimulateNetworkDelay().orElse(false) &&
                    restMethod.getNetworkDelay().isPresent()) {
                try {
                    Thread.sleep(restMethod.getNetworkDelay().get());
                } catch (InterruptedException e) {
                    LOGGER.error("Unable to simulate network delay", e);
                }
            }

            return new ResponseEntity<>(response.getBody().orElse(null), responseHeaders,
                    HttpStatus.valueOf(response.getHttpStatusCode()));
        } finally {
            final RestEvent event = RestEvent.builder()
                    .id(IdUtility.generateId())
                    .resourceName(restMethod.getName())
                    .request(restRequest)
                    .response(response)
                    .projectId(projectId)
                    .applicationId(applicationId)
                    .resourceId(resourceId)
                    .methodId(restMethod.getId())
                    .startDate(startDate)
                    .endDate(new Date())
                    .build();
            serviceProcessor.processAsync(CreateRestEventInput.builder()
                    .restEvent(event)
                    .build());
        }
    }

    /**
     * The method provides the functionality to forward a request to another endpoint
     *
     * @param request    The incoming request
     * @param restMethod The REST method which the incoming request belongs to
     * @return The response received from the external endpoint
     */
    private RestResponse forwardRequest(final RestRequest request,
                                        final RestMethod restMethod) {
        return restClient.getResponse(request, restMethod).orElseThrow(() -> new RestException("Unable to forward request to configured endpoint"));
    }

    /**
     * The method provides the functionality to forward a request to another endpoint. The response
     * will be recorded and can later be used as a mocked response
     *
     * @param restRequest The incoming request
     * @param restMethod  The REST method which the incoming request belongs to
     * @return The response received from the external endpoint
     */
    private RestResponse forwardRequestAndRecordResponse(final RestRequest restRequest,
                                                         final String projectId,
                                                         final String applicationId,
                                                         final String resourceId,
                                                         final RestMethod restMethod) {
        final RestResponse response = forwardRequest(restRequest, restMethod);
        serviceProcessor.processAsync(CreateRestMockResponseInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(restMethod.getId())
                .name(RECORDED_RESPONSE_NAME + SPACE + DATE_FORMAT.format(new Date()))
                .body(response.getBody().orElse(null))
                .methodId(restMethod.getId())
                .status(RestMockResponseStatus.ENABLED)
                .httpHeaders(response.getHttpHeaders())
                .httpStatusCode(response.getHttpStatusCode())
                .usingExpressions(Boolean.FALSE)
                .build());
        return response;
    }

    /**
     * The method provides the functionality to forward a request to another endpoint. The response
     * will be recorded and can later be used as a mocked response. The REST method status will be updated
     * to mocked.
     *
     * @param restRequest   The incoming request
     * @param projectId     The id of the project that the incoming request belongs to
     * @param applicationId The id of the application that the incoming request belongs to
     * @param resourceId    The id of the resource that the incoming request belongs to
     * @param restMethod    The REST method which the incoming request belongs to
     * @return The response received from the external endpoint
     */
    private RestResponse forwardRequestAndRecordResponseOnce(final RestRequest restRequest,
                                                             final String projectId,
                                                             final String applicationId,
                                                             final String resourceId,
                                                             final RestMethod restMethod) {
        final RestResponse response =
                forwardRequestAndRecordResponse(restRequest, projectId,
                        applicationId, resourceId, restMethod);
        serviceProcessor.process(UpdateRestMethodsStatusInput.builder()
                .projectId(projectId)
                .applicationId(applicationId)
                .resourceId(resourceId)
                .methodId(restMethod.getId())
                .methodStatus(RestMethodStatus.MOCKED)
                .build());
        return response;
    }

    /**
     * The method will echo the incoming {@link RestRequest} and create a {@link RestResponse}
     * with the same body, content type and headers.
     *
     * @param request The incoming {@link RestRequest} that will be echoed back to the
     *                service consumer.
     * @return A {@link RestResponse} based on the provided {@link RestRequest}.
     * @since 1.14
     */
    private RestResponse echoResponse(final RestRequest request) {



        final List<HttpHeader> headers = request.getContentType()
                .map(contentType -> List.of(HttpHeader.builder()
                        .name(CONTENT_TYPE)
                        .value(contentType)
                        .build()))
                .orElseGet(List::of);

        return RestResponse.builder()
                .body(request.getBody().orElse(null))
                .contentType(request.getContentType().orElse(null))
                .httpHeaders(headers)
                .httpStatusCode(DEFAULT_ECHO_RESPONSE_CODE)
                .build();
    }

    /**
     * The method is responsible for mocking a REST service. The method will identify which mocked response
     * will be returned.
     *
     * @param restMethod The REST method which the incoming request belongs to
     * @return Returns a selected mocked response which will be returned to the service consumer
     */
    protected RestResponse mockResponse(final RestRequest restRequest,
                                        final String projectId,
                                        final String applicationId,
                                        final String resourceId,
                                        final RestMethod restMethod,
                                        final Map<String, Set<String>> pathParameters,
                                        final HttpServletRequest httpServletRequest) {
        // Extract the accept-header value.
        final Collection<String> acceptHeaderValues = getHeaderValues(ACCEPT_HEADER, restRequest.getHttpHeaders());


        // Iterate through all mocked responses and extract the ones
        // that are active.
        final List<RestMockResponse> enabledMockResponses = new ArrayList<>();
        for (RestMockResponse mockResponse : restMethod.getMockResponses()) {
            if (mockResponse.getStatus().equals(RestMockResponseStatus.ENABLED)) {
                enabledMockResponses.add(mockResponse);
            }
        }

        enabledMockResponses.sort(MOCK_RESPONSE_NAME_COMPARATOR);

        final List<RestMockResponse> mockResponses = new ArrayList<>();

        if (acceptHeaderValues != null) {
            // Find request that matches the accept-header.
            for (RestMockResponse mockResponse : enabledMockResponses) {
                // The accept header has to match the content type.
                final Collection<String> mockResponseContentTypeValues = getHeaderValues(CONTENT_TYPE, mockResponse.getHttpHeaders());
                mockResponseContentTypeValues.retainAll(acceptHeaderValues);
                if (!mockResponseContentTypeValues.isEmpty()) {
                    mockResponses.add(mockResponse);
                }
            }
        }

        // If no mock responses are matching the accept-header, then
        // any mock response will do.
        if (mockResponses.isEmpty()) {
            mockResponses.addAll(enabledMockResponses);
        }

        RestMockResponse mockResponse = null;
        if (mockResponses.isEmpty()) {
            throw new RestException("No mocked response created for operation " + restMethod.getName());
        } else if (restMethod.getResponseStrategy().equals(RestResponseStrategy.RANDOM)) {
            final int responseIndex = RANDOM.nextInt(mockResponses.size());
            mockResponse = mockResponses.get(responseIndex);
        } else if (restMethod.getResponseStrategy().equals(RestResponseStrategy.SEQUENCE)) {
            Integer currentSequenceNumber = restMethod.getCurrentResponseSequenceIndex();
            if (currentSequenceNumber >= mockResponses.size()) {
                currentSequenceNumber = 0;
            }
            mockResponse = mockResponses.get(currentSequenceNumber);
            serviceProcessor.process(UpdateCurrentRestMockResponseSequenceIndexInput.builder()
                    .restProjectId(projectId)
                    .restApplicationId(applicationId)
                    .restResourceId(resourceId)
                    .restMethodId(restMethod.getId())
                    .currentRestMockResponseSequenceIndex(currentSequenceNumber + 1)
                    .build());
        } else if (restMethod.getResponseStrategy().equals(RestResponseStrategy.QUERY_MATCH)) {
            mockResponse = mockResponses.stream()
                    .filter(tmp -> RestParameterQueryValidator.validate(tmp.getParameterQueries(), pathParameters))
                    .findFirst()
                    .orElse(null);

            if (mockResponse == null) {
                LOGGER.info("Unable to match the input Query to a response");
                mockResponse = this.getDefaultMockResponse(restMethod, mockResponses).orElse(null);
            }
        } else if (restMethod.getResponseStrategy().equals(RestResponseStrategy.XPATH)) {
            mockResponse = restRequest.getBody()
                    .flatMap(body -> mockResponses.stream()
                            .filter(testedMockResponse -> testedMockResponse.getXpathExpressions()
                                    .stream()
                                    .anyMatch(xPathExpression -> XPathUtility.isValidXPathExpr(body, xPathExpression.getExpression())))
                            .findFirst())
                    .orElse(null);

            if (mockResponse == null) {
                LOGGER.info("Unable to match the input XPath to a response");
                mockResponse = this.getDefaultMockResponse(restMethod, mockResponses).orElse(null);
            }

        } else if (restMethod.getResponseStrategy().equals(RestResponseStrategy.JSON_PATH)) {
            mockResponse = restRequest.getBody()
                    .flatMap(body -> mockResponses.stream()
                            .filter(testedMockResponse -> testedMockResponse.getJsonPathExpressions()
                                    .stream()
                                    .anyMatch(jsonPathExpression -> JsonPathUtility.isValidJsonPathExpr(body, jsonPathExpression.getExpression())))
                            .findFirst())
                    .orElse(null);

            if (mockResponse == null) {
                LOGGER.info("Unable to match the input JSON Path to a response");
                mockResponse = this.getDefaultMockResponse(restMethod, mockResponses).orElse(null);
            }

        } else if (restMethod.getResponseStrategy().equals(RestResponseStrategy.HEADER_QUERY_MATCH)) {
            mockResponse = mockResponses.stream()
                    .filter(tmp -> RestHeaderQueryValidator.validate(tmp.getHeaderQueries(), restRequest.getHttpHeaders()))
                    .findFirst()
                    .orElse(null);

            if (mockResponse == null) {
                LOGGER.info("Unable to match the input Query to a response");
                mockResponse = this.getDefaultMockResponse(restMethod, mockResponses).orElse(null);
            }
        }

        if (mockResponse == null && restMethod.getAutomaticForward().orElse(false) && restMethod.getForwardedEndpoint().isPresent()) {
            return forwardRequest(restRequest, restMethod);
        }

        if (mockResponse == null) {
            throw new RestException("No mocked response created for operation " + restMethod.getName());
        }

        String body = mockResponse.getBody().orElse(null);
        if (mockResponse.getUsingExpressions()) {
            final Map<String, ExpressionArgument<?>> externalInput = new ExternalInputBuilder()
                    .pathParameters(pathParameters)
                    .queryStringParameters(restRequest.getHttpParameters())
                    .requestUrl(httpServletRequest.getRequestURL().toString())
                    .requestBody(restRequest.getBody().orElse(null))
                    .build();

            // Parse the text and apply expression functionality if
            // the mock response is configured to use expressions
            body = new TextParser().parse(body, externalInput)
                    .orElse("");
        }
        return RestResponse.builder()
                .body(body)
                .mockResponseName(mockResponse.getName())
                .httpStatusCode(mockResponse.getHttpStatusCode())
                .httpHeaders(mockResponse.getHttpHeaders())
                .contentEncodings(mockResponse.getContentEncodings())
                .build();
    }

    private Optional<RestMockResponse> getDefaultMockResponse(final RestMethod restMethod,
                                                              final List<RestMockResponse> mockResponses) {
        return restMethod.getDefaultMockResponseId()
                .flatMap(defaultMockResponseId -> mockResponses.stream()
                        .filter(tmpMockResponse-> tmpMockResponse.getId().equals(defaultMockResponseId))
                        .findFirst());
    }

    /**
     * The method returns a list of values for a header ({@link HttpHeader}).
     *
     * @param headerName  The name of the header which value will be extracted and returned.
     * @param httpHeaders A list of {@link HttpHeader}s
     * @return A list of HTTP header values
     * @since 1.13
     */
    private Collection<String> getHeaderValues(final String headerName,
                                               final List<HttpHeader> httpHeaders) {
        return httpHeaders
                .stream()
                .filter(header -> headerName.equalsIgnoreCase(header.getName()))
                .findFirst()
                .map(HttpHeader::getValue)
                .map(header -> header.split(","))
                .map(Stream::of)
                .map(stream -> stream
                        .map(String::toLowerCase)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

}
