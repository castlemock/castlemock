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

<c:url var="delete_rest_mock_responses_url"  value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethodId}/response/delete/confirm" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}"><spring:message code="rest.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}"><spring:message code="rest.breadcrumb.application"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}"><spring:message code="rest.breadcrumb.resource"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethodId}"><spring:message code="rest.breadcrumb.method"/></a></li>
        <li class="active"><spring:message code="rest.deleterestmockresponses.header.deleteresponses"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="rest.deleterestmockresponses.header.deleteresponses"/></h1>
        </div>
        <c:choose>
            <c:when test="${restMockResponses.size() > 0}">
                <p><spring:message code="rest.deleterestmockresponses.label.confirmation"/></p>
                <form:form action="${delete_rest_mock_responses_url}" method="POST" modelAttribute="deleteRestMockResponsesCommand">
                    <ul>
                        <c:forEach items="${restMockResponses}" var="restMockResponse" varStatus="loopStatus">
                            <li>${restMockResponse.name}</li>
                            <form:hidden path="restMockResponses[${loopStatus.index}].id" value="${restMockResponse.id}"/>
                        </c:forEach>
                    </ul>

                    <button class="btn btn-danger" type="submit"><i class="fa fa-trash"></i> <span><spring:message code="rest.deleterestmockresponses.button.deleteresponses"/></span></button>
                    <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethodId}"/>" class="btn btn-primary"><i class="fa fa-times"></i> <spring:message code="rest.deleterestmockresponses.button.cancel"/></a>
                </form:form>
            </c:when>
            <c:otherwise>
                <spring:message code="rest.deleterestmockresponses.label.nomockedresponses"/>
                <p>
                <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethodId}"/>" class="btn btn-primary"><i class="fa fa-times"></i> <spring:message code="rest.deleterestmockresponses.button.cancel"/></a>
                </p>
            </c:otherwise>
        </c:choose>
    </section>
</div>