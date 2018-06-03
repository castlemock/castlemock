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
                    <form:input onfocus="this.placeholder = ''" type="text" path="query" class="form-control searchNew"  placeholder="Search" ></form:input>
                    <span class="input-group-addon">
                        <button type="submit">
                            <span class="fa fa-search"></span>
                        </button>
                    </span>
                </form:form>
            </div>


            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="links nav navbar-nav navbar-right">
                    <li>
                        <div>
                            <a href="${context}/web/me">
                                <div class="avatar-username">
                                </div>
                                <div class="avatar-circle">
                                    <span class="initials"><c:out value="${fn:substring(loggedInUser, 0, 1)}" /> </span>
                                </div>
                            </a>
                        </div>
                    </li>
                    <li><a href="${context}/web/logout"><i class="fa fa-sign-out fa-2x"></i></a></li>
                </ul>
            </div>
        </div>

    </nav>
</header>