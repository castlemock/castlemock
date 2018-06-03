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
<c:url var="add_wsdl_url"  value="/web/soap/project/${soapProjectId}/add/wsdl" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/soap/project/${soapProjectId}"><spring:message code="soap.breadcrumb.project"/></a></li>
        <li class="active"><spring:message code="soap.soapaddwsdl.header.uploadfile" arguments="WSDL"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="soap.soapaddwsdl.header.uploadfile" arguments="WSDL"/></h1>
        </div>

        <div class="upload-information">
            <i class="fa fa-info-circle fa-4x"></i>
            <p>
                <spring:message code="soap.soapaddwsdl.header.description"/>
            </p>
        </div>

        <div class="upload-link">
            <h2 class="decorated"><span><spring:message code="soap.soapaddwsdl.header.link" arguments="WSDL"/></span></h2>
            <form:form method="POST" action="${add_wsdl_url}" modelAttribute="uploadForm">
                <table class="formTable">
                    <tr>
                        <td class="column1"><form:label path="link"><spring:message code="soap.soapaddwsdl.label.link" arguments="WSDL"/></form:label></td>
                        <td class="column2"><form:input class="form-control" path="link" type="text" name="wsdlLinkInput" id="wsdlLinkInput" size="100"/>
                    </tr>
                    <tr>
                        <td class="column1"><form:label path="generateResponse"><spring:message code="soap.soapaddwsdl.label.generateresponse"/></form:label></td>
                        <td class="column1"><form:checkbox class="form-control" path="generateResponse" title="Generate response"></form:checkbox></td>
                    </tr>
                </table>
                <button class="btn btn-success" type="submit" name="type" value="link"><i class="fa fa-upload"></i> <span><spring:message code="soap.soapaddwsdl.button.linkwsdl"/></span></button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form:form>
        </div>

        <h2 class="decorated"><span><spring:message code="soap.soapaddwsdl.header.upload" arguments="WSDL"/></span></h2>

        <div class="upload-file">
            <form:form method="POST" enctype="multipart/form-data" action="${add_wsdl_url}" modelAttribute="uploadForm">

                <input type="file" id="files" name="files"/>

                <div id="messages">
                </div>

                <table class="formTable">
                    <tr>
                        <td class="column1"><form:label path="generateResponse"><spring:message code="soap.soapaddwsdl.label.generateresponse"/></form:label></td>
                        <td class="column1"><form:checkbox class="form-control" path="generateResponse" title="Generate response"></form:checkbox></td>
                    </tr>
                </table>


                <button class="btn btn-success" name="type" value="file"><i class="fa fa-upload"></i> <span><spring:message code="soap.soapaddwsdl.button.uploadfiles"/></span></button>
                <a href="<c:url value="/web/soap/project/${soapProjectId}"/>" class="btn btn-primary"><i class="fa fa-times"></i> <spring:message code="soap.soapaddwsdl.button.cancel"/></a>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form:form>
        </div>
    </section>
</div>
<script src=<c:url value="/resources/js/WSDLFileUpload.js"/>></script>
<script>
    $("#wsdlLinkInput").attr('required', '');
</script>
