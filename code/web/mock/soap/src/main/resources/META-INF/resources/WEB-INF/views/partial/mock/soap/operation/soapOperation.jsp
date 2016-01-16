<%@ include file="../../../../includes.jspf"%>
<c:url var="soap_mock_response_update_url"  value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperation.id}" />
<div class="content-top">
    <h1><spring:message code="soap.soapoperation.header.operation" arguments="${soapOperation.name}"/></h1>
    <div align="right">
        <sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')">
        <a class="button-success pure-button" href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperation.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="soap.soapoperation.button.updateoperation"/></span></a>
        <a class="button-secondary pure-button" href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperation.id}/create/response"/>"><i class="fa fa-file"></i> <span><spring:message code="soap.soapoperation.button.createresponse"/></span></a>
        </sec:authorize>
    </div>
</div>

<div class="content-summary">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="soap.soapoperation.label.name"/></label></td>
            <td class="column2"><label path="name">${soapOperation.name}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="soap.soapoperation.label.identifier"/></label></td>
            <td class="column2"><label path="name">${soapOperation.identifier}</label></td>
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
    </table>
</div>


<div>
    <h2 class="decorated"><span><spring:message code="soap.soapoperation.header.mockresponses"/></span></h2>
    <c:choose>

        <c:when test="${soapOperation.mockResponses.size() > 0}">
            <form:form action="${soap_mock_response_update_url}" method="POST"  commandName="soapMockResponseModifierCommand">
                <div class="table-frame">
                    <table class="entityTable">
                        <col width="10%">
                        <col width="50%">
                        <col width="20%">
                        <col width="20%">
                        <tr>
                            <th><spring:message code="soap.soapoperation.column.selected"/></th>
                            <th><spring:message code="soap.soapoperation.column.responsename"/></th>
                            <th><spring:message code="soap.soapoperation.column.status"/></th>
                            <th><spring:message code="soap.soapoperation.column.httpstatuscode"/></th>
                        </tr>
                        <c:forEach items="${soapOperation.mockResponses}" var="soapMockResponse" varStatus="loopStatus">
                            <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                <td><form:checkbox path="soapMockResponseIds" name="${soapMockResponse.id}" value="${soapMockResponse.id}"/></td>
                                <td><a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperation.id}/response/${soapMockResponse.id}"/>">${soapMockResponse.name}</a></td>
                                <td><spring:message code="soap.type.soapmockresponsestatus.${soapMockResponse.status}"/></td>
                                <td>${soapMockResponse.httpStatusCode}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')">
                    <form:select path="soapMockResponseStatus">
                        <c:forEach items="${soapMockResponseStatuses}" var="soapMockResponseStatus">
                            <form:option value="${soapMockResponseStatus}"><spring:message code="soap.type.soapmockresponsestatus.${soapMockResponseStatus}"/></form:option>
                        </c:forEach>
                    </form:select>
                    <button class="button-success pure-button" type="submit" name="action" value="update"><i class="fa fa-check-circle"></i> <span><spring:message code="soap.soapoperation.button.update"/></span></button>
                    <button class="button-error pure-button" type="submit" name="action" value="delete"><i class="fa fa-trash"></i> <span><spring:message code="soap.soapoperation.button.deleteMockResponse"/></span></button>
                </sec:authorize>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form:form>
        </c:when>
        <c:otherwise>
            <spring:message code="soap.soapoperation.label.noresponse"/>
        </c:otherwise>
    </c:choose>
</div>

<div>
    <h2 class="decorated"><span><spring:message code="soap.soapoperation.header.events"/></span></h2>
    <c:choose>
        <c:when test="${soapEvents.size() > 0}">
            <div class="table-frame">
                <table class="entityTable">
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
                        <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
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