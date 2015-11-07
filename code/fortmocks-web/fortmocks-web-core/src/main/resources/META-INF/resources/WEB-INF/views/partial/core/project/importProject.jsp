<%@ include file="../../../includes.jspf"%>
<c:url var="import_project"  value="/web/project/import" />
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


        <div>
            <div id="filedrag">
                <i class="fa fa-cloud-upload fa-5x"></i><br/>
                <spring:message code="general.importproject.label.uploaddescription"/> <input type="file" id="files" name="files" multiple="multiple"/>
            </div>
        </div>

        <div id="messages">
        </div>

        <button class="button-success pure-button" name="type" value="file"><i class="fa fa-upload"></i> <span><spring:message code="general.importproject.button.uploadfiles"/></span></button>
        <a href="<c:url value="/web"/>" class="button-secondary pure-button"><i class="fa fa-check-circle"></i> <spring:message code="general.importproject.button.cancel"/></a>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form:form>
</div>
<script src=<c:url value="/resources/js/FileUpload.js"/>></script>