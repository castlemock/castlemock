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

<%@ include file="../../../../includes.jspf"%>
<c:url var="update_methods_endpoint_url"  value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/update/confirm" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}"><spring:message code="rest.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}"><spring:message code="rest.breadcrumb.application"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}"><spring:message code="rest.breadcrumb.resource"/></a></li>
        <li class="active"><spring:message code="rest.updaterestmethodsendpoint.header.updatemethod"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="rest.updaterestmethodsendpoint.header.updatemethod"/></h1>
        </div>
        <c:choose>
            <c:when test="${restMethods.size() > 0}">
                <p><spring:message code="rest.updaterestmethodsendpoint.label.confirmation"/></p>
                <form:form action="${update_methods_endpoint_url}" method="POST" commandName="updateRestMethodsEndpointCommand">
                    <ul>
                        <c:forEach items="${restMethods}" var="restMethod" varStatus="loopStatus">
                            <li>${restMethod.name}</li>
                            <form:hidden path="restMethods[${loopStatus.index}].id" value="${restMethod.id}"/>
                        </c:forEach>
                    </ul>
                    <table class="formTable">
                        <tr>
                            <td class="column1"><label path="forwardedEndpoint"><spring:message code="rest.updaterestmethodsendpoint.label.forwardedendpoint"/></label></td>
                            <td class="column2"><form:input path="forwardedEndpoint" value="${updateRestMethodsEndpointCommand.forwardedEndpoint}"/></td>
                        </tr>
                    </table>
                    <button class="btn btn-success"><i class="fa fa-check-circle"></i> <span><spring:message code="rest.updaterestmethodsendpoint.button.updatemethods"/></span></button>
                    <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}"/>" class="btn btn-primary"><i class="fa fa-times"></i> <spring:message code="rest.updaterestmethodsendpoint.button.cancel"/></a>
                </form:form>
            </c:when>
            <c:otherwise>
                <p><spring:message code="rest.updaterestmethodsendpoint.label.nomethods"/></p>
                <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}"/>" class="btn btn-primary"><i class="fa fa-times"></i> <spring:message code="rest.updaterestmethodsendpoint.button.cancel"/></a>
            </c:otherwise>
        </c:choose>
    </section>
</div>