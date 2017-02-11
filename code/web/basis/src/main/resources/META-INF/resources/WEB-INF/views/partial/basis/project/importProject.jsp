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
<c:url var="import_project"  value="/web/project/import" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li class="active"><spring:message code="general.importproject.header.importproject"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="general.importproject.header.importproject"/></h1>
        </div>

        <div class="upload-file">
            <form:form method="POST" enctype="multipart/form-data" action="${import_project}" modelAttribute="uploadForm">
                <table class="formTable">
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
                <input type="file" id="files" name="files" multiple="multiple"/>

                <div id="messages">
                </div>
                <br/>

                <button class="btn btn-success" name="type" value="file"><i class="fa fa-upload"></i> <span><spring:message code="general.importproject.button.uploadfiles"/></span></button>
                <a href="<c:url value="/web"/>" class="btn btn-primary"><i class="fa fa-times"></i> <spring:message code="general.importproject.button.cancel"/></a>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form:form>
        </div>
        <script src=<c:url value="/resources/js/importProject.js"/>></script>
    </section>
</div>