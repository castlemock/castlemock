<%--
 Copyright 2016 Karl Dahlgren

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>

<%@ include file="../../../includes.jspf"%>
<c:url var="project_update_url"  value="/web/project" />

<div class="content-top">
    <div class="title">
        <h1><spring:message code="general.projectoverview.header.projects"/></h1>
    </div>
    <div class="menu" align="right">
        <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
        <c:choose>
            <c:when test="${demoMode}">
                <a class="button-success pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-file"></i> <span><spring:message code="general.projectoverview.button.newproject"/></span></a>
                <a class="button-secondary pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-cloud-upload"></i> <span><spring:message code="general.projectoverview.button.importproject"/></span></a>
            </c:when>
            <c:otherwise>
                <a class="button-success pure-button" href="<c:url value="/web/project/create"/>"><i class="fa fa-file"></i> <span><spring:message code="general.projectoverview.button.newproject"/></span></a>
                <a class="button-secondary pure-button" href="<c:url value="/web/project/import"/>"><i class="fa fa-cloud-upload"></i> <span><spring:message code="general.projectoverview.button.importproject"/></span></a>
            </c:otherwise>
        </c:choose>
        </sec:authorize>
    </div>
</div>
<c:choose>
    <c:when test="${projects.size() > 0}">
        <form:form action="${project_url}" method="POST" commandName="projectModifierCommand">
            <div class="table-frame">
                <table class="entityTable sortable">
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
            <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                <button class="button-secondary pure-button" type="submit" name="action" value="export"><i class="fa fa-cloud-download"></i> <span><spring:message code="general.projectoverview.button.exportprojects"/></span></button>
                <c:choose>
                    <c:when test="${demoMode}">
                        <a class="button-error pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-trash"></i> <span><spring:message code="general.projectoverview.button.deleteprojects"/></span></a>
                    </c:when>
                    <c:otherwise>
                        <button class="button-error pure-button" type="submit" name="action" value="delete"><i class="fa fa-trash"></i> <span><spring:message code="general.projectoverview.button.deleteprojects"/></span></button>
                    </c:otherwise>
                </c:choose>
            </sec:authorize>
        </form:form>
    </c:when>
    <c:otherwise>
        <spring:message code="general.projectoverview.label.noprojects"/><sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')"><spring:message code="general.projectoverview.label.createnewproject"/></sec:authorize>
    </c:otherwise>
</c:choose>