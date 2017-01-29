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

<c:url var="soap_port_update_url"  value="/web/soap/project/${soapProject.id}" />
<div class="content-top">
    <div class="title">
        <h1><spring:message code="soap.soapproject.header.project" arguments="${soapProject.name}"/></h1>
    </div>
    <div class="menu" align="right">
        <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
            <c:choose>
                <c:when test="${demoMode}">
                    <a class="button-success pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-file"></i> <span><spring:message code="soap.soapproject.button.updateproject"/></span></a>
                    <a class="button-secondary pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-upload"></i> <span><spring:message code="soap.soapproject.button.upload" arguments="wsdl"/></span></a>
                </c:when>
                <c:otherwise>
                    <a class="button-success pure-button" href="<c:url value="/web/soap/project/${soapProject.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="soap.soapproject.button.updateproject"/></span></a>
                    <a class="button-secondary pure-button" href="<c:url value="/web/soap/project/${soapProject.id}/add/wsdl"/>"><i class="fa fa-upload"></i> <span><spring:message code="soap.soapproject.button.upload" arguments="wsdl"/></span></a>
                </c:otherwise>
            </c:choose>
        <a class="button-secondary pure-button" href="<c:url value="/web/soap/project/${soapProject.id}/export"/>"><i class="fa fa-cloud-download"></i> <span><spring:message code="soap.soapproject.button.export"/></span></a>
            <c:choose>
                <c:when test="${demoMode}">
                    <a class="button-error pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-trash"></i> <span><spring:message code="soap.soapproject.button.delete"/></span></a>
                </c:when>
                <c:otherwise>
                    <a class="button-error pure-button" href="<c:url value="/web/soap/project/${soapProject.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="soap.soapproject.button.delete"/></span></a>
                </c:otherwise>
            </c:choose>
        </sec:authorize>
    </div>
</div>
<div class="content-summary">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="soap.soapproject.label.name"/></label></td>
            <td class="column2"><label path="name">${soapProject.name}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="description"><spring:message code="soap.soapproject.label.description"/></label></td>
            <td class="column2"><label path="description">${soapProject.description}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="projectType"><spring:message code="soap.soapproject.label.type"/></label></td>
            <td class="column2"><label path="projectType">SOAP</label></td>
        </tr>
    </table>
</div>

<h2 class="decorated"><span><spring:message code="soap.soapproject.header.ports"/></span></h2>
<c:choose>
    <c:when test="${soapProject.ports.size() > 0}">
        <form:form action="${soap_port_update_url}/" method="POST"  commandName="soapPortModifierCommand">
            <div class="table-frame">
                <table class="entityTable sortable">
                    <col width="10%">
                    <col width="40%">
                    <tr>
                        <th><spring:message code="soap.soapproject.column.selected"/></th>
                        <th><spring:message code="soap.soapproject.column.port"/></th>
                        <c:forEach items="${soapOperationStatuses}" var="soapOperationStatus">
                            <th><spring:message code="soap.type.soapoperationstatus.${soapOperationStatus}"/></th>
                        </c:forEach>
                    </tr>
                    <c:forEach items="${soapProject.ports}" var="soapPort" varStatus="loopStatus">
                        <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                            <td><form:checkbox path="soapPortIds" name="${soapPort.id}" value="${soapPort.id}"/></td>
                            <td><a href="<c:url value="/web/soap/project/${soapProject.id}/port/${soapPort.id}"/>">${soapPort.name}</a></td>
                            <c:forEach items="${soapOperationStatuses}" var="soapOperationStatus">
                                <td>${soapPort.statusCount[soapOperationStatus]}</td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                <form:select path="soapPortStatus">
                    <c:forEach items="${soapOperationStatuses}" var="soapOperationStatus">
                        <form:option value="${soapOperationStatus}"><spring:message code="soap.type.soapoperationstatus.${soapOperationStatus}"/></form:option>
                    </c:forEach>
                </form:select>
                <c:choose>
                    <c:when test="${demoMode}">
                        <a class="button-success pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-check-circle"></i> <span><spring:message code="soap.soapproject.button.update"/></span></a>
                        <a class="button-secondary pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-code-fork"></i> <span><spring:message code="soap.soapproject.button.updateendpoint"/></span></a>
                        <a class="button-error pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-trash"></i> <span><spring:message code="soap.soapproject.button.deleteport"/></span></a>
                    </c:when>
                    <c:otherwise>
                        <button class="button-success pure-button" type="submit" name="action" value="update"><i class="fa fa-check-circle"></i> <span><spring:message code="soap.soapproject.button.update"/></span></button>
                        <button class="button-secondary pure-button" type="submit" name="action" value="update-endpoint"><i class="fa fa-code-fork"></i> <span><spring:message code="soap.soapproject.button.updateendpoint"/></span></button>
                        <button class="button-error pure-button" type="submit" name="action" value="delete"><i class="fa fa-trash"></i> <span><spring:message code="soap.soapproject.button.deleteport"/></span></button>
                    </c:otherwise>
                </c:choose>
            </sec:authorize>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>

    </c:when>
    <c:otherwise>
        <spring:message code="soap.soapproject.label.noports" arguments="wsdl"/>
    </c:otherwise>
</c:choose>
