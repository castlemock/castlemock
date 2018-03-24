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

<%@ include file="../includes.jspf"%>
<%@page session="true"%>
<html>
<head>
	<title><spring:message code="general.error.notfound.title"/></title>
	<link rel="icon" href="<c:url value="/favicon.ico"/>" type="image/x-icon" />
	<link rel="stylesheet" href="<c:url value="/resources/css/pure/pure-min.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/font-awesome/css/font-awesome.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/login-stylesheet.css"/>">
	<link rel="stylesheet" href="<c:url value="/resources/css/flags/flags.css"/>">
</head>
<body onload='document.loginForm.username.focus();'>


<section id="login-box">

	<img src="<c:url value="/resources/images/cm-logo.png"/>" id="logo"/>
	<div id="logo-text">Castle Mock</div>

	<div>
		<p><spring:message code="general.error.notfound.message"/></p>
	</div>

	<div id="not-found-button">
		<a href="<c:url value="/login"/>" class="btn btn-success"><spring:message code="general.error.notfound.button"/></a>
	</div>

</section>


<jsp:include page="../footer.jsp" />
</body>
</html>