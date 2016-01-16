<%@ include file="../../../../includes.jspf"%>
<div class="content-top">
    <h1><spring:message code="soap.event.header.log" arguments="${event.id}"/></h1>
</div>

<div class="content-summary">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="soap.event.column.id"/></label></td>
            <td class="column2"><label path="name">${event.id}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="soap.event.column.startdate"/></label></td>
            <td class="column2"><label path="name">${event.startDate}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="soap.event.column.enddate"/></label></td>
            <td class="column2"><label path="name">${event.endDate}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="soap.event.column.request.uri"/></label></td>
            <td class="column2"><label path="name">${event.request.uri}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="soap.event.column.request.methodtype"/></label></td>
            <td class="column2"><label path="name">${event.request.httpMethod}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="soap.event.column.request.type"/></label></td>
            <td class="column2"><label path="name">${event.request.soapVersion}</label></td>
        </tr>
    </table>
</div>

<div>
    <h2 class="decorated"><span><spring:message code="soap.event.header.request"/></span></h2>
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="soap.event.column.request.contenttype"/></label></td>
            <td class="column2"><label path="name">${event.request.contentType}</label></td>
        </tr>
    </table>
    <div class="eventMessage">
        <textarea id="requestBody" readonly><c:out value="${event.request.body}"/></textarea>
    </div>
</div>

<div>
    <h2 class="decorated"><span><spring:message code="soap.event.header.response"/></span></h2>
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="soap.event.column.response.statuscode"/></label></td>
            <td class="column2"><label path="name">${event.response.httpStatusCode}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="soap.event.column.response.contenttype"/></label></td>
            <td class="column2"><label path="name">${event.response.contentType}</label></td>
        </tr>
    </table>
    <div class="eventMessage">
        <textarea id="responseBody" readonly><c:out value="${event.response.body}"/></textarea>
    </div>
</div>

