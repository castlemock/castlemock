<%@ include file="../../../includes.jspf"%>
<div class="content-top">
    <h1><spring:message code="general.configuration.header.configuration"/></h1>
</div>
<c:choose>
    <c:when test="${configurationGroup.size() > 0}">
        <form:form action="configuration/update" method="POST"  modelAttribute="configurationUpdateCommand">
                <c:forEach items="configurationGroups" var="configurationGroup" varStatus="configurationGroupLoop">
                    <div>
                        <c:set var="groupName" value="${configurationUpdateCommand.configurationGroups[configurationGroupLoop.index].name}"/>
                        <form:hidden path="configurationGroups[${configurationGroupLoop.index}].id" />
                        <fieldset>
                            <legend><spring:message code="general.configuration.group.${groupName}"/></legend>
                            <table class="formTable">
                                <c:forEach items="configurationGroup.configurations" var="configuration" varStatus="configurationLoop">
                                    <c:set var="key" value="${configurationUpdateCommand.configurationGroups[configurationGroupLoop.index].configurations[configurationLoop.index].key}"/>
                                    <form:hidden path="configurationGroups[${configurationGroupLoop.index}].configurations[${configurationLoop.index}].key" />
                                    <tr>
                                        <td class="column1"><label path="key"><spring:message code="general.configuration.group.${groupName}.${key}"/></label></td>
                                        <td class="column2"><form:input path="configurationGroups[${configurationGroupLoop.index}].configurations[${configurationLoop.index}].value"  />
                                    </tr>
                                </c:forEach>
                            </table>
                        </fieldset>
                    </div>
                    <br/>
                </c:forEach>
            <button class="button-success pure-button" type="submit" name="submit"><i class="fa fa-plus"></i> <span><spring:message code="general.configuration.button.save"/></span></button>
            <a href="<c:url value="/web"/>" class="button-secondary pure-button"><i class="fa fa-check-circle"></i> <spring:message code="general.configuration.button.discard"/></a>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        </form:form>
    </c:when>
</c:choose>