<%@ include file="../../../includes.jspf"%>
<c:url var="update_user_url"  value="/web/user/${command.id}/update" />
<div class="content-top">
    <h1><spring:message code="general.updateuser.header.updateuser" arguments="${command.username}"/></h1>
</div>
<form:form action="${update_user_url}" method="POST">
    <table class="formTable">
        <tr>
            <td class="column1"><form:label path="username"><spring:message code="general.updateuser.label.username"/></form:label></td>
            <td class="column2"><form:input path="username" id="userUsernameInput" />
        </tr>
        <tr>
            <td class="column1"><form:label path="password"><spring:message code="general.updateuser.label.password"/></form:label></td>
            <td class="column2"><form:password path="password" id="usePasswordInput" />
        </tr>
        <tr>
            <td class="column1"><form:label path="email"><spring:message code="general.updateuser.label.email"/></form:label></td>
            <td class="column2"><form:input path="email" />
        </tr>
        <tr>
            <td class="column1"><b><spring:message code="general.updateuser.label.status"/></b></td>
            <td>
                <form:select path="status">
                    <c:forEach items="${userStatuses}" var="userStatus">
                        <spring:message var="label" code="general.type.status.${userStatus}"/>
                        <form:option value="${userStatus}" label="${label}"/>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
        <tr>
            <td class="column1"><b><spring:message code="general.updateuser.label.role"/></b></td>
            <td>
                <form:select path="role">
                    <c:forEach items="${roles}" var="role">
                        <spring:message var="label" code="general.type.role.${role}"/>
                        <form:option value="${role}" label="${label}"/>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
    </table>
    <button class="button-success pure-button" type="submit" name="submit"><i class="fa fa-check-circle"></i><spring:message code="general.updateuser.button.updateuser"/></button>
    <a href="<c:url value="/web/user/${command.id}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="general.updateuser.button.discardchanges"/></a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    
</form:form>
<script>
    $("#userUsernameInput").attr('required', '');
</script>
