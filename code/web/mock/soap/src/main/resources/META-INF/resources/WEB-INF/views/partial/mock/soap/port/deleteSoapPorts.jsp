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
<c:url var="delete_ports_url"  value="/web/soap/project/${soapProjectId}/port/delete/confirm" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/soap/project/${soapProjectId}"><spring:message code="soap.breadcrumb.project"/></a></li>
        <li class="active"><spring:message code="soap.deletesoapports.header.deleteports"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="soap.deletesoapports.header.deleteports"/></h1>
        </div>
        <c:choose>
            <c:when test="${soapPorts.size() > 0}">
                <p><spring:message code="soap.deletesoapports.label.confirmation"/></p>
                <form:form action="${delete_ports_url}" method="POST" modelAttribute="deleteSoapPortsCommand">
                    <ul>
                        <c:forEach items="${soapPorts}" var="soapPort" varStatus="loopStatus">
                            <li>${soapPort.name}</li>
                            <form:hidden path="soapPorts[${loopStatus.index}].id" value="${soapPort.id}"/>
                        </c:forEach>
                    </ul>

                    <button class="btn btn-danger" type="submit"><i class="fa fa-trash"></i> <span><spring:message code="soap.deletesoapports.button.deleteports"/></span></button>
                    <a href="<c:url value="/web/soap/project/${soapProjectId}"/>" class="btn btn-primary"><i class="fa fa-times"></i> <spring:message code="soap.deletesoapports.button.cancel"/></a>
                </form:form>
            </c:when>
            <c:otherwise>
                <spring:message code="soap.deletesoapports.label.noports"/>
                <p>
                <a href="<c:url value="/web/soap/project/${soapProjectId}"/>" class="btn btn-primary"><i class="fa fa-times"></i> <spring:message code="soap.deletesoapports.button.cancel"/></a>
                </p>
            </c:otherwise>
        </c:choose>
    </section>
</div>