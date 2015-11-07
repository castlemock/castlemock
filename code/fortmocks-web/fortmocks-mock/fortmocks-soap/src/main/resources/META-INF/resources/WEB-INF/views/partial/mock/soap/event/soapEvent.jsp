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
    </table>
</div>

<div>
    <h2 class="decorated"><span><spring:message code="soap.event.header.request"/></span></h2>
    <div class="eventMessage">
        <textarea id="requestBody" readonly><c:out value="${event.soapRequest.body}"/></textarea>
    </div>
</div>

<div>
    <h2 class="decorated"><span><spring:message code="soap.event.header.response"/></span></h2>
    <div class="eventMessage">
        <textarea id="responseBody" readonly><c:out value="${event.soapResponse.body}"/></textarea>
    </div>
</div>

