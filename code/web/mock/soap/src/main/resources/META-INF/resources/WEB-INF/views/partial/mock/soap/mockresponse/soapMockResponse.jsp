<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<c:url var="update_response_url"  value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}/response/${soapMockResponse.id}/update" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/soap/project/${soapProjectId}"><spring:message code="soap.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/soap/project/${soapProjectId}/port/${soapPortId}"><spring:message code="soap.breadcrumb.port"/></a></li>
        <li><a href="${context}/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}"><spring:message code="soap.breadcrumb.operation"/></a></li>
        <li class="active"><spring:message code="soap.soapmockresponse.header.response" arguments="${soapMockResponse.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <div class="title">
                <h1><spring:message code="soap.soapmockresponse.header.response" arguments="${soapMockResponse.name}"/></h1>
            </div>
            <div class="menu" align="right">
                <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                    <a class="btn btn-danger demo-button-disabled" href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}/response/${soapMockResponse.id}/delete"/>"><i class="fas fa-trash"></i> <span><spring:message code="soap.soapmockresponse.button.delete"/></span></a>
                </sec:authorize>
            </div>
        </div>
        <form:form action="${update_response_url}" method="POST" modelAttribute="soapMockResponse" name="soapMockResponseForm">
            <div class="content-summary">
                <table class="formTable">
                    <tr>
                        <td class="column1"><form:label path="name"><spring:message code="soap.soapmockresponse.label.name"/></form:label></td>
                        <td class="column2"><form:input class="form-control" path="name" id="soapMockResponseNameInput" /></td>
                    </tr>
                    <tr>
                        <td class="column1"><form:label path="httpStatusCode"><spring:message code="soap.soapmockresponse.label.httpstatuscode"/></form:label></td>
                        <td class="column2"><form:input class="form-control" path="httpStatusCode" id="soapMockResponseHttpStatusCodeInput" /></td>
                        <td><label id="httpCodeDefinitionLabel"><spring:message code="soap.soapmockresponse.label.httpstatuscodedefinition"/>:&nbsp;</label><label id="httpCodeLabel"></label></td>
                    </tr>
                    <tr>
                        <td class="column1"><form:label path="status"><spring:message code="soap.soapmockresponse.label.status"/></form:label></td>
                        <td>
                            <form:select class="form-control" path="status">
                                <c:forEach items="${soapMockResponseStatuses}" var="soapMockResponseStatus">
                                    <spring:message var="label" code="soap.type.soapmockresponsestatus.${soapMockResponseStatus}"/>
                                    <form:option value="${soapMockResponseStatus}" label="${label}"/>
                                </c:forEach>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td class="column1"><form:label path="usingExpressions"><spring:message code="soap.soapmockresponse.label.useexpressions"/></form:label></td>
                        <td class="column2"><span class="checkbox"><form:checkbox class="form-control" path="usingExpressions"/></span></td>
                    </tr>
                    <tr>
                        <td class="column1"><label><spring:message code="soap.soapmockresponse.label.xpathExpression"/></label></td>
                        <td class="column2"><form:input class="form-control" path="xpathExpression" id="xpathExpression" /></td>
                    </tr>
                </table>
            </div>

        <div class="panel with-nav-tabs panel-primary">
            <div class="panel-heading">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#tab-body"><spring:message code="soap.soapmockresponse.header.body"/></a></li>
                    <li><a data-toggle="tab" href="#tab-headers"><spring:message code="soap.soapmockresponse.header.headers"/></a></li>
                </ul>
            </div>
            <div class="panel-body">
                <div class="tab-content">
                    <div id="tab-body" class="tab-pane fade in active">
                        <h2 class="decorated"><span><spring:message code="soap.soapmockresponse.header.body"/></span></h2>
                        <div class="editor">
                            <form:textarea class="form-control" id="body" path="body"/>
                            <div class="editorButtons">
                                <button id="formatXmlButton" type="button"><spring:message code="soap.soapmockresponse.button.formatxml"/></button>
                            </div>
                        </div>
                    </div>

                    <div id="tab-headers" class="tab-pane fade">
                        <h2 class="decorated"><span><spring:message code="soap.soapmockresponse.field.addheader"/></span></h2>
                        <div>
                            <table class="formTable">
                                <tr>
                                    <td class="column1"><form:label path="name"><spring:message code="soap.soapmockresponse.label.headername"/></form:label></td>
                                    <td class="column2"><input type="text" class="form-control" name="headerName" id="headerNameInput"></td>
                                </tr>
                                <tr>
                                    <td class="column1"><form:label path="name"><spring:message code="soap.soapmockresponse.label.headervalue"/></form:label></td>
                                    <td class="column2"><input type="text" class="form-control" name="headerValue" id="headerValueInput"></td>
                                </tr>
                            </table>
                            <button class="btn btn-success" onclick="addHeader()" type="button"><i class="fas fa-plus-circle"></i>  <span><spring:message code="soap.soapmockresponse.button.addheader"/></span></button>
                        </div>

                        <div class="invisible-divider"></div>

                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover sortable" id="headerTable">
                                <col width="4%">
                                <col width="48%">
                                <col width="48%">
                                <tr>
                                    <th></th>
                                    <th><spring:message code="soap.soapmockresponse.column.headername"/></th>
                                    <th><spring:message code="soap.soapmockresponse.column.headervalue"/></th>
                                </tr>
                                <c:forEach items="${soapMockResponse.httpHeaders}" var="httpHeader" varStatus="loopStatus">
                                    <tr class="even">
                                        <td><div class="delete" onclick="removeHeader('${httpHeader.name}')"></div></td>
                                        <td><input name="httpHeaders[${loopStatus.index}].name" id="httpHeaders[${loopStatus.index}].name" value="${httpHeader.name}" type="hidden" />${httpHeader.name}</td>
                                        <td><input name="httpHeaders[${loopStatus.index}].value" id="httpHeaders[${loopStatus.index}].value" value="${httpHeader.value}" type="hidden"/>${httpHeader.value}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
            <button class="btn btn-success demo-button-disabled" type="submit" name="submit"><i class="fas fa-plus-circle"></i>  <span><spring:message code="soap.soapmockresponse.button.updateresponse"/></span></button>
        </sec:authorize>
        <a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}"/>" class="btn btn-danger"><i class="fas fa-times"></i> <span><spring:message code="soap.soapmockresponse.button.discardchanges"/></span></a>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>
    </section>
</div>
<script src=<c:url value="/resources/js/headerTable.js"/>></script>
<script src=<c:url value="/resources/js/editor.js"/>></script>
<script>
    $("#soapMockResponseNameInput").attr('required', '');
    $("#soapMockResponseHttpStatusCodeInput").attr('required', '');
    enableTab('body');
    initiateHttpResponseCode('soapMockResponseHttpStatusCodeInput','httpCodeLabel', 'httpCodeDefinitionLabel');
    registerXmlFormat('formatXmlButton','body');
</script>
