<%@ include file="includes.jspf"%>
<%@page session="true"%>
<html>
    <head>
        <title><spring:message code="general.login.title.title"/></title>
        <link rel="icon" href="<c:url value="/favicon.ico"/>" type="image/x-icon" />
        <link rel="stylesheet" href="<c:url value="/resources/css/pure/pure-min.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/font-awesome/css/font-awesome.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/login-stylesheet.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/flags/flags.css"/>">
    </head>
    <body onload='document.loginForm.username.focus();'>


        <section id="login-box">

            <img src="<c:url value="/resources/images/fm-logo.png"/>" id="logo"/>

            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class="msg">${msg}</div>
            </c:if>

            <form name='loginForm' action="<c:url value='/login' />" method='POST'>
                <input type="text" name="username" id="username" placeholder="<spring:message code="general.login.input.alias"/>" size="40"/>
                <input type="password" name="password" id="password" placeholder="<spring:message code="general.login.input.password"/>" size="40"/>
                <p><spring:message code="general.login.label.rememberme"/> <input type="checkbox" name="remember-me" /></p>
                <p><button class="button-success pure-button" type="submit" name="submit"><spring:message code="general.login.button.login"/> <i class="fa fa-sign-in"></i></button></p>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>

        </section>

        <jsp:include page="footer.jsp" />
    </body>
</html>