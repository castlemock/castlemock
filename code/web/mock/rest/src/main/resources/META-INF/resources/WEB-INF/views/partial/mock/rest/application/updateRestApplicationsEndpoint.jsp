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
<c:url var="update_applications_endpoint_url"  value="/web/rest/project/${restProjectId}/application/update/confirm" />
<div class="content-top">
    <h1><spring:message code="rest.updaterestapplicationsendpoint.header.updateapplication"/></h1>
</div>
<c:choose>
    <c:when test="${restApplications.size() > 0}">
        <p><spring:message code="rest.updaterestapplicationsendpoint.label.confirmation"/></p>
        <form:form action="${update_applications_endpoint_url}" method="POST" commandName="updateRestApplicationsEndpointCommand">
            <ul>
                <c:forEach items="${restApplications}" var="restApplication" varStatus="loopStatus">
                    <li>${restApplication.name}</li>
                    <form:hidden path="restApplications[${loopStatus.index}].id" value="${restApplication.id}"/>
                </c:forEach>
            </ul>
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="forwardedEndpoint"><spring:message code="rest.updaterestapplicationsendpoint.label.forwardedendpoint"/></label></td>
                    <td class="column2"><form:input path="forwardedEndpoint" value="${updateRestApplicationsEndpointCommand.forwardedEndpoint}"/></td>
                </tr>
            </table>
            <button class="button-success pure-button"><i class="fa fa-check-circle"></i> <span><spring:message code="rest.updaterestapplicationsendpoint.button.updateapplications"/></span></button>
            <a href="<c:url value="/web/rest/project/${restProjectId}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="rest.updaterestapplicationsendpoint.button.cancel"/></a>
        </form:form>
    </c:when>
    <c:otherwise>
        <p><spring:message code="rest.updaterestapplicationsendpoint.label.noapplications"/></p>
        <a href="<c:url value="/web/rest/project/${restProjectId}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="rest.updaterestapplicationsendpoint.button.cancel"/></a>
    </c:otherwise>
</c:choose>