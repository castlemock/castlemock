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
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}"><spring:message code="rest.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}"><spring:message code="rest.breadcrumb.application"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}"><spring:message code="rest.breadcrumb.resource"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethodId}"><spring:message code="rest.breadcrumb.method"/></a></li>
        <li class="active"><spring:message code="rest.restmockresponse.header.response" arguments="${restMockResponse.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <div class="title">
                <h1><spring:message code="rest.restmockresponse.header.response" arguments="${restMockResponse.name}"/></h1>
            </div>
            <div class="menu" align="right">
                <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                    <a class="btn btn-danger demo-button-disabled" href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethodId}/response/${restMockResponseId}/delete"/>"><i class="fas fa-trash"></i> <span><spring:message code="rest.restmockresponse.button.delete"/></span></a>
                </sec:authorize>
            </div>
        </div>
        <form:form action="${update_response_url}" method="POST" modelAttribute="restMockResponse">
            <div class="content-summary">
                <table class="formTable">
                    <tr>
                        <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.label.name"/></form:label></td>
                        <td class="column2"><form:input class="form-control" path="name" id="restMockResponseNameInput" /></td>
                    </tr>
                    <tr>
                        <td class="column1"><form:label path="httpStatusCode"><spring:message code="rest.restmockresponse.label.httpstatuscode"/></form:label></td>
                        <td class="column2"><form:input class="form-control" path="httpStatusCode" type="number" id="restMockResponseHttpResponseCodeInput"/></td>
                        <td><label id="httpCodeDefinitionLabel"><spring:message code="rest.restmockresponse.label.httpstatuscodedefinition"/>:&nbsp;</label><label id="httpCodeLabel"></label></td>
                    </tr>
                    <tr>
                        <td class="column1"><form:label path="status"><spring:message code="rest.restmockresponse.label.status"/></form:label></td>
                        <td>
                            <form:select class="form-control" path="status">
                                <c:forEach items="${restMockResponseStatuses}" var="restMockResponseStatus">
                                    <spring:message var="label" code="rest.type.restmockresponsestatus.${restMockResponseStatus}"/>
                                    <form:option value="${restMockResponseStatus}" label="${label}"/>
                                </c:forEach>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td class="column1"><form:label path="usingExpressions"><spring:message code="rest.restmockresponse.label.useexpressions"/></form:label></td>
                        <td class="column2"><span class="checkbox"><form:checkbox class="form-control" path="usingExpressions"/></span></td>
                    </tr>
                </table>
            </div>

            <div class="panel with-nav-tabs panel-primary">
                <div class="panel-heading">
                    <ul class="nav nav-tabs">
                        <li class="active"><a data-toggle="tab" href="#tab-body"><spring:message code="rest.restmockresponse.header.body"/></a></li>
                        <li><a data-toggle="tab" href="#tab-headers"><spring:message code="rest.restmockresponse.header.headers"/></a></li>
                        <c:choose>
                            <c:when test="${restQueryParameters.size() > 0}">
                                <li><a data-toggle="tab" href="#tab-queries"><spring:message code="rest.restmockresponse.header.queries"/></a></li>
                            </c:when>
                        </c:choose>
                        <li><a data-toggle="tab" href="#tab-xpaths"><spring:message code="rest.restmockresponse.header.xpaths"/></a></li>
                        <li><a data-toggle="tab" href="#tab-jsonpath"><spring:message code="rest.restmockresponse.header.jsonpaths"/></a></li>
                        <li><a data-toggle="tab" href="#tab-headerqueries"><spring:message code="rest.restmockresponse.header.headerqueries"/></a></li>
                    </ul>
                </div>
                <div class="panel-body">
                    <div class="tab-content">
                        <div id="tab-body" class="tab-pane fade in active">
                            <div>
                                <h2 class="decorated"><span><spring:message code="rest.restmockresponse.header.body"/></span></h2>
                                <div class="editor">
                                    <form:textarea class="form-control" id="body" path="body"/>
                                    <div class="editorButtons">
                                        <button id="formatXmlButton" type="button"><spring:message code="rest.restmockresponse.button.formatxml"/></button>
                                        <button id="formatJsonButton" type="button"><spring:message code="rest.restmockresponse.button.formatjson"/></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="tab-headers" class="tab-pane fade">
                            <h2 class="decorated"><span><spring:message code="rest.restmockresponse.field.addheader"/></span></h2>
                            <div>
                                <table class="formTable">
                                    <tr>
                                        <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.label.headername"/></form:label></td>
                                        <td class="column2"><input type="text" class="form-control" name="headerName" id="headerNameInput"></td>
                                    </tr>
                                    <tr>
                                        <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.label.headervalue"/></form:label></td>
                                        <td class="column2"><input type="text" class="form-control" name="headerValue" id="headerValueInput"></td>
                                    </tr>
                                </table>
                                <button class="btn btn-success" onclick="addHeader()" type="button"><i class="fas fa-plus-circle"></i>  <span><spring:message code="rest.restmockresponse.button.addheader"/></span></button>
                            </div>

                            <div class="invisible-divider"></div>

                            <div class="table-responsive">
                                <table class="table table-bordered table-striped table-hover sortable" id="headerTable">
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

                        <c:choose>
                            <c:when test="${restQueryParameters.size() > 0}">
                                <div id="tab-queries" class="tab-pane fade">
                                    <h2 class="decorated"><span><spring:message code="rest.restmockresponse.field.addquery"/></span></h2>
                                    <div>
                                        <table class="formTable">
                                            <tr>
                                                <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.column.parameter"/></form:label></td>
                                                <td>
                                                    <select name="queryParameterSelect" id="queryParameterSelect" class="form-control">
                                                        <c:forEach items="${restQueryParameters}" var="restQueryParameter">
                                                            <option value="${restQueryParameter.query}" >${restQueryParameter.query}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.column.query"/></form:label></td>
                                                <td class="column2"><input type="text" class="form-control" name="queryInput" id="queryInput"></td>
                                            </tr>
                                            <tr>
                                                <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.column.matchany"/></form:label></td>
                                                <td class="column2"><span class="checkbox"><input type="checkbox" name="queryInput" id="matchAnyInput" class="form-control"/></span></td>
                                            </tr>
                                            <tr>
                                                <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.column.matchcase"/></form:label></td>
                                                <td class="column2"><span class="checkbox"><input type="checkbox" name="queryInput" id="matchCaseInput" class="form-control"/></span></td>
                                            </tr>
                                            <tr>
                                                <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.column.matchregex"/></form:label></td>
                                                <td class="column2"><span class="checkbox"><input type="checkbox" name="regexInput" id="regexInput" class="form-control"/></span></td>
                                            </tr>

                                        </table>
                                        <button class="btn btn-success" onclick="addParameterQuery()" type="button"><i class="fas fa-plus-circle"></i><span><spring:message code="rest.restmockresponse.button.addquery"/></span></button>
                                    </div>

                                    <div class="invisible-divider"></div>

                                    <div class="table-responsive">
                                        <table class="table table-bordered table-striped table-hover sortable" id="queryTable">
                                            <col width="5%">
                                            <col width="32.5%">
                                            <col width="32.5%">
                                            <col width="10%">
                                            <col width="10%">
                                            <col width="10%">
                                            <tr>
                                                <th></th>
                                                <th><spring:message code="rest.restmockresponse.column.parameter"/></th>
                                                <th><spring:message code="rest.restmockresponse.column.query"/></th>
                                                <th><spring:message code="rest.restmockresponse.column.matchany"/></th>
                                                <th><spring:message code="rest.restmockresponse.column.matchcase"/></th>
                                                <th><spring:message code="rest.restmockresponse.column.matchregex"/></th>
                                            </tr>
                                            <c:forEach items="${restMockResponse.parameterQueries}" var="parameterQuery" varStatus="loopStatus">
                                                <tr class="even">
                                                    <c:set var = "parameterQueryId" value = "${UUID.randomUUID().toString()}"/>
                                                    <td hidden="hidden"><c:out value = "${parameterQueryId}"/></td>
                                                    <td><div class="delete" onclick="removeParameterQuery('${parameterQueryId}')"></div></td>
                                                    <td><input name="parameterQueries[${loopStatus.index}].parameter" id="parameterQueries[${loopStatus.index}].parameter" value="${parameterQuery.parameter}" type="hidden" />${parameterQuery.parameter}</td>
                                                    <td><input name="parameterQueries[${loopStatus.index}].query" id="parameterQueries[${loopStatus.index}].query" value="${parameterQuery.query}" type="hidden"/>${parameterQuery.query}</td>
                                                    <td><input name="parameterQueries[${loopStatus.index}].matchAny" id="parameterQueries[${loopStatus.index}].matchAny" value="${parameterQuery.matchAny}" type="hidden"/>${parameterQuery.matchAny}</td>
                                                    <td><input name="parameterQueries[${loopStatus.index}].matchCase" id="parameterQueries[${loopStatus.index}].matchCase" value="${parameterQuery.matchCase}" type="hidden"/>${parameterQuery.matchCase}</td>
                                                    <td><input name="parameterQueries[${loopStatus.index}].matchRegex" id="parameterQueries[${loopStatus.index}].matchRegex" value="${parameterQuery.matchRegex}" type="hidden"/>${parameterQuery.matchRegex}</td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>

                        <div id="tab-xpaths" class="tab-pane fade">
                            <h2 class="decorated"><span><spring:message code="rest.restmockresponse.field.addxpath"/></span></h2>
                            <div>
                                <table class="formTable">
                                    <tr>
                                        <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.label.xpath"/></form:label></td>
                                        <td class="column2"><input type="text" class="form-control" name="xpathInput" id="xpathInput"></td>
                                    </tr>
                                </table>
                                <button class="btn btn-success" onclick="addXpath()" type="button"><i class="fas fa-plus-circle"></i>  <span><spring:message code="rest.restmockresponse.button.addxpath"/></span></button>
                            </div>

                            <div class="invisible-divider"></div>

                            <div class="table-responsive">
                                <table class="table table-bordered table-striped table-hover sortable" id="xpathTable">
                                    <col width="4%">
                                    <col width="96%">
                                    <tr>
                                        <th></th>
                                        <th><spring:message code="rest.restmockresponse.column.xpath"/></th>
                                    </tr>
                                    <c:forEach items="${restMockResponse.xpathExpressions}" var="xpathExpression" varStatus="loopStatus">
                                        <tr class="even">
                                            <c:set var = "xpathId" value = "${UUID.randomUUID().toString()}"/>
                                            <td hidden="hidden"><c:out value = "${xpathId}"/></td>
                                            <td><div class="delete" onclick="removeXpath('${xpathId}')"></div></td>
                                            <td><input name="xpathExpressions[${loopStatus.index}].expression" id="xpathExpressions[${loopStatus.index}].expression" value="${xpathExpression.expression}" type="hidden" />${xpathExpression.expression}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>

                        <div id="tab-jsonpath" class="tab-pane fade">
                            <h2 class="decorated"><span><spring:message code="rest.restmockresponse.field.addjsonpath"/></span></h2>
                            <div>
                                <table class="formTable">
                                    <tr>
                                        <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.label.jsonpath"/></form:label></td>
                                        <td class="column2"><input type="text" class="form-control" name="jsonPathInput" id="jsonPathInput"></td>
                                    </tr>
                                </table>
                                <button class="btn btn-success" onclick="addJsonPath()" type="button"><i class="fas fa-plus-circle"></i>  <span><spring:message code="rest.restmockresponse.button.addjsonpath"/></span></button>
                            </div>

                            <div class="invisible-divider"></div>

                            <div class="table-responsive">
                                <table class="table table-bordered table-striped table-hover sortable" id="jsonPathTable">
                                    <col width="4%">
                                    <col width="96%">
                                    <tr>
                                        <th></th>
                                        <th><spring:message code="rest.restmockresponse.column.jsonpath"/></th>
                                    </tr>
                                    <c:forEach items="${restMockResponse.jsonPathExpressions}" var="jsonPathExpression" varStatus="loopStatus">
                                        <tr class="even">
                                            <c:set var = "jsonPathId" value = "${UUID.randomUUID().toString()}"/>
                                            <td hidden="hidden"><c:out value = "${jsonPathId}"/></td>
                                            <td><div class="delete" onclick="removeJsonPath('${jsonPathId}')"></div></td>
                                            <td><input name="jsonPathExpressions[${loopStatus.index}].expression" id="jsonPathExpressions[${loopStatus.index}].expression" value="${jsonPathExpression.expression}" type="hidden" />${jsonPathExpression.expression}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>

                        <div id="tab-headerqueries" class="tab-pane fade">
                            <h2 class="decorated"><span><spring:message code="rest.restmockresponse.field.addheaderquery"/></span></h2>
                            <div>
                                <table class="formTable">
                                    <tr>
                                        <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.column.header"/></form:label></td>
                                        <td class="column2"><input type="text" class="form-control" name="headerInput" id="headerInput"></td>
                                    </tr>
                                    <tr>
                                        <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.column.query"/></form:label></td>
                                        <td class="column2"><input type="text" class="form-control" name="headerQueryInput" id="headerQueryInput"></td>
                                    </tr>
                                    <tr>
                                        <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.column.matchany"/></form:label></td>
                                        <td class="column2"><span class="checkbox"><input type="checkbox" name="headerMatchAnyInput" id="headerMatchAnyInput" class="form-control"/></span></td>
                                    </tr>
                                    <tr>
                                        <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.column.matchcase"/></form:label></td>
                                        <td class="column2"><span class="checkbox"><input type="checkbox" name="headerMatchCaseInput" id="headerMatchCaseInput" class="form-control"/></span></td>
                                    </tr>
                                    <tr>
                                        <td class="column1"><form:label path="name"><spring:message code="rest.restmockresponse.column.matchregex"/></form:label></td>
                                        <td class="column2"><span class="checkbox"><input type="checkbox" name="headerRegexInput" id="headerRegexInput" class="form-control"/></span></td>
                                    </tr>

                                </table>
                                <button class="btn btn-success" onclick="addHeaderQuery()" type="button"><i class="fas fa-plus-circle"></i><span><spring:message code="rest.restmockresponse.button.addquery"/></span></button>
                            </div>

                            <div class="invisible-divider"></div>

                            <div class="table-responsive">
                                <table class="table table-bordered table-striped table-hover sortable" id="headerQueryTable">
                                    <col width="5%">
                                    <col width="32.5%">
                                    <col width="32.5%">
                                    <col width="10%">
                                    <col width="10%">
                                    <col width="10%">
                                    <tr>
                                        <th></th>
                                        <th><spring:message code="rest.restmockresponse.column.header"/></th>
                                        <th><spring:message code="rest.restmockresponse.column.query"/></th>
                                        <th><spring:message code="rest.restmockresponse.column.matchany"/></th>
                                        <th><spring:message code="rest.restmockresponse.column.matchcase"/></th>
                                        <th><spring:message code="rest.restmockresponse.column.matchregex"/></th>
                                    </tr>
                                    <c:forEach items="${restMockResponse.headerQueries}" var="headerQuery" varStatus="loopStatus">
                                        <tr class="even">
                                            <c:set var = "headerQueryId" value = "${UUID.randomUUID().toString()}"/>
                                            <td hidden="hidden"><c:out value = "${headerQueryId}"/></td>
                                            <td><div class="delete" onclick="removeHeaderQuery('${headerQueryId}')"></div></td>
                                            <td><input name="headerQueries[${loopStatus.index}].header" id="headerQueries[${loopStatus.index}].header" value="${headerQuery.header}" type="hidden" />${headerQuery.header}</td>
                                            <td><input name="headerQueries[${loopStatus.index}].query" id="headerQueries[${loopStatus.index}].query" value="${headerQuery.query}" type="hidden"/>${headerQuery.query}</td>
                                            <td><input name="headerQueries[${loopStatus.index}].matchAny" id="headerQueries[${loopStatus.index}].matchAny" value="${headerQuery.matchAny}" type="hidden"/>${headerQuery.matchAny}</td>
                                            <td><input name="headerQueries[${loopStatus.index}].matchCase" id="headerQueries[${loopStatus.index}].matchCase" value="${headerQuery.matchCase}" type="hidden"/>${headerQuery.matchCase}</td>
                                            <td><input name="headerQueries[${loopStatus.index}].matchRegex" id="headerQueries[${loopStatus.index}].matchRegex" value="${headerQuery.matchRegex}" type="hidden"/>${headerQuery.matchRegex}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                <button class="btn btn-success demo-button-disabled" type="submit" name="submit"><i class="fas fa-plus-circle"></i>  <span><spring:message code="rest.restmockresponse.button.updateresponse"/></span></button>
            </sec:authorize>
            <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethodId}"/>" class="btn btn-danger"><i class="fas fa-times"></i> <span><spring:message code="rest.restmockresponse.button.discardchanges"/></span></a>
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>
    </section>
</div>
<script src=<c:url value="/resources/js/headerTable.js"/>></script>
<script src=<c:url value="/resources/js/queryTable.js"/>></script>
<script src=<c:url value="/resources/js/xpathTable.js"/>></script>
<script src=<c:url value="/resources/js/jsonPathTable.js"/>></script>
<script src=<c:url value="/resources/js/headerQueryTable.js"/>></script>
<script src=<c:url value="/resources/js/editor.js"/>></script>
<script>
    $("#restMockResponseNameInput").attr('required', '');
    $("#restMockResponseHttpResponseCodeInput").attr('required', '');
    enableTab('body');
    initiateHttpResponseCode('restMockResponseHttpResponseCodeInput','httpCodeLabel', 'httpCodeDefinitionLabel');
    registerXmlFormat('formatXmlButton','body');
    registerJsonFormat('formatJsonButton','body');
</script>
