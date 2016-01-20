<%@ include file="../../../../includes.jspf"%>
<c:url var="add_wsdl_url"  value="/web/soap/project/${soapProjectId}/add/wsdl" />
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
                <td class="column2"><form:input path="link" type="text" name="wsdlLinkInput" id="wsdlLinkInput" size="100"/>
            </tr>
            <tr>
                <td class="column1"><form:label path="generateResponse"><spring:message code="soap.soapaddwsdl.label.generateresponse"/></form:label></td>
                <td class="column1"><form:checkbox path="generateResponse" title="Generate response"></form:checkbox></td>
            </tr>
        </table>
        <button class="button button-success pure-button" type="submit" name="type" value="link"><i class="fa fa-upload"></i> <span><spring:message code="soap.soapaddwsdl.button.linkwsdl"/></span></button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form:form>
</div>

<h2 class="decorated"><span><spring:message code="soap.soapaddwsdl.header.upload" arguments="WSDL"/></span></h2>

<div class="upload-file">
    <form:form method="POST" enctype="multipart/form-data" action="${add_wsdl_url}" modelAttribute="uploadForm">

        <input type="file" id="files" name="files" multiple="multiple"/>

        <div id="messages">
        </div>

        <table class="formTable">
            <tr>
                <td class="column1"><form:label path="generateResponse"><spring:message code="soap.soapaddwsdl.label.generateresponse"/></form:label></td>
                <td class="column1"><form:checkbox path="generateResponse" title="Generate response"></form:checkbox></td>
            </tr>
        </table>


        <button class="button-success pure-button" name="type" value="file"><i class="fa fa-upload"></i> <span><spring:message code="soap.soapaddwsdl.button.uploadfiles"/></span></button>
        <a href="<c:url value="/web/soap/project/${soapProjectId}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="soap.soapaddwsdl.button.cancel"/></a>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form:form>
</div>

<script src=<c:url value="/resources/js/WSDLFileUpload.js"/>></script>
<script>
    $("#wsdlLinkInput").attr('required', '');
</script>
