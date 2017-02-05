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

<%@ include file="../../../includes.jspf"%>
<div class="content-top">
    <h1><spring:message code="general.deleteproject.header.deleteproject" arguments="${project.name}"/></h1>
</div>
<spring:message code="general.deleteproject.label.confirmation" arguments="${project.name}"/>
<p>
<a href="<c:url value="/web/${project.typeIdentifier.typeUrl}/project/${project.id}/delete/confirm"/>" class="btn btn-danger"><i class="fa fa-trash"></i> <spring:message code="general.deleteproject.button.deleteproject"/></a>
<a href="<c:url value="/web/${project.typeIdentifier.typeUrl}/project/${project.id}"/>" class="btn btn-primary"><i class="fa fa-times"></i> <spring:message code="general.deleteproject.button.cancel"/></a>
</p>