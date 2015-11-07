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

<c:url var="create_rest_mock_response_url"  value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethodId}/create/response" />
<div class="content-top">
    <h1><spring:message code="rest.createrestmockresponse.header.createmockresponse"/></h1>
</div>
<form:form action="${create_rest_mock_response_url}" method="POST">
    <table class="formTable">
        <tr>
            <td class="column1"><form:label path="name"><spring:message code="rest.createrestmockresponse.label.name"/></form:label></td>
            <td class="column2"><form:input path="name" />
        </tr>
    </table>
    <div class="editor">
        <form:textarea id="body" path="body"/>
    </div>

    <button class="button-success pure-button" type="submit" name="submit"><i class="fa fa-plus"></i>  <span><spring:message code="rest.createrestmockresponse.button.createmockresponse"/></span></button>
    <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethodId}"/>" class="button-error pure-button"><i class="fa fa-times"></i> <span><spring:message code="rest.createrestmockresponse.button.cancel"/></span></a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>
