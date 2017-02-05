<%--
 Copyright 2016 Karl Dahlgren

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>

<%@ include file="../../../includes.jspf"%>
<c:url var="update_current_user_url"  value="/web/me/update" />
<div class="content-top">
    <h1><spring:message code="general.updatecurrentuser.header.updateuser" arguments="${command.user.username}"/></h1>
</div>
<form:form action="${update_current_user_url}" method="POST">
    <table class="formTable">
        <tr>
            <td class="column1"><label><spring:message code="general.updatecurrentuser.label.username"/></label></td>
            <td class="column2"><form:input path="user.username" id="userUsernameInput"/>
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
    <button class="btn btn-success" type="submit" name="submit"><i class="fa fa-check-circle"></i><spring:message code="general.updatecurrentuser.button.updateuser"/></button>
    <a href="<c:url value="/web/me"/>" class="btn btn-primary"><i class="fa fa-times"></i> <spring:message code="general.updatecurrentuser.button.discardchanges"/></a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>
<script>
    $("#userUsernameInput").attr('required', '');
</script>
