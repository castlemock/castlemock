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
<c:url var="update_resources_endpoint_url"  value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/update/confirm" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}"><spring:message code="rest.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}"><spring:message code="rest.breadcrumb.application"/></a></li>
        <li class="active"><spring:message code="rest.updaterestresourcesendpoint.header.updateresource"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="rest.updaterestresourcesendpoint.header.updateresource"/></h1>
        </div>
        <c:choose>
            <c:when test="${restResources.size() > 0}">
                <p><spring:message code="rest.updaterestresourcesendpoint.label.confirmation"/></p>
                <form:form action="${update_resources_endpoint_url}" method="POST" commandName="updateRestResourcesEndpointCommand">
                    <ul>
                        <c:forEach items="${restResources}" var="restResource" varStatus="loopStatus">
                            <li>${restResource.name}</li>
                            <form:hidden path="restResources[${loopStatus.index}].id" value="${restResource.id}"/>
                        </c:forEach>
                    </ul>
                    <table class="formTable">
                        <tr>
                            <td class="column1"><label path="forwardedEndpoint"><spring:message code="rest.updaterestresourcesendpoint.label.forwardedendpoint"/></label></td>
                            <td class="column2"><form:input path="forwardedEndpoint" value="${updateRestResourcesEndpointCommand.forwardedEndpoint}"/></td>
                        </tr>
                    </table>
                    <button class="btn btn-success"><i class="fa fa-check-circle"></i> <span><spring:message code="rest.updaterestresourcesendpoint.button.updateresources"/></span></button>
                    <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}"/>" class="btn btn-primary"><i class="fa fa-times"></i> <spring:message code="rest.updaterestresourcesendpoint.button.cancel"/></a>
                </form:form>
            </c:when>
            <c:otherwise>
                <p><spring:message code="rest.updaterestresourcesendpoint.label.noresources"/></p>
                <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}"/>" class="btn btn-primary"><i class="fa fa-times"></i> <spring:message code="rest.updaterestresourcesendpoint.button.cancel"/></a>
            </c:otherwise>
        </c:choose>
    </section>
</div>