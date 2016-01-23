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
<c:url var="delete_soap_mock_responses_url"  value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}/response/delete/confirm" />
<div class="content-top">
    <h1><spring:message code="soap.deletesoapmockresponses.header.deleteresponses"/></h1>
</div>
<c:choose>
    <c:when test="${soapMockResponses.size() > 0}">
        <p><spring:message code="soap.deletesoapmockresponses.label.confirmation"/></p>
        <form:form action="${delete_soap_mock_responses_url}" method="POST" commandName="deleteSoapMockResponsesCommand">
            <ul>
                <c:forEach items="${soapMockResponses}" var="soapMockResponse" varStatus="loopStatus">
                    <li>${soapMockResponse.name}</li>
                    <form:hidden path="soapMockResponses[${loopStatus.index}].id" value="${soapMockResponse.id}"/>
                </c:forEach>
            </ul>

            <button class="button-error pure-button" type="submit"><i class="fa fa-trash"></i> <span><spring:message code="soap.deletesoapmockresponses.button.deleteresponses"/></span></button>
            <a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="soap.deletesoapmockresponses.button.cancel"/></a>
        </form:form>
    </c:when>
    <c:otherwise>
        <spring:message code="soap.deletesoapmockresponses.label.nomockedresponses"/>
        <p>
        <a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="soap.deletesoapmockresponses.button.cancel"/></a>
        </p>
    </c:otherwise>
</c:choose>