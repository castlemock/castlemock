<%@ include file="../../../includes.jspf"%>
<c:url var="delete_projects_url"  value="/web/project/delete/confirm" />
<div class="content-top">
    <h1><spring:message code="general.deleteprojects.header.deleteproject"/></h1>
</div>
<c:choose>
    <c:when test="${projects.size() > 0}">
        <p><spring:message code="general.deleteprojects.label.confirmation"/></p>
        <form:form action="${delete_projects_url}" method="POST" commandName="deleteProjectsCommand">
            <ul>
                <c:forEach items="${projects}" var="project" varStatus="loopStatus">
                    <li>${project.name}</li>
                    <form:hidden path="projectIds[${loopStatus.index}]" value="${project.id}"/>
                    <form:hidden path="typeUrls[${loopStatus.index}]" value="${project.typeIdentifier.typeUrl}"/>
                </c:forEach>
            </ul>

            <button class="button-error pure-button" type="submit"><i class="fa fa-trash"></i> <span><spring:message code="general.deleteprojects.button.deleteproject"/></span></button>
            <a href="<c:url value="/web"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="general.deleteprojects.button.cancel"/></a>
        </form:form>
    </c:when>
    <c:otherwise>
        <spring:message code="general.deleteprojects.label.noprojects"/>
        <p>
        <a href="<c:url value="/web"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="general.deleteprojects.button.cancel"/></a>
        </p>
    </c:otherwise>
</c:choose>