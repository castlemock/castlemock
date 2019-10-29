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
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li class="active"><spring:message code="rest.restproject.header.project" arguments="${restProject.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <c:if test="${not empty upload}">
            <c:if test="${upload == 'success'}">
                <div class="alert alert-success alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong><spring:message code="rest.restimportdefinition.message.success.title"/></strong> <spring:message code="rest.restimportdefinition.message.success.body"/>
                </div>
            </c:if>
            <c:if test="${upload == 'error'}">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong><spring:message code="rest.restimportdefinition.message.error.title"/></strong> <spring:message code="rest.restimportdefinition.message.error.body"/>
                </div>
            </c:if>
        </c:if>

        <div class="content-top">
            <div class="title">
                <h1><spring:message code="rest.restproject.header.project" arguments="${restProject.name}"/></h1>
            </div>
            <div class="menu" align="right">
                <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                    <a class="btn btn-success demo-button-disabled"  href="<c:url value="/web/rest/project/${restProject.id}/update"/>"><i class="fas fa-edit"></i> <span><spring:message code="rest.restproject.button.updateproject"/></span></a>
                    <a class="btn btn-primary demo-button-disabled"  href="<c:url value="/web/rest/project/${restProject.id}/create/application"/>"><i class="fas fa-plus-circle"></i> <span><spring:message code="rest.restproject.button.createapplication"/></span></a>
                    <div class="btn-group">
                        <button type="button" class="btn btn-primary dropdown-toggle demo-button-disabled" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-upload"></i> <span><spring:message code="rest.restproject.button.upload"/> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a href="<c:url value="/web/rest/project/${restProject.id}/import?type=RAML"/>">RAML</a></li>
                            <li><a href="<c:url value="/web/rest/project/${restProject.id}/import?type=SWAGGER"/>">Swagger</a></li>
                            <li><a href="<c:url value="/web/rest/project/${restProject.id}/import?type=WADL"/>">WADL</a></li>
                        </ul>
                    </div>
                    <a class="btn btn-primary" href="<c:url value="/web/rest/project/${restProject.id}/export"/>"><i class="fas fa-cloud-download-alt"></i> <span><spring:message code="rest.restproject.button.export"/></span></a>
                    <a class="btn btn-danger demo-button-disabled" href="<c:url value="/web/rest/project/${restProject.id}/delete"/>"><i class="fas fa-trash"></i> <span><spring:message code="rest.restproject.button.delete"/></span></a>
                </sec:authorize>
            </div>
        </div>
        <div class="content-summary">
            <table class="formTable">
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

        <div class="panel panel-primary table-panel">
            <div class="panel-heading table-panel-heading">
                <h3 class="panel-title"><spring:message code="rest.restproject.header.applications"/></h3>
            </div>
            <c:choose>
                <c:when test="${restProject.applications.size() > 0}">
                    <form:form action="${rest_resource_update_url}/" method="POST"  modelAttribute="command">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover sortable">
                                <col width="10%">
                                <col width="40%">

                                <tr>
                                    <th></th>
                                    <th><spring:message code="rest.restproject.column.application"/></th>
                                    <c:forEach items="${restMethodStatuses}" var="restMethodStatus">
                                        <th><spring:message code="rest.type.restmethodstatus.${restMethodStatus}"/></th>
                                    </c:forEach>
                                </tr>
                                <c:forEach items="${restProject.applications}" var="restApplication" varStatus="loopStatus">
                                    <tr>
                                        <td><form:checkbox path="restApplicationIds" name="${restApplication.id}" value="${restApplication.id}"/></td>
                                        <td><a href="<c:url value="/web/rest/project/${restProject.id}/application/${restApplication.id}"/>">${restApplication.name}</a></td>
                                        <c:forEach items="${restMethodStatuses}" var="restMethodStatus">
                                            <td>${restApplication.statusCount[restMethodStatus]}</td>
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
                                <button class="btn btn-success demo-button-disabled" type="submit" name="action" value="update"><i class="fas fa-check-circle"></i> <span><spring:message code="rest.restproject.button.update"/></span></button>
                                <button class="btn btn-primary demo-button-disabled" type="submit" name="action" value="update-endpoint"><i class="fas fa-project-diagram"></i> <span><spring:message code="rest.restproject.button.updateendpoint"/></span></button>
                                <button class="btn btn-danger demo-button-disabled" type="submit" name="action" value="delete"><i class="fas fa-trash"></i> <span><spring:message code="rest.restproject.button.deleteapplication"/></span></button>
                            </sec:authorize>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form:form>

                </c:when>
                <c:otherwise>
                    <spring:message code="rest.restproject.label.noapplication" arguments="wadl"/>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
</div>