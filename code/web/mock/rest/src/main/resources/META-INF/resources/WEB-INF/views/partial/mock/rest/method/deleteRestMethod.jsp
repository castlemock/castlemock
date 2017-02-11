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
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}"><spring:message code="rest.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}"><spring:message code="rest.breadcrumb.application"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}"><spring:message code="rest.breadcrumb.resource"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}"><spring:message code="rest.breadcrumb.method"/></a></li>
        <li class="active"><spring:message code="rest.deletemethod.header.deletemethod" arguments="${restMethod.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
        <h1><spring:message code="rest.deletemethod.header.deletemethod" arguments="${restMethod.name}"/></h1>
        </div>
        <spring:message code="rest.deletemethod.label.confirmation" arguments="${restMethod.name}"/>

        <p>
        <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}/delete/confirm"/>" class="btn btn-danger"><i class="fa fa-trash"></i> <spring:message code="rest.deletemethod.button.deletemethod"/></a>
        <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}"/>" class="btn btn-primary"><i class="fa fa-timese"></i> <spring:message code="rest.deletemethod.button.cancel"/></a>
        </p>
    </section>
</div>