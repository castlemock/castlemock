<%@ include file="../../../includes.jspf"%>
<c:url var="create_user_url"  value="/web/user/create" />
<div class="content-top">
<h1><spring:message code="general.useroverview.header.users"/></h1>
</div>
<fieldset>
    <legend><spring:message code="general.useroverview.field.createuser"/></legend>
    <form:form action="${create_user_url}" method="POST">
        <table class="formTable">
            <tr>
                <td class="column1"><form:label path="username"><spring:message code="general.useroverview.label.username"/></form:label></td>
                <td class="column2"><form:input path="username" id="userUsernameInput" />
            </tr>
            <tr>
                <td class="column1"><form:label path="password"><spring:message code="general.useroverview.label.password"/></form:label></td>
                <td class="column2"><form:password path="password" id="userPasswordInput"/>
            </tr>
            <tr>
                <td class="column1"><form:label path="email"><spring:message code="general.useroverview.label.email"/></form:label></td>
                <td class="column2"><form:input path="email" />
            </tr>
            <tr>
                <td class="column1"><spring:message code="general.useroverview.label.role"/></td>
                <td>
                    <form:select path="role">
                        <c:forEach items="${roles}" var="role">
                            <option value="${role}"><spring:message code="general.type.role.${role}"/></option>
                        </c:forEach>
                    </form:select>
                </td>
            </tr>
        </table>
            <button class="button-success pure-button" type="submit" name="submit"><i class="fa fa-plus"></i> <span><spring:message code="general.useroverview.button.createuser"/></span></button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form:form>
</fieldset>
<h2 class="decorated"><span><spring:message code="general.useroverview.header.usertable"/></span></h2>
<table class="entityTable">
    <tr>
        <th><spring:message code="general.useroverview.column.name"/></th>
        <th><spring:message code="general.useroverview.column.email"/></th>
        <th><spring:message code="general.useroverview.column.role"/></th>
        <th><spring:message code="general.useroverview.column.status"/></th>
        <th><spring:message code="general.useroverview.column.created"/></th>
        <th><spring:message code="general.useroverview.column.updated"/></th>
    </tr>
    <c:forEach items="${users}" var="user" varStatus="loopStatus">
        <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
            <td><a href="<c:url value="/web/user/${user.id}"/>">${user.username}</a></td>
            <td><a href="<c:url value="/web/user/${user.id}"/>">${user.email}</a></td>
            <td><spring:message code="general.type.role.${user.role}"/></td>
            <td>${user.status}</td>
            <td>${user.created}</td>
            <td>${user.updated}</td>
        </tr>
    </c:forEach>
</table>
<script>
    $("#userUsernameInput").attr('required', '');
    $("#userPasswordInput").attr('required', '');
</script>
