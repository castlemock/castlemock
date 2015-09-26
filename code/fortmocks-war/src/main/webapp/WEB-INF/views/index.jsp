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
      <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js" charset="UTF-8"></script>
      <script type="text/javascript" charset="UTF-8" src="<c:url value="/resources/js/main-script.js"/>"></script>
        <style type="text/css" media="screen">
            .ace_editor {
                position: relative !important;
                border: 1px solid lightgray;
                margin: auto;
                height: 200px;
                width: 100%;
            }
            .scrollmargin {
                height: 100px;
                text-align: center;
            }
        </style>
  </head>
    <body>
        <jsp:include page="header.jsp" />

        <div class="container">
            <jsp:include page="navigation.jsp" />
            <jsp:include page="aside.jsp" />
            <section>
                    <jsp:include page="${partial}" />
            </section>
        </div>
        <jsp:include page="footer.jsp" />
    </body>
</html>