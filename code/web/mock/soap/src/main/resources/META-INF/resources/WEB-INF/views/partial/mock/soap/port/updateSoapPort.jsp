<%@ include file="../../../../includes.jspf"%>
<c:url var="update_soap_port_url"  value="/web/soap/project/${soapProjectId}/port/${soapPortId}/update" />
<div class="content-top">
    <h1><spring:message code="soap.updatesoapport.header.updateport" arguments="${soapPort.name}"/></h1>
</div>
<form:form action="${update_soap_port_url}" method="POST" commandName="soapPort">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="soap.updatesoapport.label.name"/></label></td>
            <td class="column2"><label path="name">${soapPort.name}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="uri"><spring:message code="soap.updatesoapport.label.uri"/></label></td>
            <td class="column2"><form:input path="uri" value="${soapPort.uri}"/></td>
        </tr>
    </table>
    
    <button class="button-success pure-button" type="submit" name="submit"><spring:message code="soap.updatesoapport.button.updateport"/></button>
    <a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}"/>" class="button-error pure-button"><i class="fa fa-check-circle"></i> <spring:message code="soap.updatesoapport.button.cancel"/></a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>