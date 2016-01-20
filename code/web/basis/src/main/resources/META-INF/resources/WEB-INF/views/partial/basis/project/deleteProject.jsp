<%@ include file="../../../includes.jspf"%>
<div class="content-top">
    <h1><spring:message code="general.deleteproject.header.deleteproject" arguments="${project.name}"/></h1>
</div>
<spring:message code="general.deleteproject.label.confirmation" arguments="${project.name}"/>
<p>
<a href="<c:url value="/web/${project.typeIdentifier.typeUrl}/project/${project.id}/delete/confirm"/>" class="button-error pure-button"><i class="fa fa-trash"></i> <spring:message code="general.deleteproject.button.deleteproject"/></a>
<a href="<c:url value="/web/${project.typeIdentifier.typeUrl}/project/${project.id}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="general.deleteproject.button.cancel"/></a>
</p>