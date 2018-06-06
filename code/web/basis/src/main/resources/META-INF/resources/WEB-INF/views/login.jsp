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
<%@page session="true"%>
<html>
    <head>
        <title><spring:message code="general.login.title.title"/></title>
        <link rel="icon" href="<c:url value="/favicon.ico"/>" type="image/x-icon" />
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/font-awesome/css/fontawesome-all.min.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/login-stylesheet.css"/>">
    </head>
    <body onload='document.loginForm.username.focus();'>


        <section id="login-box">

            <img src="<c:url value="/resources/images/cm-logo.png"/>" id="logo"/>
            <div id="logo-title">Castle Mock</div>
            <div id="logo-meta-text">Login with your Castle Mock ID</div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">${error}</div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class="alert alert-success" role="alert">${msg}</div>
            </c:if>

            <form name='loginForm' action="<c:url value='/login' />" method='POST'>
                <input class="form-control login-credentials" type="text" name="username" id="username" placeholder="<spring:message code="general.login.input.username"/>"/>
                <input class="form-control login-credentials" type="password" name="password" id="password" placeholder="<spring:message code="general.login.input.password"/>"/>
                <div id="login-remember-me">
                    <input class="form-check-input" id="rememberMeCheck" type="checkbox" name="remember-me" />
                    <label class="form-check-label" for="rememberMeCheck"><spring:message code="general.login.label.rememberme"/></label>
                </div>
                <div id="login-button">
                    <button class="btn btn-success" type="submit" name="submit"><spring:message code="general.login.button.login"/></button>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>

        </section>

        <footer>
            <div class="info" style="float: right;">
                <c:choose>
                    <c:when test="${demoMode}">
                        <a href="https://www.castlemock.com" target="_blank">Castle Mock version. ${appVersion} (Demo)</a>
                    </c:when>
                    <c:otherwise>
                        <a href="https://www.castlemock.com" target="_blank">Castle Mock version. ${appVersion}</a>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="rest-api" style="float: right; width: 80px;">
                <a href="<c:url value="/doc/api/rest"/>" target="_blank">REST API</a>
            </div>

        </footer>
    </body>
</html>