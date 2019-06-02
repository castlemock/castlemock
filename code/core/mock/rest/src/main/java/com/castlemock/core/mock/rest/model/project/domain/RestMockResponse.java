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

package com.castlemock.core.mock.rest.model.project.domain;

import com.castlemock.core.basis.model.http.domain.ContentEncoding;
import com.castlemock.core.basis.model.http.domain.HttpHeader;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
@XmlRootElement
public class RestMockResponse {

    private String id;
    private String name;
    private String body;
    private String methodId;
    private Integer httpStatusCode;
    private RestMockResponseStatus status;
    private boolean usingExpressions;
    private List<HttpHeader> httpHeaders = new CopyOnWriteArrayList<HttpHeader>();
    private List<ContentEncoding> contentEncodings = new CopyOnWriteArrayList<ContentEncoding>();
    private List<RestParameterQuery> parameterQueries = new CopyOnWriteArrayList<RestParameterQuery>();
    private List<RestXPathExpression> xpathExpressions = new CopyOnWriteArrayList<RestXPathExpression>();
    private List<RestJsonPathExpression> jsonPathExpressions = new CopyOnWriteArrayList<RestJsonPathExpression>();
    private List<RestHeaderQuery> headerQueries = new CopyOnWriteArrayList<RestHeaderQuery>();

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @XmlElement
    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    @XmlElement
    public RestMockResponseStatus getStatus() {
        return status;
    }

    public void setStatus(RestMockResponseStatus status) {
        this.status = status;
    }

    @XmlElement
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    @XmlElement
    public boolean isUsingExpressions() {
        return usingExpressions;
    }

    public void setUsingExpressions(boolean usingExpressions) {
        this.usingExpressions = usingExpressions;
    }

    @XmlElementWrapper(name = "httpHeaders")
    @XmlElement(name = "httpHeader")
    public List<HttpHeader> getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(List<HttpHeader> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    @XmlElementWrapper(name = "contentEncodings")
    @XmlElement(name = "contentEncoding")
    public List<ContentEncoding> getContentEncodings() {
        return contentEncodings;
    }

    public void setContentEncodings(List<ContentEncoding> contentEncodings) {
        this.contentEncodings = contentEncodings;
    }

    @XmlElementWrapper(name = "parameterQueries")
    @XmlElement(name = "parameterQuery")
    public List<RestParameterQuery> getParameterQueries() {
        return parameterQueries;
    }

    public void setParameterQueries(List<RestParameterQuery> parameterQueries) {
        this.parameterQueries = parameterQueries;
    }


    @XmlElementWrapper(name = "xpathExpressions")
    @XmlElement(name = "xpathExpression")
    public List<RestXPathExpression> getXpathExpressions() {
        return xpathExpressions;
    }

    public void setXpathExpressions(List<RestXPathExpression> xpathExpressions) {
        this.xpathExpressions = xpathExpressions;
    }


    @XmlElementWrapper(name = "jsonPathExpressions")
    @XmlElement(name = "jsonPathExpression")
    public List<RestJsonPathExpression> getJsonPathExpressions() {
        return jsonPathExpressions;
    }

    public void setJsonPathExpressions(List<RestJsonPathExpression> jsonPathExpressions) {
        this.jsonPathExpressions = jsonPathExpressions;
    }

    @XmlElementWrapper(name = "headerQueries")
    @XmlElement(name = "headerQueries")
    public List<RestHeaderQuery> getHeaderQueries() {
        return headerQueries;
    }

    public void setHeaderQueries(List<RestHeaderQuery> headerQueries) {
        this.headerQueries = headerQueries;
    }

}
