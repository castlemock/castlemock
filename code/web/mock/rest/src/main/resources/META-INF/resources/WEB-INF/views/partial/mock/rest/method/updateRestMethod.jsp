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
<div class="content-top">
    <h1><spring:message code="rest.updatemethod.header.updatemethod" arguments="${restMethod.name}"/></h1>
</div>
<form:form action="${update_rest_method_url}" method="POST" commandName="restMethod">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="rest.updatemethod.label.name"/></label></td>
            <td class="column2"><form:input path="name" id="restMethodNameInput" value="${restMethod.name}"/></td>
        </tr>
        <tr>
            <td class="column1"><spring:message code="rest.updatemethod.label.methodtype"/></td>
            <td>
                <form:select path="restMethodType">
                    <c:forEach items="${restMethodTypes}" var="restMethodType">
                        <option value="${restMethodType}">${restMethodType}</option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="column1"><spring:message code="rest.updatemethod.label.methodstatus"/></td>
            <td>
                <form:select path="restMethodStatus">
                    <c:forEach items="${restMethodStatuses}" var="restMethodStatus">
                        <option value="${restMethodStatus}"><spring:message code="rest.type.restmethodstatus.${restMethodStatus}"/></option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="column1"><spring:message code="rest.updatemethod.label.responsestrategy"/></td>
            <td>
                <form:select path="restResponseStrategy">
                    <c:forEach items="${restResponsestrategies}" var="restResponseStrategy">
                        <option value="${restResponseStrategy}"><spring:message code="rest.type.responsestrategy.${restResponseStrategy}"/></option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="rest.updatemethod.label.forwardedendpoint"/></label></td>
            <td class="column2"><form:input path="forwardedEndpoint" value="${restMethod.forwardedEndpoint}"/></td>
        </tr>
    </table>
    
    <button class="button-success pure-button" type="submit" name="submit"><spring:message code="rest.updatemethod.button.updatemethod"/></button>
    <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/${restMethod.id}"/>" class="button-error pure-button"><i class="fa fa-check-circle"></i> <spring:message code="rest.updatemethod.button.cancel"/></a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>
<script>
    $("#restMethodNameInput").attr('required', '');
</script>
