<%@ include file="includes.jspf"%>
<c:url value="/logout" var="logoutUrl" />
<form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}" />
</form>
<html>
<head>
    <title>Logging out</title>
    <script>
        function logout() {
            document.getElementById("logoutForm").submit();
        }
    </script>
</head>
<body onload="logout()">
</body>
</html>