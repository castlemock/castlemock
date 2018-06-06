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
<c:url var="update_soap_port_url"  value="/web/soap/project/${soapProjectId}/port/${soapPortId}/update" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/soap/project/${soapProjectId}"><spring:message code="soap.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/soap/project/${soapProjectId}/port/${soapPortId}"><spring:message code="soap.breadcrumb.port"/></a></li>
        <li class="active"><spring:message code="soap.updatesoapport.header.updateport" arguments="${soapPort.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="soap.updatesoapport.header.updateport" arguments="${soapPort.name}"/></h1>
        </div>
        <form:form action="${update_soap_port_url}" method="POST" modelAttribute="soapPort">
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="name"><spring:message code="soap.updatesoapport.label.name"/></label></td>
                    <td class="column2"><label path="name">${soapPort.name}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="uri"><spring:message code="soap.updatesoapport.label.uri"/></label></td>
                    <td class="column2"><form:input class="form-control" path="uri" value="${soapPort.uri}"/></td>
                </tr>
            </table>

            <button class="btn btn-success" type="submit" name="submit"><i class="fas fa-check-circle"></i><spring:message code="soap.updatesoapport.button.updateport"/></button>
            <a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}"/>" class="btn btn-danger"><i class="fas fa-times"></i> <spring:message code="soap.updatesoapport.button.cancel"/></a>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>
    </section>
</div>