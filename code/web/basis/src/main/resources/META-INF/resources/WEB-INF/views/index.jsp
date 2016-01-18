<%@ include file="includes.jspf"%>
<html>
  <head>
      <title><spring:message code="general.index.title.title"/></title>
      <link rel="icon" href="<c:url value="/favicon.ico"/>" type="image/x-icon" />
      <link rel="stylesheet" href="<c:url value="/resources/css/pure/pure-min.css"/>">
      <link rel="stylesheet" href="<c:url value="/resources/css/font-awesome/css/font-awesome.css"/>">
      <link rel="stylesheet" href="<c:url value="/resources/css/main-stylesheet.css"/>">
      <link rel="stylesheet" href="<c:url value="/resources/css/flags/flags.css"/>">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <script type="text/javascript" charset="UTF-8" src="<c:url value="/resources/js/jquery/jquery-2.1.4.min.js"/>"></script>
      <script type="text/javascript" charset="UTF-8" src="<c:url value="/resources/js/main-script.js"/>"></script>
  </head>
    <body>
        <jsp:include page="header.jsp" />
        <jsp:include page="navigation.jsp" />

        <div class="container">
            <jsp:include page="aside.jsp" />
            <section>
                    <jsp:include page="${partial}" />
            </section>
        </div>
        <jsp:include page="footer.jsp" />
    </body>
</html>