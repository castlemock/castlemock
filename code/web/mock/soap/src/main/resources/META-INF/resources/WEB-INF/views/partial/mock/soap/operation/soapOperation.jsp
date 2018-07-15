<%--
 Copyright 2016 Karl Dahlgren

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>

<%@ include file="../../../../includes.jspf"%>
<c:url var="soap_mock_response_update_url"  value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperation.id}" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/soap/project/${soapProjectId}"><spring:message code="soap.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/soap/project/${soapProjectId}/port/${soapPortId}"><spring:message code="soap.breadcrumb.port"/></a></li>
        <li class="active"><spring:message code="soap.soapoperation.header.operation" arguments="${soapOperation.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <div class="title">
                <h1><spring:message code="soap.soapoperation.header.operation" arguments="${soapOperation.name}"/></h1>
            </div>
            <div class="menu" align="right">
                <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                    <a class="btn btn-success demo-button-disabled" href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperation.id}/update"/>"><i class="fas fa-edit"></i> <span><spring:message code="soap.soapoperation.button.updateoperation"/></span></a>
                    <a class="btn btn-primary demo-button-disabled" href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperation.id}/create/response"/>"><i class="fas fa-plus-circle"></i> <span><spring:message code="soap.soapoperation.button.createresponse"/></span></a>
                </sec:authorize>
            </div>
        </div>

        <div class="content-summary">
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="name"><spring:message code="soap.soapoperation.label.identifier"/></label></td>
                    <td class="column2"><label path="name">${soapOperation.operationIdentifier.name}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="name"><spring:message code="soap.soapoperation.label.soapversion"/></label></td>
                    <td class="column2"><label path="name">${soapOperation.soapVersion.name}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="status"><spring:message code="soap.soapoperation.label.status"/></label></td>
                    <td class="column2"><label path="status"><spring:message code="soap.type.soapoperationstatus.${soapOperation.status}"/></label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="soapResponseStrategy"><spring:message code="soap.soapoperation.label.responsestrategy"/></label></td>
                    <td class="column2"><label path="soapResponseStrategy"><spring:message code="soap.type.responsestrategy.${soapOperation.responseStrategy}"/></label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="invokeAddress"><spring:message code="soap.soapoperation.label.method"/></label></td>
                    <td class="column2"><label path="invokeAddress">${soapOperation.httpMethod}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="invokeAddress"><spring:message code="soap.soapoperation.label.address"/></label></td>
                    <td class="column2"><label path="invokeAddress">${soapOperation.invokeAddress}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="originalEndpoint"><spring:message code="soap.soapoperation.label.originalendpoint"/></label></td>
                    <td class="column2"><label path="originalEndpoint">${soapOperation.originalEndpoint}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="forwardedEndpoint"><spring:message code="soap.soapoperation.label.forwardedendpoint"/></label></td>
                    <td class="column2"><label path="forwardedEndpoint">${soapOperation.forwardedEndpoint}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="simulateNetworkDelay"><spring:message code="soap.soapoperation.label.simulatenetworkdelay"/></label></td>
                    <td class="column2"><span class="checkbox"><input type="checkbox" path="simulateNetworkDelay" ${soapOperation.simulateNetworkDelay == 'true' ? 'checked' : ''} disabled /></span></td>
                </tr>
                <tr>
                    <td class="column1"><label path="networkDelay"><spring:message code="soap.soapoperation.label.networkdelay"/></label></td>
                    <td class="column2"><label path="networkDelay">${soapOperation.networkDelay} ms</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="defaultXPathResponse"><spring:message code="soap.soapoperation.label.defaultxpathresponse"/></label></td>
                    <td class="column2"><label path="defaultXPathResponse">${soapOperation.defaultXPathResponseName}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="mockOnFailure"><spring:message code="soap.soapoperation.label.mockonfailure"/></label></td>
                    <td class="column2"><span class="checkbox"><input type="checkbox" path="mockOnFailure" ${soapOperation.mockOnFailure == 'true' ? 'checked' : ''} disabled /></span></td>
                </tr>
            </table>
        </div>

        <div class="panel panel-primary table-panel">
            <div class="panel-heading table-panel-heading">
                <h3 class="panel-title"><spring:message code="soap.soapoperation.header.mockresponses"/></h3>
            </div>
            <c:choose>
                <c:when test="${soapOperation.mockResponses.size() > 0}">
                    <form:form action="${soap_mock_response_update_url}" method="POST"  modelAttribute="soapMockResponseModifierCommand">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover sortable">
                                <col width="0%">
                                <col width="50%">
                                <col width="20%">
                                <col width="20%">
                                <tr>
                                    <th></th>
                                    <th><spring:message code="soap.soapoperation.column.responsename"/></th>
                                    <th><spring:message code="soap.soapoperation.column.status"/></th>
                                    <th><spring:message code="soap.soapoperation.column.httpstatuscode"/></th>
                                </tr>
                                <c:forEach items="${soapOperation.mockResponses}" var="soapMockResponse" varStatus="loopStatus">
                                    <tr>
                                        <td><form:checkbox path="soapMockResponseIds" name="${soapMockResponse.id}" value="${soapMockResponse.id}"/></td>
                                        <td><a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperation.id}/response/${soapMockResponse.id}"/>">${soapMockResponse.name}</a></td>
                                        <td><spring:message code="soap.type.soapmockresponsestatus.${soapMockResponse.status}"/></td>
                                        <td>${soapMockResponse.httpStatusCode}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <div class="panel-buttons">
                            <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                                <form:select path="soapMockResponseStatus">
                                    <c:forEach items="${soapMockResponseStatuses}" var="soapMockResponseStatus">
                                        <form:option value="${soapMockResponseStatus}"><spring:message code="soap.type.soapmockresponsestatus.${soapMockResponseStatus}"/></form:option>
                                    </c:forEach>
                                </form:select>
                                <button class="btn btn-success demo-button-disabled" type="submit" name="action" value="update"><i class="fas fa-check-circle"></i> <span><spring:message code="soap.soapoperation.button.update"/></span></button>
                                <button class="btn btn-primary demo-button-disabled" type="submit" name="action" value="duplicate"><i class="fas fa-copy"></i> <span><spring:message code="soap.soapoperation.button.duplicate"/></span></button>
                                <button class="btn btn-danger demo-button-disabled" type="submit" name="action" value="delete"><i class="fas fa-trash"></i> <span><spring:message code="soap.soapoperation.button.deletemockresponse"/></span></button>
                            </sec:authorize>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form:form>
                </c:when>
                <c:otherwise>
                    <spring:message code="soap.soapoperation.label.noresponse"/>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="panel panel-primary table-panel">
            <div class="panel-heading table-panel-heading">
                <h3 class="panel-title"><spring:message code="soap.soapoperation.header.events"/></h3>
            </div>
            <c:choose>
                <c:when test="${soapEvents.size() > 0}">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover sortable">
                            <col width="10%">
                            <col width="40%">
                            <col width="25%">
                            <col width="25%">
                            <tr>
                                <th><spring:message code="soap.soapoperation.column.id"/></th>
                                <th><spring:message code="soap.soapoperation.column.mockedresponse"/></th>
                                <th><spring:message code="soap.soapoperation.column.startdate"/></th>
                                <th><spring:message code="soap.soapoperation.column.enddate"/></th>
                            </tr>
                            <c:forEach items="${soapEvents}" var="event" varStatus="loopStatus">
                                <tr>
                                    <td><a href="<c:url value="/web/soap/event/${event.id}"/>">${event.id}</a></td>
                                    <td><a href="<c:url value="/web/soap/event/${event.id}"/>">${event.response.mockResponseName}</a></td>
                                    <td><a href="<c:url value="/web/soap/event/${event.id}"/>">${event.startDate}</a></td>
                                    <td><a href="<c:url value="/web/soap/event/${event.id}"/>">${event.endDate}</a></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <spring:message code="soap.soapoperation.label.noevent"/>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
</div>
