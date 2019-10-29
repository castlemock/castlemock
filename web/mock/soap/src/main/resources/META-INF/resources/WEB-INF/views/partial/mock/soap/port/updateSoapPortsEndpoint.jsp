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
<c:url var="update_ports_endpoint_url"  value="/web/soap/project/${soapProjectId}/port/update/confirm" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/soap/project/${soapProjectId}"><spring:message code="soap.breadcrumb.project"/></a></li>
        <li class="active"><spring:message code="soap.updatesoapportsendpoint.header.updateport"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="soap.updatesoapportsendpoint.header.updateport"/></h1>
        </div>
        <c:choose>
            <c:when test="${soapPorts.size() > 0}">
                <p><spring:message code="soap.updatesoapportsendpoint.label.confirmation"/></p>
                <form:form action="${update_ports_endpoint_url}" method="POST" modelAttribute="command">
                    <ul>
                        <c:forEach items="${soapPorts}" var="soapPort" varStatus="loopStatus">
                            <li>${soapPort.name}</li>
                            <form:hidden path="soapPorts[${loopStatus.index}].id" value="${soapPort.id}"/>
                        </c:forEach>
                    </ul>
                    <table class="formTable">
                        <tr>
                            <td class="column1"><label path="forwardedEndpoint"><spring:message code="soap.updatesoapportsendpoint.label.forwardedendpoint"/></label></td>
                            <td class="column2"><form:input path="forwardedEndpoint" value="${command.forwardedEndpoint}"/></td>
                        </tr>
                    </table>
                    <button class="btn btn-success"><i class="fas fa-check-circle"></i> <span><spring:message code="soap.updatesoapportsendpoint.button.updateport"/></span></button>
                    <a href="<c:url value="/web/soap/project/${soapProjectId}"/>" class="btn btn-primary"><i class="fas fa-times"></i> <spring:message code="soap.updatesoapportsendpoint.button.cancel"/></a>
                </form:form>
            </c:when>
            <c:otherwise>
                <p><spring:message code="soap.updatesoapportsendpoint.label.noports"/></p>
                <a href="<c:url value="/web/soap/project/${soapProjectId}"/>" class="btn btn-primary"><i class="fas fa-times"></i> <spring:message code="soap.updatesoapportsendpoint.button.cancel"/></a>
            </c:otherwise>
        </c:choose>
    </section>
</div>