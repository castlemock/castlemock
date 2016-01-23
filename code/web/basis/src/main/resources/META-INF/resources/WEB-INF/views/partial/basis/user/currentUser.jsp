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
<div class="content-top">
    <div class="title">
        <h1><spring:message code="general.currentuser.header.user" arguments="${user.username}"/></h1>
    </div>
    <div class="menu" align="right">
        <a class="button-success pure-button" href="<c:url value="/web/me/update"/>"><i class="fa fa-file"></i> <span><spring:message code="general.currentuser.button.updateuser"/></span></a>
    </div>
</div>
<table class="entityTable">
    <tr>
        <td class="column1"><label path="username"><spring:message code="general.currentuser.label.username"/></label></td>
        <td class="column2"><label path="username">${user.username}</label></td>
    </tr>
    <tr>
        <td class="column1"><label path="email"><spring:message code="general.currentuser.label.email"/></label></td>
        <td class="column2"><label path="email">${user.email}</label></td>
    </tr>
    <tr>
        <td class="column1"><label path="status"><spring:message code="general.currentuser.label.status"/></label></td>
        <td class="column2"><label path="status">${user.status}</label></td>
    </tr>
    <tr>
        <td class="column1"><label path="role"><spring:message code="general.currentuser.label.role"/></label></td>
        <td class="column2"><label path="role">${user.role}</label></td>
    </tr>
    <tr>
        <td class="column1"><label path="username"><spring:message code="general.currentuser.label.created"/></label></td>
        <td class="column2"><label path="username">${user.created}</label></td>
    </tr>
    <tr>
        <td class="column1"><label path="updated"><spring:message code="general.currentuser.label.updated"/></label></td>
        <td class="column2"><label path="updated">${user.updated}</label></td>
    </tr>
</table>

