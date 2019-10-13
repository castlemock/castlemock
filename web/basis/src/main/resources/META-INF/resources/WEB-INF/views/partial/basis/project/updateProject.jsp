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
<c:url var="update_project_url"  value="/web/${project.typeIdentifier.typeUrl}/project/${project.id}/update" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/${project.typeIdentifier.typeUrl}/project/${project.id}"><spring:message code="${project.typeIdentifier.typeUrl}.breadcrumb.project"/></a></li>
        <li class="active"><spring:message code="general.updateproject.header.updateproject" arguments="${project.name}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="general.updateproject.header.updateproject" arguments="${project.name}"/></h1>
        </div>
        <form:form action="${update_project_url}" method="POST" modelAttribute="project">
            <table class="formTable">
                <tr>
                    <td class="column1"><form:label path="name"><spring:message code="general.updateproject.label.name"/></form:label></td>
                    <td class="column2"><form:input path="name" class="form-control" id="projectNameInput" value="${project.name}"/></td>
                </tr>
                <tr>
                    <td class="column1"><spring:message code="general.updateproject.label.description"/></td>
                    <td class="column2"><form:textarea class="form-control" rows="4" path="description"></form:textarea></td>
                </tr>
            </table>
            <button class="btn btn-success"><i class="fas fa-check-circle"></i> <spring:message code="general.updateproject.button.updateproject"/></button>
            <a href="<c:url value="/web/${project.typeIdentifier.typeUrl}/project/${project.id}"/>" class="btn btn-primary"><i class="fas fa-times"></i> <spring:message code="general.updateproject.button.discardchanges"/></a>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>
        <script>
            $("#projectNameInput").attr('required', '');
        </script>
    </section>
</div>
