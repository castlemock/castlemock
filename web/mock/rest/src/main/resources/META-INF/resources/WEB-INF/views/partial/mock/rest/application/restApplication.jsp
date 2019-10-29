
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
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}"><spring:message code="rest.breadcrumb.project"/></a></li>
        <li class="active"><spring:message code="rest.restapplication.header.application" arguments="${restApplication.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <div class="title">
                <h1><spring:message code="rest.restapplication.header.application" arguments="${restApplication.name}"/></h1>
            </div>
            <div class="menu" align="right">
                <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                    <a class="btn btn-success demo-button-disabled" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplication.id}/update"/>"><i class="fas fa-edit"></i> <span><spring:message code="rest.restapplication.button.updateapplication"/></span></a>
                    <a class="btn btn-primary demo-button-disabled" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplication.id}/create/resource"/>"><i class="fas fa-plus-circle"></i> <span><spring:message code="rest.restapplication.button.createresource"/></span></a>
                    <a class="btn btn-danger demo-button-disabled" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplication.id}/delete"/>"><i class="fas fa-trash"></i> <span><spring:message code="rest.restapplication.button.delete"/></span></a>
                </sec:authorize>
            </div>
        </div>
        <div class="content-summary">
        </div>

        <div class="panel panel-primary table-panel">
            <div class="panel-heading table-panel-heading">
                <h3 class="panel-title"><spring:message code="rest.restapplication.header.resources"/></h3>
            </div>
            <c:choose>
                <c:when test="${restApplication.resources.size() > 0}">
                    <form:form action="${rest_resource_update_url}/" method="POST"  modelAttribute="command">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover sortable">
                                <col width="10%">
                                <col width="20%">
                                <col width="30%">
                                <tr>
                                    <th></th>
                                    <th><spring:message code="rest.restapplication.column.resource"/></th>
                                    <th><spring:message code="rest.restapplication.column.uri"/></th>
                                    <c:forEach items="${restMethodStatuses}" var="restMethodStatus">
                                        <th><spring:message code="rest.type.restmethodstatus.${restMethodStatus}"/></th>
                                    </c:forEach>
                                </tr>
                                <c:forEach items="${restApplication.resources}" var="restResource" varStatus="loopStatus">
                                    <tr>
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
                        <div class="panel-buttons">
                            <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                                <form:select path="restMethodStatus">
                                    <c:forEach items="${restMethodStatuses}" var="restMethodStatus">
                                        <form:option value="${restMethodStatus}"><spring:message code="rest.type.restmethodstatus.${restMethodStatus}"/></form:option>
                                    </c:forEach>
                                </form:select>
                                <button class="btn btn-success demo-button-disabled" type="submit" name="action" value="update"><i class="fas fa-check-circle"></i> <span><spring:message code="rest.restapplication.button.update"/></span></button>
                                <button class="btn btn-primary demo-button-disabled" type="submit" name="action" value="update-endpoint"><i class="fas fa-project-diagram"></i> <span><spring:message code="rest.restapplication.button.updateendpoint"/></span></button>
                                <button class="btn btn-danger demo-button-disabled" type="submit" name="action" value="delete"><i class="fas fa-trash"></i> <span><spring:message code="rest.restapplication.button.deleteresources"/></span></button>
                            </sec:authorize>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form:form>

                </c:when>
                <c:otherwise>
                    <spring:message code="rest.restapplication.label.noresources"/>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
</div>