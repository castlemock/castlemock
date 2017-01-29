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
        <h1><spring:message code="general.user.header.user" arguments="${user.username}"/></h1>
    </div>
    <div class="menu" align="right">
        <c:choose>
            <c:when test="${demoMode}">
                <a class="button-success pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-file"></i> <span><spring:message code="general.user.button.updateuser"/></span></a>
                <a class="button-error pure-button pure-button-disabled" title="<spring:message code="general.mode.demo.disabled"/>" href="<c:url value="#"/>"><i class="fa fa-trash"></i> <span><spring:message code="general.user.button.deleteuser"/></span></a>
            </c:when>
            <c:otherwise>
                <a class="button-success pure-button" href="<c:url value="/web/user/${user.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="general.user.button.updateuser"/></span></a>
                <a class="button-error pure-button" href="<c:url value="/web/user/${user.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="general.user.button.deleteuser"/></span></a>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<table class="entityTable sortable">
    <tr>
        <td class="column1"><label path="username"><spring:message code="general.user.label.username"/></label></td>
        <td class="column2"><label path="username">${user.username}</label></td>
    </tr>
    <tr>
        <td class="column1"><label path="email"><spring:message code="general.user.label.email"/></label></td>
        <td class="column2"><label path="email">${user.email}</label></td>
    </tr>
    <tr>
        <td class="column1"><label path="status"><spring:message code="general.user.label.status"/></label></td>
        <td class="column2"><label path="status"><spring:message code="general.type.status.${user.status}"/></label></td>
    </tr>
    <tr>
        <td class="column1"><label path="role"><spring:message code="general.user.label.role"/></label></td>
        <td class="column2"><label path="role"><spring:message code="general.type.role.${user.role}"/></label></td>
    </tr>
    <tr>
        <td class="column1"><label path="username"><spring:message code="general.user.label.created"/></label></td>
        <td class="column2"><label path="username">${user.created}</label></td>
    </tr>
    <tr>
        <td class="column1"><label path="updated"><spring:message code="general.user.label.updated"/></label></td>
        <td class="column2"><label path="updated">${user.updated}</label></td>
    </tr>
</table>

