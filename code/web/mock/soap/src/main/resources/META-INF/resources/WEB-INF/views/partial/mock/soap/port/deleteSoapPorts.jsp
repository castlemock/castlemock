<%@ include file="../../../../includes.jspf"%>
<c:url var="delete_ports_url"  value="/web/soap/project/${soapProjectId}/port/delete/confirm" />
<div class="content-top">
    <h1><spring:message code="soap.deletesoapports.header.deleteports"/></h1>
</div>
<c:choose>
    <c:when test="${soapPorts.size() > 0}">
        <p><spring:message code="soap.deletesoapports.label.confirmation"/></p>
        <form:form action="${delete_ports_url}" method="POST" commandName="deleteSoapPortsCommand">
            <ul>
                <c:forEach items="${soapPorts}" var="soapPort" varStatus="loopStatus">
                    <li>${soapPort.name}</li>
                    <form:hidden path="soapPorts[${loopStatus.index}].id" value="${soapPort.id}"/>
                </c:forEach>
            </ul>

            <button class="button-error pure-button" type="submit"><i class="fa fa-trash"></i> <span><spring:message code="soap.deletesoapports.button.deleteports"/></span></button>
            <a href="<c:url value="/web/soap/project/${soapProjectId}"/>" class="button-secondary pure-button"><i class="fa fa-check-circle"></i> <spring:message code="soap.deletesoapports.button.cancel"/></a>
        </form:form>
    </c:when>
    <c:otherwise>
        <spring:message code="soap.deletesoapports.label.noports"/>
        <p>
        <a href="<c:url value="/web/soap/project/${soapProjectId}"/>" class="button-secondary pure-button"><i class="fa fa-check-circle"></i> <spring:message code="soap.deletesoapports.button.cancel"/></a>
        </p>
    </c:otherwise>
</c:choose>