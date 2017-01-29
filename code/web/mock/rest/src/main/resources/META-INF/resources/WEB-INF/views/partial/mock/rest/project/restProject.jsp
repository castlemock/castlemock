<%@ include file="../../../../includes.jspf"%>

<%--
  ~ Copyright 2015 Karl Dahlgren
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~   http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<c:url var="rest_resource_update_url"  value="/web/rest/project/${restProject.id}" />
<div class="content-top">
    <div class="title">
        <h1><spring:message code="rest.restproject.header.project" arguments="${restProject.name}"/></h1>
    </div>
    <div class="menu" align="right">
        <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
            <c:choose>
                <c:when test="${demoMode}">
                    <a class="button-success pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-file"></i> <span><spring:message code="rest.restproject.button.updateproject"/></span></a>
                    <a class="button-secondary pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>"  href="<c:url value="#"/>"><i class="fa fa-plus"></i> <span><spring:message code="rest.restproject.button.createapplication"/></span></a>
                    <a class="button-secondary pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-upload"></i> <span><spring:message code="rest.restproject.button.upload" arguments="wadl"/></span></a>
                </c:when>
                <c:otherwise>
                    <a class="button-success pure-button" href="<c:url value="/web/rest/project/${restProject.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="rest.restproject.button.updateproject"/></span></a>
                    <a class="button-secondary pure-button" href="<c:url value="/web/rest/project/${restProject.id}/create/application"/>"><i class="fa fa-plus"></i> <span><spring:message code="rest.restproject.button.createapplication"/></span></a>
                    <a class="button-secondary pure-button" href="<c:url value="/web/rest/project/${restProject.id}/add/wadl"/>"><i class="fa fa-upload"></i> <span><spring:message code="rest.restproject.button.upload" arguments="wadl"/></span></a>
                </c:otherwise>
            </c:choose>
            <a class="button-secondary pure-button" href="<c:url value="/web/rest/project/${restProject.id}/export"/>"><i class="fa fa-cloud-download"></i> <span><spring:message code="rest.restproject.button.export"/></span></a>
            <c:choose>
                <c:when test="${demoMode}">
                    <a class="button-error pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-trash"></i> <span><spring:message code="rest.restproject.button.delete"/></span></a>
                </c:when>
                <c:otherwise>
                    <a class="button-error pure-button" href="<c:url value="/web/rest/project/${restProject.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="rest.restproject.button.delete"/></span></a>
                </c:otherwise>
            </c:choose>
        </sec:authorize>
    </div>
</div>
<div class="content-summary">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="rest.restproject.label.name"/></label></td>
            <td class="column2"><label path="name">${restProject.name}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="description"><spring:message code="rest.restproject.label.description"/></label></td>
            <td class="column2"><label path="description">${restProject.description}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="projectType"><spring:message code="rest.restproject.label.type"/></label></td>
            <td class="column2"><label path="projectType">REST</label></td>
        </tr>
    </table>
</div>

<h2 class="decorated"><span><spring:message code="rest.restproject.header.applications"/></span></h2>
<c:choose>
    <c:when test="${restProject.applications.size() > 0}">
        <form:form action="${rest_resource_update_url}/" method="POST"  commandName="restApplicationModifierCommand">
            <div class="table-frame">
                <table class="entityTable sortable">
                    <tr>
                        <th><spring:message code="rest.restproject.column.selected"/></th>
                        <th><spring:message code="rest.restproject.column.application"/></th>
                        <c:forEach items="${restMethodStatuses}" var="restMethodStatus">
                            <th><spring:message code="rest.type.restmethodstatus.${restMethodStatus}"/></th>
                        </c:forEach>
                    </tr>
                    <c:forEach items="${restProject.applications}" var="restApplication" varStatus="loopStatus">
                        <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                            <td><form:checkbox path="restApplicationIds" name="${restApplication.id}" value="${restApplication.id}"/></td>
                            <td><a href="<c:url value="/web/rest/project/${restProject.id}/application/${restApplication.id}"/>">${restApplication.name}</a></td>
                            <c:forEach items="${restMethodStatuses}" var="restMethodStatus">
                                <td>${restApplication.statusCount[restMethodStatus]}</td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                <form:select path="restMethodStatus">
                    <c:forEach items="${restMethodStatuses}" var="restMethodStatus">
                        <form:option value="${restMethodStatus}"><spring:message code="rest.type.restmethodstatus.${restMethodStatus}"/></form:option>
                    </c:forEach>
                </form:select>
                <c:choose>
                    <c:when test="${demoMode}">
                        <a class="button-success pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-check-circle"></i> <span><spring:message code="rest.restproject.button.update"/></span></a>
                        <a class="button-secondary pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-code-fork"></i> <span><spring:message code="rest.restproject.button.updateendpoint"/></span></a>
                        <a class="button-error pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-trash"></i> <span><spring:message code="rest.restproject.button.deleteapplication"/></span></a>
                    </c:when>
                    <c:otherwise>
                        <button class="button-success pure-button" type="submit" name="action" value="update"><i class="fa fa-check-circle"></i> <span><spring:message code="rest.restproject.button.update"/></span></button>
                        <button class="button-secondary pure-button" type="submit" name="action" value="update-endpoint"><i class="fa fa-code-fork"></i> <span><spring:message code="rest.restproject.button.updateendpoint"/></span></button>
                        <button class="button-error pure-button" type="submit" name="action" value="delete"><i class="fa fa-trash"></i> <span><spring:message code="rest.restproject.button.deleteapplication"/></span></button>
                    </c:otherwise>
                </c:choose>
            </sec:authorize>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>

    </c:when>
    <c:otherwise>
        <spring:message code="rest.restproject.label.noapplication" arguments="wadl"/>
    </c:otherwise>
</c:choose>
