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

<c:url var="rest_mock_response_update_url"  value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}"><spring:message code="rest.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}"><spring:message code="rest.breadcrumb.application"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}"><spring:message code="rest.breadcrumb.resource"/></a></li>
        <li class="active"><spring:message code="rest.restmethod.header.method" arguments="${restMethod.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <div class="title">
                <h1><spring:message code="rest.restmethod.header.method" arguments="${restMethod.name}"/></h1>
            </div>
            <div class="menu" align="right">
                <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                    <a class="btn btn-success demo-button-disabled" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}/update"/>"><i class="fas fa-edit"></i> <span><spring:message code="rest.restmethod.button.update"/></span></a>
                    <a class="btn btn-primary demo-button-disabled" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}/create/response"/>"><i class="fas fa-plus-circle"></i> <span><spring:message code="rest.restmethod.button.createresponse"/></span></a>
                    <a class="btn btn-danger demo-button-disabled" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}/delete"/>"><i class="fas fa-trash"></i> <span><spring:message code="rest.restmethod.button.delete"/></span></a>
                </sec:authorize>
            </div>
        </div>
        <div class="content-summary">
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="name"><spring:message code="rest.restmethod.label.name"/></label></td>
                    <td class="column2"><label path="name">${restMethod.name}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="methodtype"><spring:message code="rest.restmethod.label.methodtype"/></label></td>
                    <td class="column2"><label path="methodtype">${restMethod.httpMethod}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="methodstatus"><spring:message code="rest.restmethod.label.methodstatus"/></label></td>
                    <td class="column2"><label path="methodstatus"><spring:message code="rest.type.restmethodstatus.${restMethod.status}"/></label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="address"><spring:message code="rest.restmethod.label.address"/></label></td>
                    <td class="column2"><label path="address">${restMethod.invokeAddress}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="responsestrategy"><spring:message code="rest.restmethod.label.restresponsestrategy"/></label></td>
                    <td class="column2"><label path="responsestrategy"><spring:message code="rest.type.responsestrategy.${restMethod.responseStrategy}"/></label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="name"><spring:message code="rest.restmethod.label.forwardedendpoint"/></label></td>
                    <td class="column2"><label path="name">${restMethod.forwardedEndpoint}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="simulateNetworkDelay"><spring:message code="rest.restmethod.label.simulatenetworkdelay"/></label></td>
                    <td class="column2"><span class="checkbox"><input type="checkbox" path="simulateNetworkDelay" ${restMethod.simulateNetworkDelay == 'true' ? 'checked' : ''} disabled /></span></td>
                </tr>
                <tr>
                    <td class="column1"><label path="networkDelay"><spring:message code="rest.restmethod.label.networkdelay"/></label></td>
                    <td class="column2"><label path="networkDelay">${restMethod.networkDelay} ms</label></td>
                </tr>
            </table>
        </div>



        <div class="panel panel-primary table-panel">
            <div class="panel-heading table-panel-heading">
                <h3 class="panel-title"><spring:message code="rest.restmethod.header.mockresponses"/></h3>
            </div>
            <c:choose>
                <c:when test="${restMethod.mockResponses.size() > 0}">
                    <form:form action="${rest_mock_response_update_url}" method="POST"  modelAttribute="restMockResponseModifierCommand">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover sortable">
                                <col width="10%">
                                <col width="50%">
                                <col width="20%">
                                <col width="20%">
                                <tr>
                                    <th><spring:message code="rest.restmethod.column.selected"/></th>
                                    <th><spring:message code="rest.restmethod.column.responsename"/></th>
                                    <th><spring:message code="rest.restmethod.column.status"/></th>
                                    <th><spring:message code="rest.restmethod.column.httpstatuscode"/></th>
                                </tr>
                                <c:forEach items="${restMethod.mockResponses}" var="restMockResponse" varStatus="loopStatus">
                                    <tr>
                                        <td><form:checkbox path="restMockResponseIds" name="${restMockResponse.id}" value="${restMockResponse.id}"/></td>
                                        <td><a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}/response/${restMockResponse.id}"/>">${restMockResponse.name}</a></td>
                                        <td><spring:message code="rest.type.restmockresponsestatus.${restMockResponse.status}"/></td>
                                        <td>${restMockResponse.httpStatusCode}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                            <form:select path="restMockResponseStatus">
                                <c:forEach items="${restMockResponseStatuses}" var="restMockResponseStatus">
                                    <form:option value="${restMockResponseStatus}"><spring:message code="rest.type.restmockresponsestatus.${restMockResponseStatus}"/></form:option>
                                </c:forEach>
                            </form:select>
                            <button class="btn btn-success demo-button-disabled" type="submit" name="action" value="update"><i class="fas fa-check-circle"></i> <span><spring:message code="rest.restmethod.button.update"/></span></button>
                            <button class="btn btn-primary demo-button-disabled" type="submit" name="action" value="duplicate"><i class="fas fa-copy"></i> <span><spring:message code="rest.restmethod.button.duplicate"/></span></button>
                            <button class="btn btn-danger demo-button-disabled" type="submit" name="action" value="delete"><i class="fas fa-trash"></i> <span><spring:message code="rest.restmethod.button.deletemockresponses"/></span></button>
                        </sec:authorize>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form:form>
                </c:when>
                <c:otherwise>
                    <spring:message code="rest.restmethod.label.noresponses"/>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="panel panel-primary table-panel">
            <div class="panel-heading table-panel-heading">
                <h3 class="panel-title"><spring:message code="rest.restmethod.header.events"/></h3>
            </div>
            <c:choose>
                <c:when test="${restEvents.size() > 0}">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover sortable">
                            <col width="10%">
                            <col width="40%">
                            <col width="25%">
                            <col width="25%">
                            <tr>
                                <th><spring:message code="rest.restmethod.column.id"/></th>
                                <th><spring:message code="rest.restmethod.column.mockedresponse"/></th>
                                <th><spring:message code="rest.restmethod.column.startdate"/></th>
                                <th><spring:message code="rest.restmethod.column.enddate"/></th>
                            </tr>
                            <c:forEach items="${restEvents}" var="event" varStatus="loopStatus">
                                <tr>
                                    <td><a href="<c:url value="/web/rest/event/${event.id}"/>">${event.id}</a></td>
                                    <td><a href="<c:url value="/web/rest/event/${event.id}"/>">${event.response.mockResponseName}</a></td>
                                    <td><a href="<c:url value="/web/rest/event/${event.id}"/>">${event.startDate}</a></td>
                                    <td><a href="<c:url value="/web/rest/event/${event.id}"/>">${event.endDate}</a></td>
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
    </section>
</div>