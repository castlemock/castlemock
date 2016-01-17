<%@ page import="com.fortmocks.core.basis.model.http.dto.HttpHeaderDto" %>
<%@ page import="com.fortmocks.core.mock.soap.model.project.dto.SoapMockResponseDto" %>
<%@ include file="../../../../includes.jspf"%>
<c:url var="update_response_url"  value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}/response/${soapMockResponse.id}/update" />
<div class="content-top">
    <h1><spring:message code="soap.soapmockresponse.header.response" arguments="${soapMockResponse.name}"/></h1>
    <div align="right">
        <sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')">
            <a class="button-error pure-button" href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}/response/${soapMockResponse.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="soap.soapmockresponse.button.delete"/></span></a>
        </sec:authorize>
    </div>
</div>
<form:form action="${update_response_url}" method="POST" commandName="soapMockResponse" name="soapMockResponseForm">
    <div class="content-summary">
        <table class="formTable">
            <tr>
                <td class="column1"><form:label path="name"><spring:message code="soap.soapmockresponse.label.name"/></form:label></td>
                <td class="column2"><form:input path="name" id="soapMockResponseNameInput" /></td>
            </tr>
            <tr>
                <td class="column1"><form:label path="httpStatusCode"><spring:message code="soap.soapmockresponse.label.httpstatuscode"/></form:label></td>
                <td class="column2"><form:input path="httpStatusCode" id="soapMockResponseHttpStatusCodeInput" /></td>
            </tr>
        </table>
    </div>
    <div>
        <h2 class="decorated"><span><spring:message code="soap.soapmockresponse.header.body"/></span></h2>
        <div class="editor">
            <form:textarea id="body" path="body"/>
        </div>
    </div>
    <div>
        <h2 class="decorated"><span><spring:message code="soap.soapmockresponse.header.headers"/></span></h2>

        <fieldset>
            <legend><spring:message code="soap.soapmockresponse.field.addheader"/></legend>
            <table class="formTable">
                <tr>
                    <td class="column1"><form:label path="name"><spring:message code="soap.soapmockresponse.label.headername"/></form:label></td>
                    <td class="column2"><input type="text" name="headerName" id="headerNameInput"></td>
                </tr>
                <tr>
                    <td class="column1"><form:label path="name"><spring:message code="soap.soapmockresponse.label.headervalue"/></form:label></td>
                    <td class="column2"><input type="text" name="headerValue" id="headerValueInput"></td>
                </tr>
            </table>
            <button class="button-success pure-button" onclick="addHeader()" type="button"><i class="fa fa-plus"></i>  <span><spring:message code="soap.soapmockresponse.button.addheader"/></span></button>
        </fieldset>

        <div class="table-frame">
            <table class="entityTable" id="headerTable">
                <tr>
                    <th></th>
                    <th><spring:message code="soap.soapmockresponse.column.headername"/></th>
                    <th><spring:message code="soap.soapmockresponse.column.headervalue"/></th>
                </tr>
                <c:forEach items="${soapMockResponse.httpHeaders}" var="httpHeader" varStatus="loopStatus">
                    <form:hidden path="httpHeaders"/>
                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                        <td><div class="delete" onclick="removeHeader('${httpHeader.name}')"></div></td>
                        <td>${httpHeader.name}</td>
                        <td>${httpHeader.value}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <sec:authorize access="hasRole('ADMIN') or hasRole('MODIFIER')">
        <button class="button-success pure-button" type="submit" name="submit"><i class="fa fa-plus"></i>  <span><spring:message code="soap.soapmockresponse.button.updateresponse"/></span></button>
    </sec:authorize>
    <a href="<c:url value="/web/soap/project/${soapProjectId}/port/${soapPortId}/operation/${soapOperationId}"/>" class="button-error pure-button"><i class="fa fa-times"></i> <span><spring:message code="soap.soapmockresponse.button.discardchanges"/></span></a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>
<script>
    $("#soapMockResponseNameInput").attr('required', '');
    $("#soapMockResponseHttpStatusCodeInput").attr('required', '');
    function findHeader(headerName){
        headerTable = document.getElementById("headerTable");
        for (var index = 1, row; row = headerTable.rows[index]; index++) {
            var tempHeaderName = row.cells[1].innerHTML;
            if(tempHeaderName == headerName){
                return index;
            }
        }
        return -1;
    }

    function addHeader() {
        headerTable = document.getElementById("headerTable");
        headerName = document.getElementById("headerNameInput").value;
        headerValue = document.getElementById("headerValueInput").value;

        index = findHeader(headerName);
        if(index != -1){
            return;
        }

        row = headerTable.insertRow(-1);
        headerSelected = row.insertCell(0);
        headerNameColumn = row.insertCell(1);
        headerValueColumn = row.insertCell(2);
        headerSelected.innerHTML = "<div class=\"delete\" onclick=\"removeHeader(\'' + headerName + '\')\">";
        headerNameColumn.innerHTML = headerName;
        headerValueColumn.innerHTML = headerValue;
    }

    function removeHeader(deleteHeaderName) {
        index = findHeader(deleteHeaderName);
        headerTable.deleteRow(index);
    }

</script>