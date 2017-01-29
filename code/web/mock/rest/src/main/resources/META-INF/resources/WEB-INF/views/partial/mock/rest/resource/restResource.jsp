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
<div class="content-top">
    <div class="title">
        <h1><spring:message code="rest.restresource.header.resource" arguments="${restResource.name}"/></h1>
    </div>
    <div class="menu" align="right">
        <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
            <c:choose>
                <c:when test="${demoMode}">
                    <a class="button-success pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-file"></i> <span><spring:message code="rest.restresource.button.updateresource"/></span></a>
                    <a class="button-secondary pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-plus"></i> <span><spring:message code="rest.restresource.button.createmethod"/></span></a>
                    <a class="button-error pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-trash"></i> <span><spring:message code="rest.restresource.button.delete"/></span></a>
                </c:when>
                <c:otherwise>
                    <a class="button-success pure-button" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResource.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="rest.restresource.button.updateresource"/></span></a>
                    <a class="button-secondary pure-button" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResource.id}/create/method"/>"><i class="fa fa-plus"></i> <span><spring:message code="rest.restresource.button.createmethod"/></span></a>
                    <a class="button-error pure-button" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResource.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="rest.restresource.button.delete"/></span></a>
                </c:otherwise>
            </c:choose>
        </sec:authorize>
    </div>
</div>
<div class="content-summary">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="rest.restresource.label.name"/></label></td>
            <td class="column2"><label path="name">${restResource.name}</label></td>
        </tr>
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

<h2 class="decorated"><span><spring:message code="rest.restresource.header.methods"/></span></h2>
<c:choose>
    <c:when test="${restResource.methods.size() > 0}">
        <form:form action="${rest_method_update_url}/" method="POST"  commandName="restMethodModifierCommand">
            <div class="table-frame">
                <table class="entityTable sortable">
                    <col width="10%">
                    <col width="30%">
                    <col width="10%">
                    <col width="10%">
                    <col width="40%">
                    <tr>
                        <th><spring:message code="rest.restresource.column.selected"/></th>
                        <th><spring:message code="rest.restresource.column.methodname"/></th>
                        <th><spring:message code="rest.restresource.column.methodmethodtype"/></th>
                        <th><spring:message code="rest.restresource.column.methodmethodstatus"/></th>
                        <th><spring:message code="rest.restresource.column.methodforwardedendpoint"/></th>
                    </tr>
                    <c:forEach items="${restResource.methods}" var="restMethod" varStatus="loopStatus">
                        <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                            <td><form:checkbox path="restMethodIds" name="${restMethod.id}" value="${restMethod.id}"/></td>
                            <td><a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResource.id}/method/${restMethod.id}"/>">${restMethod.name}</a></td>
                            <td><a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResource.id}/method/${restMethod.id}"/>">${restMethod.httpMethod}</a></td>
                            <td><a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResource.id}/method/${restMethod.id}"/>"><spring:message code="rest.type.restmethodstatus.${restMethod.status}"/></a></td>
                            <td><a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResource.id}/method/${restMethod.id}"/>">${restMethod.forwardedEndpoint}</a></td>
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
                        <a class="button-success pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-check-circle"></i> <span><spring:message code="rest.restresource.button.update"/></span></a>
                        <a class="button-secondary pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-code-fork"></i> <span><spring:message code="rest.restresource.button.updateendpoint"/></span></a>
                        <a class="button-error pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-trash"></i> <span><spring:message code="rest.restresource.button.deletemethods"/></span></a>
                    </c:when>
                    <c:otherwise>
                        <button class="button-success pure-button" type="submit" name="action" value="update"><i class="fa fa-check-circle"></i> <span><spring:message code="rest.restresource.button.update"/></span></button>
                        <button class="button-secondary pure-button" type="submit" name="action" value="update-endpoint"><i class="fa fa-code-fork"></i> <span><spring:message code="rest.restresource.button.updateendpoint"/></span></button>
                        <button class="button-error pure-button" type="submit" name="action" value="delete"><i class="fa fa-trash"></i> <span><spring:message code="rest.restresource.button.deletemethods"/></span></button>
                    </c:otherwise>
                </c:choose>
            </sec:authorize>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>

    </c:when>
    <c:otherwise>
        <spring:message code="rest.restresource.label.nomethods"/>
    </c:otherwise>
</c:choose>
