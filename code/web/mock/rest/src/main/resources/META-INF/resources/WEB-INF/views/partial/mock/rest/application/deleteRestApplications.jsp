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

<c:url var="delete_applications_url"  value="/web/rest/project/${restProjectId}/application/delete/confirm" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}"><spring:message code="rest.breadcrumb.project"/></a></li>
        <li class="active"><spring:message code="rest.deleteapplications.header.deleteapplications"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="rest.deleteapplications.header.deleteapplications"/></h1>
        </div>
        <c:choose>
            <c:when test="${restApplications.size() > 0}">
                <p><spring:message code="rest.deleteapplications.label.confirmation"/></p>
                <form:form action="${delete_applications_url}" method="POST" modelAttribute="deleteRestApplicationsCommand">
                    <ul>
                        <c:forEach items="${restApplications}" var="restApplication" varStatus="loopStatus">
                            <li>${restApplication.name}</li>
                            <form:hidden path="restApplications[${loopStatus.index}].id" value="${restApplication.id}"/>
                        </c:forEach>
                    </ul>

                    <button class="btn btn-danger" type="submit"><i class="fa fa-trash"></i> <span><spring:message code="rest.deleteapplications.button.deleteapplications"/></span></button>
                    <a href="<c:url value="/web/rest/project/${restProjectId}"/>" class="btn btn-primary"><i class="fa fa-times"></i> <spring:message code="rest.deleteapplications.button.cancel"/></a>
                </form:form>
            </c:when>
            <c:otherwise>
                <spring:message code="rest.deleteapplications.label.noapplications"/>
                <p>
                <a href="<c:url value="/web/rest/project/${restProjectId}"/>" class="btn btn-primary"><i class="fa fa-times"></i> <spring:message code="rest.deleteapplications.button.cancel"/></a>
                </p>
            </c:otherwise>
        </c:choose>
    </section>
</div>