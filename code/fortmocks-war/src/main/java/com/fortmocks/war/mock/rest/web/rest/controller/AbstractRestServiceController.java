package com.fortmocks.war.mock.rest.web.rest.controller;

import com.fortmocks.core.mock.rest.model.event.dto.RestEventDto;
import com.fortmocks.core.mock.rest.model.event.dto.RestRequestDto;
import com.fortmocks.core.mock.rest.model.event.dto.RestResponseDto;
import com.fortmocks.core.mock.rest.model.event.service.RestEventService;
import com.fortmocks.core.mock.rest.model.project.RestMethodStatus;
import com.fortmocks.core.mock.rest.model.project.RestMethodType;
import com.fortmocks.core.mock.rest.model.project.RestMockResponseStatus;
import com.fortmocks.core.mock.rest.model.project.RestResponseStrategy;
import com.fortmocks.core.mock.rest.model.project.dto.RestMethodDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestMockResponseDto;
import com.fortmocks.core.mock.rest.model.project.service.RestProjectService;
import com.fortmocks.war.base.web.mvc.controller.AbstractController;
import com.fortmocks.war.mock.rest.model.RestException;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Karl Dahlgren
 * @since 13.4
 */
public abstract class AbstractRestServiceController extends AbstractController {

    @Autowired
    private RestProjectService restProjectService;
    @Autowired
    private RestEventService restEventService;
    private static final String REST = "rest";
    private static final String APPLICATION = "application";
    private static final Random RANDOM = new Random();


    /**
     *
     * @param projectId The id of the project which the incoming request and mocked response belongs to
     * @param restMethodType The request method
     * @param httpServletRequest The incoming request
     * @param httpServletResponse The outgoing response
     * @return Returns the response as an String
     */
    protected String process(final Long projectId, final Long applicationId, final RestMethodType restMethodType, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){
        Preconditions.checkNotNull(projectId, "The project id cannot be null");
        Preconditions.checkNotNull(applicationId, "The application id cannot be null");
        Preconditions.checkNotNull(restMethodType, "The REST method cannot be null");
        Preconditions.checkNotNull(httpServletRequest, "The HTTP Servlet Request cannot be null");
        Preconditions.checkNotNull(httpServletResponse, "The HTTP Servlet Response cannot be null");

        final RestRequestDto restRequest = prepareRequest(projectId, applicationId, restMethodType, httpServletRequest);
        final RestMethodDto restMethod = restProjectService.findRestMethod(projectId, applicationId, restRequest.getUri(), restMethodType);

        if(restMethod == null){
            throw new RestException("Unable to locate the REST method");
        }

        return process(restRequest, restMethod);
    }

    /**
     * The method prepares an request
     * @param projectId The id of the project that the incoming request belongs to
     * @param httpServletRequest The incoming request
     * @return A new created project
     */
    protected RestRequestDto prepareRequest(final Long projectId, final Long applicationId, final RestMethodType restMethodType, final HttpServletRequest httpServletRequest) {
        final RestRequestDto request = new RestRequestDto();
        final String body = RestMessageSupport.getBody(httpServletRequest);
        final String restResourceUri = httpServletRequest.getRequestURI().toLowerCase().replace(getContext() + SLASH + MOCK + SLASH + REST + SLASH + PROJECT + SLASH + projectId + SLASH + APPLICATION + SLASH + applicationId, EMPTY);

        request.setContextType(httpServletRequest.getContentType());
        request.setBody(body);
        request.setUri(restResourceUri);
        return request;
    }


    protected String process(final RestRequestDto restRequest, final RestMethodDto restMethod){
        Preconditions.checkNotNull(restRequest, "Rest request cannot be null");
        RestEventDto event = null;
        RestResponseDto response = null;
        try {
            event = new RestEventDto(restRequest, restMethod.getId());
            if (RestMethodStatus.DISABLED.equals(restMethod.getRestMethodStatus())) {
                throw new RestException("The requested soap operation, " + restMethod.getName() + ", is disabled");
            } else if (RestMethodStatus.FORWARDED.equals(restMethod.getRestMethodStatus())) {
                response = forwardRequest(restRequest, restMethod);
            } else if (RestMethodStatus.RECORDING.equals(restMethod.getRestMethodStatus())) {
                response = forwardRequestAndRecordResponse(restRequest, restMethod);
            } else { // Status.MOCKED
                response = mockResponse(restMethod);
            }
            return response.getBody();
        } finally{
            if(event != null){
                event.finish(response);
                restEventService.save(event);
            }
        }
    }

    protected RestResponseDto forwardRequest(final RestRequestDto restRequest, final RestMethodDto restMethod){
        return null;
    }

    protected RestResponseDto forwardRequestAndRecordResponse(final RestRequestDto restRequest, final RestMethodDto restMethod){
        return null;
    }


    protected RestResponseDto mockResponse(final RestMethodDto restMethod){
        final List<RestMockResponseDto> mockResponses = new ArrayList<RestMockResponseDto>();
        for(RestMockResponseDto mockResponse : restMethod.getRestMockResponses()){
            if(mockResponse.getRestMockResponseStatus().equals(RestMockResponseStatus.ENABLED)){
                mockResponses.add(mockResponse);
            }
        }

        RestMockResponseDto mockResponse = null;
        //restProjectService.findRestMockResponse(restMethod.getId(), SoapMockResponseStatus.ENABLED);
        if(mockResponses.isEmpty()){
            throw new RestException("No mocked response created for operation " + restMethod.getName());
        } else if(restMethod.getRestResponseStrategy().equals(RestResponseStrategy.RANDOM)){
            final Integer responseIndex = RANDOM.nextInt(mockResponses.size());
            mockResponse = mockResponses.get(responseIndex);
        } else if(restMethod.getRestResponseStrategy().equals(RestResponseStrategy.SEQUENCE)){
            Integer currentSequenceNumber = restMethod.getCurrentResponseSequenceIndex();
            if(currentSequenceNumber >= mockResponses.size()){
                currentSequenceNumber = 0;
            }
            mockResponse = mockResponses.get(currentSequenceNumber);
            //restProjectService.updateCurrentResponseSequenceIndex(restMethod.getId(), currentSequenceNumber + 1);
        }

        if(mockResponse == null){
            throw new RestException("No mocked response created for operation " + restMethod.getName());
        }

        final RestResponseDto response = new RestResponseDto();
        response.setBody(mockResponse.getBody());
        response.setMockResponseName(mockResponse.getName());
        //httpServletResponse.setStatus(mockResponse.getHttpResponseCode());
        return response;
    }
}
