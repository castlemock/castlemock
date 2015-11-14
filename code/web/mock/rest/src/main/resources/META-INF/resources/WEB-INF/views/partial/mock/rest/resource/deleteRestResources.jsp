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

<c:url var="delete_resources_url"  value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/delete/confirm" />
<div class="content-top">
    <h1><spring:message code="rest.deleteresources.header.deleteresources"/></h1>
</div>
<c:choose>
    <c:when test="${restResources.size() > 0}">
        <p><spring:message code="rest.deleteresources.label.confirmation"/></p>
        <form:form action="${delete_resources_url}" method="POST" commandName="deleteRestResourcesCommand">
            <ul>
                <c:forEach items="${restResources}" var="restResource" varStatus="loopStatus">
                    <li>${restResource.name}</li>
                    <form:hidden path="restResources[${loopStatus.index}].id" value="${restResource.id}"/>
                </c:forEach>
            </ul>

            <button class="button-error pure-button" type="submit"><i class="fa fa-trash"></i> <span><spring:message code="rest.deleteresources.button.deleteresources"/></span></button>
            <a href="<c:url value="/web/rest/project/${restProjectId}"/>" class="button-secondary pure-button"><i class="fa fa-check-circle"></i> <spring:message code="rest.deleteresources.button.cancel"/></a>
        </form:form>
    </c:when>
    <c:otherwise>
        <spring:message code="rest.deleteresources.label.noresources"/>
        <p>
        <a href="<c:url value="/web/rest/project/${restProjectId}"/>" class="button-secondary pure-button"><i class="fa fa-check-circle"></i> <spring:message code="rest.deleteresources.button.cancel"/></a>
        </p>
    </c:otherwise>
</c:choose>