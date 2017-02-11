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
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/soap/project/${soapProjectId}"><spring:message code="soap.breadcrumb.project"/></a></li>
        <li><a href="${context}/web/soap/project/${soapProjectId}/port/${soapPort.id}"><spring:message code="soap.breadcrumb.port"/></a></li>
        <li class="active"><spring:message code="soap.deletesoapport.header.deleleteport" arguments="${soapPort.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
        <h1><spring:message code="soap.deletesoapport.header.deleleteport" arguments="${soapPort.name}"/></h1>
        </div>
        <spring:message code="soap.deletesoapport.label.confirmation" arguments="${soapPort.name}"/>

        <p>
        <a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPort.id}/delete/confirm"/>" class="btn btn-danger"><i class="fa fa-trash"></i> <spring:message code="soap.deletesoapport.button.deleteport"/></a>
        <a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPort.id}"/>" class="btn btn-primary"><i class="fa fa-times"></i> <spring:message code="soap.deletesoapport.button.cancel"/></a>
        </p>
    </section>
</div>