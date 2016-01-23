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
<c:url var="create_project_url"  value="/web/project/create" />
<div class="content-top">
    <h1><spring:message code="general.createproject.header"/></h1>
</div>
<form:form action="${create_project_url}" method="POST">
    <table class="formTable">
        <tr>
            <td class="column1"><label><spring:message code="general.createproject.label.name"/></label></td>
            <td class="column2"><form:input id="projectNameInput" path="project.name"  />
        </tr>
        <tr>
            <td class="column1"><spring:message code="general.createproject.label.description"/></td>
            <td class="column2"><form:textarea rows="4" path="project.description"></form:textarea></td>
        </tr>
        <tr>
            <td class="column1"><spring:message code="general.createproject.label.projecttype"/></td>
            <td>
                <form:select path="projectType">
                    <c:forEach items="${projectTypes}" var="projectType">
                        <option value="${projectType}">${projectType}</option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
    </table>
 
    <button class="button-success pure-button" type="submit" name="submit"><i class="fa fa-plus"></i> <span><spring:message code="general.createproject.button.createproject"/></span></button>
    <a href="<c:url value="/web"/>" class="button-error pure-button"><i class="fa fa-times"></i> <span><spring:message code="general.createproject.button.cancel"/></span></a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>
<script>
    $("#projectNameInput").attr('required', '');
</script>