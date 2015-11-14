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

<c:url var="create_rest_method_url"  value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/create/method" />
<div class="content-top">
    <h1><spring:message code="rest.createmethod.header.method"/></h1>
</div>
<form:form action="${create_rest_method_url}" method="POST">
    <table class="formTable">
        <tr>
            <td class="column1"><label><spring:message code="rest.restresource.label.name"/></label></td>
            <td class="column2"><form:input path="restMethod.name" />
        </tr>
        <tr>
            <td class="column1"><spring:message code="rest.createmethod.label.methodtype"/></td>
            <td>
                <form:select path="restMethod.restMethodType">
                    <c:forEach items="${restMethodTypes}" var="restMethodType">
                        <option value="${restMethodType}">${restMethodType}</option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
    </table>
 
    <button class="button-success pure-button" type="submit" name="submit"><i class="fa fa-plus"></i> <span><spring:message code="rest.createmethod.button.createmethod"/></span></button>
    <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${resourceId}"/>" class="button-error pure-button"><i class="fa fa-times"></i> <span><spring:message code="rest.createmethod.button.cancel"/></span></a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>