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

<c:url var="rest_resource_update_url"  value="/web/rest/project/${restProjectId}/application/${restApplication.id}" />
<div class="content-top">
    <div class="title">
        <h1><spring:message code="rest.restapplication.header.application" arguments="${restApplication.name}"/></h1>
    </div>
    <div class="menu" align="right">
        <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
            <c:choose>
                <c:when test="${demoMode}">
                    <a class="button-success pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-file"></i> <span><spring:message code="rest.restapplication.button.updateapplication"/></span></a>
                    <a class="button-secondary pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-plus"></i> <span><spring:message code="rest.restapplication.button.createresource"/></span></a>
                    <a class="button-error pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-trash"></i> <span><spring:message code="rest.restapplication.button.delete"/></span></a>
                </c:when>
                <c:otherwise>
                    <a class="button-success pure-button" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplication.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="rest.restapplication.button.updateapplication"/></span></a>
                    <a class="button-secondary pure-button" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplication.id}/create/resource"/>"><i class="fa fa-plus"></i> <span><spring:message code="rest.restapplication.button.createresource"/></span></a>
                    <a class="button-error pure-button" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplication.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="rest.restapplication.button.delete"/></span></a>
                </c:otherwise>
            </c:choose>
        </sec:authorize>
    </div>
</div>
<div class="content-summary">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="rest.restapplication.label.name"/></label></td>
            <td class="column2"><label path="name">${restApplication.name}</label></td>
        </tr>
    </table>
</div>

<h2 class="decorated"><span><spring:message code="rest.restapplication.header.resources"/></span></h2>
<c:choose>
    <c:when test="${restApplication.resources.size() > 0}">
        <form:form action="${rest_resource_update_url}/" method="POST"  commandName="restResourceModifierCommand">
            <div class="table-frame">
                <table class="entityTable sortable">
                    <col width="10%">
                    <col width="20%">
                    <col width="30%">
                    <tr>
                        <th><spring:message code="rest.restapplication.column.selected"/></th>
                        <th><spring:message code="rest.restapplication.column.resource"/></th>
                        <th><spring:message code="rest.restapplication.column.uri"/></th>
                        <c:forEach items="${restMethodStatuses}" var="restMethodStatus">
                            <th><spring:message code="rest.type.restmethodstatus.${restMethodStatus}"/></th>
                        </c:forEach>
                    </tr>
                    <c:forEach items="${restApplication.resources}" var="restResource" varStatus="loopStatus">
                        <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                            <td><form:checkbox path="restResourceIds" name="${restResource.id}" value="${restResource.id}"/></td>
                            <td><a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplication.id}/resource/${restResource.id}"/>">${restResource.name}</a></td>
                            <td><a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplication.id}/resource/${restResource.id}"/>">${restResource.uri}</a></td>
                            <c:forEach items="${restMethodStatuses}" var="restMethodStatus">
                                <td>${restResource.statusCount[restMethodStatus]}</td>
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
                        <a class="button-success pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-check-circle"></i> <span><spring:message code="rest.restapplication.button.update"/></span></a>
                        <a class="button-secondary pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-code-fork"></i> <span><spring:message code="rest.restapplication.button.updateendpoint"/></span></a>
                        <a class="button-error pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-trash"></i> <span><spring:message code="rest.restapplication.button.deleteresources"/></span></a>
                    </c:when>
                    <c:otherwise>
                        <button class="button-success pure-button" type="submit" name="action" value="update"><i class="fa fa-check-circle"></i> <span><spring:message code="rest.restapplication.button.update"/></span></button>
                        <button class="button-secondary pure-button" type="submit" name="action" value="update-endpoint"><i class="fa fa-code-fork"></i> <span><spring:message code="rest.restapplication.button.updateendpoint"/></span></button>
                        <button class="button-error pure-button" type="submit" name="action" value="delete"><i class="fa fa-trash"></i> <span><spring:message code="rest.restapplication.button.deleteresources"/></span></button>
                    </c:otherwise>
                </c:choose>
            </sec:authorize>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>

    </c:when>
    <c:otherwise>
        <spring:message code="rest.restapplication.label.noresources"/>
    </c:otherwise>
</c:choose>
