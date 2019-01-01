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

<c:url var="update_rest_method_url"  value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}/update" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}"><spring:message code="rest.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}"><spring:message code="rest.breadcrumb.application"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}"><spring:message code="rest.breadcrumb.resource"/></a></li>
        <li><a href="${context}/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}"><spring:message code="rest.breadcrumb.method"/></a></li>
        <li class="active"><spring:message code="rest.updatemethod.header.updatemethod" arguments="${restMethod.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="rest.updatemethod.header.updatemethod" arguments="${restMethod.name}"/></h1>
        </div>
        <form:form action="${update_rest_method_url}" method="POST" modelAttribute="restMethod">
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="name"><spring:message code="rest.updatemethod.label.name"/></label></td>
                    <td class="column2"><form:input class="form-control" path="name" id="restMethodNameInput" value="${restMethod.name}"/></td>
                </tr>
                <tr>
                    <td class="column1"><spring:message code="rest.updatemethod.label.methodtype"/></td>
                    <td>
                        <form:select class="form-control" path="httpMethod">
                            <c:forEach items="${restMethodTypes}" var="type">
                                <form:option value="${type}" label="${type}"/>
                            </c:forEach>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td class="column1"><spring:message code="rest.updatemethod.label.methodstatus"/></td>
                    <td>
                        <form:select id="status" class="form-control" path="status"
                            onchange="toggleEnabled('status', 'MOCKED', ['responseStrategy', 'defaultMockResponseId']) ">
                            <c:forEach items="${restMethodStatuses}" var="restMethodStatus">
                                <spring:message var="label" code="rest.type.restmethodstatus.${restMethodStatus}"/>
                                <form:option value="${restMethodStatus}" label="${label}"/>
                            </c:forEach>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td class="column1"><spring:message code="rest.updatemethod.label.responsestrategy"/></td>
                    <td>
                        <form:select id="responseStrategy" class="form-control" path="responseStrategy"
                                     onchange="toggleEnabled('responseStrategy', ['QUERY_MATCH', 'XPATH', 'JSON_PATH'], ['defaultMockResponseId'])">
                            <c:forEach items="${restResponsestrategies}" var="restResponseStrategy">
                                <spring:message var="label" code="rest.type.responsestrategy.${restResponseStrategy}"/>
                                <form:option value="${restResponseStrategy}" label="${label}"/>
                            </c:forEach>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td class="column1"><label path="name"><spring:message code="rest.updatemethod.label.forwardedendpoint"/></label></td>
                    <td class="column2"><form:input class="form-control" path="forwardedEndpoint" value="${restMethod.forwardedEndpoint}"/></td>
                </tr>
                <tr>
                    <td class="column1"><form:label path="simulateNetworkDelay"><spring:message code="rest.updatemethod.label.simulatenetworkdelay"/></form:label></td>
                    <td class="column2"><span class="checkbox"><form:checkbox class="form-control" path="simulateNetworkDelay"/></span></td>
                </tr>
                <tr>
                    <td class="column1"><label path="name"><spring:message code="rest.updatemethod.label.networkdelay"/> (ms)</label></td>
                    <td class="column2"><form:input class="form-control" type="number" path="networkDelay" value="${restMethod.networkDelay}"/></td>
                </tr>
                <tr>
                    <td class="column1"><spring:message code="rest.updatemethod.label.defaultresponse"/></td>
                    <td>
                        <form:select id="defaultMockResponseId" class="form-control" path="defaultMockResponseId">
                            <spring:message var="label" code="rest.updatemethod.dropdown.option.selectresponse"/>
                            <form:option value="" label="${label}"/>
                            <c:forEach items="${restMethod.mockResponses}" var="mockResponse">
                                <form:option value="${mockResponse.id}" label="${mockResponse.name}"/>
                            </c:forEach>
                        </form:select>
                    </td>
                </tr>
            </table>

            <button class="btn btn-success" type="submit" name="submit"><i class="fas fa-check-circle"></i><spring:message code="rest.updatemethod.button.updatemethod"/></button>
            <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}"/>" class="btn btn-danger"><i class="fas fa-times"></i> <spring:message code="rest.updatemethod.button.cancel"/></a>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>
    </section>
</div>
<script src=<c:url value="/resources/js/UIHelper.js"/>></script>
<script>
    $("#restMethodNameInput").attr('required', '');
    toggleEnabled('status', 'MOCKED', ['responseStrategy', 'defaultMockResponseId']);
    toggleEnabled('responseStrategy', ['QUERY_MATCH', 'XPATH', 'JSON_PATH'], ['defaultMockResponseId']);
</script>
