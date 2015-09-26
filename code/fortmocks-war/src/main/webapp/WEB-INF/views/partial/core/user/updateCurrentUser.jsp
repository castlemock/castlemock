<%@ include file="../../../includes.jspf"%>
<c:url var="update_current_user_url"  value="/web/me/update" />
<div class="content-top">
    <h1><spring:message code="general.updatecurrentuser.header.updateuser" arguments="${command.user.username}"/></h1>
</div>
<form:form action="${update_current_user_url}" method="POST">
    <table class="formTable">
        <tr>
            <td class="column1"><label><spring:message code="general.updatecurrentuser.label.username"/></label></td>
            <td class="column2"><form:input path="user.username"/>
        </tr>
        <tr>
            <td class="column1"><label><spring:message code="general.updatecurrentuser.label.email"/></label></td>
            <td class="column2"><form:input path="user.email"  />
        </tr>
        <tr>
            <td class="column1"><label><spring:message code="general.updatecurrentuser.label.password"/></label></td>
            <td class="column2"><form:password path="user.password" />
        </tr>
        <tr>
            <td class="column1"><label><spring:message code="general.updatecurrentuser.label.verifypassword"/></label></td>
            <td class="column2"><form:password path="verifiedPassword" />
        </tr>
    </table>
    <button class="button-success pure-button" type="submit" name="submit"><spring:message code="general.updatecurrentuser.button.updateuser"/></button>
    <a href="<c:url value="/web/me"/>" class="button-secondary pure-button"><i class="fa fa-check-circle"></i> <spring:message code="general.updatecurrentuser.button.discardchanges"/></a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>
