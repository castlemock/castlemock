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

<c:url var="create_rest_resource_url"  value="/web/rest/project/${restProjectId}/application/${restApplicationId}/create/resource" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}"><spring:message code="rest.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}"><spring:message code="rest.breadcrumb.application"/></a></li>
        <li class="active"><spring:message code="rest.createresource.header.resource"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="rest.createresource.header.resource"/></h1>
        </div>
        <form:form action="${create_rest_resource_url}" method="POST" modelAttribute="restResource">
            <table class="formTable">
                <tr>
                    <td class="column1"><label><spring:message code="rest.createresource.label.name"/></label></td>
                    <td class="column2"><form:input class="form-control" id="restResourceNameInput" path="name" /></td>
                </tr>
                <tr>
                    <td class="column1"><label><spring:message code="rest.createresource.label.uri"/></label></td>
                    <td class="column2"><form:input class="form-control" id="restResourceUriInput" path="uri" /></td>
                    <td class="information" title="<spring:message code="rest.createresource.tooltip.uri"/>"><i class="fas fa-question-circle fa-1x"></i></td>
                </tr>
            </table>

            <button class="btn btn-success" type="submit" name="submit"><i class="fas fa-plus"></i> <span><spring:message code="rest.createresource.button.createresource"/></span></button>
            <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}"/>" class="btn btn-danger"><i class="fas fa-times"></i> <span><spring:message code="rest.createresource.button.cancel"/></span></a>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>
    </section>
</div>
<script>
    $("#restResourceNameInput").attr('required', '');
    $("#restResourceUriInput").attr('required', '');
</script>