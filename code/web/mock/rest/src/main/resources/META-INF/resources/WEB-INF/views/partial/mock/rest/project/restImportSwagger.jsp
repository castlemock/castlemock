<%@ include file="../../../../includes.jspf"%>
<%--
  ~ Copyright 2017 Karl Dahlgren
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~   http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<c:url var="add_swagger_url"  value="/web/rest/project/${restProjectId}/import/swagger" />
<div class="content-top">
    <h1><spring:message code="rest.restimportswagger.header.uploadfile" arguments="Swagger"/></h1>
</div>

<div class="upload-information">
    <i class="fa fa-info-circle fa-4x"></i>
    <p>
        <spring:message code="rest.restimportswagger.header.description"/>
    </p>
</div>

<div class="upload-link">
    <h2 class="decorated"><span><spring:message code="rest.restimportswagger.header.link" arguments="Swagger"/></span></h2>
    <form:form method="POST" action="${add_swagger_url}" modelAttribute="uploadForm">
        <table class="formTable">
            <tr>
                <td class="column1"><form:label path="link"><spring:message code="rest.restimportswagger.label.link" arguments="Swagger"/></form:label></td>
                <td class="column2"><form:input path="link" type="text" name="swaggerLinkInput" id="swaggerLinkInput" size="100"/>
            </tr>
            <tr>
                <td class="column1"><form:label path="generateResponse"><spring:message code="rest.restimportswagger.label.generateresponse"/></form:label></td>
                <td class="column1"><form:checkbox path="generateResponse" title="Generate response"></form:checkbox></td>
            </tr>
        </table>
        <button class="button button-success pure-button" type="submit" name="type" value="link"><i class="fa fa-upload"></i> <span><spring:message code="rest.restimportswagger.button.linkswagger"/></span></button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form:form>
</div>

<h2 class="decorated"><span><spring:message code="rest.restimportswagger.header.upload" arguments="WADL"/></span></h2>

<div class="upload-file">
    <form:form method="POST" enctype="multipart/form-data" action="${add_swagger_url}" modelAttribute="uploadForm">
        <input type="file" id="files" name="files" multiple="multiple"/>

        <div id="messages">
        </div>

        <table class="formTable">
            <tr>
                <td class="column1"><form:label path="generateResponse"><spring:message code="soap.soapaddwsdl.label.generateresponse"/></form:label></td>
                <td class="column1"><form:checkbox path="generateResponse" title="Generate response"></form:checkbox></td>
            </tr>
        </table>


        <button class="button-success pure-button" name="type" value="file"><i class="fa fa-upload"></i> <span><spring:message code="rest.restimportswagger.button.uploadfiles"/></span></button>
        <a href="<c:url value="/web/rest/project/${restProjectId}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="rest.restimportswagger.button.cancel"/></a>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form:form>
</div>

<script src=<c:url value="/resources/js/WADLFileUpload.js"/>>
</script>
<script>
    $("#swaggerLinkInput").attr('required', '');
</script>