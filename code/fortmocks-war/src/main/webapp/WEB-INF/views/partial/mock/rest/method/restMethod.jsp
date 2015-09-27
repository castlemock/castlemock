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

<c:url var="rest_response_update_url"  value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restMethodId}/method/${restMethod.id}" />
<div class="content-top">
    <h1><spring:message code="rest.restmethod.header.method" arguments="${restMethod.name}"/></h1>
    <div align="right">
        <sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')">
        <a class="button-success pure-button" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="rest.restmethod.button.update"/></span></a>
        <a class="button-error pure-button" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="rest.restmethod.button.delete"/></span></a>
        </sec:authorize>
    </div>
</div>
<div class="content-summary">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="rest.restmethod.label.name"/></label></td>
            <td class="column2"><label path="name">${restMethod.name}</label></td>
        </tr>
    </table>
</div>

<h2 class="decorated"><span><spring:message code="rest.restmethod.header.responses"/></span></h2>
<c:choose>
    <c:when test="${restMethod.restResponses.size() > 0}">
        <form:form action="${rest_response_update_url}/" method="POST"  commandName="restResponseModifierCommand">
            <div class="table-frame">
                <table class="entityTable">
                    <col width="10%">
                    <col width="90%">
                    <tr>
                        <th><spring:message code="rest.restmethod.column.selected"/></th>
                        <th><spring:message code="rest.restmethod.column.response"/></th>
                    </tr>
                    <c:forEach items="${restMethod.restResponses}" var="restResponse" varStatus="loopStatus">
                        <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                            <td><form:checkbox path="restResponseIds" name="${restResponse.id}" value="${restResponse.id}"/></td>
                            <td><a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}/response/${restResponse.id}"/>">${restResponse.name}</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')">
                <button class="button-error pure-button" type="submit" name="action" value="delete"><i class="fa fa-trash"></i> <span><spring:message code="rest.restmethod.button.deleteresponses"/></span></button>
            </sec:authorize>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>

    </c:when>
    <c:otherwise>
        <spring:message code="rest.restmethod.label.noresponses"/>
    </c:otherwise>
</c:choose>
