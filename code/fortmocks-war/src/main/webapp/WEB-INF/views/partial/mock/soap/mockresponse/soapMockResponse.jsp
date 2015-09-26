<%@ include file="../../../../includes.jspf"%>
<c:url var="update_response_url"  value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}/response/${soapMockResponse.id}/update" />
<div class="content-top">
    <h1><spring:message code="soap.soapmockresponse.header.response" arguments="${soapMockResponse.name}"/></h1>
    <div align="right">
        <sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')">
            <a class="button-error pure-button" href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}/response/${soapMockResponse.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="soap.soapmockresponse.button.delete"/></span></a>
        </sec:authorize>
    </div>
</div>
<form:form action="${update_response_url}" method="POST" commandName="soapMockResponse">
    <div class="editor">
        <form:textarea id="body" path="body"/>
    </div>
  <sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')">
      <button class="button-success pure-button" type="submit" name="submit"><i class="fa fa-plus"></i>  <span><spring:message code="soap.soapmockresponse.button.updateresponse"/></span></button>
  </sec:authorize>
    <a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}"/>" class="button-error pure-button"><i class="fa fa-times"></i> <span><spring:message code="soap.soapmockresponse.button.discardchanges"/></span></a>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>