<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
 Copyright 2017 Karl Dahlgren

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
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/soap/project/${soapProjectId}"><spring:message code="soap.breadcrumb.project"/></a></li>
        <li class="active"><spring:message code="soap.soapresource.header.resource" arguments="${soapResource.id}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <div class="title">
                <h1><spring:message code="soap.soapresource.header.resource" arguments="${soapResource.name}"/></h1>
            </div>
        </div>

        <div class="content-summary">
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="name"><spring:message code="soap.soapresource.label.name"/></label></td>
                    <td class="column2"><label path="name">${soapResource.name}</label></td>
                </tr>
                <tr>
                    <td class="column1"><label path="name"><spring:message code="soap.soapresource.label.type"/></label></td>
                    <td class="column2"><label path="type">${soapResource.type}</label></td>
                </tr>
            </table>
        </div>

        <h2 class="decorated"><span><spring:message code="soap.soapresource.header.body"/></span></h2>
        <div class="editor">
            <textarea id="resource" readonly><c:out value="${soapResourceData}"/></textarea>
        </div>
    </section>
</div>
