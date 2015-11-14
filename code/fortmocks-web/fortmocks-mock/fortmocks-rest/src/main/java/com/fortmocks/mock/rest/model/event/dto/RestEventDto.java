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

package com.fortmocks.mock.rest.model.event.dto;

import com.fortmocks.core.basis.model.event.dto.EventDto;
import org.dozer.Mapping;

import java.util.Date;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class RestEventDto extends EventDto {

    @Mapping("restRequest")
    private RestRequestDto restRequest;

    @Mapping("restResponse")
    private RestResponseDto restResponse;

    @Mapping("restMethodId")
    private Long restMethodId;

    /**
     * Default constructor for the REST event DTO
     */
    public RestEventDto() {
    }

    /**
     * Default constructor for the REST event DTO
     */
    public RestEventDto(final EventDto eventDto) {
        super(eventDto);
    }

    /**
     * Constructor for the REST event DTO
     * @param restRequest The REST request that the event is representing
     * @param restMethodId The id of the REST operation that is affected by the provided REST request
     */
    public RestEventDto(final RestRequestDto restRequest, final Long restMethodId) {
        this.restRequest = restRequest;
        this.restMethodId = restMethodId;
        setStartDate(new Date());
    }

    /**
     * The finish method is used to sent the response that was sent back, but was also
     * to set the date/time for when the event ended.
     * @param restResponse
     */
    public void finish(final RestResponseDto restResponse) {
        this.restResponse = restResponse;
        setEndDate(new Date());
    }

    public RestRequestDto getRestRequest() {
        return restRequest;
    }

    public void setRestRequest(RestRequestDto restRequest) {
        this.restRequest = restRequest;
    }

    public RestResponseDto getRestResponse() {
        return restResponse;
    }

    public void setRestResponse(RestResponseDto restResponse) {
        this.restResponse = restResponse;
    }

    public Long getRestMethodId() {
        return restMethodId;
    }

    public void setRestMethodId(Long restMethodId) {
        this.restMethodId = restMethodId;
    }
}
