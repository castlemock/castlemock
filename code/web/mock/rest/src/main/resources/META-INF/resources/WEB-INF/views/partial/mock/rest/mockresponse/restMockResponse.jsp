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

<c:url var="update_response_url"  value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethodId}/response/${restMockResponseId}/update" />
<div class="content-top">
    <h1><spring:message code="rest.restmockresponse.header.response" arguments="${restMockResponse.name}"/></h1>
    <div align="right">
        <sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')">
            <a class="button-error pure-button" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethodId}/response/${restMockResponseId}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="rest.restmockresponse.button.delete"/></span></a>
        </sec:authorize>
    </div>
</div>
<form:form action="${update_response_url}" method="POST" commandName="restMockResponse">
    <div class="content-summary">
        <table class="formTable">
            <tr>
                <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.label.name"/></form:label></td>
                <td class="column2"><form:input path="name" id="restMockResponseNameInput" /></td>
            </tr>
            <tr>
                <td class="column1"><form:label path="httpStatusCode"><spring:message code="rest.restmockresponse.label.httpstatuscode"/></form:label></td>
                <td class="column2"><form:input path="httpStatusCode" id="restMockResponseHttpResponseCodeInput"/></td>
            </tr>
            <tr>
                <td class="column1"><spring:message code="rest.restmockresponse.label.restcontenttype"/></td>
                <td class="column2"><form:input path="contentType" id="restMockResponseHttpContentTypeInput"/></td>
            </tr>
        </table>
    </div>
    <div>
        <h2 class="decorated"><span><spring:message code="rest.restmockresponse.header.body"/></span></h2>
        <div class="editor">
            <form:textarea id="body" path="body"/>
        </div>
    </div>
  <sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')">
      <button class="button-success pure-button" type="submit" name="submit"><i class="fa fa-plus"></i>  <span><spring:message code="rest.restmockresponse.button.updateresponse"/></span></button>
  </sec:authorize>
    <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethodId}"/>" class="button-error pure-button"><i class="fa fa-times"></i> <span><spring:message code="rest.restmockresponse.button.discardchanges"/></span></a>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>
<script>
    $("#restMockResponseNameInput").attr('required', '');
    $("#restMockResponseHttpResponseCodeInput").attr('required', '');
    $("#restMockResponseHttpContentTypeInput").attr('required', '');
</script>
