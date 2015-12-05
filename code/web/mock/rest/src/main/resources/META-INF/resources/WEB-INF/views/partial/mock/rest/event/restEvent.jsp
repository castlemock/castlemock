<%@ include file="../../../../includes.jspf"%>
<%--
  ~ Copyright 2015 Karl Dahlgren
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~   http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<div class="content-top">
    <h1><spring:message code="rest.event.header.log" arguments="${event.id}"/></h1>
</div>

<div class="content-summary">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="rest.event.column.id"/></label></td>
            <td class="column2"><label path="name">${event.id}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="rest.event.column.startdate"/></label></td>
            <td class="column2"><label path="name">${event.startDate}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="rest.event.column.enddate"/></label></td>
            <td class="column2"><label path="name">${event.endDate}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="rest.event.column.request.uri"/></label></td>
            <td class="column2"><label path="name">${event.restRequest.uri}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="rest.event.column.request.methodtype"/></label></td>
            <td class="column2"><label path="name">${event.restRequest.restMethodType}</label></td>
        </tr>
    </table>
</div>

<div>
    <h2 class="decorated"><span><spring:message code="rest.event.header.request"/></span></h2>
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="rest.event.column.request.contenttype"/></label></td>
            <td class="column2"><label path="name">${event.restRequest.contentType}</label></td>
        </tr>
    </table>
    <div class="eventMessage">
        <textarea id="requestBody" readonly><c:out value="${event.restRequest.body}"/></textarea>
    </div>
</div>

<div>
    <h2 class="decorated"><span><spring:message code="rest.event.header.response"/></span></h2>
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="rest.event.column.response.statuscode"/></label></td>
            <td class="column2"><label path="name">${event.restResponse.httpStatusCode}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="rest.event.column.response.contenttype"/></label></td>
            <td class="column2"><label path="name">${event.restResponse.restContentType.contentType}</label></td>
        </tr>
    </table>
    <div class="eventMessage">
        <textarea id="responseBody" readonly><c:out value="${event.restResponse.body}"/></textarea>
    </div>
</div>

