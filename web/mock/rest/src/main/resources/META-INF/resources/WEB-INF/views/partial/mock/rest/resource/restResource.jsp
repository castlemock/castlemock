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

<c:url var="rest_method_update_url"  value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResource.id}" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}"><spring:message code="rest.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}"><spring:message code="rest.breadcrumb.application"/></a></li>
        <li class="active"><spring:message code="rest.restresource.header.resource" arguments="${restResource.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <div class="title">
                <h1><spring:message code="rest.restresource.header.resource" arguments="${restResource.name}"/></h1>
            </div>
            <div class="menu" align="right">
                <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                    <a class="btn btn-success demo-button-disabled" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResource.id}/update"/>"><i class="fas fa-edit"></i> <span><spring:message code="rest.restresource.button.updateresource"/></span></a>
                    <a class="btn btn-primary demo-button-disabled" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResource.id}/create/method"/>"><i class="fas fa-plus-circle"></i> <span><spring:message code="rest.restresource.button.createmethod"/></span></a>
                    <a class="btn btn-danger demo-button-disabled" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResource.id}/delete"/>"><i class="fas fa-trash"></i> <span><spring:message code="rest.restresource.button.delete"/></span></a>
                </sec:authorize>
            </div>
        </div>
        <div class="content-summary">
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="name"><spring:message code="rest.restresource.label.uri"/></label></td>
                    <td class="column2"><label path="name">${restResource.uri}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="name"><spring:message code="rest.restresource.label.address"/></label></td>
                    <td class="column2"><label path="name">${restResource.invokeAddress}</label></td>
                </tr>
            </table>
        </div>

        <div class="panel panel-primary table-panel">
            <div class="panel-heading table-panel-heading">
                <h3 class="panel-title"><spring:message code="rest.restresource.header.methods"/></h3>
            </div>
            <c:choose>
                <c:when test="${restResource.methods.size() > 0}">
                    <form:form action="${rest_method_update_url}/" method="POST"  modelAttribute="command">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover sortable">
                                <col width="10%">
                                <col width="30%">
                                <col width="10%">
                                <col width="10%">
                                <col width="40%">
                                <tr>
                                    <th></th>
                                    <th><spring:message code="rest.restresource.column.methodname"/></th>
                                    <th><spring:message code="rest.restresource.column.methodmethodtype"/></th>
                                    <th><spring:message code="rest.restresource.column.methodmethodstatus"/></th>
                                    <th><spring:message code="rest.restresource.column.methodforwardedendpoint"/></th>
                                </tr>
                                <c:forEach items="${restResource.methods}" var="restMethod" varStatus="loopStatus">
                                    <tr>
                                        <td><form:checkbox path="restMethodIds" name="${restMethod.id}" value="${restMethod.id}"/></td>
                                        <td><a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResource.id}/method/${restMethod.id}"/>">${restMethod.name}</a></td>
                                        <td><a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResource.id}/method/${restMethod.id}"/>">${restMethod.httpMethod}</a></td>
                                        <td><a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResource.id}/method/${restMethod.id}"/>"><spring:message code="rest.type.restmethodstatus.${restMethod.status}"/></a></td>
                                        <td><a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResource.id}/method/${restMethod.id}"/>">${restMethod.forwardedEndpoint}</a></td>
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
                                <button class="btn btn-success demo-button-disabled" type="submit" name="action" value="update"><i class="fas fa-check-circle"></i> <span><spring:message code="rest.restresource.button.update"/></span></button>
                                <button class="btn btn-primary demo-button-disabled" type="submit" name="action" value="update-endpoint"><i class="fas fa-project-diagram"></i> <span><spring:message code="rest.restresource.button.updateendpoint"/></span></button>
                            </sec:authorize>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form:form>

                </c:when>
                <c:otherwise>
                    <spring:message code="rest.restresource.label.nomethods"/>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
</div>