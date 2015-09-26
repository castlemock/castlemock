<%@ include file="../../../includes.jspf"%>
<div class="content-top">
    <h1><spring:message code="general.deleteuser.header.deleteuser" arguments="${user.username}"/></h1>
</div>
<spring:message code="general.deleteuser.label.confirmation" arguments="${user.username}"/>

<p>
    <a href="<c:url value="/web/user/${user.id}/delete/confirm"/>" class="button-error pure-button"><i class="fa fa-trash"></i> <spring:message code="general.deleteuser.button.deleteuser"/></a>
    <a href="<c:url value="/web/user/${user.id}"/>" class="button-secondary pure-button"><i class="fa fa-check-circle"></i> <spring:message code="general.deleteuser.button.cancel"/></a>
</p>