<%@ include file="../../../../includes.jspf"%>
<c:url var="update_operations_endpoint_url"  value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/update/confirm" />
<div class="content-top">
    <h1><spring:message code="soap.updatesoapoperationsendpoint.header.updateoperation"/></h1>
</div>
<c:choose>
    <c:when test="${soapOperations.size() > 0}">
        <p><spring:message code="soap.updatesoapoperationsendpoint.label.confirmation"/></p>
        <form:form action="${update_operations_endpoint_url}" method="POST" commandName="updateSoapOperationsEndpointCommand">
            <ul>
                <c:forEach items="${soapOperations}" var="soapOperation" varStatus="loopStatus">
                    <li>${soapOperation.name}</li>
                    <form:hidden path="soapOperations[${loopStatus.index}].id" value="${soapOperation.id}"/>
                </c:forEach>
            </ul>
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="forwardedEndpoint"><spring:message code="soap.updatesoapoperationsendpoint.label.forwardedendpoint"/></label></td>
                    <td class="column2"><form:input path="forwardedEndpoint" value="${updateSoapOperationsEndpointCommand.forwardedEndpoint}"/></td>
                </tr>
            </table>
            <button class="button-success pure-button"><i class="fa fa-check-circle"></i> <span><spring:message code="soap.updatesoapoperationsendpoint.button.updateoperation"/></span></button>
            <a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="soap.updatesoapoperationsendpoint.button.cancel"/></a>
        </form:form>
    </c:when>
    <c:otherwise>
        <p><spring:message code="soap.updatesoapoperationsendpoint.label.nooperations"/> </p>
        <a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="soap.updatesoapoperationsendpoint.button.cancel"/></a>
    </c:otherwise>
</c:choose>