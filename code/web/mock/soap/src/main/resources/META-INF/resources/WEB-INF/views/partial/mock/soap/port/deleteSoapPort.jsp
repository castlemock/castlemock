<%@ include file="../../../../includes.jspf"%>
<div class="content-top">
<h1><spring:message code="soap.deletesoapport.header.deleleteport" arguments="${soapPort.name}"/></h1>
</div>
<spring:message code="soap.deletesoapport.label.confirmation" arguments="${soapPort.name}"/>

<p>
<a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPort.id}/delete/confirm"/>" class="button-error pure-button"><i class="fa fa-trash"></i> <spring:message code="soap.deletesoapport.button.deleteport"/></a>
<a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPort.id}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="soap.deletesoapport.button.cancel"/></a>
</p>