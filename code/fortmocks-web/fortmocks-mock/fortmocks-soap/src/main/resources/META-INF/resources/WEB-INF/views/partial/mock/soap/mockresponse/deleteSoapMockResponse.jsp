<%@ include file="../../../../includes.jspf"%>
<div class="content-top">
<h1><spring:message code="soap.deletesoapmockresponse.header.deleteresponse" arguments="${soapMockResponse.name}"/></h1>
</div>
<spring:message code="soap.deletesoapmockresponse.label.confirmation" arguments="${soapMockResponse.name}"/>

<p>
<a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}/response/${soapMockResponseId}/delete/confirm"/>" class="button-error pure-button"><i class="fa fa-trash"></i> <spring:message code="soap.deletesoapmockresponse.button.deleteresponse"/></a>
<a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}/response/${soapMockResponseId}"/>" class="button-secondary pure-button"><i class="fa fa-check-circle"></i> <spring:message code="soap.deletesoapmockresponse.button.cancel"/></a>
</p>