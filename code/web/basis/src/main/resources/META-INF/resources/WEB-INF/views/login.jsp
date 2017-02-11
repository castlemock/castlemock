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
        <link rel="stylesheet" href="<c:url value="/resources/css/font-awesome/css/font-awesome.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/login-stylesheet.css"/>">
    </head>
    <body onload='document.loginForm.username.focus();'>


        <section id="login-box">

            <img src="<c:url value="/resources/images/cm-logo.png"/>" id="logo"/>
            <div id="logo-text">Castle Mock</div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">${error}</div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class="alert alert-success" role="alert">${msg}</div>
            </c:if>

            <form name='loginForm' action="<c:url value='/login' />" method='POST'>
                <input type="text" name="username" id="username" placeholder="<spring:message code="general.login.input.alias"/>"/>
                <input type="password" name="password" id="password" placeholder="<spring:message code="general.login.input.password"/>"/>
                <p><spring:message code="general.login.label.rememberme"/> <input type="checkbox" name="remember-me" /></p>
                <p><button class="btn btn-success" type="submit" name="submit"><spring:message code="general.login.button.login"/> <i class="fa fa-sign-in"></i></button></p>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>

        </section>

        <jsp:include page="footer.jsp" />
    </body>
</html>