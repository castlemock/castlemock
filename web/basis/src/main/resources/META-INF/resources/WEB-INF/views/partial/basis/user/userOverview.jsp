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
<c:url var="create_user_url"  value="/web/user/create" />
<div class="navigation">
    <ol class="breadcrumb">
        <li><a href="${context}/web"><spring:message code="general.breadcrumb.home"/></a></li>
        <li class="active"><spring:message code="general.useroverview.header.users"/></li>
    </ol>
</div>
<div class="container">
    <section>
        <div class="content-top">
        <h1><spring:message code="general.useroverview.header.users"/></h1>
        </div>
        <div class="panel panel-primary table-panel">
            <div class="panel-heading table-panel-heading">
                <h3 class="panel-title"><spring:message code="general.useroverview.field.createuser"/></h3>
            </div>
            <div class="panel-body">
                <form:form action="${create_user_url}" method="POST">
                    <table class="formTable">
                        <tr>
                            <td class="column1"><form:label path="username"><spring:message code="general.useroverview.label.username"/></form:label></td>
                            <td class="column2"><form:input class="form-control" path="username" id="userUsernameInput" />
                        </tr>
                        <tr>
                            <td class="column1"><form:label path="password"><spring:message code="general.useroverview.label.password"/></form:label></td>
                            <td class="column2"><form:password class="form-control" path="password" id="userPasswordInput"/>
                        </tr>
                        <tr>
                            <td class="column1"><form:label path="email"><spring:message code="general.useroverview.label.email"/></form:label></td>
                            <td class="column2"><form:input class="form-control" path="email" />
                        </tr>
                        <tr>
                            <td class="column1"><spring:message code="general.useroverview.label.role"/></td>
                            <td>
                                <form:select class="form-control" path="role">
                                    <c:forEach items="${roles}" var="role">
                                        <option value="${role}"><spring:message code="general.type.role.${role}"/></option>
                                    </c:forEach>
                                </form:select>
                            </td>
                        </tr>
                    </table>
                    <button class="btn btn-success demo-button-disabled" type="submit" name="submit"><i class="fas fa-plus-circle"></i> <span><spring:message code="general.useroverview.button.createuser"/></span></button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form:form>
            </div>
        </div>
        <h2 class="decorated"><span><spring:message code="general.useroverview.header.usertable"/></span></h2>
        <div class="table-responsive">
            <table class="table table-striped table-hover sortable">
                <tr>
                    <th><spring:message code="general.useroverview.column.name"/></th>
                    <th><spring:message code="general.useroverview.column.email"/></th>
                    <th><spring:message code="general.useroverview.column.role"/></th>
                    <th><spring:message code="general.useroverview.column.status"/></th>
                    <th><spring:message code="general.useroverview.column.created"/></th>
                    <th><spring:message code="general.useroverview.column.updated"/></th>
                </tr>
                <c:forEach items="${users}" var="user" varStatus="loopStatus">
                    <tr>
                        <td><a href="<c:url value="/web/user/${user.id}"/>">${user.username}</a></td>
                        <td><a href="<c:url value="/web/user/${user.id}"/>">${user.email}</a></td>
                        <td><spring:message code="general.type.role.${user.role}"/></td>
                        <td><spring:message code="general.type.status.${user.status}"/></td>
                        <td>${user.created}</td>
                        <td>${user.updated}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </section>
</div>
<script>
    $("#userUsernameInput").attr('required', '');
    $("#userPasswordInput").attr('required', '');
</script>
