<%@ include file="../../../../includes.jspf"%>
<c:url var="update_resources_endpoint_url"  value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/update/confirm" />
<div class="content-top">
    <h1><spring:message code="rest.updaterestresourcesendpoint.header.updateresource"/></h1>
</div>
<c:choose>
    <c:when test="${restResources.size() > 0}">
        <p><spring:message code="rest.updaterestresourcesendpoint.label.confirmation"/></p>
        <form:form action="${update_resources_endpoint_url}" method="POST" commandName="updateRestResourcesEndpointCommand">
            <ul>
                <c:forEach items="${restResources}" var="restResource" varStatus="loopStatus">
                    <li>${restResource.name}</li>
                    <form:hidden path="restResources[${loopStatus.index}].id" value="${restResource.id}"/>
                </c:forEach>
            </ul>
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="forwardedEndpoint"><spring:message code="rest.updaterestresourcesendpoint.label.forwardedendpoint"/></label></td>
                    <td class="column2"><form:input path="forwardedEndpoint" value="${updateRestResourcesEndpointCommand.forwardedEndpoint}"/></td>
                </tr>
            </table>
            <button class="button-success pure-button"><i class="fa fa-check-circle"></i> <span><spring:message code="rest.updaterestresourcesendpoint.button.updateresources"/></span></button>
            <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}"/>" class="button-secondary pure-button"><i class="fa fa-check-circle"></i> <spring:message code="rest.updaterestresourcesendpoint.button.cancel"/></a>
        </form:form>
    </c:when>
    <c:otherwise>
        <p><spring:message code="rest.updaterestresourcesendpoint.label.noresources"/></p>
        <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}"/>" class="button-secondary pure-button"><i class="fa fa-check-circle"></i> <spring:message code="rest.updaterestresourcesendpoint.button.cancel"/></a>
    </c:otherwise>
</c:choose>