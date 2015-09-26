<%@ include file="../../../includes.jspf"%>
<c:url var="project_update_url"  value="/web/project" />
<div class="content-top">
    <h1><spring:message code="general.projectoverview.header.projects"/></h1>

    <div align="right">
        <sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')"><a class="button-success pure-button" href="<c:url value="/web/project/create"/>"><i class="fa fa-file"></i> <span><spring:message code="general.projectoverview.button.newproject"/></span></a></sec:authorize>
        <sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')"><a class="button-secondary pure-button" href="<c:url value="/web/project/import"/>"><i class="fa fa-cloud-upload"></i> <span><spring:message code="general.projectoverview.button.importproject"/></span></a></sec:authorize>
    </div>
</div>
<c:choose>
    <c:when test="${projects.size() > 0}">
        <form:form action="${project_url}" method="POST" commandName="projectModifierCommand">
            <div class="table-frame">
                <table class="entityTable">
                    <col width="10%">
                    <col width="20%">
                    <col width="10%">
                    <col width="60%">
                    <tr>
                        <th><spring:message code="general.projectoverview.column.selected"/></th>
                        <th><spring:message code="general.projectoverview.column.project"/></th>
                        <th><spring:message code="general.projectoverview.column.type"/></th>
                        <th><spring:message code="general.projectoverview.column.description"/></th>
                    </tr>
                    <c:forEach items="${projects}" var="project" varStatus="loopStatus">
                        <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                            <td><form:checkbox path="projects" name="${project.id}" value="${project.typeIdentifier.typeUrl}/${project.id}"/></td>
                            <td><a href="<c:url value="/web/${project.typeIdentifier.typeUrl}/project/${project.id}"/>">${project.name}</a></td>
                            <td>${project.typeIdentifier.type}</td>
                            <td>${project.description}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')">
                <button class="button-secondary pure-button" type="submit" name="action" value="export" disabled="disabled"><i class="fa fa-cloud-download"></i> <span><spring:message code="general.projectoverview.button.exportprojects"/></span></button>
                <button class="button-error pure-button" type="submit" name="action" value="delete"><i class="fa fa-trash"></i> <span><spring:message code="general.projectoverview.button.deleteprojects"/></span></button>
            </sec:authorize>
        </form:form>
    </c:when>
    <c:otherwise>
        <spring:message code="general.projectoverview.label.noprojects"/><sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')"><spring:message code="general.projectoverview.label.createnewproject"/></sec:authorize>
    </c:otherwise>
</c:choose>