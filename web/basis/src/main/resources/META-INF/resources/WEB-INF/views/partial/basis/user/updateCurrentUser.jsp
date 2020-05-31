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
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li><a href="${context}/web/user"><spring:message code="general.breadcrumb.users"/></a></li>
        <li><a href="${context}/web/me">${command.user.username}</a></li>
        <li class="active"><spring:message code="general.updatecurrentuser.header.updateuser" arguments="${command.user.username}"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
            <h1><spring:message code="general.updatecurrentuser.header.updateuser" arguments="${command.user.username}"/></h1>
        </div>
        <form:form action="${update_current_user_url}" method="POST">
            <table class="formTable">
                <tr>
                    <td class="column1"><label><spring:message code="general.updatecurrentuser.label.username"/></label></td>
                    <td class="column2"><form:input class="form-control" path="user.username" id="userUsernameInput"/>
                </tr>

                <tr>
                    <td class="column1"><label><spring:message code="general.updatecurrentuser.label.fullName"/></label></td>
                    <td class="column2"><form:input class="form-control" path="user.fullName" id="userFullNameInput"/>
                </tr>

                <tr>
                    <td class="column1"><label><spring:message code="general.updatecurrentuser.label.email"/></label></td>
                    <td class="column2"><form:input class="form-control" path="user.email"  />
                </tr>
                <tr>
                    <td class="column1"><label><spring:message code="general.updatecurrentuser.label.password"/></label></td>
                    <td class="column2"><form:password class="form-control" path="user.password" />
                </tr>
                <tr>
                    <td class="column1"><label><spring:message code="general.updatecurrentuser.label.verifypassword"/></label></td>
                    <td class="column2"><form:password class="form-control" path="verifiedPassword" />
                </tr>
            </table>
            <button class="btn btn-success" type="submit" name="submit"><i class="fas fa-check-circle"></i><spring:message code="general.updatecurrentuser.button.updateuser"/></button>
            <a href="<c:url value="/web/me"/>" class="btn btn-primary"><i class="fas fa-times"></i> <spring:message code="general.updatecurrentuser.button.discardchanges"/></a>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>
        <script>
            $("#userUsernameInput").attr('required', '');
        </script>
    </section>
</div>