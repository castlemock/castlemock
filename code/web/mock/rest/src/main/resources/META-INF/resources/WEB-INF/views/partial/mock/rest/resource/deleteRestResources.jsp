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
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}"><spring:message code="rest.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}"><spring:message code="rest.breadcrumb.application"/></a></li>
        <li class="active"><spring:message code="rest.deleteresources.header.deleteresources"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="rest.deleteresources.header.deleteresources"/></h1>
        </div>
        <c:choose>
            <c:when test="${restResources.size() > 0}">
                <p><spring:message code="rest.deleteresources.label.confirmation"/></p>
                <form:form action="${delete_resources_url}" method="POST" modelAttribute="command">
                    <ul>
                        <c:forEach items="${restResources}" var="restResource" varStatus="loopStatus">
                            <li>${restResource.name}</li>
                            <form:hidden path="restResources[${loopStatus.index}].id" value="${restResource.id}"/>
                        </c:forEach>
                    </ul>

                    <button class="btn btn-danger" type="submit"><i class="fas fa-trash"></i> <span><spring:message code="rest.deleteresources.button.deleteresources"/></span></button>
                    <a href="<c:url value="/web/rest/project/${restProjectId}"/>" class="btn btn-primary"><i class="fas fa-times"></i> <spring:message code="rest.deleteresources.button.cancel"/></a>
                </form:form>
            </c:when>
            <c:otherwise>
                <spring:message code="rest.deleteresources.label.noresources"/>
                <p>
                <a href="<c:url value="/web/rest/project/${restProjectId}"/>" class="btn btn-primary"><i class="fas fa-times"></i> <spring:message code="rest.deleteresources.button.cancel"/></a>
                </p>
            </c:otherwise>
        </c:choose>
    </section>
</div>