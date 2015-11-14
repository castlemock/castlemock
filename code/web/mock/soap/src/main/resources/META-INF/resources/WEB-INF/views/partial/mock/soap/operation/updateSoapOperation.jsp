<%@ include file="../../../../includes.jspf"%>
<c:url var="update_soap_operation_url"  value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}/update" />
<div class="content-top">
    <h1><spring:message code="soap.updatesoapoperation.header.updateoperation" arguments="${command.name}"/></h1>
</div>
<form:form action="${update_soap_operation_url}" method="POST">
    <table class="formTable">
        <tr>
            <td class="column1"><spring:message code="soap.updatesoapoperation.label.status"/></td>
            <td>
                <form:select path="soapOperationStatus">
                    <c:forEach items="${soapOperationStatuses}" var="soapOperationStatus">
                        <option value="${soapOperationStatus}"><spring:message code="soap.type.soapoperationstatus.${soapOperationStatus}"/></option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="column1"><spring:message code="soap.updatesoapoperation.label.responsestrategy"/></td>
            <td>
                <form:select path="soapResponseStrategy">
                    <c:forEach items="${soapMockResponseStrategies}" var="soapMockResponseStrategy">
                        <option value="${soapMockResponseStrategy}"><spring:message code="soap.type.responsestrategy.${soapMockResponseStrategy}"/></option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="column1"><label path="originalEndpoint"><spring:message code="soap.updatesoapoperation.label.originalforwardedenpoint"/></label></td>
            <td class="column2"><label path="originalEndpoint">${command.originalEndpoint}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="forwardedEndpoint"><spring:message code="soap.updatesoapoperation.label.forwardedendpoint"/></label></td>
            <td class="column2"><form:input path="forwardedEndpoint" value="${command.forwardedEndpoint}"/></td>
        </tr>
    </table>
    
    <button class="button-success pure-button" type="submit" name="submit"><spring:message code="soap.updatesoapoperation.button.updateoperation"/></button>
    <a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}"/>" class="button-error pure-button"><i class="fa fa-check-circle"></i> <spring:message code="soap.updatesoapoperation.button.cancel"/></a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>