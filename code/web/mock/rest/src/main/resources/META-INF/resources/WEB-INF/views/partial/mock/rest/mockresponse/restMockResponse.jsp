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
    <div class="title">
        <h1><spring:message code="rest.restmockresponse.header.response" arguments="${restMockResponse.name}"/></h1>
    </div>
    <div class="menu" align="right">
        <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
            <c:choose>
                <c:when test="${demoMode}">
                    <a class="button-error pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-trash"></i> <span><spring:message code="rest.restmockresponse.button.delete"/></span></a>
                </c:when>
                <c:otherwise>
                    <a class="button-error pure-button" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethodId}/response/${restMockResponseId}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="rest.restmockresponse.button.delete"/></span></a>
                </c:otherwise>
            </c:choose>
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
                <td><label id="httpCodeDefinitionLabel"><spring:message code="soap.restmockresponse.label.httpstatuscodedefinition"/>:&nbsp;</label><label id="httpCodeLabel"></label></td>
            </tr>
            <tr>
                <td class="column1"><form:label path="status"><spring:message code="rest.restmockresponse.label.status"/></form:label></td>
                <td>
                    <form:select path="status">
                        <c:forEach items="${restMockResponseStatuses}" var="restMockResponseStatus">
                            <spring:message var="label" code="rest.type.restmockresponsestatus.${restMockResponseStatus}"/>
                            <form:option value="${restMockResponseStatus}" label="${label}"/>
                        </c:forEach>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td class="column1"><form:label path="usingExpressions"><spring:message code="rest.restmockresponse.label.useexpressions"/></form:label></td>
                <td class="column2"><form:checkbox path="usingExpressions"/></td>
            </tr>
        </table>
    </div>
    <div>
        <h2 class="decorated"><span><spring:message code="rest.restmockresponse.header.body"/></span></h2>
        <div class="editor">
            <form:textarea id="body" path="body"/>
            <div class="editorButtons">
                <button id="formatXmlButton" type="button"><spring:message code="rest.restmockresponse.button.formatxml"/></button>
                <button id="formatJsonButton" type="button"><spring:message code="rest.restmockresponse.button.formatjson"/></button>
            </div>
        </div>
    </div>
    <div>
        <h2 class="decorated"><span><spring:message code="rest.restmockresponse.header.headers"/></span></h2>

        <fieldset>
            <legend><spring:message code="rest.restmockresponse.field.addheader"/></legend>
            <table class="formTable">
                <tr>
                    <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.label.headername"/></form:label></td>
                    <td class="column2"><input type="text" name="headerName" id="headerNameInput"></td>
                </tr>
                <tr>
                    <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.label.headervalue"/></form:label></td>
                    <td class="column2"><input type="text" name="headerValue" id="headerValueInput"></td>
                </tr>
            </table>
            <button class="button-success pure-button" onclick="addHeader()" type="button"><i class="fa fa-plus"></i>  <span><spring:message code="rest.restmockresponse.button.addheader"/></span></button>
        </fieldset>

        <div class="table-frame">
            <table class="entityTable sortable" id="headerTable">
                <col width="4%">
                <col width="48%">
                <col width="48%">
                <tr>
                    <th></th>
                    <th><spring:message code="rest.restmockresponse.column.headername"/></th>
                    <th><spring:message code="rest.restmockresponse.column.headervalue"/></th>
                </tr>
                <c:forEach items="${restMockResponse.httpHeaders}" var="httpHeader" varStatus="loopStatus">
                    <tr class="even">
                        <td><div class="delete" onclick="removeHeader('${httpHeader.name}')"></div></td>
                        <td><input name="httpHeaders[${loopStatus.index}].name" id="httpHeaders[${loopStatus.index}].name" value="${httpHeader.name}" type="hidden" />${httpHeader.name}</td>
                        <td><input name="httpHeaders[${loopStatus.index}].value" id="httpHeaders[${loopStatus.index}].value" value="${httpHeader.value}" type="hidden"/>${httpHeader.value}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
        <c:choose>
            <c:when test="${demoMode}">
                <a class="button-secondary pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-plus"></i> <span><spring:message code="rest.restmockresponse.button.updateresponse"/></span></a>
            </c:when>
            <c:otherwise>
                <button class="button-success pure-button" type="submit" name="submit"><i class="fa fa-plus"></i>  <span><spring:message code="rest.restmockresponse.button.updateresponse"/></span></button>
            </c:otherwise>
        </c:choose>
    </sec:authorize>
    <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethodId}"/>" class="button-error pure-button"><i class="fa fa-times"></i> <span><spring:message code="rest.restmockresponse.button.discardchanges"/></span></a>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>
<script src=<c:url value="/resources/js/headerTable.js"/>></script>
<script src=<c:url value="/resources/js/editor.js"/>></script>
<script>
    $("#restMockResponseNameInput").attr('required', '');
    $("#restMockResponseHttpResponseCodeInput").attr('required', '');
    enableTab('body');
    initiateHttpResponseCode('restMockResponseHttpResponseCodeInput','httpCodeLabel', 'httpCodeDefinitionLabel');
    registerXmlFormat('formatXmlButton','body');
    registerJsonFormat('formatJsonButton','body');
</script>
