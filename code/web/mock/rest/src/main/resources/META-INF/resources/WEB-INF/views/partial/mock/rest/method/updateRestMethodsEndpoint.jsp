<%@ include file="../../../../includes.jspf"%>
<c:url var="update_methods_endpoint_url"  value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}/method/update/confirm" />
<div class="content-top">
    <h1><spring:message code="rest.updaterestmethodsendpoint.header.updatemethod"/></h1>
</div>
<c:choose>
    <c:when test="${restMethods.size() > 0}">
        <p><spring:message code="rest.updaterestmethodsendpoint.label.confirmation"/></p>
        <form:form action="${update_methods_endpoint_url}" method="POST" commandName="updateRestMethodsEndpointCommand">
            <ul>
                <c:forEach items="${restMethods}" var="restMethod" varStatus="loopStatus">
                    <li>${restMethod.name}</li>
                    <form:hidden path="restMethods[${loopStatus.index}].id" value="${restMethod.id}"/>
                </c:forEach>
            </ul>
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="forwardedEndpoint"><spring:message code="rest.updaterestmethodsendpoint.label.forwardedendpoint"/></label></td>
                    <td class="column2"><form:input path="forwardedEndpoint" value="${updateRestMethodsEndpointCommand.forwardedEndpoint}"/></td>
                </tr>
            </table>
            <button class="button-success pure-button"><i class="fa fa-check-circle"></i> <span><spring:message code="rest.updaterestmethodsendpoint.button.updatemethods"/></span></button>
            <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}"/>" class="button-secondary pure-button"><i class="fa fa-check-circle"></i> <spring:message code="rest.updaterestmethodsendpoint.button.cancel"/></a>
        </form:form>
    </c:when>
    <c:otherwise>
        <p><spring:message code="rest.updaterestmethodsendpoint.label.nomethods"/></p>
        <a href="<c:url value="/web/rest/project/${restProjectId}/application/${restApplicationId}/resource/${restResourceId}"/>" class="button-secondary pure-button"><i class="fa fa-check-circle"></i> <spring:message code="rest.updaterestmethodsendpoint.button.cancel"/></a>
    </c:otherwise>
</c:choose>