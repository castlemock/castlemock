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

<c:url var="rest_mock_response_update_url"  value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restMethodId}/method/${restMethod.id}" />
<div class="content-top">
    <h1><spring:message code="rest.restmethod.header.method" arguments="${restMethod.name}"/></h1>
    <div align="right">
        <sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')">
            <a class="button-success pure-button" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="rest.restmethod.button.update"/></span></a>
            <a class="button-error pure-button" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="rest.restmethod.button.delete"/></span></a>
            <a class="button-secondary pure-button" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}/create/response"/>"><i class="fa fa-file"></i> <span><spring:message code="rest.restmethod.button.createresponse"/></span></a>
        </sec:authorize>
    </div>
</div>
<div class="content-summary">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="rest.restmethod.label.name"/></label></td>
            <td class="column2"><label path="name">${restMethod.name}</label></td>
        </tr>
    </table>
</div>

<div>
    <h2 class="decorated"><span><spring:message code="rest.restmethod.header.mockresponses"/></span></h2>
    <c:choose>

        <c:when test="${restMethod.restMockResponses.size() > 0}">
            <form:form action="${rest_mock_response_update_url}" method="POST"  commandName="restMockResponseModifierCommand">
                <div class="table-frame">
                    <table class="entityTable">
                        <col width="10%">
                        <col width="10%">
                        <col width="80%">
                        <tr>
                            <th><spring:message code="rest.restmethod.column.selected"/></th>
                            <th><spring:message code="rest.restmethod.column.status"/></th>
                            <th><spring:message code="rest.restmethod.column.responsename"/></th>
                        </tr>
                        <c:forEach items="${restMethod.restMockResponses}" var="restMockResponse" varStatus="loopStatus">
                            <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                <td><form:checkbox path="restMockResponseIds" name="${restMockResponse.id}" value="${restMockResponse.id}"/></td>
                                <td><spring:message code="rest.type.restmockresponsestatus.${restMockResponse.restMockResponseStatus}"/></td>
                                <td><a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}/response/${restMockResponse.id}"/>">${restMockResponse.name}</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')">
                    <form:select path="restMockResponseStatus">
                        <c:forEach items="${restMockResponseStatuses}" var="restMockResponseStatus">
                            <form:option value="${restMockResponseStatus}"><spring:message code="rest.type.restmockresponsestatus.${restMockResponseStatus}"/></form:option>
                        </c:forEach>
                    </form:select>
                    <button class="button-success pure-button" type="submit" name="action" value="update"><i class="fa fa-check-circle"></i> <span><spring:message code="rest.restmethod.button.update"/></span></button>
                    <button class="button-error pure-button" type="submit" name="action" value="delete"><i class="fa fa-trash"></i> <span><spring:message code="rest.restmethod.button.deleteMockResponses"/></span></button>
                </sec:authorize>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form:form>
        </c:when>
        <c:otherwise>
            <spring:message code="rest.restmethod.label.noresponses"/>
        </c:otherwise>
    </c:choose>
</div>

<div>
    <h2 class="decorated"><span><spring:message code="rest.restmethod.header.events"/></span></h2>
    <c:choose>
        <c:when test="${restMethod.events.size() > 0}">
            <div class="table-frame">
                <table width="100%">
                    <tr>
                        <th><spring:message code="rest.restmethod.column.id"/></th>
                        <th><spring:message code="rest.restmethod.column.startdate"/></th>
                        <th><spring:message code="rest.restmethod.column.enddate"/></th>
                        <th><spring:message code="rest.restmethod.column.mockedresponse"/></th>
                    </tr>
                    <c:forEach items="${restMethod.events}" var="event" varStatus="loopStatus">
                        <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                            <td><a href="<c:url value="/web/rest/event/${event.id}"/>">${event.id}</a></td>
                            <td><a href="<c:url value="/web/rest/event/${event.id}"/>">${event.startDate}</a></td>
                            <td><a href="<c:url value="/web/rest/event/${event.id}"/>">${event.endDate}</a></td>
                            <td><a href="<c:url value="/web/rest/event/${event.id}"/>">${event.restResponse.mockResponseName}</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <spring:message code="rest.restmethod.label.noevent"/>
        </c:otherwise>
    </c:choose>
</div>