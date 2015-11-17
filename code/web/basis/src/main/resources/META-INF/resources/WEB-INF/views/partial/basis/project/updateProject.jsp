<%@ include file="../../../includes.jspf"%>
<c:url var="update_project_url"  value="/web/${project.typeIdentifier.typeUrl}/project/${project.id}/update" />
<div class="content-top">
    <h1><spring:message code="general.updateproject.header.updateproject" arguments="${project.name}"/></h1>
</div>
<form:form action="${update_project_url}" method="POST" commandName="project">
    <table class="formTable">
        <tr>
            <td class="column1"><form:label path="name"><spring:message code="general.updateproject.label.name"/></form:label></td>
            <td class="column2"><form:input path="name" id="projectNameInput" value="${project.name}"/></td>
        </tr>
        <tr>
            <td class="column1"><spring:message code="general.updateproject.label.description"/></td>
            <td class="column2"><form:textarea rows="4" path="description"></form:textarea></td>
        </tr>
    </table>
    <button class="button-success pure-button"><i class="fa fa-check-circle"></i> <spring:message code="general.updateproject.button.updateproject"/></button>

    <a href="<c:url value="/web/${project.typeIdentifier.typeUrl}/project/${project.id}"/>" class="button-secondary pure-button"><i class="fa fa-check-circle"></i> <spring:message code="general.updateproject.button.discardchanges"/></a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>
<script>
    $("#projectNameInput").attr('required', '');
</script>
