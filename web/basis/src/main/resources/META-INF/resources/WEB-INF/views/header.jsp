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


                <div class="input-group stylish-input-group">
                    <form:form action="${searchUrl}" method="POST" modelAttribute="searchCommand">
                        <div class="input-group-field">
                            <form:input onfocus="this.placeholder = ''" type="text" path="query" class="form-control search"  placeholder="Search" ></form:input>
                        </div>
                        <span class="input-group-addon">
                            <button type="submit">
                                <span class="fas fa-search"></span>
                            </button>
                        </span>
                    </form:form>
                </div>


            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="links nav navbar-nav navbar-right">

                    <li class="header-menu-full-row">
                        <div>
                            <a href="${context}/web/me">
                                <div class="avatar-circle">
                                    <div class="avatar-inner-circle">
                                        <i class="fas fa-user fa-2x"></i>
                                    </div>
                                </div>
                                <div class="avatar-username">
                                    ${param.loggedInUser}
                                </div>

                            </a>
                        </div>
                    </li>


                    <div class="header-menu-collapse">
                        <li class="header-menu-collapse-row">
                            <a href="${context}/web">
                                <div class="header-menu-icon">
                                    <i class="fas fa-tachometer-alt fa-2x"></i>
                                </div>
                                <div class="header-menu-title">
                                    <spring:message code="general.menu.projects"/>
                                </div>
                            </a>
                        </li>
                        <li class="header-menu-collapse-row">
                            <a href="${context}/web/event">
                                <div class="header-menu-icon">
                                    <i class="fas fa-chart-bar fa-2x"></i>
                                </div>
                                <div class="header-menu-title">
                                    <spring:message code="general.menu.logs"/>
                                </div>
                            </a>
                        </li>

                        <sec:authorize access="hasAuthority('ADMIN')">
                            <li class="header-menu-collapse-row">
                                <a href="${context}/web/user">
                                    <div class="header-menu-icon">
                                        <i class="fas fa-users-cog fa-2x"></i>
                                    </div>
                                    <div class="header-menu-title">
                                        <spring:message code="general.menu.users"/>
                                    </div>
                                </a>
                            </li>

                            <li class="header-menu-collapse-row">
                                <a href="${context}/web/system">
                                    <div class="header-menu-icon">
                                        <i class="fas fas fa-cogs fa-2x"></i>
                                    </div>
                                    <div class="header-menu-title">
                                        <spring:message code="general.menu.system"/>
                                    </div>
                                </a>
                            </li>

                        </sec:authorize>

                        <li class="header-menu-collapse-row">
                            <a href="${context}/web/logout">
                                <div class="header-menu-icon">
                                    <i class="fas fas fa-sign-out-alt fa-2x"></i>
                                </div>
                                <div class="header-menu-title">
                                    <spring:message code="general.menu.logout"/>
                                </div>
                            </a>
                        </li>
                    </div>

                    <li class="header-menu-full-row"><a href="${context}/web/logout"><i class="fas fa-sign-out-alt fa-2x"></i></a></li>
                </ul>
            </div>
        </div>

    </nav>
</header>