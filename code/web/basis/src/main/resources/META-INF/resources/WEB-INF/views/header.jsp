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

<%@ include file="includes.jspf"%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:url var="searchUrl"  value="/web/search" />
<spring:message code="general.header.input.search" var="searchPlaceholder"/>
<header class="main-header">

    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar top-bar"></span>
                    <span class="icon-bar middle-bar"></span>
                    <span class="icon-bar bottom-bar"></span>
                </button>
                <a class="navbar-brand logo" href="${context}/web">Castle Mock  <img src="${context}/resources/images/logo-landscape-white.png" id="header-logo"/></a>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="links nav navbar-nav navbar-right">
                    <form:form action="${searchUrl}" method="POST" commandName="searchCommand" class="navbar-form navbar-left search">
                        <div class="form-group">
                            <form:input onfocus="this.placeholder = ''" type="text" path="query" placeholder="${searchPlaceholder}"></form:input>
                        </div>
                    </form:form>
                    <li><a href="${context}/web" data-toggle="tooltip" data-placement="bottom" title="<spring:message code="general.menu.home"/>"><i class="fa fa-home fa-2x"></i><span class="title"> <spring:message code="general.menu.home"/></span></a></li>
                    <li><a href="${context}/web/me" data-toggle="tooltip" data-placement="bottom" title="${loggedInUser}"><i class="fa fa-user fa-2x"></i><span class="title"> ${loggedInUser}</span></a></li>
                    <li><a href="${context}/web/event" data-toggle="tooltip" data-placement="bottom" title="<spring:message code="general.menu.events"/>"><i class="fa fa-file-text fa-2x"></i><span class="title"> <spring:message code="general.menu.events"/></span></a></li>
                    <sec:authorize access="hasAuthority('ADMIN')"><li><a href="${context}/web/configuration" data-toggle="tooltip" data-placement="bottom" title="<spring:message code="general.menu.configuration"/>"><i class="fa fa-cogs fa-2x"></i><span class="title"> <spring:message code="general.menu.configuration"/></span></a></li></sec:authorize>
                    <sec:authorize access="hasAuthority('ADMIN')"><li><a href="${context}/web/user" data-toggle="tooltip" data-placement="bottom" title="<spring:message code="general.menu.users"/>"><i class="fa fa-users fa-2x"></i><span class="title"> <spring:message code="general.menu.users"/></span></a></li></sec:authorize>
                    <sec:authorize access="hasAuthority('ADMIN')"><li><a href="${context}/web/system" data-toggle="tooltip" data-placement="bottom" title="<spring:message code="general.menu.system"/>"><i class="fa fa-desktop fa-2x"></i><span class="title"> <spring:message code="general.menu.system"/></span></a></li></sec:authorize>
                    <li><a href="${context}/web/logout" data-toggle="tooltip" data-placement="bottom" title="<spring:message code="general.menu.logout"/>"><i class="fa fa-sign-out fa-2x"></i><span class="title"> <spring:message code="general.menu.logout"/></span></a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>